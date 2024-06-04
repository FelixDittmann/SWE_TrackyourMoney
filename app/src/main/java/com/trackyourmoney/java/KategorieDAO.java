package com.trackyourmoney.java;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface KategorieDAO {
    @Insert
    long insert(Kategorie kategorie);

    @Update
    void update(Kategorie kategorie);

    @Delete
    void delete(Kategorie kategorie);

    @Query("SELECT * FROM Kategorie")
    List<Kategorie> getAllKategorien();

    @Query("SELECT * FROM Kategorie WHERE id = :katid")
    Kategorie getKategorieById(long katid);

    @Query("SELECT * FROM Kategorie WHERE name = :name")
    List<Kategorie> getKategorieByName(String name);

}