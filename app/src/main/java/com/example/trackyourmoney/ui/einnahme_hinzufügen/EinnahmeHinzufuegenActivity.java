package com.example.trackyourmoney.ui.einnahme_hinzufügen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.trackyourmoney.R;
import com.example.trackyourmoney.ui.einnahmen.EinnahmenActivity;
import com.example.trackyourmoney.ui.kategorie_hinzufuegen.KategorieHinzufuegenActivity;
import com.trackyourmoney.java.AppDataBase;

import com.trackyourmoney.java.Einnahme;
import com.trackyourmoney.java.EinnahmeDAO;
import com.trackyourmoney.java.Kategorie;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EinnahmeHinzufuegenActivity extends AppCompatActivity {

    String[] alleKategorien;
    long[] alleIds;

    EditText nameInput;
    EditText betragInput;
    EditText anmerkungenInput;
    EditText dateInput;
    Spinner kategorieIdInput;
    CheckBox wiederholendInput;
    EditText wiederholungsintervallInput;
    TextView textView;
    TextView textView9;
    AppDataBase db;
    EinnahmeDAO einnahmeDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_einnahme_hinzufuegen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nameInput = (EditText) findViewById(R.id.nameInput);
        betragInput = (EditText) findViewById(R.id.betragInput);
        anmerkungenInput = (EditText) findViewById(R.id.anmerkungenInput);
        dateInput = (EditText) findViewById(R.id.dateInput);
        kategorieIdInput = (Spinner) findViewById(R.id.kategorieIdInput);
        wiederholendInput = (CheckBox) findViewById(R.id.wiederholendInput);
        wiederholungsintervallInput = (EditText) findViewById(R.id.wiederholungsintervallInput);

        textView = (TextView) findViewById(R.id.testView);
        textView9 = (TextView) findViewById(R.id.textView9);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDataBase.class, "App-database").allowMainThreadQueries().build();

        einnahmeDao = db.einnahmeDao();

        wiederholendInput.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(wiederholendInput.isChecked()){
                    wiederholungsintervallInput.setVisibility(View.VISIBLE);
                    textView9.setVisibility(View.VISIBLE);
                }
                else{
                    wiederholungsintervallInput.setVisibility(View.INVISIBLE);
                    textView9.setVisibility(View.INVISIBLE);
                }
            }
        });

        //TODO Kategorie und Id aus Datenbank auslesen und in Relation abspeichern
        List<Kategorie> Kategorien = db.kategorieDao().getAllKategorien();

        alleKategorien = new String[Kategorien.size()];
        alleIds = new long[Kategorien.size()];
        int i = 0;
        for (Kategorie list: Kategorien){
            alleKategorien[i] = list.name;
            alleIds[i] = list.id;
            i++;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, alleKategorien);
        kategorieIdInput.setAdapter(adapter);

        try{
            //würde crashen falls keine Kategorie existiert
            String kategorieEingabe = kategorieIdInput.getSelectedItem().toString();
        }
        catch(Exception e){
            Toast.makeText(this, "Zuerst Kategorie hinzufuegen!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, KategorieHinzufuegenActivity.class);
            startActivity(intent);
        }
    }

    public void validation(View view) {
        //TODO validation algorithm
        String valid = "";

        //Name
        String name = nameInput.getText().toString();
        if(name.trim().length() < 1){
            valid += "Bitte Name der Kategorie eingeben!\n";
        }

        //Betrag
        double betrag = 0;
        try {
            betrag = Double.valueOf(betragInput.getText().toString());
            if(betrag == 0){
                betrag = 1/0;
            }

        } catch (Exception e) {
            valid += "Unzul채ssiger Betrag!\n";
        }

        //Anmerkungen
        String anmerkungen = anmerkungenInput.getText().toString();

        //Datum
        Date date = new Date();
        try {
            String dateString = dateInput.getText().toString();

            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            date = formatter.parse(dateString);
        } catch (Exception e) {
            valid += "Unzul채ssige Datumsschreibweise!\n";
        }

        //KategorieID
        long kategorieId = 0;
        String kategorieEingabe = kategorieIdInput.getSelectedItem().toString();
        for(int i = 0; i < alleKategorien.length; i++){
            if(kategorieEingabe == alleKategorien[i]){
                kategorieId = alleIds[i];
            }
        }

        //Wiederholend?
        boolean wiederholend;
        if(wiederholendInput.isChecked()){
            wiederholend = true;
        }
        else{
            wiederholend = false;
        }

        //Wiederholungsintervall
        int wiederholungsintervall = 0;
        if(wiederholend){
            try {
                wiederholungsintervall = Integer.valueOf(wiederholungsintervallInput.getText().toString());
            } catch (Exception e) {
                valid += "Falsche Angabe des Wiederholungsintervalls!";
            }
        }
        else{
            wiederholungsintervall = -1;
        }

        //Eingabe hinzufügen falls alle Eingaben valide
        textView.setText(valid);

        if (valid == ""){
            hinzufuegen(name, betrag, anmerkungen, date, wiederholend, kategorieId, wiederholungsintervall);
        }
    }

    public void hinzufuegen(String name, double betrag, String anmerkungen, Date date, boolean wiederholend, long kategorieId, int wiederholungsintervall){

        Einnahme neueEinnahme = new Einnahme(name, betrag, anmerkungen, date, wiederholend, kategorieId, wiederholungsintervall);

        einnahmeDao.insert(neueEinnahme);
        Toast.makeText(this, "Einnahme '" + name + "' hinzugefügt!", Toast.LENGTH_LONG).show();
    }

    public void seiteZurueck(View view){
        Intent intent = new Intent(this, EinnahmenActivity.class);
        startActivity(intent);
    }
}