package com.example.trackyourmoney.ui.ausgaben;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.CheckBox;
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

public class AusgabeAnzeigeActivity extends AppCompatActivity {

    static long selectedId;
    AppDataBase db;
    AusgabeDAO ausgabeDao;
    EditText nameInput, betragInput, anmerkungInput, dateInput, wiederholungsintervallInput;
    Spinner kategorieIdInput;
    CheckBox wiederholendInput;


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
        anmerkungInput = (EditText) findViewById(R.id.anmerkungenInput);
        dateInput = (EditText) findViewById(R.id.dateInput);
        kategorieIdInput = (Spinner) findViewById(R.id.kategorieIdInput);
        wiederholendInput = (CheckBox) findViewById(R.id.wiederholendInput);
        wiederholungsintervallInput = (EditText) findViewById(R.id.wiederholungsintervallInput);

        Ausgabe currentAusgabe = ausgabeDao.findById(selectedId);

        if(currentAusgabe.wiederholend){
            //TODO set checkbox to checke
            wiederholendInput.setChecked(true);
        }

        nameInput.setText(currentAusgabe.name);
        betragInput.setText(String.valueOf(currentAusgabe.betrag));
        anmerkungInput.setText(currentAusgabe.anmerkungen);
        dateInput.setText(String.valueOf(currentAusgabe.date));
        wiederholungsintervallInput.setText(String.valueOf(currentAusgabe.wiederholungsintervall));


    }

    public void deleteAusgabe(View view){
        Ausgabe zuLoeschendeAusgabe = ausgabeDao.findById(selectedId);
        ausgabeDao.delete(zuLoeschendeAusgabe);

        Intent intent = new Intent(this, AusgabenActivity.class);
        startActivity(intent);
    }
}