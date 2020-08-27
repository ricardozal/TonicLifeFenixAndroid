package com.bigtechsolutions.toniclifefenix.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DistributorPointsRequest {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("points")
    @Expose
    private Double points;

    public DistributorPointsRequest() {
    }

    public DistributorPointsRequest(String id, Double points) {
        this.id = id;
        this.points = points;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }
}
