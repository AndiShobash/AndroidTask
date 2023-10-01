package com.example.androidtask;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidtask.model.Contacts;
import com.example.androidtask.model.ContactsDB;
import com.example.androidtask.model.RequesetResponse;
import com.example.androidtask.model.UsersDB;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class ContactAdd extends AppCompatActivity {

    interface RequestUser {
        @GET("/")
        Call<RequesetResponse> getGender(@Query("name") String name);
    }
    private RadioButton gender_radio;
    private String user_email;

    private String gender;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_add);
        EditText etn_first_name = (EditText) findViewById(R.id.first_name);
        EditText etn_last_name = (EditText) findViewById(R.id.last_name);
        EditText etn_email = (EditText) findViewById(R.id.email);
        EditText etn_mobile = (EditText) findViewById(R.id.mobile);
        EditText etn_address = (EditText) findViewById(R.id.address);
        Button btn_add_contact = (Button) findViewById(R.id.add_btn);
        ContactsDB db = ContactsDB.getInstance(ContactAdd.this);

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



                RequesetResponse requesetResponse= new RequesetResponse();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.genderize.io/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                RequestUser requestUser = retrofit.create(RequestUser.class);

                requestUser.getGender(first_name).enqueue(new Callback<RequesetResponse>() {
                    @Override
                    public void onResponse(Call<RequesetResponse> call, Response<RequesetResponse> response) {
                        //save_data(user_email,first_name,last_name,email,mobile,address,response.body().getGender());
                        Contacts contact = new Contacts(user_email, first_name, last_name, email, mobile, address, response.body().getGender());
                        db.getContactDao().insert(contact);
                        Toast.makeText(getApplicationContext(), "Added Contact Successfully ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ContactAdd.this, ContactList.class);
                        startActivity(intent);
                    }
                    @Override
                    public void onFailure(Call<RequesetResponse> call, Throwable t) {
                        System.out.println("Failed!!!! " + t.getMessage());
                    }
                });

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ContactAdd.this, ContactList.class);
        startActivity(intent);
    }
}
