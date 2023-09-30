package com.example.androidtask;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtask.Menu.OptionsMenu;
import com.example.androidtask.model.Contacts;

import java.util.List;

public class ContactList extends OptionsMenu {

    private static String user_email;

     String [] test_names = {"Andi","3bed","Anas","Aian","Noah","Matin","Naty","Adam","Shan","Asharf","Hamza"};
    private static ContactViewModel contactViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list);
        RecyclerView recyclerView = findViewById(R.id.recycler_view_contacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        contactViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(ContactViewModel.class);
        ContactAdapter adapter = new ContactAdapter(this,ContactList.this,contactViewModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getApplication()));


        Bundle extras = getIntent().getExtras();// Using the bundle we can get the sent items we want
        if (extras != null) {

            user_email = extras.getString("email");
            //The key argument here must match that used in the other activity
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public static String getUserEmail(){
        return user_email;
    }

    public void call_app_contact_full_info(Contacts contact) {
        Intent intent = new Intent(ContactList.this,ContactFullInfo.class);
        intent.putExtra("firstname",contact.getFirst_name());
        intent.putExtra("lastname",contact.getLast_name());
        intent.putExtra("email",contact.getFirst_name());
        intent.putExtra("mobile",contact.getLast_name());
        intent.putExtra("address",contact.getFirst_name());
        intent.putExtra("gender",contact.getLast_name());
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ContactList.this, MainActivity.class);
        startActivity(intent);
    }
}
