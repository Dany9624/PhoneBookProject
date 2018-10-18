package com.example.dany.phonebook.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.view.View;

import com.example.dany.phonebook.models.Country;
import com.example.dany.phonebook.views.ApplyFilterFragment;

/**
 * Created by Dany on 16.10.2018 г..
 */

public class ApplyFilterViewModel extends ViewModel{

    private MutableLiveData<Country> mMutableLiveDataCountry;
    private MutableLiveData<String> mMutableLiveDataGender;

    public ApplyFilterViewModel() {
        mMutableLiveDataCountry = new MutableLiveData<>();
        mMutableLiveDataGender = new MutableLiveData<>();
    }

    public int getSelectedCountryID() {
        if(mMutableLiveDataCountry.getValue() != null) {
            return mMutableLiveDataCountry.getValue().getId();
        }
        else {
            return -1;
        }
    }

    public String getSelectedGender() {
        return mMutableLiveDataGender.getValue();
    }

    public void setCountry(Country country) {
        mMutableLiveDataCountry.setValue(country);
    }

    public LiveData<Country> getCountry() {
        return mMutableLiveDataCountry;
    }

    public void setGender(String gender) {
        mMutableLiveDataGender.setValue(gender);
    }

    public LiveData<String> getGender() {
        return mMutableLiveDataGender;
    }

    public void clearFilters() {
        mMutableLiveDataCountry.setValue(null);
        mMutableLiveDataGender.setValue(null);
    }

}
