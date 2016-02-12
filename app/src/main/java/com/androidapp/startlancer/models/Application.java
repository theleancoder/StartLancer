package com.androidapp.startlancer.models;

/**
 * Created by ankit on 2/11/2016.
 */
public class Application {
    private String name;
    private String email;
    private String isConfirmed;

    public Application() {
    }

    public Application(String name, String email, String isConfirmed) {
        this.name = name;
        this.email = email;
        this.isConfirmed = isConfirmed;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getIsConfirmed() {
        return isConfirmed;
    }
}
