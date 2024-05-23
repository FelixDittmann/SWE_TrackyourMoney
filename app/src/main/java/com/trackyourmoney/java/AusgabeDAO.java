package com.trackyourmoney.java;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AusgabeDAO {
    @Insert
    long insert(Ausgabe ausgabe);

    @Update
    void update(Ausgabe ausgabe);

    @Delete
    void delete(Ausgabe ausgabe);

    @Query("SELECT * FROM Ausgabe")
    List<Ausgabe> getAllAusgaben();

    @Query("SELECT a.* FROM Ausgabe a " +
            "INNER JOIN Kategorie k ON a.kategorieId = k.id " +
            "WHERE k.name = :kategorieName")
    List<Einnahme> getAusgabenForKategorieName(String kategorieName);

    @Query("SELECT a.* FROM Einnahme a " +
            "INNER JOIN Kategorie k ON a.kategorieId = k.id " +
            "WHERE k.name = :kategorieName AND a.value BETWEEN :minBetrag AND :maxBetrag")
    List<Einnahme> getAusgabenForKategorieNameAndBetragRange(String kategorieName, double minBetrag, double maxBetrag);
}
