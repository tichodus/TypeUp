package com.example.stefan.workup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.stefan.workup.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login;
    private TextView register;
    private DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.etUsername);
        password = (EditText) findViewById(R.id.etPassword);
        login = (Button) findViewById(R.id.btnLogin);
        register = (TextView) findViewById(R.id.tvRegister);

        //Register
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent= new Intent(LoginActivity.this, RegisterActivity.class);
               startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final User loginUser = new User(null,null,null,username.getText().toString(),password.getText().toString(),null);

                dbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot userSnap : dataSnapshot.getChildren())
                        {
                            User user = userSnap.getValue(User.class);
                            if(user.getUsername().equals(loginUser.getUsername()) && user.getPassword().equals(loginUser.getPassword()))
                            {
                                SharedPreferences.Editor editor = getSharedPreferences("User",MODE_PRIVATE).edit();
                                editor.putString("username",loginUser.getUsername());
                                editor.putString("id",user.getId());
                                editor.putString("firstname",user.getFirstname());
                                editor.putString("lastname",user.getLastname());
                                editor.putString("type", user.getType().toString());
                                editor.apply();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
