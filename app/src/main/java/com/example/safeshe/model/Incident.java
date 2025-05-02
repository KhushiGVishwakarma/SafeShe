package com.example.safeshe.model;

import com.google.firebase.Timestamp;

public class Incident {

    private String incidentType;
    private String location;
    private String description;
    private Timestamp timestamp;

    public Incident() {}

    public Incident(String incidentType, String location, String description, Timestamp timestamp) {
        this.incidentType = incidentType;
        this.location = location;
        this.description = description;
        this.timestamp = timestamp;
    }

    public String getIncidentType() {
        return incidentType;
    }

    public void setIncidentType(String incidentType) {
        this.incidentType = incidentType;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
