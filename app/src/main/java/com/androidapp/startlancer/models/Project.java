package com.androidapp.startlancer.models;

/**
 * Created by ankit on 2/8/2016.
 */
public class Project {
    private String project;
    private String projectLinks;
    private String projectDescription;

    public Project() {
    }

    public Project(String project, String projectLinks, String projectDescription) {
        this.project = project;
        this.projectLinks = projectLinks;
        this.projectDescription = projectDescription;
    }

    public String getProject() {
        return project;
    }

    public String getProjectLinks() {
        return projectLinks;
    }

    public String getProjectDescription() {
        return projectDescription;
    }
}
