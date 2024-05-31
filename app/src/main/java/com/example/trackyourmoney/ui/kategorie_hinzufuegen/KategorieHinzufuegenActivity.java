package com.example.trackyourmoney.ui.kategorie_hinzufuegen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.trackyourmoney.R;
import com.example.trackyourmoney.ui.kategorie_erstellen_loeschen.KategorieErstellenLoeschenActivity;
import com.trackyourmoney.java.AppDataBase;
import com.trackyourmoney.java.Ausgabe;
import com.trackyourmoney.java.AusgabeDAO;
import com.trackyourmoney.java.DatabaseClient;
import com.trackyourmoney.java.Kategorie;
import com.trackyourmoney.java.KategorieDAO;

import java.util.List;

public class KategorieHinzufuegenActivity extends AppCompatActivity {

    String kategorie;
    double budget;

    EditText kategorieInput;
    EditText budgetInput;
    TextView validationTextView;

    AppDataBase db;

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
        validationTextView = (TextView) findViewById(R.id.validationTextView);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDataBase.class, "App-database").allowMainThreadQueries().build();
    }

    public void hinzufuegen(View view){
        kategorie = kategorieInput.getText().toString();
        String validation = "";
        if(kategorie.trim().length() < 1){
            validation += "Bitte Name der Kategorie eingeben!\n";
        }
        try{
            budget = Double.valueOf(budgetInput.getText().toString());
        }
        catch(Exception e){
            validation += "Budget entspricht nicht den Anforderungen!";
        }
        validationTextView.setText(validation);

        if(validation == ""){
            Kategorie neueKategorie = new Kategorie(kategorie, budget);
            db.kategorieDao().insert(neueKategorie);

            //Ausflistung der Vorhandenen Kategorien
            List<Kategorie> Kategorien = db.kategorieDao().getAllKategorien();
            for (Kategorie list: Kategorien){
                Log.d("Kategorien", list.id + " " + list.name + " " + list.budget);
            }
        }

        Intent intent = new Intent(this, KategorieErstellenLoeschenActivity.class);
        startActivity(intent);
    }
}