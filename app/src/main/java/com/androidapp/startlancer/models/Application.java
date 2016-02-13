package com.androidapp.startlancer.models;

/**
 * Created by ankit on 2/11/2016.
 */
public class Application {
    private String name;
    private String email;

    public Application() {
    }

    public Application(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

}
