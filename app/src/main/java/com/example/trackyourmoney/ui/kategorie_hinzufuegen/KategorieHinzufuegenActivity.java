package com.example.trackyourmoney.ui.kategorie_hinzufuegen;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.trackyourmoney.R;
import com.trackyourmoney.java.AppDataBase;
import com.trackyourmoney.java.AusgabeDAO;
import com.trackyourmoney.java.Kategorie;

public class KategorieHinzufuegenActivity extends AppCompatActivity {

    String kategorie;
    Double budget;

    EditText kategorieInput;
    EditText budgetInput;

    AppDataBase db;
    AusgabeDAO ausgabeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_kategorie_hinzufuegen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        kategorieInput = (EditText) findViewById(R.id.kategorieInput);
        budgetInput = (EditText) findViewById(R.id.budgetInput);
    }

    public void hinzufuegen(View view){
        kategorie = kategorieInput.getText().toString();
        budget = Double.valueOf(budgetInput.getText().toString());
        Kategorie neueKategorie = new Kategorie(kategorie, budget);
    }
}