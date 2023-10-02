package com.example.androidtask.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
/************************************************ Creating the database for Contacts**********************************************************/
@Database(entities = {Contacts.class},version = 1)
public abstract class ContactsDB extends RoomDatabase {
    private static ContactsDB instance;

    public abstract ContactsDao getContactDao();

    public static ContactsDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), ContactsDB.class, "contacts_database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }
}
