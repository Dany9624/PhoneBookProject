package com.example.dany.phonebook.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.dany.phonebook.models.Contact;
import com.example.dany.phonebook.repositories.ContactRepository;

import java.util.List;

/**
 * Created by Dany on 18.10.2018 г..
 */

public class MainActivityViewModel extends AndroidViewModel{

    private ContactRepository mContactRepository;
    private LiveData<List<Contact>> mContacts;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        mContactRepository = ContactRepository.getInstance(application);
    }

    public LiveData<List<Contact>> getAllContacts() {
        if(mContacts == null) {
            mContacts = mContactRepository.getAllContacts();
        }
        return mContacts;
    }
}
