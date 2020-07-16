package com.bigtechsolutions.toniclifefenix.data.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.bigtechsolutions.toniclifefenix.data.ShoppingCartDataBase;
import com.bigtechsolutions.toniclifefenix.data.dao.ShoppingCartDao;
import com.bigtechsolutions.toniclifefenix.data.entity.ShoppingCart;

import java.util.List;

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
}
