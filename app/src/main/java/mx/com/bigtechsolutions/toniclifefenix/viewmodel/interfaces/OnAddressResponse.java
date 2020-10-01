package mx.com.bigtechsolutions.toniclifefenix.viewmodel.interfaces;

import mx.com.bigtechsolutions.toniclifefenix.api.responses.models.Address;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.models.Order;

public interface OnAddressResponse {

    void OnSuccess(String title, String message, Address address);
    void OnError(String title, String message);
}
