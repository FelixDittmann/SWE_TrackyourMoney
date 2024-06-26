package com.trackyourmoney.java;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.time.OffsetDateTime;
import java.util.Date;

@Entity(foreignKeys = @ForeignKey(entity = Kategorie.class,
        parentColumns = "id",
        childColumns = "kategorieId",
        onDelete = ForeignKey.CASCADE))
@TypeConverters(DateConverter.class)
public class Ausgabe extends Posten{

    @PrimaryKey(autoGenerate = true)
    public long id;
    public String name;
    public double betrag;
    public String anmerkungen;
    public Date date;
    public Long kategorieId; // Foreign key reference to Kategorie table
    public boolean wiederholend;
    public int wiederholungsintervall;
    public Ausgabe() {

    }
    public Ausgabe(String name, double wert, String anmerkungen, Date datum, boolean wiederholend, Long kategorie, int wiederholungsintervall) {
        this.name = name;
        this.betrag = wert;
        this.anmerkungen = anmerkungen;
        this.date = datum;
        this.kategorieId = kategorie;
        this.wiederholend = wiederholend;
        this.wiederholungsintervall = wiederholungsintervall;
    }

}
