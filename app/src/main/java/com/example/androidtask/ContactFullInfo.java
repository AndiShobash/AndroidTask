package com.example.androidtask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidtask.Menu.OptionsMenu;
import com.example.androidtask.model.Contacts;
import com.example.androidtask.model.ContactsDB;
import com.example.androidtask.model.UsersDB;

/************************************************ This is where we show all the selected contact's information **********************************************************/
public class ContactFullInfo extends AppCompatActivity {
    private int user_id;
    private String first_name;
    private String last_name;
    private String email;
    private String mobile;
    private String address;
    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_full_info);
        TextView txt_first_name = findViewById(R.id.text_first_name);
        TextView txt_last_name = findViewById(R.id.text_last_name);
        TextView txt_email = findViewById(R.id.text_email);
        TextView txt_mobile = findViewById(R.id.text_mobile);
        TextView txt_address = findViewById(R.id.text_address);
        TextView txt_gender = findViewById(R.id.text_gender);
        ContactsDB db = ContactsDB.getInstance(ContactFullInfo.this);
        Button btn_edit_contact = findViewById(R.id.edit_btn);
        Bundle extras = getIntent().getExtras();// Using the bundle we can get the sent id of the contact
        if (extras != null) {

            user_id = extras.getInt("id");

        }
        Contacts contact = new Contacts();
        contact = db.getContactDao().getContact(user_id);
        txt_first_name.setText(contact.getFirst_name());
        txt_last_name.setText(contact.getLast_name());
        txt_email.setText(contact.getEmail());
        txt_mobile.setText(contact.getMobile_number());
        txt_address.setText(contact.getAddress());
        txt_gender.setText(contact.getGender());

        //if the edit button has clicked it sends the contact's id and goes to the ContactEdit
        btn_edit_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactFullInfo.this, ContactEdit.class);
                intent.putExtra("id", user_id);
                startActivity(intent);
            }
        });
    }

    //when pressed on the back button it will go to the ContactList activity
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ContactFullInfo.this, ContactList.class);
        startActivity(intent);
    }
}
