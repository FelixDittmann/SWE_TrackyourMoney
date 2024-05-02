package com.trackyourmoney.java;

import android.icu.util.DateInterval;

public class Budget {
    private String name;
    private double wert;
    private String anmerkungen;
    private Kategorie kategorie;
    private DateInterval gültigkeitzeitraum;

    public Budget(String name, double wert, String anmerkungen, Kategorie kategorie, DateInterval gültigkeitzeitraum) {
        this.name = name;
        this.wert = wert;
        this.anmerkungen = anmerkungen;
        this.kategorie = kategorie;
        this.gültigkeitzeitraum = gültigkeitzeitraum;
    }

    public void bearbeiten(String name, double wert, String anmerkungen, Kategorie kategorie, DateInterval gültigkeitzeitraum) {
        this.name = name;
        this.wert = wert;
        this.anmerkungen = anmerkungen;
        this.kategorie = kategorie;
        this.gültigkeitzeitraum = gültigkeitzeitraum;
    }
}
