package com.example.dany.phonebook.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.dany.phonebook.models.Contact;
import com.example.dany.phonebook.models.Country;
import com.example.dany.phonebook.repositories.ContactRepository;
import com.example.dany.phonebook.repositories.CountryRepository;

/**
 * Created by Dany on 14.10.2018 г..
 */

public class CreateContactViewModel extends AndroidViewModel {

    private ContactRepository mContactRepository;
    private CountryRepository mCountryRepository;

    private Contact mContact;
    private Country mSelectedCountry;
    private LiveData<Country> mLiveDataCountry;
    private LiveData<Contact> mLiveDataContact;

    public CreateContactViewModel(@NonNull Application application) {
        super(application);
        mContactRepository = ContactRepository.getInstance(application);
        mCountryRepository = CountryRepository.getInstance(application);
    }

    public LiveData<Contact> getContact(int contactID) {
        if(mLiveDataContact == null) {
            mLiveDataContact = mContactRepository.getContact(contactID);
        }
        return mLiveDataContact;
    }

    public LiveData<Country> getCountry(int countryID) {
        if(mLiveDataCountry == null) {
            mLiveDataCountry = mCountryRepository.getCountry(countryID);
        }
        return mLiveDataCountry;
    }

    public void setContact(Contact contact) {
        mContact = contact;
    }

    public void saveContact(String firstName, String lastName, String email, String phone, int countryID, String sex) {
        //if mContact is null, then the contact is new and should be added. Otherwise, it is updated.
        if(mContact == null) {
            mContact = new Contact(firstName, lastName, email, sex, phone, countryID);
            mContactRepository.saveContact(mContact);
        } else {
            mContact.setFirstName(firstName);
            mContact.setLastName(lastName);
            mContact.setEmail(email);
            mContact.setPhone(phone);
            mContact.setCountryId(countryID);
            mContact.setSex(sex);
            mContactRepository.updateContact(mContact);
        }
    }

    public void setSelectedCountry(Country country) {
        mSelectedCountry = country;
    }

    public Country getSelectedCountry() {
        return mSelectedCountry;
    }

}
