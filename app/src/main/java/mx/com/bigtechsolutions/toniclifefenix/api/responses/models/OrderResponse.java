package mx.com.bigtechsolutions.toniclifefenix.api.responses.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderResponse {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("current_points")
    @Expose
    private Double currentPoints;

    /**
     * No args constructor for use in serialization
     *
     */
    public OrderResponse() {
    }

    /**
     *
     * @param orderId
     * @param message
     */
    public OrderResponse(String message, Integer orderId, Double currentPoints) {
        super();
        this.message = message;
        this.orderId = orderId;
        this.currentPoints = currentPoints;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Double getCurrentPoints() {
        return currentPoints;
    }

    public void setCurrentPoints(Double currentPoints) {
        this.currentPoints = currentPoints;
    }
}