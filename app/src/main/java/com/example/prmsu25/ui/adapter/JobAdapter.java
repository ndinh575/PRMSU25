package com.example.prmsu25.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prmsu25.data.model.Job;
import com.example.prmsu25.databinding.ItemJobBinding;

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

    public void clearJobs(){
        jobList.clear();
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
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemJobBinding binding = ItemJobBinding.inflate(inflater, parent, false);
        return new JobViewHolder(binding);
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
        private final ItemJobBinding binding;

        public JobViewHolder(ItemJobBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Job job, OnJobClickListener listener) {
            binding.tvTitle.setText(job.title);
            binding.tvEmployer.setText(job.employerName);
            binding.tvLocation.setText(job.location);
            binding.tvSalary.setText(job.salary != null ? job.salary : "Thoả thuận");

            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) listener.onJobClick(job);
            });
        }
    }
}