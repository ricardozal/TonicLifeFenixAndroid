package com.bigtechsolutions.toniclifefenix.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.bigtechsolutions.toniclifefenix.R;
import com.bigtechsolutions.toniclifefenix.commons.Constants;
import com.bigtechsolutions.toniclifefenix.commons.SharedPreferencesManager;
import com.bigtechsolutions.toniclifefenix.login.MainActivity;
import com.bigtechsolutions.toniclifefenix.ui.shoppingcart.ChooseDeliveryFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationActivity extends AppCompatActivity {

    BottomNavigationView mBottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        findViews();
        events();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new ProductListFragment())
                .commit();

    }

    private void findViews() {
        mBottomNavigation = findViewById(R.id.bottomNavigation);
    }

    private void events() {
        mBottomNavigation.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment fragmentSelected = null;

                    switch (item.getItemId())
                    {
                        case R.id.products_page:
                            fragmentSelected = new ProductListFragment();
                            break;
                        case R.id.distributors_page:
                            fragmentSelected = new DistributorsFragment();
                            break;
                        case R.id.profile_page:
                            fragmentSelected = new ProfileFragment();
                            break;
                        case R.id.more_page:
                            fragmentSelected = new MoreFragment();
                            break;

                    }

                    if (fragmentSelected != null)
                    {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, fragmentSelected)
                                .commit();

                        return true;
                    }

                    return false;
                }
            };


}