package com.example.stefan.workup.models;

import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.Serializable;


public class Job implements Serializable, Cloneable{
    private String id;
    private UserLocation userLocation;
    private String jobName;
    private String description;
    private JobType type;
    private double payment;

    private JobStatus status;

    public Job() {}

    public Job(Job job){
        this.id = job.id;
        this.userLocation = job.userLocation;
        this.jobName =job.jobName;
        this.description = job.description;
        this.type = job.type;
        this.payment = job.payment;
        this.status = JobStatus.OPEN;
    }

    public Job(String id, String jobName, String description, JobType type, UserLocation userLocation, double payment){
        this.id = id;
        this.userLocation = userLocation;
        this.jobName =jobName;
        this.description = description;
        this.type = type;
        this.payment = payment;
        this.status = JobStatus.OPEN;
    }


    public Job cloneObject() {
        Job job = new Job();
        job.id = this.id;
        job.userLocation = this.userLocation;
        job.jobName = this.jobName;
        job.description = this.description;
        job.type = this.type;
        job.payment = this.payment;
        job.status = this.status;

        return job;
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

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

}
