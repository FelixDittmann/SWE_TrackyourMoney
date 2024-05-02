package com.trackyourmoney.java;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(Context context) {
        super(context, DatabaseContract.DATABASE_NAME, null, DatabaseContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables
        String SQL_CREATE_EINNAHME_TABLE = "CREATE TABLE " +
                DatabaseContract.EinnahmeEntry.TABLE_NAME + " (" +
                DatabaseContract.EinnahmeEntry.COLUMN_ID + " INTEGER PRIMARY KEY," +
                DatabaseContract.EinnahmeEntry.COLUMN_NAME + " TEXT," +
                DatabaseContract.EinnahmeEntry.COLUMN_BETRAG + " REAL)";
        db.execSQL(SQL_CREATE_EINNAHME_TABLE);

        // Similar for Ausgabe table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade the database
    }
}
