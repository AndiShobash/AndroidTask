package com.example.androidtask;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.androidtask.model.Contacts;
import com.example.androidtask.model.ContactsDB;
import com.example.androidtask.model.Users;
import com.example.androidtask.model.UsersDB;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    ContactsDB db = ContactsDB.getInstance(this.getApplication().getApplicationContext());
    List<Contacts> ContactsList;
    MutableLiveData<List<Contacts>> ContactsLiveData;
    MutableLiveData<Integer> SelectedContact;

    private String user_email;


    public ContactViewModel(@NonNull Application application) {
        super(application);
        SelectedContact = new MutableLiveData<>();
        ContactsLiveData = new MutableLiveData<>();
        user_email= ContactList.getUserEmail();
        ContactsList = db.getContactDao().getAllUserContacts(user_email);
        ContactsLiveData.setValue(ContactsList);
    }

    public MutableLiveData<Integer> getSelectedContactMutableLiveData() {
        return SelectedContact;
    }

    public MutableLiveData<List<Contacts>> getContactsLiveData() {
        return ContactsLiveData;
    }

    public void remove_contact(int position) {
        db.getContactDao().delete(ContactsList.get(position));
        ContactsList.remove(position);
        ContactsLiveData.setValue(ContactsList);
    }

    public void setSelectedOrder(Integer noPosition) {
        SelectedContact.setValue(noPosition);
    }

    public void setSelectedItem(Integer Item) {
        SelectedContact.setValue(Item);
    }
}
