package cycling;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class for the categorised climbs in a stage
 */
public class CategorisedClimb extends Segment implements Serializable {
    /**The type of climb the categorised climb is*/
    private SegmentType type;
    /**The average gradient of the climb*/
    private double averageGradient;
    /**The length of the climb*/
    private double length;
    /**
     * A constructor make a new instance of a categorised climb
     * @param segmentId The segments ID
     * @param location The length of the segment
     * @param type The type that the segment is
     * @param ridersResultsInSegment The riders result's for the segment
     * @param averageGradient The average gradient for the climb
     * @param length The length of the climb
     */
    public CategorisedClimb(int segmentId, double location, SegmentType type, ArrayList<RiderResult> ridersResultsInSegment, double averageGradient, double length) {

        super(segmentId, location, ridersResultsInSegment);
        this.type = type;
        this.averageGradient = averageGradient;
        this.length = length;
    }

    /**A method to calculate the points obtained by each rider in the categorised climb
     * @return returnHash, a hashmap of all riderId's and points obtained
     */
    public HashMap<Integer , Integer> calculateCategorisedClimbPoints(){

        int[] hardPointsForPosition = {20, 15, 12, 10, 8 , 6 ,4 ,2};
        int[] easyPointsFordPosition = {5 , 3 , 2 , 1};

        HashMap<Integer, Integer> returnHash = new HashMap<>();
        int count = this.type.equals(SegmentType.C1) ? 3 : this.type.equals(SegmentType.C3) ?  2 : this.type.equals(SegmentType.C4) ? 3 :  0;
        try {
            for (RiderResult riderResult : getRidersResultsInSegment()) {
                if (this.type.equals(SegmentType.HC)) {
                    if (count < 8) {
                        returnHash.put(riderResult.getRiderId(), hardPointsForPosition[count]);
                        count++;
                    }
                } else if (this.type.equals(SegmentType.C1)) {
                    if (count < 8) {
                        returnHash.put(riderResult.getRiderId(), hardPointsForPosition[count]);
                        count++;
                    }
                } else if (this.type.equals(SegmentType.C2)) {
                    if (count < 4) {
                        returnHash.put(riderResult.getRiderId(), easyPointsFordPosition[count]);
                        count++;
                    }
                } else if (this.type.equals(SegmentType.C3)) {
                    if (count < 4) {
                        returnHash.put(riderResult.getRiderId(), easyPointsFordPosition[count]);
                        count++;
                    }
                } else if (this.type.equals(SegmentType.C4)) {
                    if (count < 4) {
                        returnHash.put(riderResult.getRiderId(), easyPointsFordPosition[count]);
                        count++;
                    }
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }

        return returnHash;
    }


}
