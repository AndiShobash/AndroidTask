package com.example.androidtask;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtask.Menu.OptionsMenu;
import com.example.androidtask.model.Contacts;

import java.util.List;

/************************************************ This is where all the user's contacts will display**********************************************************/
public class ContactList extends OptionsMenu {

    private static String user_email;

    private static ContactViewModel contactViewModel;

    private static boolean in_fragment = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list);
        Bundle extras = getIntent().getExtras();// Using the bundle we can get the sent items we want
        if (extras != null) {

            user_email = extras.getString("email");
            //The key argument here must match that used in the other activity
        }
        //Activate the RecyclerView and the ViewModel
        RecyclerView recyclerView = findViewById(R.id.recycler_view_contacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        contactViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(ContactViewModel.class);
        ContactAdapter adapter = new ContactAdapter(ContactList.this, this, contactViewModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getApplication()));

    }

    //Creates the Options Menu
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    //A static function to return the user's email who logged in
    public static String getUserEmail() {
        return user_email;
    }

    //A function calls to the ContactFullInfo to watch the full info of the contact and past the contact information
    public void call_app_contact_full_info(Contacts contact) {
        Intent intent = new Intent(ContactList.this, ContactFullInfo.class);
        intent.putExtra("id", contact.getId());
        intent.putExtra("firstname", contact.getFirst_name());
        intent.putExtra("lastname", contact.getLast_name());
        intent.putExtra("email", contact.getEmail());
        intent.putExtra("mobile", contact.getMobile_number());
        intent.putExtra("address", contact.getAddress());
        intent.putExtra("gender", contact.getGender());
        startActivity(intent);
    }

    //When pressed back in the fragment of the Preference it closes the fragment else going back to the login
    @Override
    public void onBackPressed() {
        if (in_fragment) {
            //getSupportFragmentManager().popBackStack();
            finish();
            startActivity(getIntent());
            in_fragment = false;
        } else {
            Intent intent = new Intent(ContactList.this, MainActivity.class);
            startActivity(intent);
        }
    }

    /************************************************ Preference to decide what to display in the contact list **********************************************************/
    public static class MyPreferences extends PreferenceFragmentCompat {
        SwitchPreference mobile;
        SwitchPreference email;
        SwitchPreference address;
        SwitchPreference gender;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.mypreferencescreen, rootKey);
            mobile = (SwitchPreference) findPreference("MobileNumber");
            email = (SwitchPreference) findPreference("Email");
            address = (SwitchPreference) findPreference("Address");
            gender = (SwitchPreference) findPreference("Gender");
            in_fragment = true;
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            view.setBackgroundColor(Color.WHITE);
            super.onViewCreated(view, savedInstanceState);
        }
        //If one of the Preferences is clicked than it disable the others and
        // the one is picked displayed in the contact list
        public boolean onPreferenceTreeClick(Preference preference) {
            if (preference.getKey().equals("Email")) {
                mobile.setChecked(false);
                address.setChecked(false);
                gender.setChecked(false);
                return true; // Return true to indicate that you've handled the click
            }
            if (preference.getKey().equals("MobileNumber")) {
                email.setChecked(false);
                address.setChecked(false);
                gender.setChecked(false);
                return true;
            }
            if (preference.getKey().equals("Address")) {
                mobile.setChecked(false);
                email.setChecked(false);
                gender.setChecked(false);
                return true;
            }
            if (preference.getKey().equals("Gender")) {
                mobile.setChecked(false);
                address.setChecked(false);
                email.setChecked(false);
                return true;
            }
            return super.onPreferenceTreeClick(preference);
        }

    }
}
