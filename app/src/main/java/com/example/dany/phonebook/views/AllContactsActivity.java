package com.example.dany.phonebook.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.dany.phonebook.R;
import com.example.dany.phonebook.adapters.ContactsAdapter;
import com.example.dany.phonebook.models.Contact;
import com.example.dany.phonebook.models.Country;
import com.example.dany.phonebook.utils.Constants;
import com.example.dany.phonebook.utils.Enums;
import com.example.dany.phonebook.viewmodels.AllContactsViewModel;

import java.util.ArrayList;
import java.util.List;

public class AllContactsActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    private static final int SELECT_COUNTRY_REQUEST_CODE = 1;
    private static final String SELECTED_COUNTRY = "selected_country";
    private static final String CONTACT_ID = "contactID";
    private static final String COUNTRY_ID = "countryID";
    private static final String LAST_FILTER = "lastFilter";

    private ListView mListViewAllContacts;
    private ImageView mImageViewSearch;
    private ImageView mImageViewAddContact;
    private ImageView mImageViewRefresh;

    private AllContactsViewModel mAllContactsViewModel;

    private ContactsAdapter mAdapter;

    private ApplyFilterFragment mFilterFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_contacts);

        mAllContactsViewModel = ViewModelProviders.of(this).get(AllContactsViewModel.class);
        mFilterFragment = (ApplyFilterFragment) getSupportFragmentManager().findFragmentByTag(Constants.APPLY_FILTER_FRAGMENT);

        this.initUI();
        this.restoreUIState(savedInstanceState);
        this.observeViewModel();
        this.setListeners();
    }

    private void restoreUIState(Bundle bundle) {
        if(bundle != null) {
            Enums.Filter filter = (Enums.Filter) bundle.getSerializable(LAST_FILTER);
            if(filter != null) {
                mAllContactsViewModel.setFilter(filter);
            }
        }
    }

    private void initUI() {
        mImageViewSearch = findViewById(R.id.image_view_search);
        mImageViewAddContact = findViewById(R.id.image_view_add_contact);
        mImageViewRefresh = findViewById(R.id.image_view_refresh);
        mListViewAllContacts = findViewById(R.id.list_view_all_contacts);
        mAdapter = new ContactsAdapter(this, new ArrayList<>());
        mListViewAllContacts.setAdapter(mAdapter);
    }

    private void observeViewModel() {
        mAllContactsViewModel.getAllContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(@Nullable List<Contact> contacts) {
                if(contacts != null) {
                    //the data is initially loaded from the database or data is added, deleted or modified and no filter is applied
                    if(!mAllContactsViewModel.isFilterApplied()) {
                        mAllContactsViewModel.setAllContacts(contacts);
                        mAdapter.reloadData(contacts);
                        //data is added, deleted or modified in the database and filter is applied
                    } else {
                        mAllContactsViewModel.setAllContacts(contacts);
                        mAllContactsViewModel.filterContacts();
                    }
                }
            }
        });
        mAllContactsViewModel.getFilteredContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(@Nullable List<Contact> contacts) {
                //get filtered data
                if(contacts != null) {
                    mAdapter.reloadData(contacts);
                }
            }
        });
    }

    private void setListeners() {
        mImageViewAddContact.setOnClickListener(this);
        mImageViewSearch.setOnClickListener(this);
        mImageViewRefresh.setOnClickListener(this);
        mListViewAllContacts.setOnItemClickListener(this);
    }

    public void filter(int countryID, String gender) {
        mAllContactsViewModel.setFilterType(countryID, gender);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_view_add_contact:
                Intent newContactIntent = new Intent(this, CreateContactActivity.class);
                startActivity(newContactIntent);
                break;
            case R.id.image_view_search:
                mFilterFragment = new ApplyFilterFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.frame_container, mFilterFragment, Constants.APPLY_FILTER_FRAGMENT).addToBackStack(null).commit();
                break;
            case R.id.image_view_refresh:
                mAllContactsViewModel.setFilterType(-1, null);
                if(mFilterFragment != null) {
                    mFilterFragment.clearFilters();
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Contact contact = (Contact)adapterView.getItemAtPosition(position);
        int contactID = contact.getId();
        int countryID = contact.getCountryId();
        Intent contactDetailsIntent = new Intent(this, ContactDetailsActivity.class);
        contactDetailsIntent.putExtra(CONTACT_ID, contactID);
        contactDetailsIntent.putExtra(COUNTRY_ID, countryID);
        startActivity(contactDetailsIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SELECT_COUNTRY_REQUEST_CODE && resultCode == RESULT_OK) {
            if(data.getExtras() != null) {
                Country selectedCountry = (Country) data.getExtras().getSerializable(SELECTED_COUNTRY);
                if(selectedCountry != null) {
                    mFilterFragment.setCountry(selectedCountry);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(mFilterFragment != null) {
            mFilterFragment.clearFilters();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(LAST_FILTER, mAllContactsViewModel.getFilter());
    }
}
