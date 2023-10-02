package com.example.androidtask;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidtask.Menu.OptionsMenu;
import com.example.androidtask.model.Users;
import com.example.androidtask.model.UsersDB;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/************************************************ Register Page **********************************************************/
public class Register extends AppCompatActivity {
    private RadioButton gender_radio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        EditText etn_first_name = (EditText) findViewById(R.id.first_name);
        EditText etn_last_name = (EditText) findViewById(R.id.last_name);
        EditText etn_email = (EditText) findViewById(R.id.email);
        EditText etn_password = (EditText) findViewById(R.id.password);
        EditText etn_mobile = (EditText) findViewById(R.id.mobile);
        EditText etn_address = (EditText) findViewById(R.id.address);
        Button btn_register = (Button) findViewById(R.id.register_btn);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        UsersDB db = UsersDB.getInstance(Register.this);
        radioGroup.clearCheck();

/************************************************ Register Button Clicked **********************************************************/
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first_name = etn_first_name.getText().toString().trim();
                String last_name = etn_last_name.getText().toString().trim();
                String email = etn_email.getText().toString().trim();
                String password = etn_password.getText().toString().trim();
                String mobile = etn_mobile.getText().toString().trim();
                String address = etn_address.getText().toString().trim();
                int selected_id = radioGroup.getCheckedRadioButtonId();
                if (selected_id != -1) {
                    gender_radio = findViewById(selected_id);
                }


                //Check if all the fields are not empty
                if (first_name.isEmpty() || last_name.isEmpty() || email.isEmpty() || password.isEmpty() || mobile.isEmpty() || address.isEmpty() || selected_id == -1) {
                    Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else if(!isValid(email)){
                    Toast.makeText(getApplicationContext(), "Enter a valid email", Toast.LENGTH_SHORT).show();
                }
                else if (mobile.length()!=10) {
                    Toast.makeText(getApplicationContext(), "Enter a valid mobile number", Toast.LENGTH_SHORT).show();

                }

                //Check if a user is exist with the same email
                else {
                    List<Users> users_List = db.getUserDao().getAllUsers();
                    boolean email_exists = false;
                    for (Users user : users_List) {
                        if (email.equals(user.getEmail())) {
                            email_exists = true;
                            break;
                        }
                    }
                    if (email_exists) {
                        Toast.makeText(getApplicationContext(), "User exists with the email", Toast.LENGTH_SHORT).show();
                        email_exists = false;
                    } else {
                        String gender = gender_radio.getText().toString().trim();
                        Users user = new Users(first_name, last_name, email, password, mobile, address, gender);
                        db.getUserDao().insert(user);//Save the user
                        Toast.makeText(getApplicationContext(), "Registered Successfully ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Register.this, MainActivity.class);//Go back to the login page
                        startActivity(intent);
                    }

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
}
