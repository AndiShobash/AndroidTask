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
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/************************************************ This is the where the user add a new contact to the list **********************************************************/
public class ContactAdd extends AppCompatActivity {

    //an interface for using api request for GET to get the gender of the new contact
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

        Bundle extras = getIntent().getExtras();// Using the bundle we can get the user's email
        if (extras != null) {

            user_email = extras.getString("email");
            //The key argument here must match that used in the other activity
        }

        //add new contact button has been clicked and will start to add new contact
        btn_add_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first_name = etn_first_name.getText().toString().trim();
                String last_name = etn_last_name.getText().toString().trim();
                String email = etn_email.getText().toString().trim();
                String mobile = etn_mobile.getText().toString();
                String address = etn_address.getText().toString().trim();
                //Check if all the fields are not empty
                if (first_name.isEmpty() || last_name.isEmpty() || email.isEmpty() || mobile.isEmpty() || address.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else if(!isValid(email)){
                    Toast.makeText(getApplicationContext(), "Enter a valid email", Toast.LENGTH_SHORT).show();
                } else if (mobile.length()!=10) {
                    Toast.makeText(getApplicationContext(), "Enter a valid mobile number", Toast.LENGTH_SHORT).show();

                } else {
                    //using retrofit to make an api request to the URL and get the gender
                    RequesetResponse requesetResponse = new RequesetResponse();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://api.genderize.io/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    RequestUser requestUser = retrofit.create(RequestUser.class);

                    //making the api request and doing it asynchronous
                    requestUser.getGender(first_name).enqueue(new Callback<RequesetResponse>() {

                        //here we get the response of the api call if it is succeeded and then
                        // we save the contact in the database and go back to the ContactList
                        @Override
                        public void onResponse(Call<RequesetResponse> call, Response<RequesetResponse> response) {
                            //save_data(user_email,first_name,last_name,email,mobile,address,response.body().getGender());
                            Contacts contact = new Contacts(user_email, first_name, last_name, email, mobile, address, response.body().getGender());
                            db.getContactDao().insert(contact);
                            Toast.makeText(getApplicationContext(), "Added Contact Successfully ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ContactAdd.this, ContactList.class);
                            startActivity(intent);
                        }

                        //here we go if the api request has failed and display the error in a Toast
                        @Override
                        public void onFailure(Call<RequesetResponse> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
    //checks if the email format is valid or not
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
    //if pressed back it will go back to the ContactList
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ContactAdd.this, ContactList.class);
        startActivity(intent);
    }
}
