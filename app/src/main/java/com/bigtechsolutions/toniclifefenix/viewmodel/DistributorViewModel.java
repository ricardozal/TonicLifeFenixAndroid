package com.bigtechsolutions.toniclifefenix.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bigtechsolutions.toniclifefenix.api.requests.GetCandidatesRequest;
import com.bigtechsolutions.toniclifefenix.api.requests.RegisterPointsRequest;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Promotion;
import com.bigtechsolutions.toniclifefenix.data.repository.DistributorRepository;
import com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnResponse;
import com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnSharePointsResponse;

import java.util.List;

public class DistributorViewModel extends AndroidViewModel {

    private DistributorRepository distributorRepository;
    private LiveData<List<Promotion>> promotions;

    public DistributorViewModel(@NonNull Application application) {
        super(application);
        distributorRepository = new DistributorRepository();
        promotions = distributorRepository.getPromotions();
    }
    
    public LiveData<List<Promotion>> getPromotions() { return promotions; }

    public void saveFirebaseToken(String firebaseToken, OnResponse onResponse) { distributorRepository.saveFirebaseToken(firebaseToken, onResponse); }

    public void registerPoints(RegisterPointsRequest request, OnResponse onResponse) { distributorRepository.registerPoints(request,onResponse); }

    public void getCandidates(GetCandidatesRequest request, OnSharePointsResponse onSharePointsResponse) { distributorRepository.getCandidates(request, onSharePointsResponse); }

    public LiveData<Boolean> getDownloadFinished(){
        LiveData<Boolean> downloadFinished=distributorRepository.getDownloadFinished();
        return downloadFinished;
    }
}
