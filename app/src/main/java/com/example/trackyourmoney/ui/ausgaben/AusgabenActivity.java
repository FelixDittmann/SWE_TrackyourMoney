package com.example.trackyourmoney.ui.ausgaben;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.trackyourmoney.MainActivity;
import com.example.trackyourmoney.R;
import com.example.trackyourmoney.ui.ausgabe_hinzufuegen.AusgabeHinzufuegenActivity;
import com.trackyourmoney.java.AppDataBase;
import com.trackyourmoney.java.Ausgabe;
import com.trackyourmoney.java.Kategorie;

import java.util.List;

public class AusgabenActivity extends AppCompatActivity {

    AppDataBase db;
    String[] alleAusgabenString;
    long[] alleIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ausgaben);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = Room.databaseBuilder(getApplicationContext(),
                AppDataBase.class, "App-database").allowMainThreadQueries().build();

        //Ausgaben in Liste einfügen
        List<Ausgabe> Ausgaben = db.ausgabeDao().getAllAusgaben();
        alleIds = new long[Ausgaben.size()];
        alleAusgabenString = new String[Ausgaben.size()];;
        int i = 0;
        for (Ausgabe list: Ausgaben){
            alleAusgabenString[i] = list.name + ", " + list.betrag + ", " + list.date;
            alleIds[i] = list.id;
            i++;
        }
        ListView ausgaben_liste = (ListView) findViewById(R.id.ausgaben_liste);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alleAusgabenString);
        ausgaben_liste.setAdapter(adapter);

        ausgaben_liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = adapter.getItem(position);

                long selectedId = 0;

                for(int i = 0; i < alleAusgabenString.length; i++){
                    if(item == alleAusgabenString[i]){
                        selectedId = alleIds[i];
                    }
                }
                AusgabeAnzeigeActivity.selectedId = selectedId;
                Intent intent = new Intent(AusgabenActivity.this, AusgabeAnzeigeActivity.class);
                startActivity(intent);

            }
        });
    }

    public void seiteZurueck(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void switchView(View view){
        Intent intent = new Intent(this, AusgabeHinzufuegenActivity.class);
        startActivity(intent);
    }
}