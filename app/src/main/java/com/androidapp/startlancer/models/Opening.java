package com.androidapp.startlancer.models;

/**
 * Created by ankit on 2/3/2016.
 */
public class Opening {
    private String title;
    private String salary;

    public Opening() {
    }

    ;

    public Opening(String title, String salary) {
        this.title = title;
        this.salary = salary;
    }

    public String getTitle() {
        return title;
    }

    public String getSalary() {
        return salary;
    }
}
