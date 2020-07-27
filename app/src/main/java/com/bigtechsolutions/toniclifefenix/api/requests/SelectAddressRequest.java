package com.bigtechsolutions.toniclifefenix.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SelectAddressRequest {

    @SerializedName("distributor_id")
    @Expose
    private Integer distributorId;
    @SerializedName("address_id")
    @Expose
    private Integer addressId;

    /**
     * No args constructor for use in serialization
     *
     */
    public SelectAddressRequest() {
    }

    /**
     *
     * @param distributorId
     * @param addressId
     */
    public SelectAddressRequest(Integer distributorId, Integer addressId) {
        super();
        this.distributorId = distributorId;
        this.addressId = addressId;
    }

    public Integer getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Integer distributorId) {
        this.distributorId = distributorId;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

}