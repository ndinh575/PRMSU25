package com.example.prmsu25;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.prmsu25.utils.UserSessionManager;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.activity.OnBackPressedCallback;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.prmsu25.databinding.ActivityMainBinding;

import okhttp3.OkHttpClient;
import com.example.prmsu25.data.network.UnsafeOkHttpClient;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private long lastBackPressedTime = 0;
    private static final long DOUBLE_BACK_PRESS_INTERVAL = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_content_main);
        NavController navController = navHostFragment.getNavController();
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.loginFragment || destination.getId() == R.id.registerFragment) {
                binding.toolbar.setVisibility(View.GONE);
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            } else {
                binding.toolbar.setVisibility(View.VISIBLE);
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }
        });
        // Các màn hình chính trong Drawer
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.loginFragment,
                R.id.findJobsFragment,
                R.id.profileFragment,
                R.id.chatFragment,
                R.id.applicationHistoryFragment,
                R.id.manageResumeFragment,
                R.id.viewResumeFragment
        ).setOpenableLayout(drawer).build();

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Xử lý nút logout ở footer
        MenuItem logoutItem = navigationView.getMenu().findItem(R.id.nav_logout);
        if (logoutItem != null) {
            logoutItem.setOnMenuItemClickListener(item -> {
                handleLogout();
                return true;
            });
        }

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                int currentDestId = navController.getCurrentDestination().getId();

                if (currentDestId == R.id.loginFragment ||
                        currentDestId == R.id.registerFragment ||
                        currentDestId == R.id.forgotPasswordFragment) {
                    finishAffinity();
                    return;
                }

                if (currentDestId == R.id.findJobsFragment) {
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - lastBackPressedTime < DOUBLE_BACK_PRESS_INTERVAL) {
                        finishAffinity();
                    } else {
                        lastBackPressedTime = currentTime;
                        Toast.makeText(MainActivity.this, "Press back again to exit", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Navigate back to main screen
                    navController.navigate(R.id.findJobsFragment, null,
                            new NavOptions.Builder()
                                    .setPopUpTo(R.id.findJobsFragment, true)
                                    .build()
                    );
                }
            }
        });

        // Kiểm tra token trong SharedPreferences
        android.content.SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        String token = prefs.getString("token", null);
        if (token == null || token.isEmpty()) {
            // Nếu chưa có token, chuyển đến màn hình đăng nhập
            navController.navigate(R.id.loginFragment);
        }
    }

    private void handleLogout() {
        UnsafeOkHttpClient.clearCookies();
        Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
        UserSessionManager userSessionManager = new UserSessionManager(this);
        userSessionManager.clearSession();
        navigateToLoginAndClearBackStack();
    }

    private void navigateToLoginAndClearBackStack() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        navController.navigate(R.id.loginFragment,
                null,
                new androidx.navigation.NavOptions.Builder()
                        .setPopUpTo(navController.getGraph().getStartDestinationId(), true)
                        .build()
        );
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }
}
