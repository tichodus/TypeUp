package com.example.stefan.workup;


import android.bluetooth.BluetoothAdapter;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.widget.ListView;
import android.widget.Toast;

import com.example.stefan.workup.adapters.JobsAdapter;
import com.example.stefan.workup.models.Job;
import com.example.stefan.workup.models.Jobs;
import com.example.stefan.workup.models.User;
import com.example.stefan.workup.services.DistanceService;
import com.example.stefan.workup.services.UserService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private User loggedUser = null;
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private ProfileFragment profileFragment;
    private MapFragment mapFragment;
    private JobsFragment jobsFragment;
    private CreateJobFragment createJobFragment;
    Bundle bundle;

    private DatabaseReference dbRef;
    private Jobs jobsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loggedUser = UserService.getUserFromPreferences(getSharedPreferences("User", MODE_PRIVATE));

        DistanceService.sendCommand(this, 5);

        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);

        profileFragment = new ProfileFragment();
        mapFragment = new MapFragment();
        jobsFragment = new JobsFragment();
        createJobFragment = new CreateJobFragment();


        bundle = new Bundle();
        bundle.putSerializable("234", loggedUser);
        profileFragment.setArguments(bundle);
        setFragment(profileFragment);

//        BluetoothAdapter BT = BluetoothAdapter.getDefaultAdapter();
//        String address = BT.getAddress();
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {


                    case R.id.nav_profile:
                        profileFragment.setUser(loggedUser);
                        setFragment(profileFragment);
                        return true;

                    case R.id.nav_map:
                        mapFragment.setJobs(jobsList);
                        setFragment(mapFragment);
                        return true;

                    case R.id.nav_jobs:
                        setJobsFragment();
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

    private void setJobsFragment() {
        jobsFragment.setArguments(bundle);
        switch(loggedUser.getType()){
            case PROVIDER:
                jobsFragment.setJobs(jobsList);
                setFragment(jobsFragment);
                break;
            case PUBLISHER:
                createJobFragment.setUser(loggedUser);
                setFragment(createJobFragment);
                break;
            default:
                Toast.makeText(this,"User type must be specified",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        dbRef = FirebaseDatabase.getInstance().getReference("jobs");
        this.jobsList = new Jobs();

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                jobsList = new Jobs();
                for (DataSnapshot jobSnap : dataSnapshot.getChildren()) {
                    Job jobDTO = jobSnap.getValue(Job.class);
                    jobsList.addJob(jobDTO);
                }
                DistanceService.sendData(MainActivity.this, jobsList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    public void reinitalizeMapFragment(List<Job> jobs) {
     /*   if (mapFragment != null) {
            jobsList.setJobs(jobs);
            mapFragment.setJobs(jobsList);
            mapFragment.setJobsOnMap();
        }*/
        Jobs newJobs = new Jobs(jobs);
        mapFragment = new MapFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("23", newJobs);
        mapFragment.setArguments(bundle);

    }
}
