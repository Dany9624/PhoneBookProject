package com.example.dany.phonebook.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.dany.phonebook.models.Contact;
import com.example.dany.phonebook.utils.Constants;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Dany on 13.10.2018 г..
 */

@Dao
public interface ContactDao {

    @Insert(onConflict = REPLACE)
    void save(Contact contact);

    @Update
    void update(Contact contact);

    @Delete
    void delete(Contact contact);

    //The observable queries run asynchronously on a background thread
    @Query("SELECT * FROM " + Constants.CONTACT_TABLE + " WHERE contact_id = :contactID")
    LiveData<Contact> getContact(int contactID);

    @Query("SELECT * FROM " + Constants.CONTACT_TABLE)
    LiveData<List<Contact>> getAllContacts();

}
