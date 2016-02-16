package com.androidapp.startlancer.models;

/**
 * Created by ankit on 2/15/2016.
 */
public class Cofounder {
    private String name;
    private String email;
    private String location;

    public Cofounder() {
    }

    public Cofounder(String name, String email, String location) {
        this.name = name;
        this.email = email;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLocation() {
        return location;
    }
}
