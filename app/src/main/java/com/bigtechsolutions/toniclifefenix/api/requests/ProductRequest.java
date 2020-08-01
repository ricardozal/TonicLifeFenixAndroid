package com.bigtechsolutions.toniclifefenix.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductRequest {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;

    /**
     * No args constructor for use in serialization
     *
     */
    public ProductRequest() {
    }

    /**
     *
     * @param quantity
     * @param id
     */
    public ProductRequest(Integer id, Integer quantity) {
        super();
        this.id = id;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}