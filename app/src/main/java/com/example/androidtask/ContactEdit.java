package com.example.androidtask;

import android.os.Bundle;

import com.example.androidtask.Menu.OptionsMenu;

public class ContactEdit extends OptionsMenu {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_edit);
    }
}
