package com.androidapp.startlancer.models;

/**
 * Created by ankit on 2/8/2016.
 */
public class Skill {

    private String skillType;
    private String yearsOfExperience;

    public Skill() {
    }

    public Skill(String skillType, String yearsOfExperience) {
        this.skillType = skillType;
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getSkillType() {
        return skillType;
    }

    public String getYearsOfExperience() {
        return yearsOfExperience;
    }
}
