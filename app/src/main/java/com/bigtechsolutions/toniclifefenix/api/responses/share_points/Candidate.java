
package com.bigtechsolutions.toniclifefenix.api.responses.share_points;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Candidate {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("difference")
    @Expose
    private Double difference;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Candidate() {
    }

    /**
     * 
     * @param color
     * @param name
     * @param difference
     * @param id
     */
    public Candidate(Integer id, String name, String color, Double difference) {
        super();
        this.id = id;
        this.name = name;
        this.color = color;
        this.difference = difference;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getDifference() {
        return difference;
    }

    public void setDifference(Double difference) {
        this.difference = difference;
    }

}
