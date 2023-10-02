package com.example.androidtask.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/************************************************ Creating the database for Users**********************************************************/
@Database(entities = {Users.class},version = 1)
public abstract class UsersDB extends RoomDatabase {
    private static UsersDB instance;

    public abstract UsersDao getUserDao();

    public static UsersDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), UsersDB.class, "users_database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }
}
