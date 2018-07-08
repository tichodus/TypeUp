package com.example.stefan.workup;

import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.stefan.workup.models.Job;

import org.w3c.dom.Text;

public class JobDetails extends AppCompatActivity {
    private Job job;

    private TextView tvJobTitle;
    private TextView tvJobDesc;
    private TextView tvJobPayment;
    private Button bApply;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        job = (Job)getIntent().getSerializableExtra("job");

        tvJobTitle = (TextView) findViewById(R.id.tvJobTitle);
        tvJobDesc = (TextView) findViewById(R.id.tvJobDescription);
        tvJobPayment = (TextView) findViewById(R.id.tvJobPayment);
        bApply = (Button) findViewById(R.id.bApply);


        tvJobTitle.setText(job.getJobName());
        tvJobDesc.setText(job.getDescription());
        tvJobPayment.setText(String.valueOf(job.getPayment()));

        bApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Apply for job
            }
        });
    }
}
