package com.example.androidtask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtask.model.Contacts;

import java.util.ArrayList;
import java.util.List;

/************************************************ The adapter fot the RecyclerView**********************************************************/
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {
    private List<Contacts> contactsList = new ArrayList<>();
    Context context;
    private static ContactViewModel viewModel;
    public int selected_item;

    private static ContactList main_contact_list;
    SharedPreferences app_preferences;

    public ContactAdapter(Context context, ContactList contactList, ContactViewModel viewModel) {
        ContactAdapter.viewModel = viewModel;
        this.context = context;
        this.main_contact_list = contactList;

        /************************************************ LiveData to see which position selected **********************************************************/
        ContactAdapter.viewModel.getSelectedContactMutableLiveData().observe((LifecycleOwner) context, new Observer<Integer>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(Integer selected_contact) {
                selected_item = selected_contact;
                notifyDataSetChanged();
            }
        });

        /************************************************ LiveData Updates the contacts list if anything changes **********************************************************/
        ContactAdapter.viewModel.getContactsLiveData().observe((LifecycleOwner) context, new Observer<List<Contacts>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Contacts> contacts) {
                contactsList = contacts;
                notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View contactView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);

        return new ContactHolder(contactView);
    }

    //Display the contacts information in the list with the setting which will displayed
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        String full_name;
        Contacts current = contactsList.get(position);
        full_name = current.getFirst_name() + " " + current.getLast_name();
        holder.first_name_last_name.setText(full_name);
        app_preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());//Get the preferences to know that to display
        boolean pref_mobile = app_preferences.getBoolean("MobileNumber", false);
        boolean pref_email = app_preferences.getBoolean("Email", false);
        boolean pref_address = app_preferences.getBoolean("Address", false);
        boolean pref_gender = app_preferences.getBoolean("Gender", false);
        if (pref_mobile){
            holder.type.setText("Mobile:");
            holder.type_info.setText(String.valueOf(current.getMobile_number()));
        }
        if (pref_email){
            holder.type.setText("Email:");
            holder.type_info.setText(String.valueOf(current.getEmail()));
        }
        if (pref_address){
            holder.type.setText("Address:");
            holder.type_info.setText(String.valueOf(current.getAddress()));
        }
        if (pref_gender){
            holder.type.setText("Gender:");
            holder.type_info.setText(String.valueOf(current.getGender()));
        }


    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    public class ContactHolder extends RecyclerView.ViewHolder {
        private TextView first_name_last_name;
        private TextView type;

        private TextView type_info;
        private ImageButton delete_btn;

        public ContactHolder(View contactview) {
            super(contactview);
            first_name_last_name = contactview.findViewById(R.id.text_view_name);
            delete_btn = contactview.findViewById(R.id.image_delete);
            type = contactview.findViewById(R.id.text_view_type);
            type_info = contactview.findViewById(R.id.type_info);

            //The delete button has been clicked and now will delete the contact
            delete_btn.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        viewModel.remove_contact(pos);
                    }
                    Toast.makeText(context, "deleted successfully", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }
            });

            contactview.setOnClickListener(new View.OnClickListener() {
                //Clicked at the contact to see the full information about the contact
                @Override
                public void onClick(View view) {
                    ContactAdapter.viewModel.setSelectedItem(getAdapterPosition());
                    main_contact_list.call_app_contact_full_info(contactsList.get(selected_item));
                }
            });
        }
    }
}
