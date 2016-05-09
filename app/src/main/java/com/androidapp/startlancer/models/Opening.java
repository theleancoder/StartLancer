package com.androidapp.startlancer.models;

/**
 * Created by ankit on 2/9/2016.
 */
public class Opening {
    private String title;
    private String salary;
    private String description;
    private String requirements;
    private String responsibilities;

    public Opening() {
    }

    public Opening(String title, String salary, String description, String requirements, String responsibilities) {
        this.title = title;
        this.salary = salary;
        this.description = description;
        this.requirements = requirements;
        this.responsibilities = responsibilities;
    }

    public String getTitle() {
        return title;
    }

    public String getSalary() {
        return salary;
    }

    public String getDescription() {
        return description;
    }

    public String getRequirements() {
        return requirements;
    }

    public String getResponsibilities() {
        return responsibilities;
    }
}
