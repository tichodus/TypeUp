package com.example.stefan.workup;


import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.widget.ListView;

import com.example.stefan.workup.adapters.JobsAdapter;
import com.example.stefan.workup.models.Job;
import com.example.stefan.workup.models.Jobs;
import com.example.stefan.workup.models.User;
import com.example.stefan.workup.services.UserService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private User loggedUser = null;
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private ProfileFragment profileFragment;
    private MapFragment mapFragment;
    private JobsFragment jobsFragment;
    private DatabaseReference dbRef;
    private Jobs jobsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);

        profileFragment = new ProfileFragment();
        mapFragment = new MapFragment();
        jobsFragment = new JobsFragment();


        setFragment(profileFragment);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){


                    case R.id.nav_profile:
                        setFragment(profileFragment);
                        return true;

                    case R.id.nav_map:
                        mapFragment.setJobs(jobsList);
                        setFragment(mapFragment);
                        return true;

                    case R.id.nav_jobs:
                        jobsFragment.setUser(loggedUser);
                        jobsFragment.setJobs(jobsList);
                        setFragment(jobsFragment);
                        return true;

                    default: return false;
                }
            }
        });
    }

    @Override
    protected  void onStart() {
        super.onStart();
        loggedUser = UserService.getUserFromPreferences(getSharedPreferences("User",MODE_PRIVATE));
        dbRef = FirebaseDatabase.getInstance().getReference("jobs");
        this.jobsList = new Jobs();

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot jobSnap : dataSnapshot.getChildren()) {
                    Job jobDTO = jobSnap.getValue(Job.class);
                    jobsList.addJob(jobDTO);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setFragment(Fragment fragment){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();
    }
}
