package com.example.androidtask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidtask.model.Contacts;
import com.example.androidtask.model.ContactsDB;
import com.example.androidtask.model.UsersDB;

public class ContactAdd extends AppCompatActivity {
    private RadioButton gender_radio;
    private String user_email;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_add);
        EditText etn_first_name = (EditText) findViewById(R.id.first_name);
        EditText etn_last_name = (EditText) findViewById(R.id.last_name);
        EditText etn_email = (EditText) findViewById(R.id.email);
        EditText etn_mobile = (EditText) findViewById(R.id.mobile);
        EditText etn_address = (EditText) findViewById(R.id.address);
        Button btn_add_contact = (Button) findViewById(R.id.add_btn);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        ContactsDB db = ContactsDB.getInstance(ContactAdd.this);
        radioGroup.clearCheck();

        Bundle extras = getIntent().getExtras();// Using the bundle we can get the sent items we want
        if (extras != null) {

            user_email = extras.getString("email");
            //The key argument here must match that used in the other activity
        }

        btn_add_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first_name = etn_first_name.getText().toString().trim();
                String last_name = etn_last_name.getText().toString().trim();
                String email = etn_email.getText().toString().trim();
                String mobile = etn_mobile.getText().toString();
                String address = etn_address.getText().toString().trim();
                int selected_id = radioGroup.getCheckedRadioButtonId();
                if (selected_id != -1) {
                    gender_radio = findViewById(selected_id);
                }
                String gender = gender_radio.getText().toString().trim();

                Contacts contact = new Contacts(user_email, first_name, last_name, email, mobile, address, gender);
                db.getContactDao().insert(contact);
                Toast.makeText(getApplicationContext(), "Added Contact Successfully ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ContactAdd.this, ContactList.class);
                startActivity(intent);
            }
        });
    }

}
