package com.example.trackyourmoney.ui.einnahmen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.trackyourmoney.R;
import com.example.trackyourmoney.ui.ausgaben.AusgabenActivity;
import com.trackyourmoney.java.AppDataBase;
import com.trackyourmoney.java.Ausgabe;
import com.trackyourmoney.java.AusgabeDAO;
import com.trackyourmoney.java.Einnahme;
import com.trackyourmoney.java.EinnahmeDAO;

public class EinnahmeAnzeigeActivity extends AppCompatActivity {

    static long selectedId;
    AppDataBase db;
    EinnahmeDAO einnahmeDao;

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
    }

    public void deleteEinnahme(View view){
        Einnahme zuLoeschendeEinnahme = einnahmeDao.findById(selectedId);
        einnahmeDao.delete(zuLoeschendeEinnahme);

        Intent intent = new Intent(this, EinnahmenActivity.class);
        startActivity(intent);
    }
}