package com.example.stefan.workup.models;

import android.widget.ArrayAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Jobs implements Serializable, Cloneable{
    List<Job> jobs = null;

    public Jobs(){
        this.jobs = new ArrayList<Job>();
    }

    public List<Job> getJobs(){
        return jobs;
    }

    public Jobs(List<Job> jobs){
        this.jobs = jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public void addJob(Job job){
        jobs.add(job);
    }

    public Job getJobById(String id){
        Job result = null;
        for(Job job: jobs)
        {
            if(job.getId().equals(id))
                result = job;
        }
        return result;
    }

    public boolean jobExists(Job job){
        boolean exists = false;
        for(Job j: jobs)
        {
            if(j.getId().equals(job.getId()))
                exists = true;
        }
        return exists;
    }

    public List<Job> getJobsByType(JobType type){
        List<Job> result = new ArrayList<Job>();

        for(Job job : jobs)
        {
            if(job.getType() ==  type)
                result.add(job);
        }

        return result;
    }

    public Jobs cloneObject(){
        Jobs jobs = new Jobs();
        jobs.setJobs(this.jobs);
        return jobs;
    }

    public void updateJob(Job update){
        if(jobExists(update)){
            Job job = getJobById(update.getId());
            job.setStatus(update.getStatus());
            job.setDescription(update.getDescription());
            job.setId(update.getId());
            job.setJobName(update.getJobName());
            job.setUserLocation(update.getUserLocation());
            job.setPayment(update.getPayment());
            job.setType(update.getType());
        }
    }
}
