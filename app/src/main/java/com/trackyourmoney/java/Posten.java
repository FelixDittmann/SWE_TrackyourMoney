package com.trackyourmoney.java;

import java.time.OffsetDateTime;
import java.util.Date;

public abstract class Posten {
    String name;
    double value;
    String anmerkungen;
    long datum;
    boolean wiederholend;
    long kategorieId;
    OffsetDateTime wiederholungsintervall;
    public void bearbeiten(String name, String anmerkungen) {
        this.name = name;
        this.anmerkungen = anmerkungen;
    }

    public void bearbeiten(double wert) {
        this.value = wert;
    }
}
