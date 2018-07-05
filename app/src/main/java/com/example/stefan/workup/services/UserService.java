package com.example.stefan.workup.services;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.stefan.workup.models.User;
import com.example.stefan.workup.models.UserType;

public class UserService {
    public static User getUserFromPreferences(SharedPreferences preference){
        String username = preference.getString("username","");
        String firstname = preference.getString("firstname","");
        String lastname = preference.getString("lastname","");
        String type = preference.getString("type","");
        String id = preference.getString("id","");

        UserType userType;

       if(type.equals(UserType.PUBLISHER.toString()))
           userType = UserType.PUBLISHER;
       else
           userType = UserType.PROVIDER;

        return new User(id,firstname,lastname,username,"",userType);
    }
}
