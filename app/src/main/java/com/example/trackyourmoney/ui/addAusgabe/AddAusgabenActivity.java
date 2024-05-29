package com.example.trackyourmoney.ui.addAusgabe;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.trackyourmoney.R;

public class AddAusgabenActivity extends AppCompatActivity {

    String name;
    double betrag;
    long date, kategorieId;
    boolean wiederholend;
    int wiederholungsintervall;

    EditText nameInput;
    EditText betragInput;
    EditText dateInput;
    Spinner kategorieIdInput;
    CheckBox wiederholendInput;
    EditText wiederholungsintervallInput;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_addausgaben);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nameInput = (EditText) findViewById(R.id.nameInput);
        betragInput = (EditText) findViewById(R.id.betragInput);
        dateInput = (EditText) findViewById(R.id.dateInput);
        kategorieIdInput = (Spinner) findViewById(R.id.kategorieIdInput);
        wiederholendInput = (CheckBox) findViewById(R.id.wiederholendInput);
        wiederholungsintervallInput = (EditText) findViewById(R.id.wiederholungsintervallInput);

        textView = (TextView) findViewById(R.id.testView);

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
            valid += "Unzulässiger Betrag!\n";
        }
        try {
            date = Long.valueOf(dateInput.getText().toString());
        } catch (Exception e) {
            valid += "Unzulässige Datumsschreibweise!\n";
        }
        //kategorieId = Long.valueOf(kategorieIdInput.getText().toString());
        wiederholend = Boolean.valueOf(wiederholendInput.getText().toString());
        try {
            wiederholungsintervall = Integer.valueOf(wiederholungsintervallInput.getText().toString());
        } catch (Exception e) {
            valid += "Falsche Angabe des Wiederholungsintervalls!";
        }
        textView.setText(valid);

        if (valid == ""){
            //TODO add Ausgabe
        }
    }
}