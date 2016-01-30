package com.androidapp.startlancer.utils;

/**
 * Created by ankit on 29/1/16.
 */
public class Utils {
    public static String encodeEmail(String email) {
        return email.replace(".", ",");
    }
}
