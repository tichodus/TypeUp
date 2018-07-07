package com.example.stefan.workup;


import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;

import com.example.stefan.workup.models.User;
import com.example.stefan.workup.services.UserService;

public class MainActivity extends AppCompatActivity {
    private User loggedUser = null;
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private ProfileFragment profileFragment;
    private MapFragment mapFragment;
    private JobsFragment jobsFragment;



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
                        setFragment(mapFragment);
                        return true;

                    case R.id.nav_jobs:
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
    }

    private void setFragment(Fragment fragment){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();
    }
}
