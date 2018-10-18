package com.example.dany.phonebook.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.dany.phonebook.database.daos.ContactDao;
import com.example.dany.phonebook.database.daos.CountryDao;
import com.example.dany.phonebook.models.Contact;
import com.example.dany.phonebook.models.Country;
import com.example.dany.phonebook.utils.Constants;

/**
 * Created by Dany on 13.10.2018 г..
 */

@Database(entities = {Contact.class, Country.class}, version = 1, exportSchema = false)
public abstract class PhoneBookDatabase extends RoomDatabase {

    private static PhoneBookDatabase sInstance;

    public abstract ContactDao contactDao();
    public abstract CountryDao countryDao();

    //Create Singleton
    public static PhoneBookDatabase getDatabaseInstance(Context context) {
        if(sInstance == null) {
            sInstance = Room.databaseBuilder(context, PhoneBookDatabase.class, Constants.DATABASE_NAME).build();
        }
        return sInstance;
    }
}
