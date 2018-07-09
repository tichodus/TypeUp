package com.example.stefan.workup;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stefan.workup.models.Job;
import com.example.stefan.workup.models.JobStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JobDetails extends AppCompatActivity {
    private Job job;

    private TextView tvJobTitle;
    private TextView tvJobDesc;
    private TextView tvJobPayment;
    private TextView tvJobStatus;
    private TextView tvJobType;
//    private TextView onBtn;
//    private TextView offBtn;
//    private TextView text;
    private Button bApply;
    private Button bVerify;
//
//    private static final int REQUEST_ENABLE_BT = 1;
//    BluetoothAdapter mBluetoothAdapter;
//    private ProgressDialog mProgressDig;
//    private BluetoothAdapter myBluetoothAdapter;
//    private Set<BluetoothDevice> pairedDevices;
//    private TextView discover;
//    ListView myListView;
//    List<BluetoothDevice> myDeviceList;

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

        if(job.getStatus()==JobStatus.DONE) {
            bVerify.setVisibility(View.INVISIBLE);
            bApply.setVisibility(View.INVISIBLE);
        }
        else {
            bVerify.setVisibility(View.VISIBLE);
            bApply.setVisibility(View.VISIBLE);
        }
        bVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                job.setStatus(JobStatus.DONE);
                Intent intent = new Intent();
                intent.putExtra("updatedJob", job);
                setResult(1, intent);
                finish();

            }
        });
//        myListView = (ListView) findViewById(R.id.lvDevices);
//
//        text = (TextView) findViewById(R.id.tvText);
//        onBtn = findViewById(R.id.tvBtnOn);
//        offBtn = findViewById(R.id.tvBtnOff);
//        discover = (TextView) findViewById(R.id.tvDiscover);
//        myDeviceList = new ArrayList<>();
//        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        ArrayAdapter listAdapter;
//        mProgressDig = new ProgressDialog(this);
//        mProgressDig.setMessage("Scanning...");
//        mProgressDig.setCancelable(false);
//        mProgressDig.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int which) {
//                dialogInterface.dismiss();
//
//
//            }
//        });
//
//        discover.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                makeDiscoverable();
//            }
//        });
//
//        if(myBluetoothAdapter == null){
//            showUnsupported();
//        }
//        else{
//            bVerify.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Set<BluetoothDevice> pairedDevices = myBluetoothAdapter.getBondedDevices();
//
//                    if(pairedDevices == null || pairedDevices.size() == null)
//                    {
//                        Toast.makeText(JobDetails.this,"No Paired Devices Found",Toast.LENGTH_LONG).show();
//                    }
//                    else{
//                        ArrayList<BluetoothDevice> list = new ArrayList<>();
//
//                        list.addAll(pairedDevices);
//                        mAd
//                    }
//                }
//            });
//            if(myBluetoothAdapter.isEnabled()){
//                showEnabled();
//            }
//            else{
//                showDisabled();
//            }
//            onBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent turnOnIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                    startActivityForResult(turnOnIntent,REQUEST_ENABLE_BT);
//                }
//            });
//
//            offBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                   myBluetoothAdapter.disable();
//                   showDisabled();
//                }
//            });

        tvJobTitle.setText(job.getJobName());
        tvJobDesc.setText(job.getDescription());
        tvJobPayment.setText(String.valueOf(job.getPayment()));

        tvJobStatus.setText(job.getStatus().toString());
        tvJobType.setText(job.getType().toString());

        if (job.getStatus() == JobStatus.PENDING) {
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
        } else {
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
//        bVerify.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
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


//            }
//        });


    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == REQUEST_ENABLE_BT){
//            if(myBluetoothAdapter.isEnabled()){
//                showEnabled();
//            }
//            else{
//                showDisabled();
//            }
//        }
//    }

//    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
//                // Discovery has found a device. Get the BluetoothDevice
//                // object and its info from the Intent.
//                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                String deviceName = device.getName();
//                String deviceHardwareAddress = device.getAddress(); // MAC address
//                if (deviceHardwareAddress.equalsIgnoreCase("50:55:27:6A:2D:54")) {
//                  //  bluetoothUtils.connectToDevice(device);
//                    unregisterReceiver(mReceiver);
//                }
//            }
//        }
//    };

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



//    private void showEnabled(){
//        text.setText("Bluetooth is On");
//        text.setTextColor(Color.BLUE);
//        offBtn.setEnabled(true);
//        onBtn.setEnabled(false);
//    }
//
//    private void showDisabled(){
//        text.setText("Bluetooth is Off");
//        text.setTextColor(Color.RED);
//        offBtn.setEnabled(false);
//        onBtn.setEnabled(true);
//    }
//
//    private void showUnsupported(){
//        text.setText("Bluetooth is unsupported by this device");
//    }

//
//    private void makeDiscoverable(){
//        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
//        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,300);
//        startActivity(discoverableIntent);
//    }
    }

