package com.example.androidtask.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactsDao {
    @Insert
    void insert(Contacts contact);
    @Update
    void update(Contacts contact);
    @Delete
    void delete(Contacts contact);

    @Query("SELECT * FROM Contacts")
    List<Contacts> getAllContacts();
}


