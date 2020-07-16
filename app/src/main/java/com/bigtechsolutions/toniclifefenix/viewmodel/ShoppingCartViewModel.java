package com.bigtechsolutions.toniclifefenix.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.bigtechsolutions.toniclifefenix.data.entity.ShoppingCart;
import com.bigtechsolutions.toniclifefenix.data.repository.ShoppingCartRepository;

import java.util.List;

public class ShoppingCartViewModel extends AndroidViewModel {

    private LiveData<List<ShoppingCart>> all;
    private ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartViewModel(Application application) {
        super(application);

        shoppingCartRepository = new ShoppingCartRepository(application);
        all = shoppingCartRepository.getAll();
    }

    public LiveData<List<ShoppingCart>> getAll() { return all;}

    public void insert(ShoppingCart shoppingCart) { shoppingCartRepository.insert(shoppingCart);}

}