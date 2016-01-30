package com.androidapp.startlancer.utils;

import com.androidapp.startlancer.BuildConfig;

/**
 * Created by ankit on 29/1/16.
 */
public final class Constants {
    public static final String FIREBASE_URL = BuildConfig.UNIQUE_FIREBASE_ROOT_URL;
    public static final String FIREBASE_LOCATION_USERS = "users";
    public static final String FIREBASE_PROPERTY_TIMESTAMP = "timestamp";

    //Constants for Firebase login
    public static final String PASSWORD_PROVIDER = "password";
    public static final String GOOGLE_PROVIDER = "google";
    public static final String FIREBASE_URL_USERS = FIREBASE_URL + "/" + FIREBASE_LOCATION_USERS;
    private static final String FIREBASE_LOCATION_STARTUPS = "startups";
    public static final String FIREBASE_URL_STARTUPS = FIREBASE_URL + "/" + FIREBASE_LOCATION_STARTUPS;
    public static final String KEY_GOOGLE_EMAIL = "GOOGLE_EMAIL";
    public static final String PROVIDER_DATA_DISPLAY_NAME = "displayName";
}
