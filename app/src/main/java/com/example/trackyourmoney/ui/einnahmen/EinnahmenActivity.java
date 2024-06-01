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
import android.widget.ArrayAdapter;

import com.example.trackyourmoney.MainActivity;
import com.example.trackyourmoney.R;
import com.example.trackyourmoney.ui.einnahme_hinzufügen.EinnahmeHinzufuegenActivity;

public class EinnahmenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_einnahmen);

    }

    public void einnahmeHinzufuegen(View view){
        Intent intent = new Intent(this, EinnahmeHinzufuegenActivity.class);
        startActivity(intent);
    }
    public void seiteZurueck(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}