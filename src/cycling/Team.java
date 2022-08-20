package cycling;

import java.io.Serializable;
import java.util.HashMap;

/**
 * A class for creating and managing a team
 *Contains methods and attributes for creating and managing a team
 */
public class Team implements Serializable {
    /** A hashmap of all the riders in a team. Key the rider's Id and value the Rider object */
    private HashMap<Integer, Rider> teamRiders = new HashMap<>();
    /**The teams name */
    private String teamName;
    /**The teams Id */
    private int teamId;
    /**The teams description */
    private String description;
    /**
     * A constructor to make a new team instance
     * @param teamName The team name
     * @param teamId The teams unique ID
     * @param description The team description
     */
    public Team( String teamName, int teamId, String description) {
        this.teamName = teamName;
        this.teamId = teamId;
        this.description = description;
    }
    /**
     * A function to return the name of the team
     * @return teamName
     */
    public String getTeamName() {

        return teamName;
    }
    /**
     * A function to get the ID of the team
     * @return teamId
     */
    public int getTeamId() {
        return teamId;
    }
    /**
     * A method to return the hashmap of riders in the team
     * @return teamRiders
     */
    public HashMap<Integer, Rider> getTeamRiders() {
        return teamRiders;
    }
    /**
     * A method to add a new rider Id and object to the hashmap teamRiders
     * @param riderId The rider ID
     * @param rider The rider object
     */
    public void addRiderToTeam(Integer riderId, Rider rider){
        this.teamRiders.put(riderId, rider);
    }
    /**
     * A method to remove a rider from the team
     * @param riderID the rider being removed
     */
    public void removeRiderFromTeam(Integer riderID){
        teamRiders.remove(riderID);
    }
}
