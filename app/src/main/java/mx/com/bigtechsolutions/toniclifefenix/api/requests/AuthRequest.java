
package mx.com.bigtechsolutions.toniclifefenix.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthRequest {

    @SerializedName("tonic_life_id")
    @Expose
    private String tonicLifeId;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("remember")
    @Expose
    private boolean remember;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AuthRequest() {
    }

    /**
     * 
     * @param remember
     * @param password
     * @param tonicLifeId
     */
    public AuthRequest(String tonicLifeId, String password, boolean remember) {
        this.tonicLifeId = tonicLifeId;
        this.password = password;
        this.remember = remember;
    }

    public String getTonicLifeId() {
        return tonicLifeId;
    }

    public void setTonicLifeId(String tonicLifeId) {
        this.tonicLifeId = tonicLifeId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

}
