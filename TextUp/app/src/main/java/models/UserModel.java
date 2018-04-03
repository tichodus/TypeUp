package models;

/**
 * Created by Stefan on 3/15/2018.
 */

public class UserModel {
    private String id;
    private String firstName;
    private String lastName;
    private String password;
    private String username;

    public UserModel(String firstName, String lastName, String password, String username){
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.username = username;
    }


    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

}
