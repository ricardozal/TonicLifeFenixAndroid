package mx.com.bigtechsolutions.toniclifefenix.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Token {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("tonic_life_id")
    @Expose
    private String tonicLifeId;
    @SerializedName("current_points")
    @Expose
    private Double currentPoints;
    @SerializedName("distributor_country")
    @Expose
    private Integer distributorCountry;
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("token_type")
    @Expose
    private String tokenType;
    @SerializedName("expires_at")
    @Expose
    private String expiresAt;

    /**
     * No args constructor for use in serialization
     *
     */
    public Token() {
    }

    /**
     *
     * @param id
     * @param name
     * @param email
     * @param tonicLifeId
     * @param accessToken
     * @param tokenType
     * @param expiresAt
     */
    public Token(int id, String name, String email, String tonicLifeId, Double currentPoints, Integer distributorCountry, String accessToken, String tokenType, String expiresAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.tonicLifeId = tonicLifeId;
        this.currentPoints = currentPoints;
        this.distributorCountry = distributorCountry;
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresAt = expiresAt;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTonicLifeId() {
        return tonicLifeId;
    }

    public void setTonicLifeId(String tonicLifeId) {
        this.tonicLifeId = tonicLifeId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Double getCurrentPoints() {
        return currentPoints;
    }

    public void setCurrentPoints(Double currentPoints) {
        this.currentPoints = currentPoints;
    }

    public Integer getDistributorCountry() {
        return distributorCountry;
    }

    public void setDistributorCountry(Integer distributorCountry) {
        this.distributorCountry = distributorCountry;
    }
}