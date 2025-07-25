package com.example.prmsu25.data.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prmsu25.R;
import com.example.prmsu25.data.model.ResumeApplicationItem;

import java.util.List;

public class ResumeApplicationAdapter extends RecyclerView.Adapter<ResumeApplicationAdapter.ViewHolder> {

    public interface OnCVClickListener {
        void onViewCV(ResumeApplicationItem item);
    }

    private final List<ResumeApplicationItem> items;
    private final OnCVClickListener listener;

    public ResumeApplicationAdapter(List<ResumeApplicationItem> items, OnCVClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cv_application, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResumeApplicationItem item = items.get(position);
        holder.applicantName.setText(item.getApplicantName());
        holder.jobTitle.setText("Applied for: " + item.getJobTitle());
        holder.cvFileName.setText("CV: " + item.getCvFileName());
        holder.appliedDate.setText("Submitted: " + item.getAppliedDate());

        holder.btnViewCV.setOnClickListener(v -> listener.onViewCV(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView applicantName, jobTitle, cvFileName, appliedDate;
        Button btnViewCV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            applicantName = itemView.findViewById(R.id.text_applicant_name);
            jobTitle = itemView.findViewById(R.id.text_job_title);
            cvFileName = itemView.findViewById(R.id.text_cv_file);
            appliedDate = itemView.findViewById(R.id.text_date);
            btnViewCV = itemView.findViewById(R.id.btn_view_cv);
        }
    }
}
