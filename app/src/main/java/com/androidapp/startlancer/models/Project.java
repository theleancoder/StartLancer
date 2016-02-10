package com.androidapp.startlancer.models;

/**
 * Created by ankit on 2/8/2016.
 */
public class Project {
    private String project;
    private String projectDescription;
    private String place;

    public Project() {
    }

    public Project(String project, String projectDescription, String place) {
        this.project = project;
        this.projectDescription = projectDescription;
        this.place = place;
    }

    public String getProject() {
        return project;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public String getPlace() {
        return place;
    }
}
