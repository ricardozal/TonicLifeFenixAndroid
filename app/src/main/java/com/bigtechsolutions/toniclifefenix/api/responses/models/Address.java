package com.bigtechsolutions.toniclifefenix.api.responses.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("ext_num")
    @Expose
    private String extNum;
    @SerializedName("int_num")
    @Expose
    private String intNum;
    @SerializedName("zip_code")
    @Expose
    private String zipCode;
    @SerializedName("colony")
    @Expose
    private String colony;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("references")
    @Expose
    private String references;
    @SerializedName("full_address")
    @Expose
    private String fullAddress;

    /**
     * No args constructor for use in serialization
     *
     */
    public Address() {
    }

    /**
     *
     * @param zipCode
     * @param country
     * @param colony
     * @param references
     * @param city
     * @param street
     * @param intNum
     * @param fullAddress
     * @param id
     * @param state
     * @param extNum
     */
    public Address(Integer id, String street, String extNum, String intNum, String zipCode, String colony, String city, String state, String country, String references, String fullAddress) {
        super();
        this.id = id;
        this.street = street;
        this.extNum = extNum;
        this.intNum = intNum;
        this.zipCode = zipCode;
        this.colony = colony;
        this.city = city;
        this.state = state;
        this.country = country;
        this.references = references;
        this.fullAddress = fullAddress;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getExtNum() {
        return extNum;
    }

    public void setExtNum(String extNum) {
        this.extNum = extNum;
    }

    public String getIntNum() {
        return intNum;
    }

    public void setIntNum(String intNum) {
        this.intNum = intNum;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getColony() {
        return colony;
    }

    public void setColony(String colony) {
        this.colony = colony;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getReferences() {
        return references;
    }

    public void setReferences(String references) {
        this.references = references;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

}
