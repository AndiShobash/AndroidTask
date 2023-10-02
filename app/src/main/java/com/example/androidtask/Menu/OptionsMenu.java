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
import com.example.androidtask.MainActivity;
import com.example.androidtask.R;

/************************************************ This is the option menu is handled**********************************************************/
public class OptionsMenu extends AppCompatActivity {

    private String email;

    //creates the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    // handle item selection from the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_contact) {//if selected orders the it will go to see the orders in the db
            add_new_contact();
        }
        //if the settings is selected then it makes a fragment for preferences
        else if (item.getItemId()== R.id.Settings){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(android.R.id.content ,new ContactList.MyPreferences()).addToBackStack(null)
                    .commit();
        }


        return super.onOptionsItemSelected(item);
    }

    //goes to the ContactAdd if it is selected
    public void add_new_contact() {
        Intent intent = new Intent(OptionsMenu.this, ContactAdd.class);
        email = ContactList.getUserEmail();
        intent.putExtra("email", email);
        startActivity(intent);
    }
}
