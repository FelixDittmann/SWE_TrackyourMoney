package com.example.trackyourmoney.ui.einnahmen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.trackyourmoney.MainActivity;
import com.example.trackyourmoney.R;
import com.example.trackyourmoney.ui.ausgabe_hinzufuegen.AusgabeHinzufuegenActivity;
import com.example.trackyourmoney.ui.ausgaben.AusgabeAnzeigeActivity;
import com.example.trackyourmoney.ui.ausgaben.AusgabenActivity;
import com.example.trackyourmoney.ui.einnahme_hinzufügen.EinnahmeHinzufuegenActivity;
import com.trackyourmoney.java.AppDataBase;
import com.trackyourmoney.java.Ausgabe;
import com.trackyourmoney.java.Einnahme;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EinnahmenActivity extends AppCompatActivity {

    AppDataBase db;
    String[] alleEinnahmenString;
    long[] alleIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_einnahmen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = Room.databaseBuilder(getApplicationContext(),
                AppDataBase.class, "App-database").allowMainThreadQueries().build();

        //Ausgaben in Liste einfügen
        List<Einnahme> Einnahmen = db.einnahmeDao().getAllEinnahmen();
        alleIds = new long[Einnahmen.size()];
        alleEinnahmenString = new String[Einnahmen.size()];;
        int i = 0;
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd.MM.yyyy");
        for (Einnahme list: Einnahmen){
            Date date = list.date;
            String newDate = targetFormat.format(date);
            alleEinnahmenString[i] = list.name + ", " + werteAnpassen(String.valueOf(list.value)) + ", " + newDate;
            alleIds[i] = list.id;
            i++;
        }
        ListView einnahmen_liste = (ListView) findViewById(R.id.einnahmen_liste);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alleEinnahmenString);
        einnahmen_liste.setAdapter(adapter);

        einnahmen_liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = adapter.getItem(position);

                long selectedId = 0;

                for(int i = 0; i < alleEinnahmenString.length; i++){
                    if(item == alleEinnahmenString[i]){
                        selectedId = alleIds[i];
                    }
                }
                EinnahmeAnzeigeActivity.selectedId = selectedId;
                Intent intent = new Intent(EinnahmenActivity.this, EinnahmeAnzeigeActivity.class);
                startActivity(intent);

            }
        });
    }

    public void einnahmeHinzufuegen(View view){
        Intent intent = new Intent(this, EinnahmeHinzufuegenActivity.class);
        startActivity(intent);
    }
    public void seiteZurueck(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void switchView(View view){
        Intent intent = new Intent(this, EinnahmeHinzufuegenActivity.class);
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