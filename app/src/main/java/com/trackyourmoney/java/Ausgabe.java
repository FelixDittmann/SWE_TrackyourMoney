package com.trackyourmoney.java;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.OffsetDateTime;
import java.util.Date;

@Entity(foreignKeys = @ForeignKey(entity = Kategorie.class,
        parentColumns = "id",
        childColumns = "kategorieId",
        onDelete = ForeignKey.CASCADE))
public class Ausgabe extends Posten{
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String name;
    public double betrag;
    public long date;
    public long kategorieId; // Foreign key reference to Kategorie table
    public boolean wiederholend;
    public int wiederholungsintervall;
    public Ausgabe(String name, double wert, String anmerkungen, Date datum, boolean wiederholend, long kategorie, int wiederholungsintervall) {
        this.name = name;
        this.value = wert;
        this.anmerkungen = anmerkungen;
        this.datum = datum;
        this.kategorieId = kategorie;
        this.wiederholend = wiederholend;
        this.wiederholungsintervall = wiederholungsintervall;
    }

}