package com.example.prmsu25.ui.chat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.prmsu25.data.network.NetworkResult;
import com.example.prmsu25.databinding.FragmentChatBinding;
import com.example.prmsu25.ui.adapter.MessageAdapter;
import com.example.prmsu25.utils.UserSessionManager;

public class ChatFragment extends Fragment {

    private FragmentChatBinding binding;
    private ChatViewModel viewModel;
    private MessageAdapter adapter;
    private String receiverId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ChatViewModel.class);

        if (getArguments() != null) {
            receiverId = getArguments().getString("receiverId");
        }

        setupRecyclerView();
        setupInput();
        setupObservers();

        if (receiverId != null) {
            viewModel.loadMessages(receiverId, 1);
            viewModel.setActiveConversation(receiverId);
        }
    }

    private void setupRecyclerView() {
        UserSessionManager sessionManager = new UserSessionManager(requireContext());
        adapter = new MessageAdapter(sessionManager.getUserId());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true); // To show latest messages at the bottom
        binding.rvMessages.setLayoutManager(layoutManager);
        binding.rvMessages.setAdapter(adapter);
    }

    private void setupInput() {
        binding.btnSend.setOnClickListener(v -> {
            String message = binding.etMessageInput.getText().toString().trim();
            Log.d("ChatFragment", "Message: " + message + ", ReceiverId: " + receiverId);
            if (!message.isEmpty() && receiverId != null) {
                viewModel.sendMessage(receiverId, message);
                binding.etMessageInput.setText("");
            }
        });
    }

    private void setupObservers() {
        viewModel.messagesResult.observe(getViewLifecycleOwner(), result -> {
            if (result.getStatus() == NetworkResult.Status.SUCCESS && result.getData() != null) {
                adapter.setMessages(result.getData().getData());
            } else if (result.getStatus() == NetworkResult.Status.ERROR) {
                Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.newMessage.observe(getViewLifecycleOwner(), chatMessage -> {
            if (chatMessage != null && (chatMessage.getReceiverId().equals(receiverId) || chatMessage.getSenderId().equals(receiverId))) {
                adapter.addMessage(chatMessage);
                binding.rvMessages.scrollToPosition(0);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}