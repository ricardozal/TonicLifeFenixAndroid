package com.bigtechsolutions.toniclifefenix.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bigtechsolutions.toniclifefenix.data.entity.ShoppingCart;
import com.bigtechsolutions.toniclifefenix.data.repository.ShoppingCartRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ShoppingCartViewModel extends AndroidViewModel {

    private LiveData<List<ShoppingCart>> all;
    private Double totalOrder;
    private ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartViewModel(Application application) throws ExecutionException, InterruptedException {
        super(application);

        shoppingCartRepository = new ShoppingCartRepository(application);
        all = shoppingCartRepository.getAll();
        totalOrder = shoppingCartRepository.getTotalOrder();
    }

    public LiveData<List<ShoppingCart>> getAll() { return all;}

    public void insert(ShoppingCart shoppingCart) { shoppingCartRepository.insert(shoppingCart);}

    public Double getTotalOrder() { return totalOrder; }

}