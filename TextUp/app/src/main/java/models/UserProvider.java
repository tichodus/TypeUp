package models;


import java.util.ArrayList;
import java.util.Iterator;

import interfaces.IJobProvider;

/**
 * Created by Stefan on 4/13/2018.
 */

public class UserProvider extends User implements IJobProvider{
    private ArrayList<Job> submitedJobs;
    private ArrayList<Job> allowedJobs;
    public UserProvider(String id, String firstName, String lastName, String password, String username, String email) {
        super(id, firstName, lastName, password, username, email);
        this.submitedJobs =  new ArrayList<Job>();
        this.allowedJobs =  new ArrayList<Job>();
    }

    @Override
    public void submitJob(Job job) {
        this.submitedJobs.add(job);
    }

    @Override
    public void cancelSubmition(Job job) {
        Iterator<Job> jobIterator = this.submitedJobs.iterator();
        while(jobIterator.hasNext()){
            Job submitedJob = jobIterator.next();
            if(submitedJob.getId() == job.getId())
                jobIterator.remove();
        }
    }
}
