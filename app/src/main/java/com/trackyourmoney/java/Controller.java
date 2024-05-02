package com.trackyourmoney.java;

import android.icu.util.DateInterval;

import java.time.OffsetDateTime;
import java.util.Date;

public class Controller {
    public void seitendatenAktualisieren() {

    }

    public void postenDetails(int index) {

    }

    public Posten ausgabeErstellen(String name, double wert, Date datum, String anmerkungen, Kategorie kategorie, OffsetDateTime wiederholungsintervall) {
        Ausgabe newPosten = new Ausgabe(name, wert, anmerkungen, datum, false, kategorie, wiederholungsintervall);
        return newPosten;
    }

    public Posten einnahmeErstellen(String name, double wert, Date datum, String anmerkungen, Kategorie kategorie, OffsetDateTime wiederholungsintervall) {
        Einnahme newPosten = new Einnahme(name, wert, anmerkungen, datum, false, kategorie, wiederholungsintervall);
        return newPosten;
    }
}
