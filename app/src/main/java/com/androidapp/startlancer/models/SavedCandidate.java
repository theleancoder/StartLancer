package com.androidapp.startlancer.models;

/**
 * Created by ankit on 2/15/2016.
 */
public class SavedCandidate {
    private String name;
    private String email;
    private String opening;

    public SavedCandidate() {
    }

    public SavedCandidate(String name, String email, String opening) {
        this.name = name;
        this.email = email;
        this.opening = opening;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getOpening() {
        return opening;
    }
}
