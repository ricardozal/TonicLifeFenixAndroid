package com.bigtechsolutions.toniclifefenix.viewmodel;

public interface OnOrderResponse {

    void OnSuccess(String title, String message, Integer orderId);
    void OnError(String title, String message);

}
