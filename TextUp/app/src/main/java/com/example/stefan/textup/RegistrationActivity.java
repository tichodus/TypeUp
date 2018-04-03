package com.example.stefan.textup;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import models.UserModel;
import services.AsyncResponse;
import services.HttpGetService;
import services.HttpPostService;

public class RegistrationActivity extends AppCompatActivity implements AsyncResponse {
    TextView tvResult;
    EditText firstName;
    EditText lastName;
    EditText username;
    EditText password;
    EditText repeatedPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Button register = (Button) findViewById(R.id.bRegister);
        firstName = (EditText) findViewById(R.id.etFirstName);
        lastName = (EditText) findViewById(R.id.etLastName);
        username = (EditText) findViewById(R.id.etUsername);
        password = (EditText) findViewById(R.id.etPassword);
        repeatedPassword = (EditText) findViewById(R.id.etRepeatPassword);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //make model
                UserModel user = new UserModel(firstName.getText().toString(),lastName.getText().toString(),password.getText().toString(),username.getText().toString());
                Gson gson = new Gson();
                final String sendData = gson.toJson(user);

                //make Get request
                HttpPostService post = new HttpPostService(sendData);
                post.setAsyncResponse(RegistrationActivity.this);
                post.execute("register");
            }
        });
    }

    @Override
    public void processResponse(String response) {

    }
}
