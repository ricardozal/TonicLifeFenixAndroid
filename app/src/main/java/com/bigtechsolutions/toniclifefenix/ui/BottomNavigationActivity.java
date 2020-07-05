package com.bigtechsolutions.toniclifefenix.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.bigtechsolutions.toniclifefenix.R;
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

//
//                    switch (item.getItemId())
//                    {
//                        case R.id.products_page:
//
//                    }

                    return true;
                }
            };


}