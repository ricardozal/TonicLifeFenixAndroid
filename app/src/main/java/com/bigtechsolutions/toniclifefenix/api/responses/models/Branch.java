package com.bigtechsolutions.toniclifefenix.api.responses.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Branch {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private Address address;

    /**
     * No args constructor for use in serialization
     *
     */
    public Branch() {
    }

    /**
     *
     * @param address
     * @param name
     * @param id
     */
    public Branch(Integer id, String name, Address address) {
        super();
        this.id = id;
        this.name = name;
        this.address = address;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}