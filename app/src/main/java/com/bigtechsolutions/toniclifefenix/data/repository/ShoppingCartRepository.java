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
       return new getOrderTotalAsyncTask(shoppingCartDao).execute().get();
    }

    public Integer getCountProducts() throws ExecutionException, InterruptedException {
       return new getCountProductsAsyncTask(shoppingCartDao).execute().get();
    }

    public void insert(ShoppingCart product)
    {
        new insertAsyncTask(shoppingCartDao).execute(product);
    }

    public void deleteById(int productId)
    {
        new deleteByIdAsyncTask(shoppingCartDao).execute(productId);
    }

    public void deleteAll()
    {
        new deleteAllAsyncTask(shoppingCartDao).execute();
    }

    /* ******** Async Tasks ******* */

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

    private static class getOrderTotalAsyncTask extends AsyncTask<Void, Void, Double> {
        private ShoppingCartDao shoppingCartDaoAsyncTask;

        getOrderTotalAsyncTask(ShoppingCartDao dao){
            shoppingCartDaoAsyncTask = dao;
        }

        @Override
        protected Double doInBackground(Void... params) {
            return shoppingCartDaoAsyncTask.getTotalOrder();
        }
    }

    private static class getCountProductsAsyncTask extends AsyncTask<Void, Void, Integer> {
        private ShoppingCartDao shoppingCartDaoAsyncTask;

        getCountProductsAsyncTask(ShoppingCartDao dao){
            shoppingCartDaoAsyncTask = dao;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            return shoppingCartDaoAsyncTask.getCountProducts();
        }
    }

    private static class deleteByIdAsyncTask extends AsyncTask<Integer, Void, Void> {
        private ShoppingCartDao shoppingCartDaoAsyncTask;

        deleteByIdAsyncTask(ShoppingCartDao dao){
            shoppingCartDaoAsyncTask = dao;
        }

        @Override
        protected Void doInBackground(Integer... productId) {
            shoppingCartDaoAsyncTask.deleteById(productId[0]);
            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private ShoppingCartDao shoppingCartDaoAsyncTask;

        deleteAllAsyncTask(ShoppingCartDao dao){
            shoppingCartDaoAsyncTask = dao;
        }

        @Override
        protected Void doInBackground(Void... params) {
            shoppingCartDaoAsyncTask.deleteAll();
            return null;
        }
    }
}
