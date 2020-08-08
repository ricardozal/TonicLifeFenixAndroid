package com.bigtechsolutions.toniclifefenix.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.bigtechsolutions.toniclifefenix.R;

public class WaitingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        FragmentManager fm = getSupportFragmentManager();
        ChooseCountryFragment dialog = new ChooseCountryFragment();
        dialog.setCancelable(false);
        dialog.show(fm, "ChooseCountryFragment");

    }
}