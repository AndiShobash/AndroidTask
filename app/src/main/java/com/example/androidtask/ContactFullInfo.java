package com.example.androidtask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.androidtask.Menu.OptionsMenu;
import com.example.androidtask.model.UsersDB;

public class ContactFullInfo extends OptionsMenu {
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
        Button btn_edit_contact = findViewById(R.id.edit_btn);
        Bundle extras = getIntent().getExtras();// Using the bundle we can get the sent items we want
        if (extras != null) {

            user_id = extras.getInt("id");
            first_name = extras.getString("firstname");
            last_name = extras.getString("lastname");
            email = extras.getString("email");
            mobile = extras.getString("mobile");
            address = extras.getString("address");
            gender = extras.getString("gender");

        }
        txt_first_name.setText(first_name);
        txt_last_name.setText(last_name);
        txt_email.setText(email);
        txt_mobile.setText(mobile);
        txt_address.setText(address);
        txt_gender.setText(gender);

        btn_edit_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactFullInfo.this, ContactEdit.class);
                intent.putExtra("id",user_id);
                intent.putExtra("firstname", first_name);
                intent.putExtra("lastname", last_name);
                intent.putExtra("email", email);
                intent.putExtra("mobile", mobile);
                intent.putExtra("address", address);
                intent.putExtra("gender", gender);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ContactFullInfo.this, ContactList.class);
        startActivity(intent);
    }
}
