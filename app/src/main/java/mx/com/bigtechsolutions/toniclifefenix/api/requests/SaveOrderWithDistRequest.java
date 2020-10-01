
package mx.com.bigtechsolutions.toniclifefenix.api.requests;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveOrderWithDistRequest {

    @SerializedName("address_id")
    @Expose
    private Integer addressId;
    @SerializedName("distributor_id")
    @Expose
    private Integer distributorId;
    @SerializedName("branch_id")
    @Expose
    private Integer branchId;
    @SerializedName("points_for_dist")
    @Expose
    private Double pointsForDist;
    @SerializedName("leftover")
    @Expose
    private Double leftover;
    @SerializedName("products")
    @Expose
    private List<ProductRequest> products;
    @SerializedName("distributors")
    @Expose
    private List<DistributorPointsRequest> distributors;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SaveOrderWithDistRequest() {
    }

    /**
     *
     * @param branchId
     * @param leftover
     * @param distributors
     * @param distributorId
     * @param pointsForDist
     * @param addressId
     * @param products
     */
    public SaveOrderWithDistRequest(Integer addressId, Integer distributorId, Integer branchId, Double pointsForDist, Double leftover, List<ProductRequest> products, List<DistributorPointsRequest> distributors) {
        this.addressId = addressId;
        this.distributorId = distributorId;
        this.branchId = branchId;
        this.pointsForDist = pointsForDist;
        this.leftover = leftover;
        this.products = products;
        this.distributors = distributors;
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

    public Double getPointsForDist() {
        return pointsForDist;
    }

    public void setPointsForDist(Double pointsForDist) {
        this.pointsForDist = pointsForDist;
    }

    public Double getLeftover() {
        return leftover;
    }

    public void setLeftover(Double leftover) {
        this.leftover = leftover;
    }

    public List<ProductRequest> getProducts() {
        return products;
    }

    public void setProducts(List<ProductRequest> products) {
        this.products = products;
    }

    public List<DistributorPointsRequest> getDistributors() {
        return distributors;
    }

    public void setDistributors(List<DistributorPointsRequest> distributors) {
        this.distributors = distributors;
    }
}
