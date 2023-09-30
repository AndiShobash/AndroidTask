package com.example.androidtask;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidtask.Menu.OptionsMenu;
import com.example.androidtask.model.Users;
import com.example.androidtask.model.UsersDB;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends OptionsMenu {

    private List<Users> users_List = new ArrayList<>();

    UsersDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        EditText etn_email = (EditText) findViewById(R.id.email);
        EditText etn_password = (EditText) findViewById(R.id.password);
        Button btn_register = (Button) findViewById(R.id.register_btn);
        Button btn_login = (Button) findViewById(R.id.login_btn);
        db = UsersDB.getInstance(MainActivity.this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etn_email.getText().toString().trim();
                String password = etn_password.getText().toString().trim();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
                } else {
                    users_List = db.getUserDao().getAllUsers();
                    for (Users user : users_List) {
                        if (email.equals(user.getEmail()) && password.equals(user.getPassword())) {
                            Intent intent = new Intent(MainActivity.this, ContactList.class);
                            intent.putExtra("email", user.getEmail());
                            startActivity(intent);
                        }
                    }

                }
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


}
