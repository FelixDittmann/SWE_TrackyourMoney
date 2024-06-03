package com.trackyourmoney.java;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Einnahme.class, Ausgabe.class, Kategorie.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDataBase extends RoomDatabase {
    public abstract EinnahmeDAO einnahmeDao();
    public abstract AusgabeDAO ausgabeDao();
    public abstract KategorieDAO kategorieDao();
}