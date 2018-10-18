package com.example.dany.phonebook.views;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dany.phonebook.R;
import com.example.dany.phonebook.models.Contact;
import com.example.dany.phonebook.models.Country;
import com.example.dany.phonebook.viewmodels.ContactDetailsViewModel;

public class ContactDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String CONTACT_ID = "contactID";
    private static final String COUNTRY_ID = "countryID";

    private Button mButtonDelete;
    private Button mButtonEdit;

    private ContactDetailsViewModel mContactDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        this.initUI();
        this.setListeners();

        mContactDetailsViewModel = ViewModelProviders.of(this).get(ContactDetailsViewModel.class);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            //persist the ids in the viewModel
            mContactDetailsViewModel.setContactID(bundle.getInt(CONTACT_ID));
            mContactDetailsViewModel.setCountryID(bundle.getInt(COUNTRY_ID));
            this.observeViewModel();
        }
    }

    private void initUI() {
        mButtonDelete = findViewById(R.id.button_delete);
        mButtonEdit = findViewById(R.id.button_edit);
    }

    private void setListeners() {
        mButtonDelete.setOnClickListener(this);
        mButtonEdit.setOnClickListener(this);
    }

    private void observeViewModel() {
        mContactDetailsViewModel.getContact().observe(this, new Observer<Contact>() {
            @Override
            public void onChanged(@Nullable Contact contact) {
                if(contact != null) {
                    setContactInfo(contact);
                    mContactDetailsViewModel.setContact(contact);
                    //in case the contact's country was updated, check if the selected ID is
                    //different from the previous one, and if that's true, set the new country ID
                    //and trigger update to the UI associated with the country
                    if(mContactDetailsViewModel.getSelectedCountryID().getValue() != null &&
                            contact.getCountryId() != mContactDetailsViewModel.getSelectedCountryID().getValue()) {
                        mContactDetailsViewModel.setSelectedCountryID(contact.getCountryId());
                    }
                }
            }
        });
        mContactDetailsViewModel.getSelectedCountryID().observe(ContactDetailsActivity.this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer countryID) {
                mContactDetailsViewModel.getCountry().observe(ContactDetailsActivity.this, new Observer<Country>() {
                    @Override
                    public void onChanged(@Nullable Country country) {
                        if(country != null) {
                            setCountryInfo(country);
                        }
                    }
                });
            }
        });
    }

    private void setContactInfo(Contact contact) {
        //TextView textViewCircle = findViewById(R.id.text_view_circle);
        TextView textViewNames = findViewById(R.id.text_view_names);
        TextView textViewEmail = findViewById(R.id.text_view_email);
        TextView textViewPhone = findViewById(R.id.text_view_phone);
        TextView textViewSex = findViewById(R.id.text_view_sex);

        //textViewCircle.setText(String.valueOf(contact.getFirstName().charAt(0)));
        textViewNames.setText(getResources().getString(R.string.first_last_name, contact.getFirstName(), contact.getLastName()));
        textViewEmail.setText(contact.getEmail());
        textViewPhone.setText(contact.getPhone());
        textViewSex.setText(contact.getSex());
    }

    private void setCountryInfo(Country country) {
        TextView textViewCountry = findViewById(R.id.text_view_country);
        TextView textViewCountryCode = findViewById(R.id.text_view_country_code);

        textViewCountry.setText(country.getName());
        textViewCountryCode.setText(country.getCode());
    }

    private void deleteContact() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_delete_dialog);
        dialog.setCancelable(true);
        dialog.show();

        Button yes = dialog.findViewById(R.id.button_yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContactDetailsViewModel.deleteContact();
                Toast.makeText(ContactDetailsActivity.this, getResources().getString(R.string.successfully_deleted), Toast.LENGTH_LONG).show();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_delete:
                this.deleteContact();
                break;
            case R.id.button_edit:
                Intent newContactIntent = new Intent(this, CreateContactActivity.class);
                newContactIntent.putExtra(CONTACT_ID, mContactDetailsViewModel.getContactID());
                newContactIntent.putExtra(COUNTRY_ID, mContactDetailsViewModel.getSelectedCountryID().getValue());
                startActivity(newContactIntent);
                break;
        }
    }
}
