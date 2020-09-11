package com.bigtechsolutions.toniclifefenix.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.bigtechsolutions.toniclifefenix.R;
import com.bigtechsolutions.toniclifefenix.commons.Constants;
import com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import com.bigtechsolutions.toniclifefenix.commons.SharedPreferencesManager;
import com.bigtechsolutions.toniclifefenix.login.MainActivity;
import com.bigtechsolutions.toniclifefenix.ui.shoppingcart.ChooseDeliveryFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class BottomNavigationActivity extends AppCompatActivity {

    BottomNavigationView mBottomNavigation;

    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        findViews();
        events();


        String state = SharedPreferencesManager.getStringValue(Constants.STATE_NAME);
        String country = SharedPreferencesManager.getStringValue(Constants.CURRENT_COUNTRY);

        if(state == null || country == null){

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {

                        Location location = task.getResult();

                        if(location != null){
                            try {
                                Geocoder geocoder = new Geocoder(MyFenixApp.getContext(), Locale.getDefault());

                                List<Address> addresses = geocoder.getFromLocation(
                                        location.getLatitude(), location.getLongitude(), 1
                                );

                                Log.i("AQUI", "Apenas: "+addresses.get(0).getAdminArea());
                                Log.i("AQUI", "Apenas: "+addresses.get(0).getCountryName());

                                SharedPreferencesManager.setStringValue(Constants.STATE_NAME, addresses.get(0).getAdminArea());
                                SharedPreferencesManager.setStringValue(Constants.CURRENT_COUNTRY, addresses.get(0).getCountryName());

                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .add(R.id.fragment_container, new ProductListFragment())
                                        .commit();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
            }
        } else {
            Log.i("AQUI", "Guardado: "+state);
            Log.i("AQUI", "Guardado: "+country);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, new ProductListFragment())
                    .commit();
        }



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