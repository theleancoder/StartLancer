package com.androidapp.startlancer.models;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by ankit on 30/1/16.
 */
public class Startup {
    private String name;
    private String email;
    private HashMap<String, Object> timestampJoined;
    private int topCount;
    private int trendingCount;
    private Date date;

    public Startup() {
    }

    public Startup(String name, String email, HashMap<String, Object> timestampJoined, int topCount, int trendingCount) {
        this.name = name;
        this.email = email;
        this.timestampJoined = timestampJoined;
        this.topCount = topCount;
        this.trendingCount = trendingCount;
        this.date = new Date();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public HashMap<String, Object> getTimestampJoined() {
        return timestampJoined;
    }

    public int getTopCount() {
        return topCount;
    }

    public int getTrendingCount() {
        return trendingCount;
    }

    public Date getDate() {
        return date;
    }
}
