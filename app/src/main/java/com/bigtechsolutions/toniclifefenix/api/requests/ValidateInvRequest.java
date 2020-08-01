package com.bigtechsolutions.toniclifefenix.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ValidateInvRequest {

    @SerializedName("address_id")
    @Expose
    private Integer addressId;
    @SerializedName("branch_id")
    @Expose
    private Integer branchId;
    @SerializedName("products")
    @Expose
    private List<ProductRequest> products;

    /**
     * No args constructor for use in serialization
     *
     */
    public ValidateInvRequest() {
    }

    /**
     *
     * @param branchId
     * @param products
     * @param addressId
     */
    public ValidateInvRequest(Integer addressId, Integer branchId, List<ProductRequest> products) {
        super();
        this.addressId = addressId;
        this.branchId = branchId;
        this.products = products;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public List<ProductRequest> getProducts() {
        return products;
    }

    public void setProducts(List<ProductRequest> products) {
        this.products = products;
    }
}