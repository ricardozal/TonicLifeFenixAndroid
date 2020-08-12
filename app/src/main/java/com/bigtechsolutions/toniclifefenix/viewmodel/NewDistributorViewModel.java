package com.bigtechsolutions.toniclifefenix.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.bigtechsolutions.toniclifefenix.api.requests.NewDistributorRequest;
import com.bigtechsolutions.toniclifefenix.data.repository.NewDistributorRepository;

public class NewDistributorViewModel extends AndroidViewModel {

    private NewDistributorRepository newDistributorRepository;

    public NewDistributorViewModel(@NonNull Application application) {
        super(application);
        newDistributorRepository = new NewDistributorRepository();
    }

    public void saveNewDistributor(NewDistributorRequest newDistributorRequest, OnResponse onResponse){
        newDistributorRepository.saveNewDistributor(newDistributorRequest, onResponse);
    }

}
