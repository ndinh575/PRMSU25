package com.example.prmsu25.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.prmsu25.ui.messaging.tabs.ActiveChatsFragment;
import com.example.prmsu25.ui.messaging.tabs.RecruiterContactsFragment;

public class MessagingPagerAdapter extends FragmentStateAdapter {

    public MessagingPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ActiveChatsFragment();
            case 1:
                return new RecruiterContactsFragment();
            default:
                throw new IllegalStateException("Unexpected position: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return 2; // We have two tabs
    }
}