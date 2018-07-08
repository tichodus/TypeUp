package com.example.stefan.workup.models;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class UserLocation implements Serializable {
    private User user;
    private String longitude;
    private String latitude;

    public UserLocation() { }

    public UserLocation(User user)
    {
        this.user = user;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
