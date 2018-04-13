package models;

/**
 * Created by Stefan on 3/15/2018.
 */

public abstract class User {
    private String id;
    private String firstName;
    private String lastName;
    private String password;
    private String username;
    private String email;
    private UserType type;

    public User(String id, String firstName, String lastName, String password, String username,String email){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.username = username;
        this.email = email;
    }

    public String getId() { return id; }

    public String getFirstName() { return firstName; }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public UserType getType() { return type; }

}
