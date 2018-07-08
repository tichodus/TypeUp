package com.example.stefan.workup.models;

import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.Serializable;


public class Job implements Serializable{
    private String id;
    private UserLocation userLocation;
    private String jobName;
    private String description;
    private JobType type;
    private double payment;

    public Job() {}

    public Job(String id, String jobName, String description, JobType type, UserLocation userLocation, double payment){
        this.id = id;
        this.userLocation = userLocation;
        this.jobName =jobName;
        this.description = description;
        this.type = type;
        this.payment = payment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JobType getType() {
        return type;
    }

    public void setType(JobType type) {
        this.type = type;
    }

    public UserLocation getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(UserLocation userLocation) {
        this.userLocation = userLocation;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }
}
