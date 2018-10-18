package com.example.dany.phonebook.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.dany.phonebook.models.Contact;
import com.example.dany.phonebook.models.Country;
import com.example.dany.phonebook.utils.Constants;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Dany on 13.10.2018 г..
 */

@Dao
public interface CountryDao {

    @Insert(onConflict = REPLACE)
    void save(Country country);

    @Insert(onConflict = REPLACE)
    void saveAll(List<Country> listCountries);

    @Query("SELECT * FROM " + Constants.COUNTRY_TABLE)
    LiveData<List<Country>> getAllCountries();

    @Query("SELECT * FROM " + Constants.COUNTRY_TABLE + " WHERE country_id = :countryID")
    LiveData<Country> getCountry(int countryID);
}
