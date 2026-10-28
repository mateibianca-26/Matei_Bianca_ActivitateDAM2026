package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ListaMobilierActivity extends AppCompatActivity {

    private ListView lvMobiliere;
    private ArrayList<Mobilier> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_mobilier);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lvMobiliere = findViewById(R.id.mobiliere_LV);
        Intent intent = getIntent();
        lista = (ArrayList<Mobilier>) intent.getSerializableExtra("LISTA_MOBILIER");

        if (lista != null) {
            ArrayAdapter<Mobilier> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    lista
            );
            lvMobiliere.setAdapter(adapter);

            lvMobiliere.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Mobilier mobilierSelectat = lista.get(position);
                    Toast.makeText(ListaMobilierActivity.this, "Selectat:\n"+mobilierSelectat.toString(), Toast.LENGTH_LONG).show();
                }
            });

            lvMobiliere.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    String denumireStearsa = lista.get(position).getDenumire();
                    lista.remove(position);
                    adapter.notifyDataSetChanged();

                    Toast.makeText(ListaMobilierActivity.this, "Elementul \"" + denumireStearsa + "\" a fost sters", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }
    }
}