package com.trackyourmoney.java;

import java.util.List;

public class Kategorie {
    private String name;
    private List<Posten> elemente;
    private Budget budget;

    public Kategorie(String name) {
        this.name = name;
    }

    public void postenHinzufügen(Posten neuerPosten) {
        elemente.add(neuerPosten);
    }

    public void postenEntfernen(Posten posten) {
        elemente.remove(posten);
    }
}
