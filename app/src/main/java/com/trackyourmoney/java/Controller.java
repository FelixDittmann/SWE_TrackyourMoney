package com.trackyourmoney.java;

import android.icu.util.DateInterval;

import java.time.OffsetDateTime;
import java.util.Date;

public class Controller {
    public void seitendatenAktualisieren() {

    }

    public void postenDetails(int index) {

    }

    public Posten ausgabeErstellen(String name, double wert, long datum, String anmerkungen, Long kategorie, int wiederholungsintervall) {
        Ausgabe newPosten = new Ausgabe(name, wert, anmerkungen, datum, false, kategorie, wiederholungsintervall);
        return newPosten;
    }

    public Posten einnahmeErstellen(String name, double wert, long datum, String anmerkungen, long kategorie, int wiederholungsintervall) {
        Einnahme newPosten = new Einnahme(name, wert, anmerkungen, datum, false, kategorie, wiederholungsintervall);
        return newPosten;
    }
}
