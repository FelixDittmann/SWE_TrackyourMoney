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
public class Einnahme extends Posten{

    @PrimaryKey(autoGenerate = true)
    public long id;
    public String name;
    public double value;
    public String anmerkungen;
    public Date date;
    public boolean wiederholend;
    public Long kategorieId;
    public int wiederholungsintervall;

    public Einnahme(){

    }
    public Einnahme(String name, double wert, String anmerkungen, Date datum, boolean wiederholend, long kategorie, int wiederholungsintervall) {
        this.name = name;
        this.value = wert;
        this.anmerkungen = anmerkungen;
        this.date = datum;
        this.kategorieId = kategorie;
        this.wiederholend = wiederholend;
        this.wiederholungsintervall = wiederholungsintervall;
    }
}
