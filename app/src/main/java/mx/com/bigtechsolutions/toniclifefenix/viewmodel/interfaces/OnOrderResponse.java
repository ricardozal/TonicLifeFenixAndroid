package mx.com.bigtechsolutions.toniclifefenix.viewmodel.interfaces;

public interface OnOrderResponse {

    void OnSuccess(String title, String message, Integer orderId, Double currentPoints);
    void OnError(String title, String message);

}
