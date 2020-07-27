package com.bigtechsolutions.toniclifefenix.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bigtechsolutions.toniclifefenix.api.responses.models.Address;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Product;
import com.bigtechsolutions.toniclifefenix.data.repository.AddressRepository;
import com.bigtechsolutions.toniclifefenix.data.repository.ProductRepository;

import java.util.List;

public class AddressViewModel extends AndroidViewModel {

    private AddressRepository addressRepository;
    private LiveData<List<Address>> addresses;

    public AddressViewModel(@NonNull Application application) {
        super(application);
        addressRepository = new AddressRepository();
        addresses = addressRepository.getAddresses();
    }

    public LiveData<List<Address>> getAddresses() { return addresses; }

    public void setSelectedAddress(int addressId, int distributorId) { addressRepository.setSelectedAddress(addressId, distributorId); }

}
