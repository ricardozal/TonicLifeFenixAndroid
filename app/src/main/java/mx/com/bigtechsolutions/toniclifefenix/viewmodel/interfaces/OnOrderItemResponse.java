package mx.com.bigtechsolutions.toniclifefenix.viewmodel.interfaces;

import mx.com.bigtechsolutions.toniclifefenix.api.responses.models.Order;

public interface OnOrderItemResponse {

    void OnSuccess(String title, String message, Order order);
    void OnError(String title, String message);

}
