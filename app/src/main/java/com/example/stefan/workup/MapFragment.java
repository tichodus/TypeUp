package com.example.stefan.workup;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.stefan.workup.models.Job;
import com.example.stefan.workup.models.JobStatus;
import com.example.stefan.workup.models.Jobs;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, LocationListener,GoogleMap.OnMarkerClickListener {

    private static final int REQUEST_CODE_GPS = 453;
    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;
    private LocationManager locationManager;
    private Jobs jobsList;
    DatabaseReference dbRef;
    Map<String, Job> jobMap;
    public MapFragment() {
        // Required empty public constructor
        jobMap = new HashMap<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_map, container, false);
        dbRef = FirebaseDatabase.getInstance().getReference("jobs");
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = (MapView) mView.findViewById(R.id.map);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }


        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                jobsList = new Jobs();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Job job = snapshot.getValue(Job.class);
                    jobsList.addJob(job);
                }
                if (mGoogleMap!=null) {
                    mGoogleMap.clear();
                    setJobsOnMap();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initLocation() {
        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_GPS);
            } else {
                setJobsOnMap();
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 5, MapFragment.this, Looper.getMainLooper());
            }
        }
        //    Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_GPS) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initLocation();
            } else {
                Toast.makeText(getContext(), "Perssmison has to be granted!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (getActivity() == null || getContext() == null) {
            return;
        }
        MapsInitializer.initialize((getContext()));

        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        initLocation();
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 5);
            return;
        } else {
            mGoogleMap.setMyLocationEnabled(true);
            setJobsOnMap();
        }


    }

    public void setJobs(Jobs jobs) {
        this.jobsList = jobs;
    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(getContext(), String.valueOf(location.getAltitude()), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onDetach() {
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }

        super.onDetach();
    }

    private BitmapDescriptor getMarkerColor(Job job) {
        JobStatus jobStatus = job.getStatus();
        BitmapDescriptor result = null;

        switch (jobStatus) {
            case DONE:
                result = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
                break;
            case PENDING:
                result = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
                break;
            case OPEN:
                result = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
                break;

            default:
                result = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN);
                break;
        }

        return result;
    }


    private void setMarker(Job job, int i) {
        String latitude = job.getUserLocation().getLatitude();
        String longitude = job.getUserLocation().getLongitude();

        if (latitude != null && longitude != null) {
            Marker marker =   mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(latitude) + i, Double.parseDouble(longitude) + i)).title(job.getJobName()).snippet(job.getDescription()).icon(this.getMarkerColor(job)));
            mGoogleMap.setOnMarkerClickListener(this);
            jobMap.put(marker.getId(),job);
        }
    }



    public void setJobsOnMap() {
        if (getArguments() != null) {
            jobsList = (Jobs) getArguments().getSerializable("23");
        }
        if (mGoogleMap == null) {
            return;
        }
        mGoogleMap.clear();
        int i = 0;
        for (Job job : jobsList.getJobs()) {
            setMarker(job, i++);
        }
    }




    @Override
    public boolean onMarkerClick(Marker marker) {

       if(jobMap.get(marker.getId()) != null){
           Job job = jobMap.get(marker.getId());
           Intent intent = new Intent(getContext(), JobDetails.class);
           intent.putExtra("job", (Serializable) job);
           startActivityForResult(intent,2);
           return true;
       }
       return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2 && data!=null){
            Job job = (Job) data.getSerializableExtra("updatedJob");
            for (Job job1 : jobsList.getJobs()) {
                if(job1.getId().equalsIgnoreCase(job.getId()))
                    job1.setStatus(job.getStatus());
            }
            Map<String, Object> jobHashMap = new HashMap<>();
            jobHashMap.put("status", job.getStatus());
            dbRef.child(job.getId()).updateChildren(jobHashMap);
        }

    }
}
