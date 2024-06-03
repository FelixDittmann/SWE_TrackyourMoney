package com.example.trackyourmoney.ui.kategorie_erstellen_loeschen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.trackyourmoney.MainActivity;
import com.example.trackyourmoney.R;
import com.example.trackyourmoney.ui.kategorie_hinzufuegen.KategorieHinzufuegenActivity;
import com.trackyourmoney.java.AppDataBase;
import com.trackyourmoney.java.Kategorie;

import java.util.List;

public class KategorieActivity extends AppCompatActivity {
    ListView kategorie_list;
    AppDataBase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_kategorie_erstellen_loeschen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        kategorie_list = (ListView) findViewById(R.id.kategorie_list);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDataBase.class, "App-database").allowMainThreadQueries().build();
        updateGUI();
    }

    public void updateGUI(){
        List<Kategorie> Kategorien = db.kategorieDao().getAllKategorien();
        String[] kategorieList = new String[Kategorien.size()];

        int i = 0;
        for (Kategorie list: Kategorien){
            kategorieList[i] = "Name: " + list.name + ", Budget: " + list.budget;
            i++;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, kategorieList);
        kategorie_list.setAdapter(adapter);

    }

    public void switchView(View view){
        Intent intent = new Intent(this, KategorieHinzufuegenActivity.class);
        startActivity(intent);
    }

    public void seiteZurueck(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}