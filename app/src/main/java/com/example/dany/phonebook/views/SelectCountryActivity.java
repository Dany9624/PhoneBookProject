package com.example.dany.phonebook.views;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.dany.phonebook.R;
import com.example.dany.phonebook.adapters.CountriesAdapter;
import com.example.dany.phonebook.models.Country;
import com.example.dany.phonebook.repositories.CountryRepository;
import com.example.dany.phonebook.utils.SharedPrefs;
import com.example.dany.phonebook.viewmodels.SelectCountryViewModel;

import java.util.ArrayList;
import java.util.List;

public class SelectCountryActivity extends AppCompatActivity implements TextWatcher, AdapterView.OnItemClickListener {

    private static final String SELECTED_COUNTRY = "selected_country";

    private CountriesAdapter mAdapter;
    private SelectCountryViewModel mSelectCountryViewModel;

    private EditText mEditTextSearch;
    private ListView mListViewCountries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_country);

        this.initUI();
        this.setListeners();

        mSelectCountryViewModel = ViewModelProviders.of(this).get(SelectCountryViewModel.class);

        mSelectCountryViewModel.getCountriesLiveData().observe(this, new Observer<List<Country>>() {
            @Override
            public void onChanged(@Nullable List<Country> countries) {
                reloadListData(countries);
                mSelectCountryViewModel.setCountries(countries);
            }
        });
    }

    private void initUI() {
        mListViewCountries = findViewById(R.id.list_view_countries);
        mAdapter = new CountriesAdapter(this, new ArrayList<Country>());
        mListViewCountries.setAdapter(mAdapter);
        mEditTextSearch = findViewById(R.id.edit_text_search);
    }

    private void setListeners() {
        mEditTextSearch.addTextChangedListener(this);
        mListViewCountries.setOnItemClickListener(this);
    }

    private void reloadListData(List<Country> listCountries) {
        mAdapter.reloadData(listCountries);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        ArrayList<Country> listMatches = (ArrayList<Country>) mSelectCountryViewModel.getSearchedCountries(charSequence.toString());
        mAdapter.reloadData(listMatches);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Country country = (Country) adapterView.getItemAtPosition(i);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(SELECTED_COUNTRY, country);
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK, intent);
        this.finish();
    }
}
