package com.example.dany.phonebook.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.dany.phonebook.models.Contact;
import com.example.dany.phonebook.models.Country;
import com.example.dany.phonebook.repositories.ContactRepository;
import com.example.dany.phonebook.repositories.CountryRepository;

/**
 * Created by Dany on 15.10.2018 г..
 */

public class ContactDetailsViewModel extends AndroidViewModel {

    private ContactRepository mContactRepository;
    private CountryRepository mCountryRepository;

    private int mContactID;
    private int mCountryID;
    private Contact mContact;
    private LiveData<Contact> mContactLiveData;
    private MutableLiveData<Integer> mSelectedCountryID;

    public ContactDetailsViewModel(@NonNull Application application) {
        super(application);
        mContactRepository = ContactRepository.getInstance(application);
        mCountryRepository = CountryRepository.getInstance(application);
    }

    public void setContactID(int contactID) {
        mContactID = contactID;
    }

    public int getContactID() {
        return mContactID;
    }

    public void setContact(Contact contact) {
        mContact = contact;
    }

    public void deleteContact() {
        mContactRepository.deleteContact(mContact);
    }

    public LiveData<Contact> getContact() {
        if(mContact == null) {
            mContactLiveData = mContactRepository.getContact(mContactID);
        }
        return mContactLiveData;
    }

    public void setCountryID(int countryID) {
        mCountryID = countryID;
    }

    public void setSelectedCountryID(int selectedCountryID) {
        mSelectedCountryID.setValue(selectedCountryID);
    }

    public MutableLiveData<Integer> getSelectedCountryID() {
        if(mSelectedCountryID == null) {
            mSelectedCountryID = new MutableLiveData<>();
            mSelectedCountryID.setValue(mCountryID);
        }
        return mSelectedCountryID;
    }

    public LiveData<Country> getCountry() {
        int countryID = -1;
        if(mSelectedCountryID.getValue() != null) {
            countryID = mSelectedCountryID.getValue();
        }
        return mCountryRepository.getCountry(countryID);
    }

}
