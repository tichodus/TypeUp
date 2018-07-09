package com.example.stefan.workup.services;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.example.stefan.workup.MainActivity;
import com.example.stefan.workup.MapFragment;
import com.example.stefan.workup.R;
import com.example.stefan.workup.models.Job;
import com.example.stefan.workup.models.Jobs;
import com.example.stefan.workup.models.UserLocation;

import java.io.Serializable;

public class DistanceService extends Service implements LocationListener {
    private LocationManager locationManager;
    private Jobs jobs;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void sendData(Context context, Jobs jobs) {
        try {
            Intent intent = new Intent(context, DistanceService.class);
            intent.putExtra("454", jobs);
            intent.putExtra("service_command", 1);
            context.startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendCommand(Context context, int flag) {
        try {
            Intent intent = new Intent(context, DistanceService.class);
            intent.putExtra("service_command", flag);
            context.startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        processData(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    private void processData(Intent intent) {
        if (intent != null) {
            int service_command = intent.getIntExtra("service_command", -1);
            switch (service_command) {
                case 1:
                    //updated list
                    jobs = (Jobs) intent.getSerializableExtra("454");
                    break;
                case 2:
                    //start servivce
                    break;
                case 3:
                    if (locationManager != null) {
                        locationManager.removeUpdates(this);
                    }
                    stopSelf();
                    // stop servicel
                    break;
            }

        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initLocation();
    }

    private void initLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 10, this, Looper.getMainLooper());
            }
        }
        //    Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            if (jobs != null && jobs.getJobs() != null) {
                for (Job job : jobs.getJobs()) {
                    if (job.getUserLocation() != null) {
                        UserLocation userLocation = job.getUserLocation();
                        Location location1 = new Location("Jobs");
                        location1.setLatitude(Double.parseDouble(userLocation.getLatitude()));
                        location1.setLongitude(Double.parseDouble(userLocation.getLongitude()));
                        if (location.distanceTo(location1) < 150.0F) {
                            postNotfication();
                            break;
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void postNotfication() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String channelId = "some_channel_id";
        if (notificationManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                CharSequence channelName = "Some Channel";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notificationManager.createNotificationChannel(notificationChannel);
            }
            Notification notification = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                notification = new Notification.Builder(this, channelId)
                        .setContentTitle("Work up")
                        .setContentText("You've new jobs nearby!")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setChannelId(channelId)
                        .build();
            } else {
                notification = new Notification.Builder(this)
                        .setContentTitle("Work up")
                        .setContentText("You've new jobs nearby!")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .build();
            }
            notificationManager.notify(987, notification);
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
