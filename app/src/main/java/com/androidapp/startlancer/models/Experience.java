package com.androidapp.startlancer.models;

/**
 * Created by ankit on 2/8/2016.
 */
public class Experience {
    private String exp;
    private String expDescription;
    private String expLocation;
    private String expDuration;

    public Experience() {
    }

    public Experience(String exp, String expDescription, String expLocation, String expDuration) {
        this.exp = exp;
        this.expDescription = expDescription;
        this.expLocation = expLocation;
        this.expDuration = expDuration;
    }

    public String getExp() {
        return exp;
    }

    public String getExperienceDescription() {
        return expDescription;
    }

    public String getExperienceLocation() {
        return expLocation;
    }

    public String getExperienceDuration() {
        return expDuration;
    }
}
