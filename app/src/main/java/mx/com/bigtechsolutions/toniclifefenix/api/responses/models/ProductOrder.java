
package mx.com.bigtechsolutions.toniclifefenix.api.responses.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductOrder {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("points")
    @Expose
    private Integer points;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("accumulated_points")
    @Expose
    private Integer accumulatedPoints;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ProductOrder() {
    }

    /**
     * 
     * @param total
     * @param quantity
     * @param accumulatedPoints
     * @param price
     * @param name
     * @param points
     */
    public ProductOrder(String name, Integer points, String price, Integer quantity, String total, Integer accumulatedPoints) {
        super();
        this.name = name;
        this.points = points;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
        this.accumulatedPoints = accumulatedPoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Integer getAccumulatedPoints() {
        return accumulatedPoints;
    }

    public void setAccumulatedPoints(Integer accumulatedPoints) {
        this.accumulatedPoints = accumulatedPoints;
    }

}
