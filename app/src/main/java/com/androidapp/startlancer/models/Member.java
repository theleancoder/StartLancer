package com.androidapp.startlancer.models;

/**
 * Created by ankit on 2/28/2016.
 */
public class Member {
    private String name;
    private String email;
    private String position;
    private String contact;
    private String about;

    public Member() {
    }

    public Member(String name, String email, String position, String contact, String about) {
        this.name = name;
        this.email = email;
        this.position = position;
        this.contact = contact;
        this.about = about;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPosition() {
        return position;
    }

    public String getContact() {
        return contact;
    }

    public String getAbout() {
        return about;
    }
}
