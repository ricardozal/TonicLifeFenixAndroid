package com.bigtechsolutions.toniclifefenix.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bigtechsolutions.toniclifefenix.api.requests.NewDistributorRequest;
import com.bigtechsolutions.toniclifefenix.api.requests.UpsertAddressRequest;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Address;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Branch;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Product;
import com.bigtechsolutions.toniclifefenix.data.repository.AddressRepository;
import com.bigtechsolutions.toniclifefenix.data.repository.ProductRepository;
import com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnAddressResponse;
import com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnResponse;

import java.util.List;

public class AddressViewModel extends AndroidViewModel {

    private AddressRepository addressRepository;
    private LiveData<List<Address>> addresses;
    private LiveData<List<Branch>> branches;

    public AddressViewModel(@NonNull Application application) {
        super(application);
        addressRepository = new AddressRepository();
        addresses = addressRepository.getAddresses();
        branches = addressRepository.getBranches();
    }

    public LiveData<List<Address>> getAddresses() { return addresses; }

    public LiveData<List<Branch>> getBranches() { return branches; }

    public void setSelectedAddress(int addressId, int distributorId) { addressRepository.setSelectedAddress(addressId, distributorId); }

    public void saveAddress(UpsertAddressRequest upsertAddressRequest, OnResponse onResponse){
        addressRepository.saveAddress(upsertAddressRequest, onResponse);
    }

    public void getAddress(int addressId, OnAddressResponse onResponse) { addressRepository.getAddress(addressId, onResponse); }

    public LiveData<Boolean> getDownloadFinished(){
        LiveData<Boolean> downloadFinished=addressRepository.getDownloadFinished();
        return downloadFinished;
    }

}
