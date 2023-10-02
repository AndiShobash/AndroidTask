package com.example.androidtask;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidtask.model.ContactsDB;
import com.example.androidtask.model.RequesetResponse;
import com.example.androidtask.model.Users;
import com.example.androidtask.model.UsersDB;

import java.util.ArrayList;
import java.util.List;

/************************************************ Login Page **********************************************************/

public class MainActivity extends AppCompatActivity {
    private List<Users> users_List = new ArrayList<>();

    UsersDB db;
    static ContactsDB data_base;

    static String gender;

    private boolean check_login_correct = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        EditText etn_email = (EditText) findViewById(R.id.email);
        EditText etn_password = (EditText) findViewById(R.id.password);
        Button btn_register = (Button) findViewById(R.id.register_btn);
        Button btn_login = (Button) findViewById(R.id.login_btn);
        db = UsersDB.getInstance(MainActivity.this);
        data_base = ContactsDB.getInstance(MainActivity.this);

/************************************************ Login Button Clicked **********************************************************/
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etn_email.getText().toString().trim();
                String password = etn_password.getText().toString().trim();
                if (email.isEmpty() || password.isEmpty()) { //Checks if the email and password field are empty
                    Toast.makeText(MainActivity.this, "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
                } else {
                    // Go over all the users to see if a user is exist
                    users_List = db.getUserDao().getAllUsers();
                    for (Users user : users_List) {
                        // If exists then go in to see contacts
                        if (email.equals(user.getEmail()) && password.equals(user.getPassword())) {
                            //Go to the ContactList Activity and also pass to him the user email information
                            check_login_correct = true;
                            Intent intent = new Intent(MainActivity.this, ContactList.class);
                            intent.putExtra("email", user.getEmail());
                            startActivity(intent);
                        }
                    }
                    if (!check_login_correct) {
                        Toast.makeText(MainActivity.this, "Email or Password is incorrect", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

/************************************************ SignUp Button Clicked **********************************************************/
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
    }

    /************************************************ Exists the app after clicking the back button **********************************************************/
    @Override
    public void onBackPressed() {
        Intent serviceIntent = new Intent(MainActivity.this.getApplicationContext(), Service.class);
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        System.exit(1);
    }


}
