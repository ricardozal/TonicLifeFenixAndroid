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

    @Query("UPDATE shopping_cart SET quantity = :quantityNum WHERE product_id = :productId")
    void updateQuantity(int quantityNum, int productId);

    @Query("DELETE FROM shopping_cart")
    void deleteAll();

    @Query("DELETE FROM shopping_cart WHERE id = :productId")
    void deleteById(int productId);

    @Query("SELECT * FROM shopping_cart ORDER BY id ASC")
    LiveData<List<ShoppingCart>> getAll();

    @Query("SELECT SUM(price * quantity) as total FROM shopping_cart")
    Double getTotalOrder();

    @Query("SELECT COUNT(*) as count FROM shopping_cart")
    Integer getCountProducts();

    @Query("SELECT COUNT(*) as count FROM shopping_cart WHERE is_kit = 1")
    Integer getNumberKits();

}
