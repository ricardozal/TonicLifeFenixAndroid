package com.bigtechsolutions.toniclifefenix.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bigtechsolutions.toniclifefenix.data.entity.ShoppingCart;

import java.util.List;

@Dao
public interface ShoppingCartDao {

    @Insert
    void insert(ShoppingCart product);

    @Update
    void update(ShoppingCart product);

    @Query("DELETE FROM shopping_cart")
    void deleteAll();

    @Query("DELETE FROM shopping_cart WHERE id = :productId")
    void deleteById(int productId);

    @Query("SELECT * FROM shopping_cart ORDER BY id ASC")
    LiveData<List<ShoppingCart>> getAll();

    @Query("SELECT SUM(price * quantity) as total FROM shopping_cart")
    Double getTotalOrder();

}
