package models;

/**
 * Created by Stefan on 4/13/2018.
 */

public class Job {

    private String id;
    private String name;
    private String description;
    private int deposite;
    private JobComplexity complexity;

    public Job(String id, String name, String description, int deposite, JobComplexity complexity){
        this.id = id;
        this.name = name;
        this.description = description;
        this.deposite = deposite;
        this.complexity = complexity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getDeposite() {
        return deposite;
    }

    public JobComplexity getComplexity() {
        return complexity;
    }

}
