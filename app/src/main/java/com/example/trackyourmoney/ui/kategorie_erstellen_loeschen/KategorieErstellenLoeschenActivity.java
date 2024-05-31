package com.example.trackyourmoney.ui.kategorie_erstellen_loeschen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.trackyourmoney.R;
import com.example.trackyourmoney.ui.kategorie_hinzufuegen.KategorieHinzufuegenActivity;

public class KategorieErstellenLoeschenActivity extends AppCompatActivity {
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
    }

    public void switchView(View view){
        Intent intent = new Intent(this, KategorieHinzufuegenActivity.class);
        startActivity(intent);
    }
}