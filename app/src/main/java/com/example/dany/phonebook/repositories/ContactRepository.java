package com.example.dany.phonebook.repositories;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.dany.phonebook.database.PhoneBookDatabase;
import com.example.dany.phonebook.database.daos.ContactDao;
import com.example.dany.phonebook.database.daos.CountryDao;
import com.example.dany.phonebook.models.Contact;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Dany on 14.10.2018 г..
 */

public class ContactRepository {

    private static ContactRepository sInstance = null;

    private ContactDao mContactDao;
    private Executor mExecutor;

    private ContactRepository(Context context) {
        mContactDao = PhoneBookDatabase.getDatabaseInstance(context).contactDao();
        mExecutor = Executors.newSingleThreadExecutor();
    }

    //Create Singleton
    public static ContactRepository getInstance(Context context) {
        if(sInstance == null) {
            sInstance = new ContactRepository(context);
        }
        return sInstance;
    }

    public void saveContact(Contact contact) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mContactDao.save(contact);
            }
        });
    }

    public void updateContact(Contact contact) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mContactDao.update(contact);
            }
        });
    }

    public void deleteContact(Contact contact) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mContactDao.delete(contact);
            }
        });
    }

    public LiveData<Contact> getContact(int contactID) {
        return mContactDao.getContact(contactID);
    }

    public LiveData<List<Contact>> getAllContacts() {
        return mContactDao.getAllContacts();
    }

}
