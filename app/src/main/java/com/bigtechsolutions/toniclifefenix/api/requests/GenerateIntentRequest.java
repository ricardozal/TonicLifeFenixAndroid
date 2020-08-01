package com.bigtechsolutions.toniclifefenix.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenerateIntentRequest {

    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("currency")
    @Expose
    private String currency;

    /**
     * No args constructor for use in serialization
     *
     */
    public GenerateIntentRequest() {
    }

    /**
     *
     * @param amount
     * @param currency
     */
    public GenerateIntentRequest(Double amount, String currency) {
        super();
        this.amount = amount;
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}