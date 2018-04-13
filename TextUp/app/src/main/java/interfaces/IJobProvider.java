package interfaces;

import models.Job;

/**
 * Created by Stefan on 4/13/2018.
 */

public interface IJobProvider {

    public void submitJob(Job job);

    public void cancelSubmition(Job job);

}
