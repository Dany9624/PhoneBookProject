package com.example.dany.phonebook.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.example.dany.phonebook.utils.Constants;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Dany on 13.10.2018 г..
 */

@Entity(tableName = Constants.CONTACT_TABLE,
        foreignKeys = @ForeignKey(entity = Country.class,
                                    parentColumns = Constants.COUNTRY_ID,
                                    childColumns = Constants.COUNTRY_ID,
                                    onDelete = CASCADE))
public class Contact {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.CONTACT_ID)
    private int id;

    @ColumnInfo(name = Constants.CONTACT_FIRST_NAME)
    private String firstName;

    @ColumnInfo(name = Constants.CONTACT_LAST_NAME)
    private String lastName;

    @ColumnInfo(name = Constants.CONTACT_EMAIL)
    private String email;

    @ColumnInfo(name = Constants.CONTACT_SEX)
    private String sex;

    @ColumnInfo(name = Constants.CONTACT_PHONE)
    private String phone;

    @ColumnInfo(name = Constants.COUNTRY_ID)
    private int countryId;

    public Contact(String firstName, String lastName, String email, String sex, String phone, int countryId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.sex = sex;
        this.phone = phone;
        this.countryId = countryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
}
