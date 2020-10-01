
package mx.com.bigtechsolutions.toniclifefenix.api.responses.share_points;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CandidatesPromo {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("get_points")
    @Expose
    private Double getPoints;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CandidatesPromo() {
    }

    /**
     * 
     * @param color
     * @param name
     * @param getPoints
     * @param id
     */
    public CandidatesPromo(Integer id, String name, String color, Double getPoints) {
        super();
        this.id = id;
        this.name = name;
        this.color = color;
        this.getPoints = getPoints;
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

    public Double getGetPoints() {
        return getPoints;
    }

    public void setGetPoints(Double getPoints) {
        this.getPoints = getPoints;
    }

}
