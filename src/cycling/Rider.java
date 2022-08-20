package cycling;

import java.io.Serializable;

/**
 * Class to create the object Rider that
 * Contains methods and attributes for creating a rider
 */
public class Rider implements Serializable {

    /**The rider's name */
    private String name;
    /**The rider's year of birth */
    private int yearOfBirth;
    /**The rider's unique Id */
    private int riderId;

    /**
     * A method to create a new Rider instance.
     * @param name The name of the new Rider.
     * @param yearOfBirth The date of birth for the new rider.
     * @param riderId The new ID of the rider.
     */
    public Rider(String name, int yearOfBirth, int riderId) {

        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.riderId = riderId;
    }
    /**
     * A function to return the name of the rider.
     * @return The name of the rider.
     */
    public String getName() {
        return name;
    }
    /**
     * A method to set the name of the rider.
     * @param name The name of the rider.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * A function to return the rider ID of the rider.
     * @return The riders ID.
     */
    public int getRiderId() {
        return riderId;
    }
    /**
     * A method to set the rider ID of the rider.
     * @param riderId The id to be set.
     */
    public void setRiderId(int riderId) {
        this.riderId = riderId;
    }
}
