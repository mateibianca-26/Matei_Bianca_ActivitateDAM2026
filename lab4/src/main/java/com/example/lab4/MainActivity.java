package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Mobilier> listaMobilier = new ArrayList<>();
    private static final int REQUEST_CODE_ADAUGARE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    public void deschideFormular(View view) {
        Intent it = new Intent(this, AddMobilierActivity.class);
        startActivityForResult(it, REQUEST_CODE_ADAUGARE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADAUGARE && resultCode == RESULT_OK && data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                Mobilier mobilier = (Mobilier) bundle.getSerializable("OBIECT_MOBILIER");
                if (mobilier != null) {
                    listaMobilier.add(mobilier);

                    TextView tv = findViewById(R.id.rezultat_TV);
                    tv.setText("Numar elemente salvate: " + listaMobilier.size() + "\n" + mobilier.toString());

                    Toast.makeText(this, getString(R.string.toast_succes) + " " + mobilier.getDenumire(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void deschideLista(View view) {
        if(listaMobilier.isEmpty()){
            Toast.makeText(this, "Lista este goala! Adaugati mobilier", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, ListaMobilierActivity.class);
        intent.putExtra("LISTA_MOBILIER", listaMobilier);
        startActivity(intent);
    }
}
