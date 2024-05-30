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
    AppDataBase adb;

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

        adb = Room.databaseBuilder(getApplicationContext(),
                AppDataBase.class, "app-database").build();

        //TODO Kategorie und Id aus Datenbank auslesen und in Relation abspeichern
        //String[][] kategorien = ;
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, kategorien);
        //kategorieIdInput.setAdapter(adapter);
    }

    public void validation(View view) {
        //TODO validation algorithm
        String valid = "";
        name = nameInput.getText().toString();
        try {
            betrag = Double.valueOf(betragInput.getText().toString());
        } catch (Exception e) {
            valid += "Unzul채ssiger Betrag!\n";
        }
        anmerkungen = anmerkungenInput.getText().toString();
        try {
            date = Long.valueOf(dateInput.getText().toString());
        } catch (Exception e) {
            valid += "Unzul채ssige Datumsschreibweise!\n";
        }
        //kategorieId = Long.valueOf(kategorieIdInput.getText().toString());
        kategorieId = 2;
        wiederholend = Boolean.valueOf(wiederholendInput.getText().toString());
        try {
            wiederholungsintervall = Integer.valueOf(wiederholungsintervallInput.getText().toString());
        } catch (Exception e) {
            valid += "Falsche Angabe des Wiederholungsintervalls!";
        }
        textView.setText(valid);

        if (valid == ""){
            hinzuf체gen();
        }
    }

    public void hinzuf체gen(){
        Ausgabe ausgabe = new Ausgabe(name, betrag, anmerkungen, date, wiederholend, kategorieId, wiederholungsintervall);
        adb.ausgabeDao().insert(ausgabe);

        List<Ausgabe> Ausgaben = adb.ausgabeDao().getAllAusgaben();
        for (Ausgabe list: Ausgaben){
            Log.d("Ausgaben", list.name + " " + list.betrag + " " + list.anmerkungen + " " + list.date + " " + list.wiederholend + " " + list.kategorieId + " " + list.wiederholungsintervall);
        }
    }
}