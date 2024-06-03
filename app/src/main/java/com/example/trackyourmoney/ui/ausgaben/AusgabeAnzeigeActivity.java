package com.example.trackyourmoney.ui.ausgaben;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.trackyourmoney.R;
import com.example.trackyourmoney.ui.einnahme_hinzufügen.EinnahmeHinzufuegenActivity;
import com.trackyourmoney.java.AppDataBase;
import com.trackyourmoney.java.Ausgabe;
import com.trackyourmoney.java.AusgabeDAO;
import com.trackyourmoney.java.Kategorie;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AusgabeAnzeigeActivity extends AppCompatActivity {

    static long selectedId;
    String[] alleKategorien;
    long[] alleIds;
    AppDataBase db;
    AusgabeDAO ausgabeDao;
    EditText nameInput, betragInput, anmerkungenInput, dateInput, wiederholungsintervallInput;
    Spinner kategorieIdInput;
    CheckBox wiederholendInput;
    TextView textView9, testView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ausgabe_anzeige);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = Room.databaseBuilder(getApplicationContext(),
                AppDataBase.class, "App-database").allowMainThreadQueries().build();

        ausgabeDao = db.ausgabeDao();

        nameInput = (EditText) findViewById(R.id.nameInput);
        betragInput = (EditText) findViewById(R.id.betragInput);
        anmerkungenInput = (EditText) findViewById(R.id.anmerkungenInput);
        dateInput = (EditText) findViewById(R.id.dateInput);
        kategorieIdInput = (Spinner) findViewById(R.id.kategorieIdInput);
        wiederholendInput = (CheckBox) findViewById(R.id.wiederholendInput);
        wiederholungsintervallInput = (EditText) findViewById(R.id.wiederholungsintervallInput);
        textView9 = (TextView) findViewById(R.id.textView9);
        testView = (TextView) findViewById(R.id.testView);

        Ausgabe currentAusgabe = ausgabeDao.findById(selectedId);

        if(currentAusgabe.wiederholend){
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

        //TODO Datum vorher richtig konvertieren
        dateInput.setText(String.valueOf(currentAusgabe.date));

        nameInput.setText(currentAusgabe.name);
        betragInput.setText(String.valueOf(currentAusgabe.betrag));
        anmerkungenInput.setText(currentAusgabe.anmerkungen);
        wiederholungsintervallInput.setText(String.valueOf(currentAusgabe.wiederholungsintervall));

        long gesuchteId = currentAusgabe.kategorieId;

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

    public void deleteAusgabe(View view){
        Ausgabe zuLoeschendeAusgabe = ausgabeDao.findById(selectedId);
        ausgabeDao.delete(zuLoeschendeAusgabe);

        Intent intent = new Intent(this, AusgabenActivity.class);
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

        //Ausgabe hinzufügen falls alle Eingaben valide
        testView.setText(valid);

        if (valid == ""){
            bearbeiten(name, betrag, anmerkungen, date, wiederholend, kategorieId, wiederholungsintervall);
        }
    }

    public void bearbeiten(String name, double betrag, String anmerkungen, Date date, boolean wiederholend, long kategorieId, int wiederholungsintervall){
        Ausgabe zuBearbeitendeAusgabe = ausgabeDao.findById(selectedId);

        zuBearbeitendeAusgabe.name = name;
        zuBearbeitendeAusgabe.betrag = betrag;
        zuBearbeitendeAusgabe.anmerkungen = anmerkungen;
        zuBearbeitendeAusgabe.date = date;
        zuBearbeitendeAusgabe.wiederholend = wiederholend;
        zuBearbeitendeAusgabe.kategorieId = kategorieId;
        zuBearbeitendeAusgabe.wiederholungsintervall = wiederholungsintervall;

        ausgabeDao.update(zuBearbeitendeAusgabe);

        Intent intent = new Intent(this, AusgabenActivity.class);
        startActivity(intent);
    }
}