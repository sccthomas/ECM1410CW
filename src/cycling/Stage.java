package cycling;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

/**
 *A class for creating a stage
 *Contains methods and attributes to do with stages in a race
 */
public class Stage implements Serializable, Sorts {
    /**The stages unique Id*/
    private int stageId;
    /**The stages name*/
    private String stageName;
    /**The distance of the stage*/
    private double stageLength;
    /**The stageType*/
    private StageType type;
    /**The start time of the stage*/
    private LocalDateTime startTime;
    /**A hashmap of the segment objects and their Ids that exist in this stage*/
    private HashMap<Integer, Segment> segments;
    /**A description of the stage*/
    private String description;
    /**The stages state*/
    private String stageState;
    /**An object of the stageResults class to store the results for this stage*/
    private StageResults stageResults;

    /**
     * A constructor to make a new stage instance
     * @param stageId The id of the stage
     * @param stageName The name of the stage
     * @param stageLength The distance of the stage in the race
     * @param type The stages type
     * @param startTime The start time of the stage in the race
     * @param description A description of the stage
     */
    public Stage(int stageId, String stageName, double stageLength, StageType type, LocalDateTime startTime, String description) {
        this.stageId = stageId;
        this.stageName = stageName;
        this.stageLength = stageLength;
        this.type = type;
        this.startTime = startTime;
        this.segments = new HashMap<Integer, Segment>();
        this.description = description;
        this.stageState = "Unfinished";
        this.stageResults = new StageResults();
    }
    /**
     * A function to get the stages name
     * @return stageName
     */
    public String getStageName(){
        return stageName;
    }
    /**
     * A function to get the ID of the stage
     * @return stageId
     */
    public int getStageId() {

        return stageId;
    }
    /**
     * A method to set the id of the stage
     * @param stageId The stage ID
     */
    public void setStageId(int stageId) {
        this.stageId = stageId;
    }
    /**
     * A function to return the length of the stage
     * @return stageLength
     */
    public double getStageLength() {
        return stageLength;
    }
    /**
     * A method to set the length of the stage
     * @param stageLength The stage length
     */
    public void setStageLength(int stageLength) {
        this.stageLength = stageLength;
    }
    /**
     * A function to return the stage type
     * @return type
     */
    public StageType getType() {
        return type;
    }
    /**
     * A method to set the stages type
     * @param type The stages type
     */
    public void setType(StageType type) {
        this.type = type;
    }
    /**
     * A function to return the start time of the stage
     * @return startTime
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }
    /**
     * A function to return the segments in the stage
     * @return segments
     */
    public HashMap<Integer, Segment> getSegments() {
        return segments;
    }
    /**
     * A function to return the description of the stage
     * @return description
     */
    public String getDescription() {
        return description;
    }
    /**
     * A method to set the description of the stage
     * @param description The stages description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * A function to return the stages state
     * @return stageState
     */
    public String getStageState() {
        return stageState;
    }
    /**
     * A method to set the state of the stage
     * @param stageState The new state of the stage
     */
    public void setStageState(String stageState) {
        this.stageState = stageState;
    }
    /**
     * A function to get the results of the stage
     * @return stageResults
     */
    public StageResults getStageResults() {

        return stageResults;
    }
    /**
     * A method to add a new segment to the segment hashmap
     * @param segmentId The segments ID
     * @param segment The segment object
     */
    public void addSegmentToSegmentHashmap(Integer segmentId , Segment segment){
        this.segments.put(segmentId, segment);
    }


    /**
     * A function to get the results for each segment object
     * @param riderId The riders id
     * @param checkpoints The checkpoints wanted
     */
    public void GetSegmentAndAddRiderResults(int riderId, LocalTime[] checkpoints){
        Collection<Segment> collection = segments.values();
        ArrayList<Segment> segmentArrayList = new ArrayList<>(collection);
        ArrayList<LocalTime> checkpointsArrayList= new ArrayList<>(Arrays.asList(checkpoints));

        checkpointsArrayList.remove(0);

            for (int i = 0; i < checkpointsArrayList.size() ; i++) {
                if (i + 1 < checkpointsArrayList.size()) {
                    LocalTime segmentTime = checkpointsArrayList.get(i);
                    segmentArrayList.get(i).getRidersResultsInSegment().add(new RiderResult(riderId, segmentTime));
                }
            }
    }
    /**
     * A method to push the segment points to a given rider in the hashmap in stageResults
     *
     */
    public void pushTotalSegmentPointsToStageResults(){
        for(Segment segment : segments.values()){
            segment.sortRiderResultsInSegment();
            if(segment instanceof CategorisedClimb){
                HashMap<Integer, Integer> riderIdAndPoints = ((CategorisedClimb) segment).calculateCategorisedClimbPoints(); //Hashmap of the  rider Id and points earned in the segment
                riderIdAndPoints.forEach((key , value) -> {
                    if(this.stageResults.getStageMountainPointsObtained().containsKey(key)){
                        stageResults.getStageMountainPointsObtained().put(key, stageResults.getStageMountainPointsObtained().get(key) + value);
                    }else{
                        stageResults.getStageMountainPointsObtained().put(key, value);
                    }
                });
            }else if(segment instanceof  IntermediateSprint){
                HashMap<Integer, Integer> riderIdAndPoints = ((IntermediateSprint) segment).calculatePointsIntermediateSprint();
                riderIdAndPoints.forEach((key , value) -> {
                    if(this.stageResults.getStagePointsObtained().containsKey(key)){
                        stageResults.getStagePointsObtained().put(key, stageResults.getStagePointsObtained().get(key) + value);
                    }else{
                        stageResults.getStagePointsObtained().put(key, value);
                    }
                });
            }

        }
    }
    /**
     * A method to calculate the riders points based of their finishing position in the stage and
     * put each rider's finish points in the stage results
     */
    public void pushFinishPointsToStageResults() {
        int[] flatFinish = {50,30,20,18,16,14,12,10,8,7,6,5,4,3,2};
        int[] hillyFinishOrMediumMountainFinish = {30,25,22,19,17,15,13,11,9,7,6,5,4,3,2};
        int[] highMountainOrIndividualTimeTrialFinish = {20,17,15,13,11,10,9,8,7,6,5,4,3,2,1};

        HashMap<Integer, Integer> riderIdAndPoints = new HashMap<>();
        int count = 0;
        int[] ridersRankByTime = this.stageResults.returnRankedRiderIdbyTime();

        try {
            for (int rider : ridersRankByTime) {
                if (this.type.equals(StageType.FLAT)) {
                    if (count < 14) {
                        riderIdAndPoints.put(rider, flatFinish[count]);
                        count++;
                    }
                } else if (this.type.equals(StageType.MEDIUM_MOUNTAIN)) {
                    if (count < 14) {
                        riderIdAndPoints.put(rider, hillyFinishOrMediumMountainFinish[count]);
                        count++;
                    }
                } else if (this.type.equals(StageType.HIGH_MOUNTAIN) || this.type.equals(StageType.TT)) {
                    if (count < 14) {
                        riderIdAndPoints.put(rider, highMountainOrIndividualTimeTrialFinish[count]);
                        count++;
                    }
                }
            }
        }catch(Exception e){
            System.out.println(e);
        }

        riderIdAndPoints.forEach((key , value) -> {
            if(this.stageResults.getStagePointsObtained().containsKey(key)){
                stageResults.getStagePointsObtained().put(key, stageResults.getStagePointsObtained().get(key) + value);
            }else{
                stageResults.getStagePointsObtained().put(key, value);
            }
        });

    }

    /**
     * A function to return the attributes of a stage instance in a string form
     * @return All the classes attributes
     */
    @Override
    public String toString() {
        return "Stage{" +
                "stageId=" + stageId +
                ", stageName='" + stageName + '\'' +
                ", stageLength=" + stageLength +
                ", type=" + type +
                ", startTime=" + startTime +
                ", segments=" + segments +
                ", description='" + description + '\'' +
                ", stageState='" + stageState + '\'' +
                ", stageResults=" + stageResults +
                '}';
    }


}

