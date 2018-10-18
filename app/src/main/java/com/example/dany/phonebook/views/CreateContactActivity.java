package com.example.dany.phonebook.views;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dany.phonebook.R;
import com.example.dany.phonebook.models.Contact;
import com.example.dany.phonebook.models.Country;
import com.example.dany.phonebook.utils.Constants;
import com.example.dany.phonebook.utils.EmailValidator;
import com.example.dany.phonebook.utils.PhoneValidator;
import com.example.dany.phonebook.viewmodels.CreateContactViewModel;

public class CreateContactActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int SELECT_COUNTRY_REQUEST_CODE = 1;
    private static final String SELECTED_COUNTRY = "selected_country";
    private static final String ADD_FIRST_CONTACT = "addFirst";

    private static final String CONTACT_ID = "contactID";
    private static final String COUNTRY_ID = "countryID";

    private static final String COUNTRY= "country";
    private static final String SEX = "sex";

    private CreateContactViewModel mCreateContactViewModel;

    private Button mButtonSelectCountry;
    private Button mButtonConfirm;
    private EditText mEditTextFirstName;
    private EditText mEditTextLastName;
    private EditText mEditTextEmail;
    private EditText mEditTextPhone;
    private TextView mTextViewCountry;
    private TextView mTextViewCode;
    private RadioButton mRadioMale;
    private RadioButton mRadioFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        mCreateContactViewModel = ViewModelProviders.of(this).get(CreateContactViewModel.class);

        this.initUI();
        Bundle bundle = getIntent().getExtras();
        //If bundle is not null and addFirstContact is false, the activity is opened for editing an existing contact
        if(bundle != null && !bundle.getBoolean(ADD_FIRST_CONTACT)) {
            int contactID = bundle.getInt(CONTACT_ID);
            int countryID = bundle.getInt(COUNTRY_ID);
            observeViewModel(contactID, countryID);
        }
        if(savedInstanceState != null) {
            this.restoreUIState(savedInstanceState);
        }
        this.setListeners();
    }

    private void initUI() {
        mButtonSelectCountry = findViewById(R.id.button_select_country);
        mButtonConfirm = findViewById(R.id.button_confirm);
        mEditTextFirstName = findViewById(R.id.edit_text_first_name);
        mEditTextLastName = findViewById(R.id.edit_text_last_name);
        mEditTextEmail = findViewById(R.id.edit_text_email);
        mEditTextPhone = findViewById(R.id.edit_text_phone_number);
        mTextViewCountry = findViewById(R.id.text_view_country);
        mTextViewCode = findViewById(R.id.text_view_country_code);
        mRadioMale = findViewById(R.id.radio_m);
        mRadioFemale = findViewById(R.id.radio_f);
    }

    private void restoreUIState(Bundle savedInstanceState) {
        Country selectedCountry = (Country) savedInstanceState.getSerializable(COUNTRY);
        if(selectedCountry != null) {
            mCreateContactViewModel.setSelectedCountry(selectedCountry);
            mTextViewCode.setText(selectedCountry.getCode());
            mTextViewCountry.setText(selectedCountry.getName());
        }
        String sex = savedInstanceState.getString(SEX);
        if(sex != null && sex.equals(Constants.MALE)) {
            mRadioMale.setChecked(true);
        } else {
            mRadioFemale.setChecked(true);
        }
    }

    private void observeViewModel(int contactID, int countryID) {
        mCreateContactViewModel.getContact(contactID).observe(this, new Observer<Contact>() {
            @Override
            public void onChanged(@Nullable Contact contact) {
                if(contact != null) {
                    setContactInfo(contact);
                }
            }
        });
        mCreateContactViewModel.getCountry(countryID).observe(this, new Observer<Country>() {
            @Override
            public void onChanged(@Nullable Country country) {
                //if the activity is destroyed due to rotation, show the country corresponding to him in the database
                //only if he hasn't selected another country (mNewContactViewModel.getSelectedCountry() == null).
                //Otherwise, show the country preserved with onSaveInstanceState();
                if(country != null && mCreateContactViewModel.getSelectedCountry() == null) {
                    mCreateContactViewModel.setSelectedCountry(country);
                    setCountryInfo(country);
                }
            }
        });
    }

    private void setContactInfo(Contact contact) {
        mCreateContactViewModel.setContact(contact);
        mEditTextFirstName.setText(contact.getFirstName());
        mEditTextLastName.setText(contact.getLastName());
        mEditTextEmail.setText(contact.getEmail());
        mEditTextPhone.setText(contact.getPhone());
        String sex = contact.getSex();
        if(sex != null && sex.equals(Constants.MALE)) {
            mRadioMale.setChecked(true);
        } else {
            mRadioFemale.setChecked(true);
        }
    }

    private void setCountryInfo(Country country) {
        mTextViewCode.setText(country.getCode());
        mTextViewCountry.setText(country.getName());
    }

    private void setListeners() {
        mButtonSelectCountry.setOnClickListener(this);
        mButtonConfirm.setOnClickListener(this);
    }

    private boolean checkForEmptyFields() {
        boolean emptyFields = false;
        if(TextUtils.isEmpty(mEditTextFirstName.getText().toString()) ||
                TextUtils.isEmpty(mEditTextLastName.getText().toString()) ||
                TextUtils.isEmpty(mEditTextEmail.getText().toString()) ||
                TextUtils.isEmpty(mEditTextPhone.getText().toString()) ||
                mCreateContactViewModel.getSelectedCountry() == null ) {
            emptyFields = true;
        }
        return emptyFields;
    }

    private void showSaveDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_save_dialog);
        dialog.setCancelable(true);
        dialog.show();

        Button yes = dialog.findViewById(R.id.button_yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sex;
                if(mRadioMale.isChecked()) {
                    sex = Constants.MALE;
                } else {
                    sex = Constants.FEMALE;
                }
                mCreateContactViewModel.saveContact(mEditTextFirstName.getText().toString(),
                        mEditTextLastName.getText().toString(),
                        mEditTextEmail.getText().toString(),
                        mEditTextPhone.getText().toString(),
                        mCreateContactViewModel.getSelectedCountry().getId(),
                        sex);
                Toast.makeText(CreateContactActivity.this, getResources().getString(R.string.successfully_saved), Toast.LENGTH_LONG).show();
                Bundle bundle = getIntent().getExtras();
                if(bundle != null && bundle.getBoolean(ADD_FIRST_CONTACT)) {
                    Intent intent = new Intent(CreateContactActivity.this, AllContactsActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        });

        Button no = dialog.findViewById(R.id.button_no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    private void saveContact() {
        boolean valid = true;
        if(this.checkForEmptyFields()) {
            Toast.makeText(this, getResources().getString(R.string.empty_fields), Toast.LENGTH_LONG).show();
            valid = false;
        } else {
            if(!EmailValidator.validate(mEditTextEmail.getText().toString())) {
                Toast.makeText(this, getResources().getString(R.string.invalid_email), Toast.LENGTH_LONG).show();
                valid = false;
            }
            if(!PhoneValidator.validate(mEditTextPhone.getText().toString() + mEditTextPhone.getText().toString())) {
                Toast.makeText(this, getResources().getString(R.string.invalid_phone), Toast.LENGTH_LONG).show();
                valid = false;
            }
        }
        if(valid) {
            showSaveDialog();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_select_country:
                Intent selectCountryIntent = new Intent(this, SelectCountryActivity.class);
                startActivityForResult(selectCountryIntent, SELECT_COUNTRY_REQUEST_CODE);
                break;
            case R.id.button_confirm:
                this.saveContact();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SELECT_COUNTRY_REQUEST_CODE && resultCode == RESULT_OK) {
            if(data.getExtras() != null) {
                Country selectedCountry = (Country) data.getExtras().getSerializable(SELECTED_COUNTRY);
                if(selectedCountry != null) {
                        mCreateContactViewModel.setSelectedCountry(selectedCountry);
                        mTextViewCountry.setText(selectedCountry.getName());
                        mTextViewCode.setText(selectedCountry.getCode());
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String sex;
        if(mRadioMale.isChecked()) {
            sex = Constants.MALE;
        } else {
            sex = Constants.FEMALE;
        }
        outState.putString(SEX, sex);
        outState.putSerializable(COUNTRY, mCreateContactViewModel.getSelectedCountry());
    }
}
