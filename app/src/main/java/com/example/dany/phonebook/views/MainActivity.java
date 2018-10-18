package com.example.dany.phonebook.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.dany.phonebook.R;
import com.example.dany.phonebook.models.Contact;
import com.example.dany.phonebook.utils.Constants;
import com.example.dany.phonebook.viewmodels.MainActivityViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String ADD_FIRST_CONTACT = "addFirst";

    private MainActivityViewModel mMainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        this.observeViewModel();

        ImageView imageViewAddContact = findViewById(R.id.image_view_add_contact);
        imageViewAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateContactActivity.class);
                intent.putExtra(ADD_FIRST_CONTACT, true);
                startActivity(intent);
                finish();
            }
        });
    }

    private void observeViewModel() {
        mMainActivityViewModel.getAllContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(@Nullable List<Contact> contacts) {
                if(contacts != null && contacts.size() > 0) {
                    Intent intent = new Intent(MainActivity.this, AllContactsActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
