package com.bigtechsolutions.toniclifefenix.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shopping_cart")
public class ShoppingCart {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "product_name")
    public String productName;

    @ColumnInfo(name = "price")
    public double price;

    @ColumnInfo(name = "points")
    public double points;

    @ColumnInfo(name = "image_url")
    public String imageUrl;

    @ColumnInfo(name = "quantity")
    public int quantity;

    @ColumnInfo(name = "product_id")
    public int productId;

    @ColumnInfo(name = "is_kit")
    private boolean isKit;

    public ShoppingCart(String productName, double price, double points, String imageUrl, int quantity, int productId, boolean isKit) {
        this.productName = productName;
        this.price = price;
        this.points = points;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.productId = productId;
        this.isKit = isKit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public boolean isKit() {
        return isKit;
    }

    public void setKit(boolean kit) {
        isKit = kit;
    }
}
