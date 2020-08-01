package com.bigtechsolutions.toniclifefenix.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bigtechsolutions.toniclifefenix.api.requests.ValidateInvRequest;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Address;
import com.bigtechsolutions.toniclifefenix.api.responses.models.PaymentMethod;
import com.bigtechsolutions.toniclifefenix.data.repository.OrderRepository;

import java.util.List;

public class OrderViewModel extends AndroidViewModel {

    private OrderRepository orderRepository;
    private LiveData<List<PaymentMethod>> paymentMethods;

    public OrderViewModel(@NonNull Application application) {
        super(application);
        orderRepository = new OrderRepository();
        paymentMethods = orderRepository.getPaymentMethods();
    }

    public LiveData<List<PaymentMethod>> getPaymentMethods() { return paymentMethods; }

    public void validateInventory(ValidateInvRequest validateInvRequest, OnSuccess onSuccess){ orderRepository.validateInventory(validateInvRequest, onSuccess ); }

    public LiveData<Boolean> getDownloadFinished(){
        LiveData<Boolean> downloadFinished=orderRepository.getDownloadFinished();
        return downloadFinished;
    }

}
