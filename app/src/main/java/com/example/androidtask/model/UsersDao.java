package com.example.androidtask.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UsersDao {
    @Insert
    void insert(Users user);
    @Update
    void update(Users user);
    @Delete
    void delete(Users user);

    @Query("SELECT * FROM Users")
    List<Users> getAllUsers();
}
