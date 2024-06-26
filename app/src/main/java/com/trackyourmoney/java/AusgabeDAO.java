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

    @Query("SELECT * FROM Ausgabe WHERE id = :ausgabeId LIMIT 1")
    Ausgabe findById(long ausgabeId);

    @Query("SELECT a.* FROM Ausgabe a " +
            "INNER JOIN Kategorie k ON a.kategorieId = k.id " +
            "WHERE k.name = :kategorieName")
    List<Ausgabe> getAusgabenForKategorieName(String kategorieName);

    @Query("SELECT a.* FROM Ausgabe a " +
            "INNER JOIN Kategorie k ON a.kategorieId = k.id " +
            "WHERE k.name = :kategorieName AND a.value BETWEEN :minBetrag AND :maxBetrag")
    List<Ausgabe> getAusgabenForKategorieNameAndBetragRange(String kategorieName, double minBetrag, double maxBetrag);
}
