package com.example.stefan.workup;


import android.Manifest;
import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.stefan.workup.models.Job;
import com.example.stefan.workup.models.JobType;
import com.example.stefan.workup.models.User;
import com.example.stefan.workup.models.UserLocation;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateJobFragment extends Fragment implements LocationListener {

    private DatabaseReference dbRef;
    private EditText mDesc;
    private EditText mJobTitle;
    private RadioGroup mJobType;
    private Button mCreateJob;
    private EditText mPayment;

    private LocationManager locationManager;

    private View view;
    private static final int REQUEST_CODE_GPS = 454;

    private String longitude;
    private String latitude;
    private User loggedUser;

    public CreateJobFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.fragment_create_job, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbRef = FirebaseDatabase.getInstance().getReference("jobs");

        mDesc = (EditText) view.findViewById(R.id.desc);
        mJobTitle = (EditText) view.findViewById(R.id.job_title);
        mJobType = (RadioGroup) view.findViewById(R.id.radio_group);
        mCreateJob = (Button) view.findViewById(R.id.create_job);
        mPayment = (EditText) view.findViewById(R.id.payment);
        mCreateJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLocation userLocation = getUserLocation();
                String id = dbRef.push().getKey();
                JobType jobType = getJobType(mJobType);
                Job job = new Job(id, mJobTitle.getText().toString(), mDesc.getText().toString(), jobType, userLocation, Double.parseDouble(mPayment.getText().toString()));
                dbRef.child(id).setValue(job);
            }
        });

        initLocation();
    }

    private UserLocation getUserLocation() {
        UserLocation userLocation = new UserLocation(loggedUser);
        if (this.longitude == null || this.latitude == null) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_GPS);

            }
           else if(this.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) != null) {
                this.longitude = String.valueOf(this.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude());
                this.latitude = String.valueOf(this.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude());
            }
        }
        userLocation.setLatitude(this.latitude);
        userLocation.setLongitude(this.longitude);
        return userLocation;
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
    private JobType getJobType(RadioGroup mJobType) {

        JobType type = null;
        switch (mJobType.getCheckedRadioButtonId()) {
            case R.id.pharmacy:
                type = JobType.PHARMACY;
                break;

            case R.id.market:
                type = JobType.MARKET;
                break;

            case R.id.fast_food:
                type = JobType.FAST_FOOD;
                break;

            case R.id.dog_walker:
                type = JobType.DOG_WALKER;
                break;

        }
        return type;

    }

    private void initLocation() {
        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_GPS);
            } else {
                locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER,this,Looper.getMainLooper());
               // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, CreateJobFragment.this, Looper.getMainLooper());
            }
        }
        //    Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

    }

    @Override
    public void onLocationChanged(Location location) {
         this.latitude = String.valueOf(location.getLatitude());
        this.longitude = String.valueOf(location.getLongitude());
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

    public void setUser(User user) {
        this.loggedUser = user;
    }
}
