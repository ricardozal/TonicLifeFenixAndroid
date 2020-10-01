
package mx.com.bigtechsolutions.toniclifefenix.api.requests;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCandidatesRequest {

    @SerializedName("distributor_id")
    @Expose
    private Integer distributorId;
    @SerializedName("products")
    @Expose
    private List<ProductRequest> products;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GetCandidatesRequest() {
    }

    /**
     * 
     * @param distributorId
     * @param products
     */
    public GetCandidatesRequest(Integer distributorId, List<ProductRequest> products) {
        super();
        this.distributorId = distributorId;
        this.products = products;
    }

    public Integer getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Integer distributorId) {
        this.distributorId = distributorId;
    }

    public List<ProductRequest> getProducts() {
        return products;
    }

    public void setProducts(List<ProductRequest> products) {
        this.products = products;
    }

}
