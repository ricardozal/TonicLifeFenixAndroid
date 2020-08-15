package com.bigtechsolutions.toniclifefenix.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bigtechsolutions.toniclifefenix.api.requests.GenerateIntentRequest;
import com.bigtechsolutions.toniclifefenix.api.requests.OrderRequest;
import com.bigtechsolutions.toniclifefenix.api.requests.ValidateInvRequest;
import com.bigtechsolutions.toniclifefenix.api.responses.models.OrderItem;
import com.bigtechsolutions.toniclifefenix.api.responses.models.PaymentMethod;
import com.bigtechsolutions.toniclifefenix.data.repository.OrderRepository;
import com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnOrderItemResponse;
import com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnOrderResponse;
import com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnResponse;
import com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnSuccess;

import java.util.List;

public class OrderViewModel extends AndroidViewModel {

    private OrderRepository orderRepository;
    private LiveData<List<PaymentMethod>> paymentMethods;
    private LiveData<List<OrderItem>> orderItems;

    public OrderViewModel(@NonNull Application application) {
        super(application);
        orderRepository = new OrderRepository();
        paymentMethods = orderRepository.getPaymentMethods();
        orderItems = orderRepository.getOrders();
    }

    public LiveData<List<PaymentMethod>> getPaymentMethods() { return paymentMethods; }

    public LiveData<List<OrderItem>> getOrders() { return orderItems; }

    public void getOrder(int orderId, OnOrderItemResponse onOrderItemResponse) { orderRepository.getOrder(orderId, onOrderItemResponse); }

    public void validateInventory(ValidateInvRequest validateInvRequest, OnResponse onResponse){ orderRepository.validateInventory(validateInvRequest, onResponse ); }

    public void generateIntent(GenerateIntentRequest generateIntentRequest, OnSuccess onSuccess){orderRepository.generateIntent(generateIntentRequest, onSuccess);}
    
    public void saveOrder(OrderRequest orderRequest, OnOrderResponse onOrderResponse){ orderRepository.saveOrder(orderRequest, onOrderResponse); }

    public LiveData<Boolean> getDownloadFinished(){
        LiveData<Boolean> downloadFinished=orderRepository.getDownloadFinished();
        return downloadFinished;
    }

}
