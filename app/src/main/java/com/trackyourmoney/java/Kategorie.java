package com.trackyourmoney.java;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Kategorie {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;
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
