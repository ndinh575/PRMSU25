package com.example.prmsu25.ui.messaging;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.prmsu25.R;
import com.example.prmsu25.data.model.Conversation;
import com.example.prmsu25.databinding.FragmentConversationsBinding;
import com.example.prmsu25.ui.adapter.ConversationAdapter;

public class ConversationsFragment extends Fragment {

    private FragmentConversationsBinding binding;
    private ConversationsViewModel viewModel;
    private ConversationAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentConversationsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ConversationsViewModel.class);

        setupRecyclerView();
        setupObservers();
        setupRefresh();

        // Initial load
        viewModel.loadConversations("");
    }

    private void setupRecyclerView() {
        adapter = new ConversationAdapter(conversation -> {
            // TODO: Navigate to ChatFragment
            // Example:
            // Bundle bundle = new Bundle();
            // bundle.putString("receiverId", conversation.getReceiverId());
            // bundle.putString("receiverName", conversation.getName());
            // NavHostFragment.findNavController(this).navigate(R.id.action_conversations_to_chat, bundle);
        });
        binding.rvConversations.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvConversations.setAdapter(adapter);
    }

    private void setupObservers() {
        viewModel.conversationsResult.observe(getViewLifecycleOwner(), result -> {
            binding.swipeRefreshLayout.setRefreshing(false);
            switch (result.getStatus()) {
                case LOADING:
                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.tvErrorMessage.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    binding.progressBar.setVisibility(View.GONE);
                    if (result.getData() != null && result.getData().isSuccess()) {
                        adapter.setConversations(result.getData().getData());
                        binding.tvErrorMessage.setVisibility(result.getData().getData().isEmpty() ? View.VISIBLE : View.GONE);
                        binding.tvErrorMessage.setText("No conversations found.");
                    }
                    break;
                case ERROR:
                    binding.progressBar.setVisibility(View.GONE);
                    binding.tvErrorMessage.setVisibility(View.VISIBLE);
                    binding.tvErrorMessage.setText(result.getMessage());
                    break;
            }
        });
    }

    private void setupRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener(() -> viewModel.loadConversations(""));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
