package com.bigtechsolutions.toniclifefenix.api.responses.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContentMobileResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("absolute_url")
    @Expose
    private String absoluteUrl;

    /**
     * No args constructor for use in serialization
     *
     */
    public ContentMobileResponse() {
    }

    /**
     *
     * @param absoluteUrl
     * @param name
     * @param id
     * @param type
     * @param key
     * @param url
     */
    public ContentMobileResponse(Integer id, String name, String type, String key, String url, String absoluteUrl) {
        super();
        this.id = id;
        this.name = name;
        this.type = type;
        this.key = key;
        this.url = url;
        this.absoluteUrl = absoluteUrl;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAbsoluteUrl() {
        return absoluteUrl;
    }

    public void setAbsoluteUrl(String absoluteUrl) {
        this.absoluteUrl = absoluteUrl;
    }

}