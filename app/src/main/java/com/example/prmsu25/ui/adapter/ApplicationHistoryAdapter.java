package com.example.prmsu25.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prmsu25.data.model.Application;
import com.example.prmsu25.databinding.ItemApplicationHistoryBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ApplicationHistoryAdapter extends RecyclerView.Adapter<ApplicationHistoryAdapter.ApplicationViewHolder> {

    public interface OnApplicationClickListener {
        void onApplicationClick(Application application);
    }

    private final List<Application> applicationList;
    private final OnApplicationClickListener listener;

    public ApplicationHistoryAdapter(List<Application> applications, OnApplicationClickListener listener) {
        this.applicationList = new ArrayList<>(applications);
        this.listener = listener;
    }

    public void setApplications(List<Application> newList) {
        applicationList.clear();
        if (newList != null) {
            applicationList.addAll(newList);
        }
        notifyDataSetChanged();
    }

    public void clearApplications(){
        applicationList.clear();
        notifyDataSetChanged();
    }

    public void addApplications(List<Application> newApplications){
        int start = applicationList.size();
        applicationList.addAll(newApplications);
        notifyItemRangeChanged(start, newApplications.size());
    }

    @NonNull
    @Override
    public ApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemApplicationHistoryBinding binding = ItemApplicationHistoryBinding.inflate(inflater, parent, false);
        return new ApplicationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicationViewHolder holder, int position) {
        Application application = applicationList.get(position);
        holder.bind(application, listener);
    }

    @Override
    public int getItemCount() {
        return applicationList.size();
    }

    static class ApplicationViewHolder extends RecyclerView.ViewHolder {
        private final ItemApplicationHistoryBinding binding;

        public ApplicationViewHolder(ItemApplicationHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Application application, OnApplicationClickListener listener) {
            binding.tvJobTitle.setText(application.getJob().getTitle());
            binding.tvJobDescription.setText(application.getJob().getDescription());
            binding.tvLocation.setText(application.getJob().getLocation());
            binding.tvExperience.setText(application.getJob().getExperience());
            binding.tvSalary.setText(application.getJob().getSalary());
            binding.tvApplicationDate.setText(formatDate(application.getAppliedAt()));
            binding.tvStatus.setText("Đang chờ");

            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) listener.onApplicationClick(application);
            });
        }

        private String formatDate(String isoDate) {
            try {
                SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
                parser.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
                Date date = parser.parse(isoDate);

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy, HH:mm", Locale.getDefault());
                return formatter.format(date);
            } catch (Exception e) {
                return isoDate;
            }
        }
    }
}
