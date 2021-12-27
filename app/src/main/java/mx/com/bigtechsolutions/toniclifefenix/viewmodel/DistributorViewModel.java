package mx.com.bigtechsolutions.toniclifefenix.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import mx.com.bigtechsolutions.toniclifefenix.api.requests.BankData;
import mx.com.bigtechsolutions.toniclifefenix.api.requests.GetCandidatesRequest;
import mx.com.bigtechsolutions.toniclifefenix.api.requests.RegisterPointsRequest;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.models.Promotion;
import mx.com.bigtechsolutions.toniclifefenix.data.repository.DistributorRepository;
import mx.com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnBankDataResponse;
import mx.com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnResponse;
import mx.com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnSharePointsResponse;

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

    public void getBankData(OnBankDataResponse onResponse) { distributorRepository.getBankData(onResponse); }

    public void saveBankData(BankData bankData, OnResponse onResponse) { distributorRepository.saveBankData(bankData, onResponse); }

    public LiveData<Boolean> getDownloadFinished(){
        LiveData<Boolean> downloadFinished=distributorRepository.getDownloadFinished();
        return downloadFinished;
    }
}
