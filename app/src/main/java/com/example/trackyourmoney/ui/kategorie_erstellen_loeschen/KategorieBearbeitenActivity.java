package com.example.trackyourmoney.ui.kategorie_erstellen_loeschen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;


import com.example.trackyourmoney.R;

import com.trackyourmoney.java.AppDataBase;
import com.trackyourmoney.java.Einnahme;
import com.trackyourmoney.java.Kategorie;
import com.trackyourmoney.java.KategorieDAO;

public class KategorieBearbeitenActivity extends AppCompatActivity {

    static long selectedId;
    String kategorie;
    double budget;
    AppDataBase db;
    KategorieDAO kategorieDao;

    EditText kategorieInput;
    EditText budgetInput;
    TextView validationTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_kategorie_bearbeiten);
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

        kategorieDao = db.kategorieDao();
        Kategorie currentKategorie = kategorieDao.getKategorieById(selectedId);

        kategorieInput.setText(currentKategorie.name);
        budgetInput.setText(werteAnpassen(String.valueOf(currentKategorie.budget)));



    }

    public void deleteKategorie(View view){
        Log.d("Test", String.valueOf(selectedId));
        Kategorie zuLoeschendeKategorie = kategorieDao.getKategorieById(selectedId);
        kategorieDao.delete(zuLoeschendeKategorie);

        Toast.makeText(this, "Kategorie '" + zuLoeschendeKategorie.name + "' geloescht!", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, KategorieActivity.class);
        startActivity(intent);
    }

    public void bearbeiten(View view){
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
            Kategorie zuBearbeitendeKategorie = kategorieDao.getKategorieById(selectedId);

            zuBearbeitendeKategorie.name = kategorie;
            zuBearbeitendeKategorie.budget = budget;

            db.kategorieDao().update(zuBearbeitendeKategorie);
            Toast.makeText(this, "Kategorie '" + kategorie + "' bearbeitet!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, KategorieActivity.class);
            startActivity(intent);
        }
    }

    public void seiteZurueck(View view){
        Intent intent = new Intent(this, KategorieActivity.class);
        startActivity(intent);
    }

    public String werteAnpassen(String wert){
        wert += "00";
        int index = wert.indexOf(".");
        String vorkomma = wert.substring(0,index);
        int nachkomma = Integer.valueOf(wert.substring(index+1, index+3));
        int uebertrag = Integer.valueOf(wert.substring(index+3,index+4));
        if(uebertrag >= 5){
            nachkomma += 1;
        }
        if(nachkomma == 100){
            nachkomma = 0;
            vorkomma += 1;
        }
        String newString;
        if(nachkomma < 10){
            newString = vorkomma + ".0" + nachkomma;
        }
        else{
            newString = vorkomma + "." + nachkomma;
        }
        return newString;
    }
}