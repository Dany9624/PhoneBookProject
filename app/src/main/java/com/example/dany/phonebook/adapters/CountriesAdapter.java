package com.example.dany.phonebook.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dany.phonebook.R;
import com.example.dany.phonebook.models.Country;

import java.util.List;

/**
 * Created by Dany on 13.10.2018 г..
 */

public class CountriesAdapter extends BaseAdapter{

    private Context mContext;
    private List<Country> mListCountries;

    public CountriesAdapter(Context context, List<Country>  listCountries) {
        mContext = context;
        mListCountries = listCountries;
    }

    @Override
    public int getCount() {
        return mListCountries.size();
    }

    @Override
    public Object getItem(int position) {
        return mListCountries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_countries_item, viewGroup, false);
        }

        TextView textViewCountryName = convertView.findViewById(R.id.text_view_country_name);
        textViewCountryName.setText(mListCountries.get(position).getName());

        return convertView;
    }

    public void reloadData(List<Country> listCountries) {
        mListCountries = listCountries;
        notifyDataSetChanged();
    }
}
