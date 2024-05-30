package com.example.trackyourmoney;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import androidx.test.core.app.ApplicationProvider;

import android.content.Context;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.trackyourmoney.java.AppDataBase;
import com.trackyourmoney.java.Ausgabe;
import com.trackyourmoney.java.AusgabeDAO;
import com.trackyourmoney.java.Budget;
import com.trackyourmoney.java.DatabaseClient;
import com.trackyourmoney.java.Einnahme;
import com.trackyourmoney.java.EinnahmeDAO;
import com.trackyourmoney.java.Kategorie;
import com.trackyourmoney.java.KategorieDAO;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ControllerUnitTest {

    private AppDataBase database;
    private EinnahmeDAO einnahmeDao;
    private AusgabeDAO ausgabeDao;
    private KategorieDAO kategorieDao;
    private DatabaseClient databaseClient;
    private long katId;
    private long  einnahmeId;
    Einnahme newEinnahme;

    @Before
    public void initializeDataBank() {
        database = Room.inMemoryDatabaseBuilder(
                        ApplicationProvider.getApplicationContext(),
                        AppDataBase.class)
                .allowMainThreadQueries()
                .build();
        einnahmeDao = database.einnahmeDao();
        ausgabeDao = database.ausgabeDao();
        kategorieDao = database.kategorieDao();
        Kategorie kat = new Kategorie("ersteKategorie", 300.0);
        katId = kategorieDao.insert(kat);
    }

    @Test
    public void addAndRemoveEinnahme() {
        // Add an Einnahme
        Einnahme einnahme = new Einnahme();
        einnahme.name = "Test Income";
        einnahme.value = 5000.0;
        einnahme.kategorieId = katId;
        long einnahmeId = einnahmeDao.insert(einnahme);
        assertNotNull(einnahmeId);

        // Verify Einnahme was added
        List<Einnahme> einnahmen = einnahmeDao.getAllEinnahmen();
        assertEquals(1, einnahmen.size());
        assertEquals("Test Income", einnahmen.get(0).name);
        assertEquals(5000.0, einnahmen.get(0).value, 0.01);

        Einnahme einnahmeToDelete = einnahmeDao.findById(einnahmeId);
        assertNotNull(einnahmeToDelete);
        einnahmeDao.delete(einnahmeToDelete);

        einnahmen.clear();
        // Verify Einnahme was removed
        einnahmen = einnahmeDao.getAllEinnahmen();
        assertTrue(einnahmen.isEmpty());
    }

    @Test
    public void addAndRemoveAusgabe() {
        // Add an Einnahme
        Ausgabe ausgabe = new Ausgabe();
        ausgabe.name = "Test Income";
        ausgabe.betrag = 5000.0;
        ausgabe.kategorieId = katId;
        long ausgabeId = ausgabeDao.insert(ausgabe);
        assertNotNull(ausgabeId);

        // Verify Einnahme was added
        List<Ausgabe> ausgaben = ausgabeDao.getAllAusgaben();
        assertEquals(1, ausgaben.size());
        assertEquals("Test Income", ausgaben.get(0).name);
        assertEquals(5000.0, ausgaben.get(0).betrag, 0.01);

        Ausgabe ausgabeToDelete = ausgabeDao.findById(ausgabeId);
        assertNotNull(ausgabeToDelete);
        ausgabeDao.delete(ausgabeToDelete);

        ausgaben.clear();
        // Verify Einnahme was removed
        ausgaben = ausgabeDao.getAllAusgaben();
        assertTrue(ausgaben.isEmpty());
    }

    @Test
    public void addNewCategory() {
        Kategorie kategorie = new Kategorie("testKategorie", 400.0);
        long kategorieID = kategorieDao.insert(kategorie);
        assertNotNull(kategorieID);
    }

    @After
    public void removeDataBank() {
        database.close();
    }
}
