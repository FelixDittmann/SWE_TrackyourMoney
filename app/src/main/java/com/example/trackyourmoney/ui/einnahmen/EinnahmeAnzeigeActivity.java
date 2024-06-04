package com.example.trackyourmoney.ui.einnahmen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.trackyourmoney.java.EinnahmeDAO;
import com.trackyourmoney.java.Kategorie;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EinnahmeAnzeigeActivity extends AppCompatActivity {

    static long selectedId;
    AppDataBase db;
    EinnahmeDAO einnahmeDao;
    String[] alleKategorien;
    long[] alleIds;
    EditText nameInput, betragInput, anmerkungenInput, dateInput, wiederholungsintervallInput;
    Spinner kategorieIdInput;
    CheckBox wiederholendInput;
    TextView textView9, testView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_einnahme_anzeige);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = Room.databaseBuilder(getApplicationContext(),
                AppDataBase.class, "App-database").allowMainThreadQueries().build();
        einnahmeDao = db.einnahmeDao();

        nameInput = (EditText) findViewById(R.id.nameInput);
        betragInput = (EditText) findViewById(R.id.betragInput);
        anmerkungenInput = (EditText) findViewById(R.id.anmerkungenInput);
        dateInput = (EditText) findViewById(R.id.dateInput);
        kategorieIdInput = (Spinner) findViewById(R.id.kategorieIdInput);
        wiederholendInput = (CheckBox) findViewById(R.id.wiederholendInput);
        wiederholungsintervallInput = (EditText) findViewById(R.id.wiederholungsintervallInput);
        textView9 = (TextView) findViewById(R.id.textView9);
        testView = (TextView) findViewById(R.id.testView);

        Einnahme currentEinnahme = einnahmeDao.findById(selectedId);

        if(currentEinnahme.wiederholend){
            wiederholendInput.setChecked(true);
            wiederholungsintervallInput.setVisibility(View.VISIBLE);
            textView9.setVisibility(View.VISIBLE);
        }

        wiederholendInput.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

        //Datum vorher richtig konvertieren
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = currentEinnahme.date;
        String newDate = targetFormat.format(date);

        dateInput.setText(newDate);

        nameInput.setText(currentEinnahme.name);
        betragInput.setText(werteAnpassen(String.valueOf(currentEinnahme.value)));
        anmerkungenInput.setText(currentEinnahme.anmerkungen);
        wiederholungsintervallInput.setText(String.valueOf(currentEinnahme.wiederholungsintervall));

        long gesuchteId = currentEinnahme.kategorieId;

        List<Kategorie> Kategorien = db.kategorieDao().getAllKategorien();

        alleKategorien = new String[Kategorien.size()];
        alleIds = new long[Kategorien.size()];
        int startField = 0;
        int i = 0;
        for (Kategorie list: Kategorien){
            alleKategorien[i] = list.name;
            alleIds[i] = list.id;
            if(alleIds[i] == gesuchteId){
                startField = i;
            }
            i++;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, alleKategorien);
        kategorieIdInput.setAdapter(adapter);
        kategorieIdInput.setSelection(startField);
    }

    public void deleteEinnahme(View view){
        Einnahme zuLoeschendeEinnahme = einnahmeDao.findById(selectedId);
        einnahmeDao.delete(zuLoeschendeEinnahme);

        Toast.makeText(this, "Einnahme '" + zuLoeschendeEinnahme.name + "' geloescht!", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, EinnahmenActivity.class);
        startActivity(intent);
    }

    public void validation(View view) {
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
                throw new RuntimeException("invalid betrag");
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

        //Einnahme hinzufügen falls alle Eingaben valide
        testView.setText(valid);

        if (valid == ""){
            bearbeiten(name, betrag, anmerkungen, date, wiederholend, kategorieId, wiederholungsintervall);
        }
    }

    public void bearbeiten(String name, double betrag, String anmerkungen, Date date, boolean wiederholend, long kategorieId, int wiederholungsintervall){
        Einnahme zuBearbeitendeEinnahme = einnahmeDao.findById(selectedId);

        zuBearbeitendeEinnahme.name = name;
        zuBearbeitendeEinnahme.value = betrag;
        zuBearbeitendeEinnahme.anmerkungen = anmerkungen;
        zuBearbeitendeEinnahme.date = date;
        zuBearbeitendeEinnahme.wiederholend = wiederholend;
        zuBearbeitendeEinnahme.kategorieId = kategorieId;
        zuBearbeitendeEinnahme.wiederholungsintervall = wiederholungsintervall;

        einnahmeDao.update(zuBearbeitendeEinnahme);

        Toast.makeText(this, "Einnahme '" + name + "' bearbeitet!", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, EinnahmenActivity.class);
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