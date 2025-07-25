package com.example.prmsu25.ui.messaging.tabs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.prmsu25.R;
import com.example.prmsu25.databinding.FragmentRecruiterContactsBinding;
import com.example.prmsu25.ui.adapter.RecruiterContactAdapter;
import com.example.prmsu25.ui.messaging.ConversationsViewModel;

public class RecruiterContactsFragment extends Fragment {

    private FragmentRecruiterContactsBinding binding;
    private ConversationsViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRecruiterContactsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ConversationsViewModel.class);

        setupRecyclerView();
        setupObservers();

        binding.swipeRefreshLayout.setOnRefreshListener(() -> viewModel.loadRecruiterContacts());
    }

    private void setupRecyclerView() {
        RecruiterContactAdapter adapter = new RecruiterContactAdapter(contact -> {
            Bundle bundle = new Bundle();
            bundle.putString("receiverId", contact.getRecruiter().getId());
            Navigation.findNavController(requireView()).navigate(R.id.action_conversationsFragment_to_chatFragment, bundle);
        });
        binding.rvContacts.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvContacts.setAdapter(adapter);
    }

    private void setupObservers() {
        viewModel.contactsResult.observe(getViewLifecycleOwner(), result -> {
            binding.swipeRefreshLayout.setRefreshing(false);
            switch (result.getStatus()) {
                case LOADING:
                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.tvErrorMessage.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    binding.progressBar.setVisibility(View.GONE);
                    if (result.getData() != null && result.getData().isSuccess()) {
                        ((RecruiterContactAdapter) binding.rvContacts.getAdapter()).setContacts(result.getData().getData());
                        binding.tvErrorMessage.setVisibility(result.getData().getData().isEmpty() ? View.VISIBLE : View.GONE);
                        binding.tvErrorMessage.setText("Apply to a job to see recruiters here.");
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
