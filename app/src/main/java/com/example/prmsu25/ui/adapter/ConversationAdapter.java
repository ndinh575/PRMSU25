package com.example.prmsu25.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prmsu25.R;
import com.example.prmsu25.data.model.Conversation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder> {

    public interface OnConversationClickListener {
        void onConversationClick(Conversation conversation);
    }

    private List<Conversation> conversationList = new ArrayList<>();
    private final OnConversationClickListener listener;

    public ConversationAdapter(OnConversationClickListener listener) {
        this.listener = listener;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversationList = conversations;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_conversation, parent, false);
        return new ConversationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationViewHolder holder, int position) {
        Conversation conversation = conversationList.get(position);
        holder.bind(conversation, listener);
    }

    @Override
    public int getItemCount() {
        return conversationList.size();
    }

    static class ConversationViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final TextView tvLastMessage;
        private final TextView tvTimestamp;

        public ConversationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvLastMessage = itemView.findViewById(R.id.tv_last_message);
            tvTimestamp = itemView.findViewById(R.id.tv_timestamp);
        }

        void bind(final Conversation conversation, final OnConversationClickListener listener) {
            tvName.setText(conversation.getName());
            tvLastMessage.setText(conversation.getLastMessage());

            if (conversation.getLastMessageAt() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                tvTimestamp.setText(sdf.format(conversation.getLastMessageAt()));
            } else {
                tvTimestamp.setText("");
            }

            itemView.setOnClickListener(v -> listener.onConversationClick(conversation));
        }
    }
}
