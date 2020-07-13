package com.bigtechsolutions.toniclifefenix.api.responses.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private int id;
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

    /**
     * No args constructor for use in serialization
     *
     */
    public Product() {
    }

    /**
     *
     * @param id
     * @param total
     * @param imageUrl
     * @param name
     * @param distributorPrice
     * @param tax
     * @param points
     */
    public Product(int id, String name, String imageUrl, Double distributorPrice, Double tax, Double total, Integer points) {
        super();
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.distributorPrice = distributorPrice;
        this.tax = tax;
        this.total = total;
        this.points = points;
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

}