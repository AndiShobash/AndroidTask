package com.example.androidtask;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidtask.Menu.OptionsMenu;
import com.example.androidtask.model.Contacts;
import com.example.androidtask.model.ContactsDB;

public class ContactEdit extends OptionsMenu {
    private RadioButton gender_radio;

    private int user_id;
    private String user_email;
    private String contact_first_name;
    private String contact_last_name;
    private String contact_email;
    private String contact_mobile;
    private String contact_address;
    private String contact_gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_edit);
        EditText edt_first_name = findViewById(R.id.first_name_edit);
        EditText edt_last_name = findViewById(R.id.last_name_edit);
        EditText edt_email = findViewById(R.id.email_edit);
        EditText edt_mobile = findViewById(R.id.mobile_edit);
        EditText edt_address = findViewById(R.id.address_edit);
        RadioGroup radioGroup = findViewById(R.id.radio_group_edit);
        Button btn_edit_contact = findViewById(R.id.edit_btn);
        ContactsDB db = ContactsDB.getInstance(ContactEdit.this);
        radioGroup.clearCheck();
        Bundle extras = getIntent().getExtras();// Using the bundle we can get the sent items we want
        if (extras != null) {

            user_email = ContactList.getUserEmail();
            user_id = extras.getInt("id");
            contact_first_name = extras.getString("firstname");
            contact_last_name = extras.getString("lastname");
            contact_email = extras.getString("email");
            contact_mobile = extras.getString("mobile");
            contact_address = extras.getString("address");
            contact_gender = extras.getString("gender");
            //The key argument here must match that used in the other activity
        }
        edt_first_name.setText(contact_first_name);
        edt_last_name.setText(contact_last_name);
        edt_email.setText(contact_email);
        edt_mobile.setText(contact_mobile);
        edt_address.setText(contact_address);
        if (contact_gender.equals("male")) {
            radioGroup.check(R.id.male_edit);
        } else {
            radioGroup.check(R.id.female_edit);
        }
        btn_edit_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first_name = edt_first_name.getText().toString().trim();
                String last_name = edt_last_name.getText().toString().trim();
                String email = edt_email.getText().toString().trim();
                String mobile = edt_mobile.getText().toString().trim();
                String address = edt_address.getText().toString().trim();
                int selected_id = radioGroup.getCheckedRadioButtonId();
                if (selected_id != -1) {
                    gender_radio = findViewById(selected_id);
                }
                String gender = gender_radio.getText().toString().trim();
                Contacts contacts = new Contacts(user_email, first_name, last_name, email, mobile, address, gender);
                contacts.setId(user_id);
                db.getContactDao().update(contacts);
                Toast.makeText(getApplicationContext(), "Edited Successfully ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ContactEdit.this, ContactFullInfo.class);
                intent.putExtra("id",contacts.getId());
                intent.putExtra("firstname",contacts.getFirst_name());
                intent.putExtra("lastname",contacts.getLast_name());
                intent.putExtra("email",contacts.getEmail());
                intent.putExtra("mobile",contacts.getMobile_number());
                intent.putExtra("address",contacts.getAddress());
                intent.putExtra("gender",contacts.getGender());
                startActivity(intent);
            }
        });
    }
}
