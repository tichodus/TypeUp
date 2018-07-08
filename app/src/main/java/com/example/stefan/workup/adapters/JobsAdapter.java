package com.example.stefan.workup.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stefan.workup.R;
import com.example.stefan.workup.models.Job;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class JobsAdapter extends ArrayAdapter<Job> {
    private Listener listener;

    public JobsAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public JobsAdapter(@NonNull Context context, int resource, @NonNull Job[] objects) {
        super(context, resource, objects);
    }

    public JobsAdapter(@NonNull Context context, int resource, @NonNull List<Job> objects) {
        super(context, resource, objects);
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.item_job_list, parent, false);

        Job job = getItem(position);


        TextView name = (TextView) listItem.findViewById(R.id.txt_title);
        name.setText(job.getJobName());

        TextView release = (TextView) listItem.findViewById(R.id.txt_info);
        release.setText(job.getDescription());
        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemSelected(getItem(position));
                }
            }
        });

        return listItem;

    }

    public void setListener(Listener listner) {
        this.listener = listner;
    }

    public interface Listener {
        void onItemSelected(Job job);
    }

}
