package com.example.prmsu25.ui.findjobs;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prmsu25.R;

public class FindJobsFragment extends Fragment {

    private FindJobsViewModel mViewModel;

    private NavController navController;

    public static FindJobsFragment newInstance() {
        return new FindJobsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_find_jobs, container, false);

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);

        root.findViewById(R.id.button).setOnClickListener(v -> {
            navController.navigate(R.id.action_findJobs_to_jobDetail);
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FindJobsViewModel.class);
        // TODO: Use the ViewModel
    }

}