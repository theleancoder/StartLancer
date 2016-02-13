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
    public static final String FIREBASE_PROPERTY_EMAIL = "email";

    public static final String KEY_PROVIDER_STARTUP = "PROVIDER_STARTUP";
    public static final String KEY_PROVIDER_FREELANCER = "PROVIDER_FREELANCER";
    public static final String KEY_ENCODED_EMAIL_FREELANCER = "ENCODED_EMAIL_FREELANCER";
    public static final String KEY_ENCODED_EMAIL_STARTUP = "ENCODED_EMAIL_STARTUP";
    private static final String FIREBASE_LOCATION_OPENINGS = "openings";
    private static final String FIREBASE_LOCATION_OPENINGS_DETAILS = "openingsDetail";
    public static final String FIREBASE_URL_OPENINGS = FIREBASE_URL + "/" + FIREBASE_LOCATION_OPENINGS;
    private static final String FIREBASE_LOCATION_FREELANCER_PROFILE = "freelancerProfiles";
    public static final String FIREBASE_URL_FREELANCER_PROFILE = FIREBASE_URL + "/" + FIREBASE_LOCATION_FREELANCER_PROFILE;
    public static final String FIREBASE_URL_OPENINGS_DETAIL = FIREBASE_URL + "/" + FIREBASE_LOCATION_OPENINGS_DETAILS;
    private static final String FIREBASE_LOCATION_APPLICATIONS = "applications";
    public static final String FIREBASE_URL_APPLICATIONS = FIREBASE_URL + "/" + FIREBASE_LOCATION_APPLICATIONS;
    private static final String FIREBASE_LOCATION_SAVED_CANDIDATES = "savedCandidates";
    public static final String FIREBASE_URL_SAVED_CANDIDATES = FIREBASE_URL + "/" + FIREBASE_LOCATION_SAVED_CANDIDATES;
}
