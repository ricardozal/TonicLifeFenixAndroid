package mx.com.bigtechsolutions.toniclifefenix.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderRequest {

    @SerializedName("address_id")
    @Expose
    private Integer addressId;
    @SerializedName("distributor_id")
    @Expose
    private Integer distributorId;
    @SerializedName("branch_id")
    @Expose
    private Integer branchId;
    @SerializedName("payment_method_id")
    @Expose
    private Integer paymentMethodId;
    @SerializedName("products")
    @Expose
    private List<ProductRequest> products;

    public OrderRequest() {
    }

    /**
     *
     * @param addressId
     * @param distributorId
     * @param branchId
     * @param paymentMethodId
     * @param products
     */
    public OrderRequest(Integer addressId, Integer distributorId, Integer branchId, Integer paymentMethodId, List<ProductRequest> products) {
        this.addressId = addressId;
        this.distributorId = distributorId;
        this.branchId = branchId;
        this.paymentMethodId = paymentMethodId;
        this.products = products;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Integer distributorId) {
        this.distributorId = distributorId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Integer paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public List<ProductRequest> getProducts() {
        return products;
    }

    public void setProducts(List<ProductRequest> products) {
        this.products = products;
    }
}
