package com.example.prmsu25.ui.messaging;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.prmsu25.databinding.FragmentConversationsBinding;
import com.example.prmsu25.ui.adapter.MessagingPagerAdapter;
import com.google.android.material.tabs.TabLayoutMediator;

public class ConversationsFragment extends Fragment {

    private FragmentConversationsBinding binding;
    private ConversationsViewModel viewModel;

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

        setupViewPager();

        // Initial load for both tabs
        viewModel.loadConversations("");
        viewModel.loadRecruiterContacts();
    }

    private void setupViewPager() {
        binding.viewPager.setAdapter(new MessagingPagerAdapter(this));

        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Chats");
                    break;
                case 1:
                    tab.setText("Contacts");
                    break;
            }
        }).attach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
