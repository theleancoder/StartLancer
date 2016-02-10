package com.androidapp.startlancer.models;

/**
 * Created by ankit on 2/8/2016.
 */
public class Experience {
    private String experience;
    private String experienceDescription;
    private String place;

    public Experience() {
    }

    public Experience(String experience, String experienceDescription, String place) {
        this.experience = experience;
        this.experienceDescription = experienceDescription;
        this.place = place;
    }

    public String getExperience() {
        return experience;
    }

    public String getExperienceDescription() {
        return experienceDescription;
    }

    public String getPlace() {
        return place;
    }
}
