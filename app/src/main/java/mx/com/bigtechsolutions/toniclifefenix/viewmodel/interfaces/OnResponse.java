package mx.com.bigtechsolutions.toniclifefenix.viewmodel.interfaces;

public interface OnResponse {
    void OnSuccess(String title, String message);
    void OnError(String title, String message);
}
