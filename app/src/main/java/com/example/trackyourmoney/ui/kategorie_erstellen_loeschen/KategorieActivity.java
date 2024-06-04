package com.example.trackyourmoney.ui.kategorie_erstellen_loeschen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.trackyourmoney.ui.ausgaben.AusgabeAnzeigeActivity;
import com.example.trackyourmoney.ui.ausgaben.AusgabenActivity;
import com.example.trackyourmoney.ui.kategorie_hinzufuegen.KategorieHinzufuegenActivity;
import com.trackyourmoney.java.AppDataBase;
import com.trackyourmoney.java.Kategorie;

import java.util.List;

public class KategorieActivity extends AppCompatActivity {
    ListView kategorie_list;
    AppDataBase db;
    ArrayAdapter<String> adapter;
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

        List<Kategorie> Kategorien = db.kategorieDao().getAllKategorien();
        String[] kategorieList = new String[Kategorien.size()];
        long[] kategorieIds = new long[Kategorien.size()];

        int i = 0;
        for (Kategorie list: Kategorien){
            kategorieList[i] = "Name: " + list.name + ", Budget: " + werteAnpassen(String.valueOf(list.budget));
            kategorieIds[i] = list.id;
            i++;
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, kategorieList);
        kategorie_list.setAdapter(adapter);

        kategorie_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = adapter.getItem(position);

                long selectedId = 0;

                for(int i = 0; i < kategorieList.length; i++){
                    if(item == kategorieList[i]){
                        selectedId = kategorieIds[i];
                    }
                }
                KategorieBearbeitenActivity.selectedId = selectedId;
                Intent intent = new Intent(KategorieActivity.this, KategorieBearbeitenActivity.class);
                startActivity(intent);

            }
        });
    }

    public void switchView(View view){
        Intent intent = new Intent(this, KategorieHinzufuegenActivity.class);
        startActivity(intent);
    }

    public void seiteZurueck(View view){
        Intent intent = new Intent(this, MainActivity.class);
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