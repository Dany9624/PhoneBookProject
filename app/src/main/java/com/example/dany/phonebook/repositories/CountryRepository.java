package com.example.dany.phonebook.repositories;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.dany.phonebook.database.PhoneBookDatabase;
import com.example.dany.phonebook.database.daos.CountryDao;
import com.example.dany.phonebook.models.Country;
import com.example.dany.phonebook.utils.SharedPrefs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Dany on 13.10.2018 г..
 */

public class CountryRepository {

    private static CountryRepository sInstance;
    private CountryDao mCountryDao;
    private Executor mExecutor;

    private CountryRepository(Context context) {
        mCountryDao = PhoneBookDatabase.getDatabaseInstance(context).countryDao();
        mExecutor = Executors.newSingleThreadExecutor();

        if(SharedPrefs.getInitialLaunch(context)) {
            this.populateCountriesTable(context);
        }
    }

    //Create Singleton
    public static CountryRepository getInstance(Context context) {
        if(sInstance == null) {
            sInstance = new CountryRepository(context);
        }
        return sInstance;
    }

    public LiveData<List<Country>> getAllCountries() {
        return mCountryDao.getAllCountries();
    }

    public LiveData<Country> getCountry(int countryID) {
        return mCountryDao.getCountry(countryID);
    }

    private void populateCountriesTable(Context context) {
        Country greece = new Country("Greece","(+30)");
        Country belgium = new Country("Belgium","(+32)");
        Country france  = new Country("France","(+33)");
        Country spain = new Country("Spain","(+34)");
        Country portugal  = new Country("Portugal","(+351)");
        Country ireland = new Country("Ireland","(+353)");
        Country finland = new Country("Finland","(+358)");
        Country bulgaria  = new Country("Bulgaria","(+359)");
        Country hungary = new Country("Hungary","(+36)");
        Country lithuania = new Country("Lithuania","(+370)");
        Country latvia = new Country("Latvia","(+371)");
        Country estonia = new Country("Estonia","(+372)");
        Country moldova = new Country("Moldova","(+373)");
        Country armenia = new Country("Armenia","(+374)");
        Country monaco = new Country("Monaco","(+377)");
        Country vatican = new Country("Vatican","(+379)");
        Country macedonia = new Country("Macedonia","(+389)");
        Country italy = new Country("Italy","(+39)");
        Country switzerland = new Country("Switzerland","(+41)");
        Country romania = new Country("Romania","(+40)");
        Country slovakia = new Country("Slovakia","(+421)");
        Country austria = new Country("Austria","(+43)");
        Country denmark = new Country("Denmark","(+45)");
        Country sweden = new Country("Sweden","(+46)");
        Country norway = new Country("Norway","(+47)");
        Country poland = new Country("Poland","(+48)");
        Country germany = new Country("Germany","(+49)");

        List<Country> list = new ArrayList<>();
        list.add(greece);
        list.add(belgium);
        list.add(france);
        list.add(spain);
        list.add(portugal);
        list.add(ireland);
        list.add(finland);
        list.add(bulgaria);
        list.add(hungary);
        list.add(lithuania);
        list.add(latvia);
        list.add(estonia);
        list.add(moldova);
        list.add(armenia);
        list.add(monaco);
        list.add(vatican);
        list.add(macedonia);
        list.add(italy);
        list.add(switzerland);
        list.add(romania);
        list.add(slovakia);
        list.add(austria);
        list.add(denmark);
        list.add(sweden);
        list.add(norway);
        list.add(poland);
        list.add(germany);

        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mCountryDao.saveAll(list);
            }
        });

        SharedPrefs.setInitialLaunch(context, false);
    }

}
