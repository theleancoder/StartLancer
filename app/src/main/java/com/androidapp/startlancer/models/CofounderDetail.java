package com.androidapp.startlancer.models;

/**
 * Created by ankit on 2/15/2016.
 */
public class CofounderDetail {
    private String location;
    private String cofounderReq;
    private String cofounderReason;

    public CofounderDetail() {
    }

    public CofounderDetail(String location, String cofounderReq, String cofounderReason) {
        this.location = location;
        this.cofounderReq = cofounderReq;
        this.cofounderReason = cofounderReason;
    }

    public String getLocation() {
        return location;
    }

    public String getCofounderReq() {
        return cofounderReq;
    }

    public String getCofounderReason() {
        return cofounderReason;
    }
}
