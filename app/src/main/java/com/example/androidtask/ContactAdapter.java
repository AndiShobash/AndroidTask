package com.example.androidtask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtask.model.Contacts;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {
    private List<Contacts> contactsList = new ArrayList<>();
    Context context;
    private static ContactViewModel viewModel;
    public int selected_item;

    private static ContactList main_contact_list;
    String[] test_names;

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

        /************************************************ LiveData Updates the orders list if anything changes **********************************************************/
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        String full_name;
        Contacts current = contactsList.get(position);
        full_name = current.getFirst_name() + " " + current.getLast_name();
        holder.first_name_last_name.setText(full_name);
        holder.type.setText("Number: ");
        holder.type_info.setText(String.valueOf(current.getMobile_number()));

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
                @Override
                public void onClick(View view) {
                    ContactAdapter.viewModel.setSelectedItem(getAdapterPosition());
                    main_contact_list.call_app_contact_full_info(contactsList.get(getAdapterPosition()));


                }
            });
        }
    }
}
