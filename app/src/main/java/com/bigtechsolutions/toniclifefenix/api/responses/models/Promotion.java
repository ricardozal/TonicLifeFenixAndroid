package com.bigtechsolutions.toniclifefenix.api.responses.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Promotion {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("date_begin")
    @Expose
    private String dateBegin;
    @SerializedName("date_end")
    @Expose
    private String dateEnd;
    @SerializedName("min_amount")
    @Expose
    private String minAmount;
    @SerializedName("is_accumulative")
    @Expose
    private String isAccumulative;

    /**
     * No args constructor for use in serialization
     *
     */
    public Promotion() {
    }

    /**
     *
     * @param dateBegin
     * @param isAccumulative
     * @param minAmount
     * @param name
     * @param description
     * @param id
     * @param dateEnd
     */
    public Promotion(Integer id, String name, String description, String dateBegin, String dateEnd, String minAmount, String isAccumulative) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.minAmount = minAmount;
        this.isAccumulative = isAccumulative;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(String dateBegin) {
        this.dateBegin = dateBegin;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(String minAmount) {
        this.minAmount = minAmount;
    }

    public String getIsAccumulative() {
        return isAccumulative;
    }

    public void setIsAccumulative(String isAccumulative) {
        this.isAccumulative = isAccumulative;
    }

}