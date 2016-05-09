package com.androidapp.startlancer.utils;

import com.androidapp.startlancer.BuildConfig;

/**
 * Created by ankit on 29/1/16.
 */
public final class Constants {
    public static final String FIREBASE_URL = BuildConfig.UNIQUE_FIREBASE_ROOT_URL;
    public static final String FIREBASE_LOCATION_USERS = "USERS";
    public static final String FIREBASE_PROPERTY_TIMESTAMP = "timestamp";

    //Constants for Firebase login
    public static final String PASSWORD_PROVIDER = "password";
    public static final String GOOGLE_PROVIDER = "google";
    public static final String FIREBASE_URL_USERS = FIREBASE_URL + "/" + FIREBASE_LOCATION_USERS;
    private static final String FIREBASE_LOCATION_STARTUPS = "STARTUPS";
    public static final String FIREBASE_URL_STARTUPS = FIREBASE_URL + "/" + FIREBASE_LOCATION_STARTUPS;
    public static final String KEY_GOOGLE_EMAIL = "GOOGLE_EMAIL";
    public static final String PROVIDER_DATA_DISPLAY_NAME = "displayName";
    public static final String FIREBASE_PROPERTY_EMAIL = "email";

    public static final String KEY_PROVIDER_STARTUP = "PROVIDER_STARTUP";
    public static final String KEY_PROVIDER_FREELANCER = "PROVIDER_FREELANCER";
    public static final String KEY_ENCODED_EMAIL_FREELANCER = "ENCODED_EMAIL_FREELANCER";
    public static final String KEY_ENCODED_EMAIL_STARTUP = "ENCODED_EMAIL_STARTUP";
    private static final String FIREBASE_LOCATION_OPENINGS = "OPENINGS";
    private static final String FIREBASE_LOCATION_OPENINGS_DETAILS = "OPENING DETAIL";
    public static final String FIREBASE_URL_OPENINGS = FIREBASE_URL + "/" + FIREBASE_LOCATION_OPENINGS;
    private static final String FIREBASE_LOCATION_FREELANCER_PROFILE = "FREELANCER PROFILE";
    public static final String FIREBASE_URL_FREELANCER_PROFILE = FIREBASE_URL + "/" + FIREBASE_LOCATION_FREELANCER_PROFILE;
    public static final String FIREBASE_URL_OPENINGS_DETAIL = FIREBASE_URL + "/" + FIREBASE_LOCATION_OPENINGS_DETAILS;
    private static final String FIREBASE_LOCATION_APPLICATIONS = "APPLICATIONS";
    public static final String FIREBASE_URL_APPLICATIONS = FIREBASE_URL + "/" + FIREBASE_LOCATION_APPLICATIONS;
    private static final String FIREBASE_LOCATION_SAVED_CANDIDATES = "SAVED CANDIDATES";
    private static final String FIREBASE_LOCATION_COFOUNDERS = "COFOUNDER";
    private static final String FIREBASE_LOCATION_COFOUNDERS_DETAIL = "COFOUNDER DETAIL";
    public static final String FIREBASE_URL_SAVED_CANDIDATES = FIREBASE_URL + "/" + FIREBASE_LOCATION_SAVED_CANDIDATES;
    public static final String FIREBASE_URL_COFOUNDERS = FIREBASE_URL + "/" + FIREBASE_LOCATION_COFOUNDERS;
    public static final String FIREBASE_URL_COFOUNDERS_DETAIL = FIREBASE_URL + "/" + FIREBASE_LOCATION_COFOUNDERS_DETAIL;
    public static final String KEY_GOOGLE_ID = "GOOGLE_ID";
    private static final String FIREBASE_LOCATION_OPEN_PROJECTS = "OPEN PROJECTS";
    private static final String FIREBASE_LOCATION_SKILLS = "SKILLS";
    public static final String FIREBASE_URL_OPEN_PROJECTS = FIREBASE_URL + "/" + FIREBASE_LOCATION_OPEN_PROJECTS;
    public static final String FIREBASE_URL_SKILLS = FIREBASE_URL + "/" + FIREBASE_LOCATION_SKILLS;
    public static final String FIREBASE_LOCATION_STARTUP_MEMBERS = "STARTUP MEMBERS";
    public static final String FIREBASE_URL_STARTUP_MEMBERS = FIREBASE_URL + "/" + FIREBASE_LOCATION_STARTUP_MEMBERS;
    public static final String FIREBASE_LOCATION_RESPONSES = "RESPONSES";
    public static final String FIREBASE_APPLICATION_RESPONSES = FIREBASE_URL + "/" + FIREBASE_LOCATION_RESPONSES;
}
