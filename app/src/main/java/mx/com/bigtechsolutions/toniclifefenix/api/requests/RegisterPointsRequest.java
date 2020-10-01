package mx.com.bigtechsolutions.toniclifefenix.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegisterPointsRequest {

    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("distributors")
    @Expose
    private List<DistributorPointsRequest> distributors;

    public RegisterPointsRequest() {
    }

    public RegisterPointsRequest(Integer orderId, List<DistributorPointsRequest> distributors) {
        this.orderId = orderId;
        this.distributors = distributors;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public List<DistributorPointsRequest> getDistributors() {
        return distributors;
    }

    public void setDistributors(List<DistributorPointsRequest> distributors) {
        this.distributors = distributors;
    }
}
