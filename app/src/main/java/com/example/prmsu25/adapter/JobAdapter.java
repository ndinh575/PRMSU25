package com.example.prmsu25.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prmsu25.R;
import com.example.prmsu25.model.Job;

import java.util.ArrayList;
import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {

    public interface OnJobClickListener {
        void onJobClick(Job job);
    }

    private final List<Job> jobList;
    private final OnJobClickListener listener;

    public JobAdapter(List<Job> jobs, OnJobClickListener listener) {
        this.jobList = new ArrayList<>(jobs);
        this.listener = listener;
    }

    public void setJobs(List<Job> jobs) {
        jobList.clear();
        jobList.addAll(jobs);
        notifyDataSetChanged();
    }

    public void addJobs(List<Job> newJobs) {
        int start = jobList.size();
        jobList.addAll(newJobs);
        notifyItemRangeInserted(start, newJobs.size());
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_job, parent, false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        Job job = jobList.get(position);
        holder.bind(job, listener);
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    static class JobViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvEmployer, tvLocation, tvSalary;
        public JobViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvEmployer = itemView.findViewById(R.id.tvEmployer);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvSalary = itemView.findViewById(R.id.tvSalary);
        }
        void bind(Job job, OnJobClickListener listener) {
            tvTitle.setText(job.title);
            tvEmployer.setText(job.employerName);
            tvLocation.setText(job.location);
            tvSalary.setText(job.salary != null ? job.salary : "Thoả thuận");
            itemView.setOnClickListener(v -> {
                if (listener != null) listener.onJobClick(job);
            });
        }
    }
}
