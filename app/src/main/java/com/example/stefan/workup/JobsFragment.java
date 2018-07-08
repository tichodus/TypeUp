package com.example.stefan.workup;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.stefan.workup.adapters.JobsAdapter;
import com.example.stefan.workup.models.Job;
import com.example.stefan.workup.models.JobStatus;
import com.example.stefan.workup.models.JobType;
import com.example.stefan.workup.models.Jobs;
import com.example.stefan.workup.models.User;
import com.example.stefan.workup.models.UserLocation;
import com.example.stefan.workup.services.UserService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class JobsFragment extends Fragment implements JobsAdapter.Listener {
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    Jobs jobsList;
    User user;
    private DatabaseReference dbRef;

    public JobsFragment() {
    }

    private View view;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        dbRef = FirebaseDatabase.getInstance().getReference("jobs");
//        String id = dbRef.push().getKey();
//        UserLocation userLocation = new UserLocation(user);
//        userLocation.setLatitude("45");
//        userLocation.setLongitude("45");
//        Job job = new Job(id,"Job1","Desc1", JobType.DOG_WALKER,userLocation,20);
//        Job job1 = new Job(id,"Job2","Desc2", JobType.PHARMACY,userLocation,30);
//        job1.setStatus(JobStatus.DONE);
//        Job job2 = new Job(id,"Job3","Desc3", JobType.MARKET,userLocation,40);
//        Job job3 = new Job(id,"Job4","Desc4", JobType.FAST_FOOD,userLocation,50);
//        job3.setStatus(JobStatus.DONE);
//        Job job4 = new Job(id,"Job5","Desc5", JobType.DOG_WALKER,userLocation,60);
//        job4.setStatus(JobStatus.PENDING);
//        dbRef.child(id).setValue(job);
//        dbRef.child(id+1).setValue(job1);
//        dbRef.child(id+2).setValue(job2);
//        dbRef.child(id+3).setValue(job3);
//        dbRef.child(id+4).setValue(job4);

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_jobs, container, false);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinner = (Spinner) view.findViewById(R.id.sJobType);
        adapter = ArrayAdapter.createFromResource(getContext(), R.array.sort_jobs_criteria, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), adapterView.getItemIdAtPosition(i) + "Selected", Toast.LENGTH_LONG).show();
                int data = Filter.values()[i].data;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    public void onItemSelected(Job job) {
        Intent intent = new Intent(getContext(),JobDetails.class);
        intent.putExtra("job", (Serializable) job);
        startActivity(intent);
    }

    enum Filter {
        ALL(1),Pharamcy(2);
        int data;

        private Filter(int value) {
            this.data = value;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        ListView listView = view.findViewById(R.id.list);
        listView.setAdapter(new JobsAdapter(getContext(),R.layout.item_job_list,jobsList.getJobs()));
        ((JobsAdapter) listView.getAdapter()).setListener(JobsFragment.this);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setJobs(Jobs jobs){
        this.jobsList = jobs;
    }

}
