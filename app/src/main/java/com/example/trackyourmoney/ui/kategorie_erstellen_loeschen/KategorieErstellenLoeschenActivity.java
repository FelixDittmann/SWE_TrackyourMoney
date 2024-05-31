package com.example.trackyourmoney.ui.kategorie_erstellen_loeschen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.trackyourmoney.R;
import com.example.trackyourmoney.ui.kategorie_hinzufuegen.KategorieHinzufuegenActivity;
import com.trackyourmoney.java.AppDataBase;
import com.trackyourmoney.java.Kategorie;

import java.util.List;

public class KategorieErstellenLoeschenActivity extends AppCompatActivity {

    String kategorie;
    String budget;
    TextView AnzeigeKategorie, AnzeigeBudget;
    AppDataBase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_kategorie_erstellen_loeschen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        kategorie = "";
        budget = "";

        AnzeigeKategorie = (TextView) findViewById(R.id.AnzeigeKategorien);
        AnzeigeBudget = (TextView) findViewById(R.id.AnzeigeBudget);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDataBase.class, "App-database").allowMainThreadQueries().build();
        updateGUI();
    }

    public void updateGUI(){
        List<Kategorie> Kategorien = db.kategorieDao().getAllKategorien();

        for (Kategorie list: Kategorien){
            Log.d("Kategorien", list.id + " " + list.name + " " + list.budget);
        }

        for (Kategorie list: Kategorien){
            kategorie += list.name + "\n";
            budget += list.budget + "\n";
        }
        Log.d("Test", kategorie + " " + budget);
        AnzeigeKategorie.setText(kategorie);
        AnzeigeBudget.setText(budget);
    }

    public void switchView(View view){
        Intent intent = new Intent(this, KategorieHinzufuegenActivity.class);
        startActivity(intent);
    }
}