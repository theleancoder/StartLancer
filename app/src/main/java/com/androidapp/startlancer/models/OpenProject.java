package com.androidapp.startlancer.models;

/**
 * Created by ankit on 2/22/2016.
 */
public class OpenProject {
    private String title, description, links, technologies, author, authorEmail;

    public OpenProject() {
    }

    public OpenProject(String title, String description, String links,
                       String technologies, String author, String authorEmail) {
        this.title = title;
        this.description = description;
        this.links = links;
        this.technologies = technologies;
        this.author = author;
        this.authorEmail = authorEmail;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLinks() {
        return links;
    }

    public String getTechnologies() {
        return technologies;
    }

    public String getAuthor() {
        return author;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }
}
