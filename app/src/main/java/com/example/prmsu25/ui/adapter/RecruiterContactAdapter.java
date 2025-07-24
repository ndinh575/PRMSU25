package com.example.prmsu25.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prmsu25.R;
import com.example.prmsu25.data.model.Job;
import com.example.prmsu25.data.model.RecruiterContact;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecruiterContactAdapter extends RecyclerView.Adapter<RecruiterContactAdapter.ContactViewHolder> {

    public interface OnContactClickListener {
        void onContactClick(RecruiterContact contact);
    }

    private List<RecruiterContact> contactList = new ArrayList<>();
    private final OnContactClickListener listener;

    public RecruiterContactAdapter(OnContactClickListener listener) {
        this.listener = listener;
    }

    public void setContacts(List<RecruiterContact> contacts) {
        this.contactList = contacts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recruiter_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        RecruiterContact contact = contactList.get(position);
        holder.bind(contact, listener);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final TextView tvAppliedJobs;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAppliedJobs = itemView.findViewById(R.id.tv_applied_jobs);
        }

        void bind(final RecruiterContact contact, final OnContactClickListener listener) {
            if (contact.getRecruiter() != null) {
                tvName.setText(contact.getRecruiter().getName());
            }

            if (contact.getJobs() != null && !contact.getJobs().isEmpty()) {
                String appliedJobs = "Applied to: " + contact.getJobs().stream()
                        .map(Job::getTitle)
                        .collect(Collectors.joining(", "));
                tvAppliedJobs.setText(appliedJobs);
            } else {
                tvAppliedJobs.setText("No applied jobs found.");
            }

            itemView.setOnClickListener(v -> listener.onContactClick(contact));
        }
    }
}