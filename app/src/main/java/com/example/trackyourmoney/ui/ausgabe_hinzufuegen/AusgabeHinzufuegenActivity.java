package com.example.trackyourmoney.ui.ausgabe_hinzufuegen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

import com.example.trackyourmoney.MainActivity;
import com.example.trackyourmoney.R;
import com.example.trackyourmoney.ui.ausgaben.AusgabenActivity;
import com.example.trackyourmoney.ui.kategorie_hinzufuegen.KategorieHinzufuegenActivity;
import com.trackyourmoney.java.AppDataBase;
import com.trackyourmoney.java.Ausgabe;
import com.trackyourmoney.java.AusgabeDAO;
import com.trackyourmoney.java.DatabaseClient;
import com.trackyourmoney.java.Kategorie;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AusgabeHinzufuegenActivity extends AppCompatActivity {

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
    AusgabeDAO ausgabeDao;

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
        ausgabeDao = db.ausgabeDao();

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
        textView.setText(valid);

        if (valid == ""){
            hinzufuegen(name, betrag, anmerkungen, date, wiederholend, kategorieId, wiederholungsintervall);
        }
    }

    public void hinzufuegen(String name, double betrag, String anmerkungen, Date date, boolean wiederholend, long kategorieId, int wiederholungsintervall){

        Ausgabe neueAusgabe = new Ausgabe(name, betrag, anmerkungen, date, wiederholend, kategorieId, wiederholungsintervall);

        ausgabeDao.insert(neueAusgabe);
        Toast.makeText(this, "Ausgabe '" + name + "' hinzugefügt!", Toast.LENGTH_LONG).show();
    }

    public void seiteZurueck(View view){
        Intent intent = new Intent(this, AusgabenActivity.class);
        startActivity(intent);
    }
}