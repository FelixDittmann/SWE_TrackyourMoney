package com.example.trackyourmoney.ui.ausgaben;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

    }

    public void setSelectedId(long Id){
        selectedId = Id;
    }

    public void deleteAusgabe(View view){
        Log.d("Test", "1");
        Ausgabe zuLoeschendeAusgabe = ausgabeDao.findById(selectedId);
        Log.d("Test", "2");
        Log.d("Test", String.valueOf(selectedId));
        ausgabeDao.delete(zuLoeschendeAusgabe);
        Log.d("Test", "3");

        Intent intent = new Intent(this, AusgabenActivity.class);
        startActivity(intent);
    }
}