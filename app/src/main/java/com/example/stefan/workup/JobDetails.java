package com.example.stefan.workup;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stefan.workup.models.Job;
import com.example.stefan.workup.models.JobStatus;

public class JobDetails extends AppCompatActivity {
    private Job job;

    private TextView tvJobTitle;
    private TextView tvJobDesc;
    private TextView tvJobPayment;
    private TextView tvJobStatus;
    private TextView tvJobType;
    private Button bApply;
    private Button bVerify;

  ///  BluetoothUtils bluetoothUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);
      //  bluetoothUtils = new BluetoothUtils(JobDetails.this);
        job = (Job) getIntent().getSerializableExtra("job");

        tvJobTitle = (TextView) findViewById(R.id.tvJobTitle);
        tvJobDesc = (TextView) findViewById(R.id.tvJobDescription);
        tvJobPayment = (TextView) findViewById(R.id.tvJobPayment);
        tvJobStatus = (TextView) findViewById(R.id.tvJobStatus);
        tvJobType = (TextView) findViewById(R.id.tvJobType);
        bApply = (Button) findViewById(R.id.bApply);
        bVerify = (Button) findViewById(R.id.bVerify);

        tvJobTitle.setText(job.getJobName());
        tvJobDesc.setText(job.getDescription());
        tvJobPayment.setText(String.valueOf(job.getPayment()));

        tvJobStatus.setText(job.getStatus().toString());
        tvJobType.setText(job.getType().toString());

        if(job.getStatus() == JobStatus.PENDING){
            bApply.setText("Cancel");
            bApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    job.setStatus(JobStatus.OPEN);
                    Intent intent = new Intent();
                    intent.putExtra("updatedJob", job);
                    setResult(1, intent);
                    finish();
                }
            });
        }
        else {
            bApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    job.setStatus(JobStatus.PENDING);
                    Intent intent = new Intent();
                    intent.putExtra("updatedJob", job);
                    setResult(1, intent);
                    finish();
                }
            });
        }
        bVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Apply for job
//
//                BluetoothDevice device = null;
//                while (BluetoothAdapter.getDefaultAdapter().getBondedDevices().iterator().hasNext()) {
//                    BluetoothDevice next = BluetoothAdapter.getDefaultAdapter().getBondedDevices().iterator().next();
//                    if (next != null && next.getAddress().equalsIgnoreCase("50:55:27:6A:2D:54")) {
//                        device = next;
//                        break;
//                    }
//                }
//                if (device!=null) {
//                    bluetoothUtils.connectToDevice(device);
//                }
//                else {
//                    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//                    registerReceiver(mReceiver, filter);
//                    BluetoothAdapter.getDefaultAdapter().startDiscovery();
//                }


            }
        });


    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                if (deviceHardwareAddress.equalsIgnoreCase("50:55:27:6A:2D:54")) {
                  //  bluetoothUtils.connectToDevice(device);
                    unregisterReceiver(mReceiver);
                }
            }
        }
    };

//    @Override
//    public void handleClientHandshake(boolean response, Job job) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(JobDetails.this, response ? "client ok" : "client failed", Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//    @Override
//    public void handleServerHandshake(boolean response, Job job) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(JobDetails.this, response ? "server ok" : "server failed", Toast.LENGTH_LONG).show();
//            }
//        });

    }

