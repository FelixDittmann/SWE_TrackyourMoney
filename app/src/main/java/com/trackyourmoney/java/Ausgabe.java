package com.trackyourmoney.java;

import java.time.OffsetDateTime;
import java.util.Date;

public class Ausgabe extends Posten{
    public Ausgabe(String name, double wert, String anmerkungen, Date datum, boolean wiederholend, Kategorie kategorie, OffsetDateTime wiederholungsintervall) {
        this.name = name;
        this.value = wert;
        this.anmerkungen = anmerkungen;
        this.datum = datum;
        this.kategorie = kategorie;
        this.wiederholend = wiederholend;
        this.wiederholungsintervall = wiederholungsintervall;
    }
}
