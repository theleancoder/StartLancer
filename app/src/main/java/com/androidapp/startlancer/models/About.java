package com.androidapp.startlancer.models;

/**
 * Created by ankit on 2/8/2016.
 */
public class About {
    private String contact;
    private String githubAccout;
    private String about;

    public About() {
    }

    public About(String contact, String githubAccout, String about) {
        this.contact = contact;
        this.githubAccout = githubAccout;
        this.about = about;
    }

    public String getContact() {
        return contact;
    }

    public String getGithubAccout() {
        return githubAccout;
    }

    public String getAbout() {
        return about;
    }
}
