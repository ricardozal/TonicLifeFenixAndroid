package mx.com.bigtechsolutions.toniclifefenix.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpsertAddressRequest {

    @SerializedName("alias")
    @Expose
    private String alias;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("zip_code")
    @Expose
    private String zipCode;
    @SerializedName("ext_num")
    @Expose
    private String extNum;
    @SerializedName("int_num")
    @Expose
    private String intNum;
    @SerializedName("colony")
    @Expose
    private String colony;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("fk_id_country")
    @Expose
    private Integer fkIdCountry;
    @SerializedName("addressId")
    @Expose
    private Integer addressId;


    /**
     * @param zipCode
     * @param colony
     * @param city
     * @param street
     * @param intNum
     * @param alias
     * @param state
     * @param fkIdCountry
     * @param extNum
     * @param addressId
     */
    public UpsertAddressRequest(String alias, String street, String zipCode, String extNum, String intNum, String colony, String city, String state, Integer fkIdCountry, Integer addressId) {
        super();
        this.alias = alias;
        this.street = street;
        this.zipCode = zipCode;
        this.extNum = extNum;
        this.intNum = intNum;
        this.colony = colony;
        this.city = city;
        this.state = state;
        this.fkIdCountry = fkIdCountry;
        this.addressId = addressId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getExtNum() {
        return extNum;
    }
}