
package mx.com.bigtechsolutions.toniclifefenix.api.responses.share_points;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SharePointsResponse {

    @SerializedName("original_points")
    @Expose
    private Double originalPoints;
    @SerializedName("total_points")
    @Expose
    private Double totalPoints;
    @SerializedName("to_dist")
    @Expose
    private String toDist;
    @SerializedName("points_for_distributor")
    @Expose
    private Double pointsForDistributor;
    @SerializedName("candidates")
    @Expose
    private List<Candidate> candidates;
    @SerializedName("candidates_promos")
    @Expose
    private List<CandidatesPromo> candidatesPromos;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SharePointsResponse() {
    }

    /**
     * 
     * @param candidates
     * @param candidatesPromos
     * @param totalPoints
     * @param originalPoints
     * @param pointsForDistributor
     * @param toDist
     */
    public SharePointsResponse(Double originalPoints, Double totalPoints, String toDist, Double pointsForDistributor, List<Candidate> candidates, List<CandidatesPromo> candidatesPromos) {
        super();
        this.originalPoints = originalPoints;
        this.totalPoints = totalPoints;
        this.toDist = toDist;
        this.pointsForDistributor = pointsForDistributor;
        this.candidates = candidates;
        this.candidatesPromos = candidatesPromos;
    }

    public Double getOriginalPoints() {
        return originalPoints;
    }

    public void setOriginalPoints(Double originalPoints) {
        this.originalPoints = originalPoints;
    }

    public Double getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Double totalPoints) {
        this.totalPoints = totalPoints;
    }

    public String getToDist() {
        return toDist;
    }

    public void setToDist(String toDist) {
        this.toDist = toDist;
    }

    public Double getPointsForDistributor() {
        return pointsForDistributor;
    }

    public void setPointsForDistributor(Double pointsForDistributor) {
        this.pointsForDistributor = pointsForDistributor;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    public List<CandidatesPromo> getCandidatesPromos() {
        return candidatesPromos;
    }

    public void setCandidatesPromos(List<CandidatesPromo> candidatesPromos) {
        this.candidatesPromos = candidatesPromos;
    }

}
