package com.androidapp.startlancer.models;

/**
 * Created by ankit on 2/3/2016.
 */
public class OpeningDetail {
    private String requirements;
    private String responsibilities;
    private String salary;
    private String title;

    public OpeningDetail() {
    }

    public OpeningDetail(String requirements, String responsibilities, String salary, String title) {
        this.requirements = requirements;
        this.responsibilities = responsibilities;
        this.salary = salary;
        this.title = title;
    }

    public String getRequirements() {
        return requirements;
    }

    public String getResponsibilities() {
        return responsibilities;
    }

    public String getSalary() {
        return salary;
    }

    public String getTitle() {
        return title;
    }
}
