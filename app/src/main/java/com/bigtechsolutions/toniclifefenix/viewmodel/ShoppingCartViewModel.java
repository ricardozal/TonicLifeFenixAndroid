package com.bigtechsolutions.toniclifefenix.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bigtechsolutions.toniclifefenix.api.requests.ChangeQuantityRequest;
import com.bigtechsolutions.toniclifefenix.data.entity.ShoppingCart;
import com.bigtechsolutions.toniclifefenix.data.repository.ShoppingCartRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ShoppingCartViewModel extends AndroidViewModel {

    private LiveData<List<ShoppingCart>> all;
    private Double totalOrder;
    private Integer countProducts, numberKits;
    private ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartViewModel(Application application) throws ExecutionException, InterruptedException {
        super(application);

        shoppingCartRepository = new ShoppingCartRepository(application);
        all = shoppingCartRepository.getAll();
        totalOrder = shoppingCartRepository.getTotalOrder();
        countProducts = shoppingCartRepository.getCountProducts();
        numberKits = shoppingCartRepository.getNumberKits();
    }

    public LiveData<List<ShoppingCart>> getAll() { return all;}

    public void insert(ShoppingCart shoppingCart) { shoppingCartRepository.insert(shoppingCart);}

    public void updateQuantity(ChangeQuantityRequest changeQuantityRequest) { shoppingCartRepository.updateQuantity(changeQuantityRequest); }

    public void deleteById(int productId) { shoppingCartRepository.deleteById(productId);}

    public void deleteAll() { shoppingCartRepository.deleteAll();}

    public Double getTotalOrder() { return totalOrder; }

    public Integer getCountProducts() { return countProducts; }

    public Integer getNumberKits() { return numberKits; }

    public Integer productExist(int productId) throws ExecutionException, InterruptedException { return shoppingCartRepository.productExit(productId); }

}