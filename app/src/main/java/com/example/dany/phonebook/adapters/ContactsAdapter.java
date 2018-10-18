package com.example.dany.phonebook.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dany.phonebook.R;
import com.example.dany.phonebook.models.Contact;

import java.util.List;

/**
 * Created by Dany on 15.10.2018 г..
 */

public class ContactsAdapter extends BaseAdapter {

    private Context mContext;
    private List<Contact> mListContacts;

    public ContactsAdapter(Context context, List<Contact> listContacts) {
        mContext = context;
        mListContacts = listContacts;
    }

    @Override
    public int getCount() {
        return mListContacts.size();
    }

    @Override
    public Object getItem(int position) {
        return mListContacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_view_all_contacts_item, viewGroup, false);
        }

        Contact contact = mListContacts.get(position);

        TextView textViewContactName = convertView.findViewById(R.id.text_view_contact_name);
        //TextView textViewCircle = convertView.findViewById(R.id.text_view_circle);
        textViewContactName.setText(mContext.getResources().getString(R.string.first_last_name, contact.getFirstName(), contact.getLastName()));
        //textViewCircle.setText(String.valueOf(contact.getFirstName().charAt(0)));

        return convertView;
    }

    public void reloadData(List<Contact> listContacts) {
        mListContacts = listContacts;
        notifyDataSetChanged();
    }
}
