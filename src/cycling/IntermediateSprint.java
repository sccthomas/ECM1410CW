package cycling;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *A class for the intermediate sprints in a stage
 */
public class IntermediateSprint extends Segment implements Serializable {
    /**
     * A constructor for creating a new IntermediateSprint instance
     * @param segmentId The segments ID
     * @param location The distance of the race
     * @param ridersResultsInSegment The riders result's in the segment
     */
    public IntermediateSprint(int segmentId, double location, ArrayList<RiderResult> ridersResultsInSegment) {

        super(segmentId, location, ridersResultsInSegment);

    }
    /**
     * A function to calculate the points obtained by each rider in the segment based of their finishing positions
     * @return returnHash, a hashmap of riderId and the points they obtained in the segment
     */
    public HashMap<Integer , Integer> calculatePointsIntermediateSprint(){

        int[] PointsForPosition = {20, 17, 15, 13, 11, 10 , 9 , 8 , 7 , 6 , 5 , 4 , 3 , 2 , 1};

        HashMap<Integer, Integer> returnHash = new HashMap<>();
        int count = 0;
        try {

            for (RiderResult riderSegResult : getRidersResultsInSegment()) {
                if(count < 14){
                    returnHash.put(riderSegResult.getRiderId() , PointsForPosition[count]);
                    count++;
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }

        return returnHash;
    }
}
