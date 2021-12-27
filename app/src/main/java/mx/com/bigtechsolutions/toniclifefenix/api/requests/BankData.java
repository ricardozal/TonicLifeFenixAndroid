package mx.com.bigtechsolutions.toniclifefenix.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankData {

    @SerializedName("bank_name")
    @Expose
    private String bankName;
    @SerializedName("bank_owner_name")
    @Expose
    private String bankOwnerName;
    @SerializedName("bank_account_number")
    @Expose
    private String bankAccountNumber;

    public BankData() {
    }

    public BankData(String bankName, String bankOwnerName, String bankAccountNumber) {
        this.bankName = bankName;
        this.bankOwnerName = bankOwnerName;
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankOwnerName() {
        return bankOwnerName;
    }

    public void setBankOwnerName(String bankOwnerName) {
        this.bankOwnerName = bankOwnerName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }
}
