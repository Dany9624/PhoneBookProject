package com.example.dany.phonebook.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.dany.phonebook.models.Country;
import com.example.dany.phonebook.repositories.CountryRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dany on 13.10.2018 г..
 */

public class SelectCountryViewModel extends AndroidViewModel {

    private CountryRepository mCountryRepository;

    private LiveData<List<Country>> mLiveDataCountries;
    private List<Country> mCountries;

    public SelectCountryViewModel(@NonNull Application application) {
        super(application);
        mCountryRepository = CountryRepository.getInstance(application);
    }

    public void setCountries(List<Country> countries) {
        this.mCountries = countries;
    }

    public LiveData<List<Country>> getCountriesLiveData() {
        if(mLiveDataCountries == null) {
            mLiveDataCountries = mCountryRepository.getAllCountries();
        }
        return mLiveDataCountries;
    }

    public List<Country> getSearchedCountries(String prefix) {
        List<Country> listMatches = new ArrayList<>();
        for(Country country : mCountries) {
            //if the editText is empty, the startsWith method always returns true
            //compared to any string, because a string is a set of characters and
            //the empty string is the empty set, and the empty set is always part of any set.
            if(country.getName().toLowerCase().startsWith(prefix.toLowerCase())) {
                listMatches.add(country);
            }
        }
        return listMatches;
    }
}
