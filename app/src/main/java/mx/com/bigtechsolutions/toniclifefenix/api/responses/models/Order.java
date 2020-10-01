
package mx.com.bigtechsolutions.toniclifefenix.api.responses.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

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
    @SerializedName("total_taxes")
    @Expose
    private String totalTaxes;
    @SerializedName("shipping_price")
    @Expose
    private String shippingPrice;
    @SerializedName("total_products")
    @Expose
    private Integer totalProducts;
    @SerializedName("products")
    @Expose
    private List<ProductOrder> products;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("delivery")
    @Expose
    private String delivery;
    @SerializedName("country_id")
    @Expose
    private Integer countryId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Order() {
    }

    /**
     * 
     * @param date
     * @param delivery
     * @param totalTaxes
     * @param shippingPrice
     * @param totalPrice
     * @param totalProducts
     * @param totalPoints
     * @param paymentMethod
     * @param id
     * @param products
     * @param status
     */
    public Order(Integer id, String date, String totalPrice, String totalPoints, String totalTaxes, String shippingPrice, Integer totalProducts, List<ProductOrder> products, String status, String paymentMethod, String delivery, Integer countryId) {
        super();
        this.id = id;
        this.date = date;
        this.totalPrice = totalPrice;
        this.totalPoints = totalPoints;
        this.totalTaxes = totalTaxes;
        this.shippingPrice = shippingPrice;
        this.totalProducts = totalProducts;
        this.products = products;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.delivery = delivery;
        this.countryId = countryId;
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

    public String getTotalTaxes() {
        return totalTaxes;
    }

    public void setTotalTaxes(String totalTaxes) {
        this.totalTaxes = totalTaxes;
    }

    public String getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(String shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public Integer getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(Integer totalProducts) {
        this.totalProducts = totalProducts;
    }

    public List<ProductOrder> getProducts() {
        return products;
    }

    public void setProducts(List<ProductOrder> products) {
        this.products = products;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }
}
