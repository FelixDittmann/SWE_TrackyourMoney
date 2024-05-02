package com.trackyourmoney.java;

public class DatabaseContract {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MyDatabase.db";

    private DatabaseContract() {} // Private constructor to prevent instantiation

    public static class EinnahmeEntry {
        public static final String TABLE_NAME = "einnahmen";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_BETRAG = "betrag";
    }

    public static class AusgabeEntry {
        public static final String TABLE_NAME = "ausgaben";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_BETRAG = "betrag";
    }
}
