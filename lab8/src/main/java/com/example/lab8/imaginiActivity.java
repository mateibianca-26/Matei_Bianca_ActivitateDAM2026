package com.example.lab8;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

public class imaginiActivity extends AppCompatActivity {

    private List<ImaginiMobilier> lista = new ArrayList<>();
    private List<Bitmap> imaginiDescarcate = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_imagini);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView lv = findViewById(R.id.imagini_LV);

        List<String> linkuriImagini = new ArrayList<>();
        linkuriImagini.add("https://cdn.pixabay.com/photo/2014/08/11/21/40/bedroom-416062_1280.jpg\n");
        linkuriImagini.add("https://cdn.pixabay.com/photo/2015/10/20/18/57/furniture-998265_1280.jpg\n");
        linkuriImagini.add("https://cdn.pixabay.com/photo/2020/11/09/17/07/chair-5727263_1280.jpg\n");
        linkuriImagini.add("https://cdn.pixabay.com/photo/2020/10/07/12/33/cafe-5635015_1280.jpg\n");
        linkuriImagini.add("https://cdn.pixabay.com/photo/2017/06/29/07/32/furniture-2453307_1280.jpg\n");

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                for(String link : linkuriImagini){
                    HttpURLConnection con = null;
                    try{
                        URL url = new URL(link);
                        con = (HttpURLConnection) url.openConnection();
                        InputStream is = con.getInputStream();
                        imaginiDescarcate.add(BitmapFactory.decodeStream(is));
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if(con != null){
                            con.disconnect();
                        }
                    }
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(imaginiDescarcate.size() == 5){
                            lista.add(new ImaginiMobilier("Dormitor complet modern", imaginiDescarcate.get(0), "https://www.ikea.com/ro/ro/cat/paturi-bm003/"));
                            lista.add(new ImaginiMobilier("Living elegant mobilat", imaginiDescarcate.get(1), "https://www.ikea.com/ro/ro/cat/canapele-fu003/"));
                            lista.add(new ImaginiMobilier("Scaun design minimalist", imaginiDescarcate.get(2), "https://www.ikea.com/ro/ro/cat/scaune-fu002/"));
                            lista.add(new ImaginiMobilier("Mobilier cafenea si bistro", imaginiDescarcate.get(3), "https://www.ikea.com/ro/ro/cat/mese-fu004/"));
                            lista.add(new ImaginiMobilier("Set mobilier interior", imaginiDescarcate.get(4), "https://www.ikea.com/ro/ro/cat/dulapuri-19053/"));

                            ImagineAdapter adapter = new ImagineAdapter(
                                    getApplicationContext(),
                                    R.layout.item_imagine_mobilier,
                                    lista
                            );
                            lv.setAdapter(adapter);
                        }
                    }
                });
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(getApplicationContext(), WebViewActivity.class);
                it.putExtra("link", lista.get(position).getLink());
                startActivity(it);
            }
        });
    }

}