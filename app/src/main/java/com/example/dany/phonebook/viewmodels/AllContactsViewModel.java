package com.example.dany.phonebook.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.dany.phonebook.models.Contact;
import com.example.dany.phonebook.repositories.ContactRepository;
import com.example.dany.phonebook.utils.Enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dany on 15.10.2018 г..
 */

public class AllContactsViewModel extends AndroidViewModel {

    private Enums.Filter mFilter;

    private ContactRepository mContactRepository;
    private LiveData<List<Contact>> mAllContactsLiveData;
    private List<Contact> mAllContacts;
    private MutableLiveData<List<Contact>> mFilteredContacts;

    private int mCountryID = -1;
    private String mGender = null;

    public AllContactsViewModel(@NonNull Application application) {
        super(application);
        mContactRepository = ContactRepository.getInstance(application);
        mFilteredContacts = new MutableLiveData<>();
        mFilter = Enums.Filter.ALL;
    }

    public void setAllContacts(List<Contact> contacts) {
        mAllContacts = contacts;
    }

    public LiveData<List<Contact>> getAllContacts() {
        if(mAllContactsLiveData == null) {
            mAllContactsLiveData = mContactRepository.getAllContacts();
        }
        return mAllContactsLiveData;
    }

    public LiveData<List<Contact>> getFilteredContacts() {
        return mFilteredContacts;
    }

    public Enums.Filter getFilter() {
        return mFilter;
    }

    public void setFilter(Enums.Filter filter) {
        mFilter = filter;
    }

    public boolean isFilterApplied() {
        return (mFilter != Enums.Filter.ALL);
    }

    public void filterContacts() {
        this.setFilterType(mCountryID, mGender);
    }

    public void setFilterType(int countryID, String gender) {
        mCountryID = countryID;
        mGender = gender;
        if(countryID != -1 && gender != null) {
            mFilter = Enums.Filter.COMPLEX;
        } else if (countryID == -1 && gender != null) {
            mFilter = Enums.Filter.GENDER;
        } else if (countryID != -1) {
            mFilter = Enums.Filter.COUNTRY;
        } else {
            mFilter = Enums.Filter.ALL;
        }
        applyFilter(countryID, gender);
    }

    private void applyFilter(int countryID, String gender) {
        switch (mFilter) {
            case GENDER:
                filterByGender(gender);
                break;
            case COUNTRY:
                filterByCountry(countryID);
                break;
            case COMPLEX:
                filterComplex(countryID, gender);
                break;
            case ALL:
                filterAll();
                break;
        }
    }

   private void filterByCountry(int countryID) {
        List<Contact> resultList = new ArrayList<>();
        for (Contact contact : mAllContacts) {
            if(contact.getCountryId() == countryID) {
                resultList.add(contact);
            }
        }
        mFilteredContacts.setValue(resultList);
   }

   private void filterByGender(String gender) {
        List<Contact> resultList = new ArrayList<>();
        for(Contact contact : mAllContacts) {
            if(contact.getSex().equals(gender)) {
                resultList.add(contact);
            }
        }
        mFilteredContacts.setValue(resultList);
   }

   private void filterComplex(int countryID, String gender) {
        List<Contact> resultList = new ArrayList<>();
        for(Contact contact : mAllContacts) {
            if(contact.getCountryId() == countryID && contact.getSex().equals(gender)) {
                resultList.add(contact);
            }
        }
        mFilteredContacts.setValue(resultList);
   }

   private void filterAll() {
        mFilteredContacts.setValue(mAllContacts);
   }
}
