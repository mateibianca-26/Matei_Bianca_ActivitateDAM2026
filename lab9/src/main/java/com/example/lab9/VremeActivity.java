package com.example.lab9;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class VremeActivity extends AppCompatActivity {

    private static final String API_KEY = "API_KEY";

    private EditText etOras;
    private Button btnCauta;
    private TextView tvRezultat;
    private Spinner spZile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vreme);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etOras = findViewById(R.id.numeOras_ET);
        spZile = findViewById(R.id.zile_SP);
        btnCauta = findViewById(R.id.cautaVremea_BT);
        tvRezultat = findViewById(R.id.rezultateVreme_TV);

        String[] optiuni = {"1 zi", "5 zile"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, optiuni);
        spZile.setAdapter(adapter);

        btnCauta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oras = etOras.getText().toString().trim();
                if(oras.isEmpty()){
                    Toast.makeText(VremeActivity.this, "Introduceti numele unui oras!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String perioada = spZile.getSelectedItemPosition() == 0 ? "1day" : "5day";
                cautaVreme(oras, perioada);
            }
        });
    }

    private void cautaVreme(String oras, String perioada){
        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    String link = "https://dataservice.accuweather.com/locations/v1/cities/search?apikey=" + API_KEY + "&q=" + URLEncoder.encode(oras, "UTF-8");
                    String jsonOras = descarcaJson(link);
                    JSONArray vectorOrase = new JSONArray(jsonOras);
                    if (vectorOrase.length() == 0) {
                        handler.post(() -> tvRezultat.setText("Orasul nu a fost gasit."));
                        return;
                    }

                    JSONObject obiectOras = vectorOrase.getJSONObject(0);
                    String cheieOras = obiectOras.getString("Key");
                    String numeOras = obiectOras.getString("LocalizedName");

                    String linkVreme = "https://dataservice.accuweather.com/forecasts/v1/daily/"
                            + perioada + "/" + cheieOras + "?apikey=" + API_KEY + "&metric=true";

                    String jsonVreme = descarcaJson(linkVreme);
                    JSONObject radacina = new JSONObject(jsonVreme);
                    JSONArray dailyForecasts = radacina.getJSONArray("DailyForecasts");

                    StringBuilder afisare = new StringBuilder();
                    afisare.append("Oras: ").append(numeOras).append(" (Cod: ").append(cheieOras).append(")\n\n");

                    for (int i = 0; i < dailyForecasts.length(); i++) {
                        JSONObject zi = dailyForecasts.getJSONObject(i);
                        String data = zi.getString("Date").substring(0, 10);

                        JSONObject temp = zi.getJSONObject("Temperature");
                        JSONObject minObj = temp.getJSONObject("Minimum");
                        JSONObject maxObj = temp.getJSONObject("Maximum");

                        double tempMin = minObj.getDouble("Value");
                        double tempMax = maxObj.getDouble("Value");
                        String unitate = minObj.getString("Unit");

                        afisare.append("Data: ").append(data)
                                .append("\nMin: ").append(tempMin).append(" °").append(unitate)
                                .append(" | Max: ").append(tempMax).append(" °").append(unitate)
                                .append("\n\n");
                    }

                    handler.post(() -> tvRezultat.setText(afisare.toString()));

                } catch (Exception e) {
                    e.printStackTrace();
                    handler.post(() -> tvRezultat.setText("Eroare: " + e.getMessage()));
                }
            }
        });
    }
    private String descarcaJson(String link) throws Exception{
        URL url = new URL(link);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String linie;
        while ((linie = reader.readLine()) != null) {
            builder.append(linie);
        }
        reader.close();
        conn.disconnect();
        return builder.toString();
    }
}