package com.example.dany.phonebook.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.example.dany.phonebook.utils.Constants;

import java.io.Serializable;

/**
 * Created by Dany on 13.10.2018 г..
 */

@Entity(tableName = Constants.COUNTRY_TABLE)
public class Country implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.COUNTRY_ID)
    private int id;

    @ColumnInfo(name = Constants.COUNTRY_NAME)
    private String name;

    @ColumnInfo(name = Constants.COUNTRY_CODE)
    private String code;

    public Country(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
