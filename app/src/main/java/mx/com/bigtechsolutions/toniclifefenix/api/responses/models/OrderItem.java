package mx.com.bigtechsolutions.toniclifefenix.api.responses.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderItem {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("total_price")
    @Expose
    private String totalPrice;
    @SerializedName("total_points")
    @Expose
    private String totalPoints;
    @SerializedName("total_products")
    @Expose
    private Integer totalProducts;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("delivery")
    @Expose
    private String delivery;

    /**
     * No args constructor for use in serialization
     *
     */
    public OrderItem() {
    }

    /**
     *
     * @param date
     * @param delivery
     * @param totalPrice
     * @param totalProducts
     * @param totalPoints
     * @param id
     * @param status
     */
    public OrderItem(Integer id, String date, String totalPrice, String totalPoints, Integer totalProducts, String status, String delivery) {
        super();
        this.id = id;
        this.date = date;
        this.totalPrice = totalPrice;
        this.totalPoints = totalPoints;
        this.totalProducts = totalProducts;
        this.status = status;
        this.delivery = delivery;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(String totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Integer getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(Integer totalProducts) {
        this.totalProducts = totalProducts;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

}