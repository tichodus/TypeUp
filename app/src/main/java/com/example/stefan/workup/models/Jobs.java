package com.example.stefan.workup.models;

import java.util.ArrayList;
import java.util.List;

public class Jobs {
    List<Job> jobs = null;

    public Jobs(){
        this.jobs = new ArrayList<Job>();
    }

    public List<Job> getJobs(){
        return jobs;
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
}
