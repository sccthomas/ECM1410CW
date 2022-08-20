package cycling;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A class for creating a segment for each stage.
 *Contains methods and attributes for storing segment results
 */
public class Segment implements Serializable {
    /**The segments unique Id*/
    private int segmentId;
    /**The location of the segment within the stage*/
    private double location;
    /**An arrayList of all the riderResults in a given segment*/
    private ArrayList<RiderResult> ridersResultsInSegment;

    /**
     * A method that will sort the riders results in the segment in order of time.
     *
     */
    public void sortRiderResultsInSegment(){

        for (int i = 0; i < ridersResultsInSegment.size(); i++) {
            for (int j = i + 1; j < ridersResultsInSegment.size(); j++) {
                if (ridersResultsInSegment.get(i).getFinishTime().isAfter(ridersResultsInSegment.get(j).getFinishTime())) {
                    RiderResult temp = ridersResultsInSegment.get(j);
                    ridersResultsInSegment.set(j, ridersResultsInSegment.get(i));
                    ridersResultsInSegment.set(i, temp);

                }
            }
        }
    }

    /**
     * A constructor to create an instance of a segment
     * @param segmentId The segment ID
     * @param location The location of the segment
     * @param ridersResultsInSegment The rider results in the segment
     */
    public Segment(int segmentId, double location, ArrayList<RiderResult> ridersResultsInSegment) {

        this.segmentId = segmentId;
        this.location = location;
        this.ridersResultsInSegment = ridersResultsInSegment;
    }
    /**
     * A function to return the riders results in the segment
     * @return ridersResultsInSegment
     */
    public ArrayList<RiderResult> getRidersResultsInSegment() {
        return ridersResultsInSegment;
    }
    /**
     * A function to return the segment ID
     * @return segmentId
     */
    public int getSegmentId() {

        return segmentId;
    }
    /**
     * A function to return the location of the segment
     * @return location
     */
    public double getLocation() {

        return location;
    }


}
