package mx.com.bigtechsolutions.toniclifefenix.api.responses.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("distributor_price")
    @Expose
    private Double distributorPrice;
    @SerializedName("tax")
    @Expose
    private Double tax;
    @SerializedName("total")
    @Expose
    private Double total;
    @SerializedName("points")
    @Expose
    private Integer points;
    @SerializedName("is_kit")
    @Expose
    private boolean isKit;
    @SerializedName("inventory")
    @Expose
    private String inventory;

    /**
     * No args constructor for use in serialization
     *
     */
    public Product() {
    }

    /**
     *
     * @param id
     * @param name
     * @param category
     * @param imageUrl
     * @param distributorPrice
     * @param tax
     * @param total
     * @param points
     * @param isKit
     * @param inventory
     */
    public Product(int id, String name, String category, String imageUrl, Double distributorPrice, Double tax, Double total, Integer points, boolean isKit, String inventory) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.imageUrl = imageUrl;
        this.distributorPrice = distributorPrice;
        this.tax = tax;
        this.total = total;
        this.points = points;
        this.isKit = isKit;
        this.inventory = inventory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getDistributorPrice() {
        return distributorPrice;
    }

    public void setDistributorPrice(Double distributorPrice) {
        this.distributorPrice = distributorPrice;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public boolean isKit() {
        return isKit;
    }

    public void setKit(boolean kit) {
        isKit = kit;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }
}