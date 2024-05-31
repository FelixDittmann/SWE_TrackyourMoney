package com.example.trackyourmoney.ui.ausgabe_hinzufuegen;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.trackyourmoney.java.AppDataBase;
import com.trackyourmoney.java.Ausgabe;
import com.trackyourmoney.java.AusgabeDAO;
import com.trackyourmoney.java.DatabaseClient;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import java.util.List;

public class AusgabeHinzufuegenActivity extends AppCompatActivity {

    String name, anmerkungen;
    double betrag;
    long date, kategorieId;
    boolean wiederholend;
    int wiederholungsintervall;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ausgabe_hinzufuegen);
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
        //String[][] kategorien = ;
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, kategorien);
        //kategorieIdInput.setAdapter(adapter);
    }

    public void validation(View view) {
        //TODO validation algorithm
        String valid = "";

        name = nameInput.getText().toString();
        if(name.trim().length() < 1){
            valid += "Bitte Name der Kategorie eingeben!\n";
        }

        try {
            betrag = Double.valueOf(betragInput.getText().toString());
            if(betrag == 0){
                betrag = 1/0;
            }
        } catch (Exception e) {
            valid += "Unzul채ssiger Betrag!\n";
        }

        anmerkungen = anmerkungenInput.getText().toString();

        try {
            date = Long.valueOf(dateInput.getText().toString());
        } catch (Exception e) {
            valid += "Unzul채ssige Datumsschreibweise!\n";
        }

        //TODO kategorie auslese
        //kategorieId = Long.valueOf(kategorieIdInput.getText().toString());
        kategorieId = 1;

        if(wiederholendInput.isChecked()){
            wiederholend = true;
        }
        else{
            wiederholend = false;
        }

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

        textView.setText(valid);

        if (valid == ""){
            hinzufuegen();
        }
    }

    public void hinzufuegen(){
        Ausgabe neueAusgabe = new Ausgabe(name, betrag, anmerkungen, date, wiederholend, kategorieId, wiederholungsintervall);

        db.ausgabeDao().insert(neueAusgabe);

        List<Ausgabe> Ausgaben = db.ausgabeDao().getAllAusgaben();
        for (Ausgabe list: Ausgaben){
            Log.d("Ausgaben", list.id + " " + list.name + " " + list.betrag + " " + list.anmerkungen + " " + list.date + " " + list.wiederholend + " " + list.kategorieId + " " + list.wiederholungsintervall);
        }
    }
}