package com.trackyourmoney.java;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EinnahmeDAO {
    @Insert
    long insert(Einnahme einnahme);

    @Update
    void update(Einnahme einnahme);

    @Delete
    void delete(Einnahme einnahme);

    @Query("SELECT * FROM Einnahme")
    List<Einnahme> getAllEinnahmen();

    @Query("SELECT e.* FROM Einnahme e " +
            "INNER JOIN Kategorie k ON e.kategorieId = k.id " +
            "WHERE k.name = :kategorieName")
    List<Einnahme> getEinnahmenForKategorieName(String kategorieName);

    @Query("SELECT e.* FROM Einnahme e " +
            "INNER JOIN Kategorie k ON e.kategorieId = k.id " +
            "WHERE k.name = :kategorieName AND e.value BETWEEN :minBetrag AND :maxBetrag")
    List<Einnahme> getEinnahmenForKategorieNameAndBetragRange(String kategorieName, double minBetrag, double maxBetrag);
}