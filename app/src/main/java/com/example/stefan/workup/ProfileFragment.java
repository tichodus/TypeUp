package com.example.stefan.workup;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stefan.workup.models.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private TextView fullName;
    private TextView username;
    private TextView userType;
    private TextView jobsDone;

    User loggedUser;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        loggedUser = (User) getArguments().getSerializable("234");
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fullName = (TextView) getActivity().findViewById(R.id.tvFullname);
        username = (TextView) getActivity().findViewById(R.id.tvUsername);
        userType = (TextView) getActivity().findViewById(R.id.tvUserType);
        jobsDone = (TextView) getActivity().findViewById(R.id.tvJobsDone);



        if(loggedUser != null) {
            fullName.setText(loggedUser.getFirstname() + " " + loggedUser.getLastname());
            username.setText(loggedUser.getUsername());
            userType.setText(loggedUser.getType().toString());
            jobsDone.setText(String.valueOf(loggedUser.getJobsDone()));
        }
    }

    public void setUser(User user)
    {
        this.loggedUser = user;
    }


}
