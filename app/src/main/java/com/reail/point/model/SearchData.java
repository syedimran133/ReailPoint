package com.reail.point.model;

public class SearchData {
    private String Name;
    private String status;
    private String lat;
    private String log;

    public SearchData(String name, String status, String lat, String log) {
        Name = name;
        this.status = status;
        this.lat = lat;
        this.log = log;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}
