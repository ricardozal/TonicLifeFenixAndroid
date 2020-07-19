package com.bigtechsolutions.toniclifefenix.data.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.bigtechsolutions.toniclifefenix.data.ShoppingCartDataBase;
import com.bigtechsolutions.toniclifefenix.data.dao.ShoppingCartDao;
import com.bigtechsolutions.toniclifefenix.data.entity.ShoppingCart;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ShoppingCartRepository {
    private ShoppingCartDao shoppingCartDao;
    private LiveData<List<ShoppingCart>> allShoppingCart;

    public ShoppingCartRepository(Application application){
        ShoppingCartDataBase db = ShoppingCartDataBase.getDatabase(application);
        shoppingCartDao = db.shoppingCartDao();
        allShoppingCart = shoppingCartDao.getAll();
    }

    public LiveData<List<ShoppingCart>> getAll() {
        return allShoppingCart;
    }

    public Double getTotalOrder() throws ExecutionException, InterruptedException {
       return new getOrdeTotalAsyncTask(shoppingCartDao).execute().get();
    }

    public void insert(ShoppingCart product)
    {
        new insertAsyncTask(shoppingCartDao).execute(product);
    }

    private static class insertAsyncTask extends AsyncTask<ShoppingCart, Void, Void> {
        private ShoppingCartDao shoppingCartDaoAsyncTask;

        insertAsyncTask(ShoppingCartDao dao){
            shoppingCartDaoAsyncTask = dao;
        }

        @Override
        protected Void doInBackground(ShoppingCart... shoppingCarts) {
            shoppingCartDaoAsyncTask.insert(shoppingCarts[0]);
            return null;
        }
    }

    private static class getOrdeTotalAsyncTask extends AsyncTask<Void, Void, Double> {
        private ShoppingCartDao shoppingCartDaoAsyncTask;

        getOrdeTotalAsyncTask(ShoppingCartDao dao){
            shoppingCartDaoAsyncTask = dao;
        }

        @Override
        protected Double doInBackground(Void... params) {
            return shoppingCartDaoAsyncTask.getTotalOrder();
        }
    }
}
