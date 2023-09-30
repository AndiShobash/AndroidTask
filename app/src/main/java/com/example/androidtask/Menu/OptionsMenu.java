package com.example.androidtask.Menu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidtask.ContactAdd;
import com.example.androidtask.ContactList;
import com.example.androidtask.R;


public class OptionsMenu extends AppCompatActivity {

    private String email;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.add_contact) {//if selected orders the it will go to see the orders in the db
            add_new_contact();
        }


        return super.onOptionsItemSelected(item);
    }

    public void add_new_contact() {
        Intent intent = new Intent(OptionsMenu.this, ContactAdd.class);
        email = ContactList.getUserEmail();
        intent.putExtra("email", email);
        startActivity(intent);
    }
}