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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidtask.Menu.OptionsMenu;
import com.example.androidtask.model.Contacts;
import com.example.androidtask.model.ContactsDB;

import java.util.regex.Pattern;

/************************************************ This is the where the user can edit the contact's information**********************************************************/
public class ContactEdit extends AppCompatActivity {
    private RadioButton gender_radio;

    private int user_id;
    private String user_email;
    private EditText edt_first_name;
    private EditText edt_last_name;
    private EditText edt_email;
    private EditText edt_mobile;
    private EditText edt_address;
    private  RadioGroup radioGroup;
    private ContactsDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_edit);
         edt_first_name = findViewById(R.id.first_name_edit);
         edt_last_name = findViewById(R.id.last_name_edit);
         edt_email = findViewById(R.id.email_edit);
         edt_mobile = findViewById(R.id.mobile_edit);
         edt_address = findViewById(R.id.address_edit);
         radioGroup = findViewById(R.id.radio_group_edit);
        Button btn_edit_contact = findViewById(R.id.edit_btn);
        db = ContactsDB.getInstance(ContactEdit.this);
        radioGroup.clearCheck();
        Bundle extras = getIntent().getExtras();// Using the bundle we can get the id of the contact
        if (extras != null) {

            user_email = ContactList.getUserEmail();
            user_id = extras.getInt("id");
            //The key argument here must match that used in the other activity
        }
        Contacts contact = new Contacts();
        contact = db.getContactDao().getContact(user_id);
        edt_first_name.setText(contact.getFirst_name());
        edt_last_name.setText(contact.getLast_name());
        edt_email.setText(contact.getEmail());
        edt_mobile.setText(contact.getMobile_number());
        edt_address.setText(contact.getAddress());
        if (contact.getGender().equals("male")) {
            radioGroup.check(R.id.male_edit);
        } else {
            radioGroup.check(R.id.female_edit);
        }

        //edit button has been clicked and will edit the database
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
                //Check if all the fields are not empty
                if (first_name.isEmpty() || last_name.isEmpty() || email.isEmpty() || mobile.isEmpty() || address.isEmpty() || selected_id == -1) {
                    Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                } else if (!isValid(email)) {
                    Toast.makeText(getApplicationContext(), "Enter a valid email", Toast.LENGTH_SHORT).show();
                } else if (mobile.length() != 10) {
                    Toast.makeText(getApplicationContext(), "Enter a valid mobile number", Toast.LENGTH_SHORT).show();

                } else {
                    String gender = gender_radio.getText().toString().trim();
                    Contacts contacts = new Contacts(user_email, first_name, last_name, email, mobile, address, gender);
                    contacts.setId(user_id);
                    db.getContactDao().update(contacts);
                    Toast.makeText(getApplicationContext(), "Edited Successfully ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ContactEdit.this, ContactFullInfo.class);//going back to the contact full information and giving him the id of the contact
                    intent.putExtra("id", contacts.getId());
                    startActivity(intent);
                }
            }
        });
    }
    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}
