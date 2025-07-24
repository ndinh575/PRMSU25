package com.example.prmsu25.ui.messaging.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.prmsu25.databinding.FragmentActiveChatsBinding;
import com.example.prmsu25.ui.adapter.ConversationAdapter;
import com.example.prmsu25.ui.messaging.ConversationsViewModel;

public class ActiveChatsFragment extends Fragment {

    private FragmentActiveChatsBinding binding;
    private ConversationsViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentActiveChatsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ConversationsViewModel.class);

        setupRecyclerView();
        setupObservers();

        binding.swipeRefreshLayout.setOnRefreshListener(() -> viewModel.loadConversations(""));
    }

    private void setupRecyclerView() {
        ConversationAdapter adapter = new ConversationAdapter(conversation -> {
            // TODO: Navigate to ChatFragment
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
                        ((ConversationAdapter) binding.rvConversations.getAdapter()).setConversations(result.getData().getData());
                        binding.tvErrorMessage.setVisibility(result.getData().getData().isEmpty() ? View.VISIBLE : View.GONE);
                        binding.tvErrorMessage.setText("No active chats.");
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}