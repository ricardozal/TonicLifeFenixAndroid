package mx.com.bigtechsolutions.toniclifefenix.viewmodel.interfaces;

import mx.com.bigtechsolutions.toniclifefenix.api.requests.BankData;

public interface OnBankDataResponse {
    void OnSuccess(BankData bankData);
    void OnError(String title, String message);
}
