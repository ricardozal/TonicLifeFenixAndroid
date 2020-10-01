package mx.com.bigtechsolutions.toniclifefenix.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewDistributorRequest {

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
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("marital_status")
    @Expose
    private String maritalStatus;
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("birth_place")
    @Expose
    private String birthPlace;
    @SerializedName("nationality")
    @Expose
    private String nationality;
    @SerializedName("rfc_or_itin")
    @Expose
    private String rfcOrItin;
    @SerializedName("curp_or_ssn")
    @Expose
    private String curpOrSsn;
    @SerializedName("phone_1")
    @Expose
    private String phone1;
    @SerializedName("phone_2")
    @Expose
    private String phone2;
    @SerializedName("no_official_identification")
    @Expose
    private String noOfficialIdentification;
    @SerializedName("fk_id_order")
    @Expose
    private Integer fkIdOrder;
    @SerializedName("bank_name")
    @Expose
    private String bankName;
    @SerializedName("account_name")
    @Expose
    private String accountName;
    @SerializedName("bank_account_number")
    @Expose
    private String bankAccountNumber;
    @SerializedName("clabe_routing_bank")
    @Expose
    private String clabeRoutingBank;

    /**
     * No args constructor for use in serialization
     *
     */
    public NewDistributorRequest() {
    }

    /**
     *
     * @param birthday
     * @param zipCode
     * @param colony
     * @param city
     * @param accountName
     * @param intNum
     * @param curpOrSsn
     * @param phone2
     * @param bankName
     * @param noOfficialIdentification
     * @param fkIdCountry
     * @param extNum
     * @param phone1
     * @param rfcOrItin
     * @param birthPlace
     * @param fkIdOrder
     * @param nationality
     * @param street
     * @param name
     * @param bankAccountNumber
     * @param state
     * @param email
     * @param maritalStatus
     * @param clabeRoutingBank
     */
    public NewDistributorRequest(String street, String zipCode, String extNum, String intNum, String colony, String city, String state, Integer fkIdCountry, String name, String email, String maritalStatus, String birthday, String birthPlace, String nationality, String rfcOrItin, String curpOrSsn, String phone1, String phone2, String noOfficialIdentification, Integer fkIdOrder, String bankName, String accountName, String bankAccountNumber, String clabeRoutingBank) {
        super();
        this.street = street;
        this.zipCode = zipCode;
        this.extNum = extNum;
        this.intNum = intNum;
        this.colony = colony;
        this.city = city;
        this.state = state;
        this.fkIdCountry = fkIdCountry;
        this.name = name;
        this.email = email;
        this.maritalStatus = maritalStatus;
        this.birthday = birthday;
        this.birthPlace = birthPlace;
        this.nationality = nationality;
        this.rfcOrItin = rfcOrItin;
        this.curpOrSsn = curpOrSsn;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.noOfficialIdentification = noOfficialIdentification;
        this.fkIdOrder = fkIdOrder;
        this.bankName = bankName;
        this.accountName = accountName;
        this.bankAccountNumber = bankAccountNumber;
        this.clabeRoutingBank = clabeRoutingBank;
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

    public void setExtNum(String extNum) {
        this.extNum = extNum;
    }

    public String getIntNum() {
        return intNum;
    }

    public void setIntNum(String intNum) {
        this.intNum = intNum;
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

    public Integer getFkIdCountry() {
        return fkIdCountry;
    }

    public void setFkIdCountry(Integer fkIdCountry) {
        this.fkIdCountry = fkIdCountry;
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

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getRfcOrItin() {
        return rfcOrItin;
    }

    public void setRfcOrItin(String rfcOrItin) {
        this.rfcOrItin = rfcOrItin;
    }

    public String getCurpOrSsn() {
        return curpOrSsn;
    }

    public void setCurpOrSsn(String curpOrSsn) {
        this.curpOrSsn = curpOrSsn;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getNoOfficialIdentification() {
        return noOfficialIdentification;
    }

    public void setNoOfficialIdentification(String noOfficialIdentification) {
        this.noOfficialIdentification = noOfficialIdentification;
    }

    public Integer getFkIdOrder() {
        return fkIdOrder;
    }

    public void setFkIdOrder(Integer fkIdOrder) {
        this.fkIdOrder = fkIdOrder;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getClabeRoutingBank() {
        return clabeRoutingBank;
    }

    public void setClabeRoutingBank(String clabeRoutingBank) {
        this.clabeRoutingBank = clabeRoutingBank;
    }

}