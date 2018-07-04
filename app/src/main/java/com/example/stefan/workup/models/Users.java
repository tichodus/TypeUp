package com.example.stefan.workup.models;

import java.util.ArrayList;
import java.util.List;

public class Users {
    List<User> users = null;

    public Users(){
        this.users = new ArrayList<User>();
    }

    public List<User> getUsers(){
        return users;
    }

    public void addUser(User user){
        users.add(user);
    }

    public User getUserById(String id){
        User result = null;
        for(User user: users)
        {
            if(user.getId().equals(id))
                result = user;
        }
        return result;
    }

    public User getUserByUsername(String username){
        User result = null;
        for(User user: users)
        {
            if(user.getUsername().equals(username))
                result = user;
        }
        return result;
    }

    public boolean userExist(User user){
        boolean exists = false;
        for(User u: users)
        {
            if(u.getId().equals(user.getId()) || u.getUsername().equals(user.getUsername()))
                exists = true;
        }
        return exists;
    }
}
