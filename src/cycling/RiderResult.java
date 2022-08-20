package cycling;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * A class to create the rider result object.
 *Contains methods and attributes for  storing and retrieving a ridersResult
 */
public class RiderResult implements Serializable {

    /**The rider's unique Id */
    private int riderId;
    /**The rider's finish time*/
    private LocalTime finishTime;
    /**The rider's points*/
    private int points;
    /**
     * A method to create an instance of the RiderResult class excluding rider points .
     * @param riderId The ID of the rider.
     * @param segmentFinishTime The segment finish time of the rider.
     */
    public RiderResult(int riderId, LocalTime segmentFinishTime) {

        this.riderId = riderId;
        this.finishTime = segmentFinishTime;
    }
    /**A method to create an instance of the RiderResult class.
     * @param riderId The ID of the rider.
     * @param segmentFinishTime The segment finish time of the rider.
     * @param points The points obtained per segment.
     */
    public RiderResult(int riderId, LocalTime segmentFinishTime, int points) {

        this.riderId = riderId;
        this.finishTime = segmentFinishTime;
        this.points = points;
    }
    /**A method to create an instance of the RiderResult class excluding the rider time
     * @param riderId The ID of the rider.
     * @param points The points obtained per segment.
     */
    public RiderResult(int riderId, int points) {

        this.riderId = riderId;
        this.points = points;
    }
    /**
     * A method to set the rider ID.
     * @param riderId
     */
    public void setRiderId(int riderId) {

        this.riderId = riderId;
    }
    /**
     * A method to set the points obtained by the rider.
     * @param points The points achieved.
     */
    public void setPoints(int points) {

        this.points = points;
    }
    /**
     * A method to set the finish time of a rider.
     * @param finishTime The time the rider finished in.
     */
    public void setFinishTime(LocalTime finishTime) {

        finishTime = finishTime;
    }
    /**
     * A function to get the points achieved by the rider.
     * @return The points of the rider.
     */
    public int getPoints() {

        return points;
    }
    /**
     * A function to return the riders ID.
     * @return The riders ID.
     */
    public int getRiderId() {

        return riderId;
    }
    /**
     * A function to return the finish time of the rider.
     * @return The riders finish time.
     */
    public LocalTime getFinishTime() {

        return finishTime;
    }
}
