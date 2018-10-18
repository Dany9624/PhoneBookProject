package com.example.dany.phonebook.views;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.dany.phonebook.R;
import com.example.dany.phonebook.models.Country;
import com.example.dany.phonebook.utils.Constants;
import com.example.dany.phonebook.viewmodels.ApplyFilterViewModel;


public class ApplyFilterFragment extends Fragment implements View.OnClickListener {

    private static final int SELECT_COUNTRY_REQUEST_CODE = 1;

    private RadioButton mRadioM;
    private RadioButton mRadioF;
    private RadioGroup mRadioGroup;
    private Button mButtonCountry;
    private Button mButtonSearch;
    private View mViewBackground;
    private TextView mTextViewCountry;

    private ApplyFilterViewModel mApplyFilterViewModel;

    public ApplyFilterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mApplyFilterViewModel = ViewModelProviders.of(getActivity()).get(ApplyFilterViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_apply_filter, container, false);
        this.initUI(rootView);
        this.observeViewModel();
        this.fadeIn();
        this.setListeners();
        return rootView;
    }

    private void initUI(View rootView) {
        mViewBackground = rootView.findViewById(R.id.view_background);
        mTextViewCountry = rootView.findViewById(R.id.text_view_country);
        mRadioGroup = rootView.findViewById(R.id.radio_group);
        mRadioM = rootView.findViewById(R.id.radio_m);
        mRadioF = rootView.findViewById(R.id.radio_f);
        mButtonSearch = rootView.findViewById(R.id.button_search);
        mButtonCountry = rootView.findViewById(R.id.button_select_country);
    }

    private void setListeners() {
        mButtonCountry.setOnClickListener(this);
        mButtonSearch.setOnClickListener(this);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if(checkedId == R.id.radio_m) {
                    mApplyFilterViewModel.setGender(Constants.MALE);
                } else if (checkedId == R.id.radio_f) {
                    mApplyFilterViewModel.setGender(Constants.FEMALE);
                }
            }
        });
    }

    private void observeViewModel() {
        mApplyFilterViewModel.getCountry().observe(this, new Observer<Country>() {
            @Override
            public void onChanged(@Nullable Country country) {
                if(country != null) {
                    mTextViewCountry.setText(country.getName());
                }
            }
        });
        mApplyFilterViewModel.getGender().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String gender) {
                if(gender != null) {
                    if(gender.equals(Constants.MALE)) {
                        mRadioM.setChecked(true);
                    } else {
                        mRadioF.setChecked(true);
                    }
                }
            }
        });
    }

    private void fadeIn() {
        AlphaAnimation fadeIn = new AlphaAnimation(0.f, 1.f);
        fadeIn.setDuration(300);
        mViewBackground.startAnimation(fadeIn);
    }

    private void search() {
       // ((AllContactsActivity) getActivity()).setMustShowFilterFragment(false);
        ((AllContactsActivity) getActivity()).filter(mApplyFilterViewModel.getSelectedCountryID(), mApplyFilterViewModel.getSelectedGender());
        //clear the previous search filters before searching again
        mApplyFilterViewModel.clearFilters();
        getFragmentManager().popBackStack();
    }

    public void setCountry(Country country) {
        mApplyFilterViewModel.setCountry(country);
    }

    public void clearFilters() {
        mApplyFilterViewModel.clearFilters();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button_select_country:
                Intent selectCountryIntent = new Intent(getActivity(), SelectCountryActivity.class);
                getActivity().startActivityForResult(selectCountryIntent, SELECT_COUNTRY_REQUEST_CODE);
                break;
            case R.id.button_search:
                this.search();
                break;
        }
    }

}
