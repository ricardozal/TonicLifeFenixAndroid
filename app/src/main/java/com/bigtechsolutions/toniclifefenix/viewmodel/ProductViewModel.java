package com.bigtechsolutions.toniclifefenix.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bigtechsolutions.toniclifefenix.api.responses.models.Product;
import com.bigtechsolutions.toniclifefenix.data.repository.ProductRepository;
import com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnContentResponse;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    private ProductRepository productRepository;
    private LiveData<List<Product>> products;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        productRepository = new ProductRepository();
        products = productRepository.getProducts();
    }

    public LiveData<List<Product>> getProducts() { return products; }

    public void getContentMobileApp(OnContentResponse onContentResponse) { productRepository.getContentMobileApp(onContentResponse); }

    public LiveData<Boolean> getDownloadFinished(){
        LiveData<Boolean> downloadFinished=productRepository.getDownloadFinished();
        return downloadFinished;
    }
}
