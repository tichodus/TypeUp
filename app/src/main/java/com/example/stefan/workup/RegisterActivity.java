package com.example.stefan.workup;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.stefan.workup.models.User;
import com.example.stefan.workup.models.UserType;
import com.example.stefan.workup.models.Users;
import com.example.stefan.workup.services.AsyncResponse;
import com.example.stefan.workup.services.HttpPostService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.Console;

public class RegisterActivity extends Activity {

    private EditText firstname;
    private EditText lastname;
    private EditText username;
    private EditText password;
    private Button register;
    private RadioGroup userType;
    private DatabaseReference dbRef = null;
    private Users users = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstname = (EditText) findViewById(R.id.etFirstname);
        lastname = (EditText) findViewById(R.id.etLastname);
        username = (EditText) findViewById(R.id.etUsername);
        password = (EditText) findViewById(R.id.etPassword);
        register = (Button) findViewById(R.id.btnRegister);
        userType = (RadioGroup)  findViewById(R.id.rgUserType);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                String id = dbRef.push().getKey();

                UserType type = getUserTypeValue(userType);
                User user = new User(id,firstname.getText().toString(),lastname.getText().toString(),username.getText().toString(),password.getText().toString(),type);
                if(!users.userExist(user))
                {
                    users.addUser(user);
                    dbRef.child(id).setValue(user);
                }

            }
        });
    }

    @Override
    protected  void onStart(){
        super.onStart();
        users = new Users();
        dbRef = FirebaseDatabase.getInstance().getReference("users");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot userSnapshot :dataSnapshot.getChildren())
                {
                    try
                    {
                    User user = (User) userSnapshot.getValue(User.class);

                    users.addUser(user);
                    }
                    catch (Exception e)
                    {
                        System.err.println(e.toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private UserType getUserTypeValue(RadioGroup group) {
        UserType type = null;
        switch (group.getCheckedRadioButtonId()) {
            case R.id.rbProvider:
                type = UserType.PROVIDER;
                break;

            case R.id.rbPublisher:
                type = UserType.PUBLISHER;
                break;
        }
        return type;
    }
}
