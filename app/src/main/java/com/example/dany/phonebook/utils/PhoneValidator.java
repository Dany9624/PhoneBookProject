package com.example.dany.phonebook.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dany on 17.10.2018 г..
 */

public class PhoneValidator {

    private static final Pattern VALID_PHONE_REGEX =
            Pattern.compile("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String phone) {
        Matcher matcher = VALID_PHONE_REGEX .matcher(phone);
        return matcher.find();
    }

}
