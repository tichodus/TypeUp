package com.example.stefan.workup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.stefan.workup.models.User;
import com.example.stefan.workup.services.UserService;

public class MainActivity extends AppCompatActivity {
    private User loggedUser = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected  void onStart() {
        super.onStart();
        loggedUser = UserService.getUserFromPreferences(getSharedPreferences("User",MODE_PRIVATE));
    }
}
