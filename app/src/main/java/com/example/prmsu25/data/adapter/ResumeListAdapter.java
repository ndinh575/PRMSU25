package com.example.prmsu25.data.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prmsu25.R;
import com.example.prmsu25.data.model.ResumeItem;

import java.util.List;

public class ResumeListAdapter extends RecyclerView.Adapter<ResumeListAdapter.ResumeViewHolder> {

    public interface OnActionListener {
        void onViewClicked(ResumeItem item);
        void onDeleteClicked(ResumeItem item);
    }

    private final List<ResumeItem> resumeList;
    private final OnActionListener listener;

    public ResumeListAdapter(List<ResumeItem> list, OnActionListener listener) {
        this.resumeList = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ResumeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resume_card, parent, false);
        return new ResumeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResumeViewHolder holder, int position) {
        ResumeItem item = resumeList.get(position);
        holder.fileName.setText(item.getFileName());
        holder.uploadedDate.setText("Uploaded: " + item.getUploadedDate());
        holder.btnView.setOnClickListener(v -> listener.onViewClicked(item));
        holder.btnDelete.setOnClickListener(v -> listener.onDeleteClicked(item));
    }

    @Override
    public int getItemCount() {
        return resumeList.size();
    }

    static class ResumeViewHolder extends RecyclerView.ViewHolder {
        TextView fileName, uploadedDate;
        Button btnView, btnDelete;

        ResumeViewHolder(@NonNull View itemView) {
            super(itemView);
            fileName = itemView.findViewById(R.id.text_file_name);
            uploadedDate = itemView.findViewById(R.id.text_uploaded_date);
            btnView = itemView.findViewById(R.id.btn_view);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
