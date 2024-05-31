package com.trackyourmoney.java;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Kategorie {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;
    public Double budget;

    public Kategorie(String name, Double budget) {
        this.name = name;
        this.budget = budget;
    }
    public Double getBudget() {
        return this.budget;
    }

}
