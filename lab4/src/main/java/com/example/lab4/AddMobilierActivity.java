package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddMobilierActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_mobilier);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void culegeSiTrimiteDate(View view) {
        EditText etDenumire = findViewById(R.id.denumire_ET);
        EditText etPret = findViewById(R.id.pret_ET);
        Spinner spMaterial = findViewById(R.id.material_SP);
        CheckBox cbMontaj = findViewById(R.id.montaj_CB);
        Switch swStoc = findViewById(R.id.inStoc_SW);

        String denumire = etDenumire.getText().toString().trim();
        String pretStr = etPret.getText().toString().trim();

        if (denumire.isEmpty() || pretStr.isEmpty()) {
            Toast.makeText(this, getString(R.string.eroare_completare), Toast.LENGTH_SHORT).show();
            return;
        }

        double pret = Double.parseDouble(pretStr);
        String material = spMaterial.getSelectedItem().toString();
        boolean montajInclus = cbMontaj.isChecked();
        boolean inStoc = swStoc.isChecked();


        Mobilier mobilier = new Mobilier(denumire, pret, material, montajInclus, inStoc);

        Intent it = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("OBIECT_MOBILIER", mobilier);
        it.putExtras(bundle);

        setResult(RESULT_OK, it);
        finish();
    }
}