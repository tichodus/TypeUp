package interfaces;

import models.Job;

/**
 * Created by Stefan on 4/13/2018.
 */

public interface IJobPublisher {

    public void publishJob(Job job);

    public void cancelPublication(Job job);

}
