package com.example.trackyourmoney;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trackyourmoney.ui.ausgabe_hinzufuegen.AusgabeHinzufuegenActivity;
import com.example.trackyourmoney.ui.einnahme_hinzufügen.EinnahmeHinzufuegenActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.trackyourmoney.databinding.ActivityMainBinding;
import com.trackyourmoney.java.AppDataBase;
import com.trackyourmoney.java.Ausgabe;
import com.trackyourmoney.java.AusgabeDAO;
import com.trackyourmoney.java.DatabaseClient;
import com.trackyourmoney.java.Einnahme;
import com.trackyourmoney.java.EinnahmeDAO;
import com.trackyourmoney.java.Kategorie;
import com.trackyourmoney.java.KategorieDAO;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


       /* AppDataBase db = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
        EinnahmeDAO einnahmeDao = db.einnahmeDao();
        AusgabeDAO ausgabeDao = db.ausgabeDao();
        KategorieDAO kategorieDao = db.kategorieDao();*/


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppDataBase db = Room.databaseBuilder(getApplicationContext(),
                AppDataBase.class, "App-database").allowMainThreadQueries().build();

        TextView monthlybudget = (TextView) findViewById(R.id.monthlybudget);
        TextView used_Budget = (TextView) findViewById(R.id.used_budget);

        List<Kategorie> Kategorien = db.kategorieDao().getAllKategorien();
        double usableBudget = 0;
        for (Kategorie list: Kategorien){
            usableBudget += list.budget;
        }
        monthlybudget.setText(String.valueOf(usableBudget));

        List<Ausgabe> Ausgaben = db.ausgabeDao().getAllAusgaben();
        double usedBudget = 0;
        for (Ausgabe list: Ausgaben){
            usedBudget += list.betrag;
        }
        List<Einnahme> Einnahmen = db.einnahmeDao().getAllEinnahmen();
        for (Einnahme list: Einnahmen){
            usedBudget -= list.value;
        }
        used_Budget.setText(String.valueOf(usedBudget));

        TextView unused_Budget = (TextView) findViewById(R.id.unused_budget);
        unused_Budget.setText(String.valueOf(usableBudget - usedBudget));


        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_analyse, R.id.nav_fixkosten, R.id.nav_ausgaben, R.id.nav_einnahmen, R.id.nav_fixkosten_aendern, R.id.nav_budget_aendern, R.id.nav_ausgabe_hinzufuegen, R.id.nav_einnahme_hinzufuegen, R.id.nav_kategorie_erstellen_loeschen, R.id.nav_ausgabenlimit_aendern)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void switchViewAusgaben(View view){
        Intent intent = new Intent(this, AusgabeHinzufuegenActivity.class);
        startActivity(intent);
    }

    public void switchViewEinnahmen(View view){
        Intent intent = new Intent(this, EinnahmeHinzufuegenActivity.class);
        startActivity(intent);
    }


}