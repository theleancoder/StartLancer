package com.androidapp.startlancer.models;

import java.util.HashMap;

/**
 * Created by ankit on 30/1/16.
 */
public class Startup {
    private String name;
    private String email;
    private String about;
    private HashMap<String, Object> timestampJoined;

    public Startup() {
    }

    public Startup(String name, String email, HashMap<String, Object> timestampJoined) {
        this.name = name;
        this.email = email;
        this.timestampJoined = timestampJoined;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAbout() {
        return about;
    }

    public HashMap<String, Object> getTimestampJoined() {
        return timestampJoined;
    }
}
