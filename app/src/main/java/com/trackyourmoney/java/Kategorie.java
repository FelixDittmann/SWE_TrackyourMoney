package com.trackyourmoney.java;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Kategorie {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;
    private Budget budget;

    public Kategorie(String name) {
        this.name = name;
    }
    public Kategorie(String name, Budget budget) {
        this.name = name;
        this.budget = budget;
    }

}
