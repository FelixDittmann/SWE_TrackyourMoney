package com.example.trackyourmoney.ui.addAusgabe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.trackyourmoney.R;

public class AddAusgabenActivity extends AppCompatActivity {

    String name;
    double betrag;
    long date, kategorieId;
    boolean wiederholend;
    int wiederholungsintervall;

    EditText nameInput;
    EditText betragInput;
    EditText dateInput;
    EditText kategorieIdInput;
    EditText wiederholendInput;
    EditText wiederholungsintervallInput;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_addausgaben);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nameInput = (EditText) findViewById(R.id.nameInput);
        betragInput = (EditText) findViewById(R.id.betragInput);
        dateInput = (EditText) findViewById(R.id.dateInput);
        kategorieIdInput = (EditText) findViewById(R.id.kategorieIdInput);
        wiederholendInput = (EditText) findViewById(R.id.wiederholendInput);
        wiederholungsintervallInput = (EditText) findViewById(R.id.wiederholungsintervallInput);

        textView = (TextView) findViewById(R.id.testView);
    }

    public void validation(View view) {
        //TODO validation algorithm
        name = nameInput.getText().toString();
        //betrag = Double.valueOf(betragInput.getText().toString());
        //date = Long.valueOf(dateInput.getText().toString());
        //kategorieId = Long.valueOf(kategorieIdInput.getText().toString());
        //wiederholend = Boolean.valueOf(wiederholendInput.getText().toString());
        //wiederholungsintervall = Integer.valueOf(wiederholungsintervallInput.getText().toString());
        textView.setText(name);
    }
}