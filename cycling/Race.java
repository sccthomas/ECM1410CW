package cycling;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class for creating a race
 * Contains methods for storing attributes of the race and retrieving the results from stages
 */
public class Race implements Serializable {

    /**Hashmap that contains all the riders present in the race: riderId and Rider object*/
    HashMap<Integer,Rider> riders;
    /**Hashmap that contains all the stages present in the race: stageIds and Stage object*/
    HashMap<Integer, Stage> stages;
    /**The name of the race*/
    private String raceName;
    /**The unique Id of the race*/
    private int raceId;
    /**The description of the race*/
    private String description;

    /**
     * A constructor for creating a new race instance
     * @param raceName The name of the race.
     * @param raceId The ID of the race.
     * @param description The description of the race.
     */
    public Race(String raceName, int raceId, String description) {
        this.riders = new HashMap<Integer,Rider>();
        this.stages = new HashMap<Integer,Stage>();
        this.raceName = raceName;
        this.raceId = raceId;
        this.description = description;
    }

    /**
     * Function to return the race objects attributes.
     * @return A string that contains all the attributes of the race object.
     */
    @Override
    public String toString() {

        double totalLength = 0;
        for (Stage stage : stages.values()){
            totalLength += stage.getStageLength();
        }
        return "\nRace:" +
                "  \nraceId =" + raceId +
                ", \nraceName ='" + raceName + '\'' +
                ", \ndescription ='" + description + '\'' +
                ", \nnumber of stages =" + stages.size() +
                ", \ntotal Length =" + totalLength ;
    }
    /**
     * Function to return the race's name.
     * @return The race objects name attribute.
     */
    public String getRaceName(){

        return this.raceName;
    }
    /**
     * Function to return the race's Id.
     * @return The races ID.
     */
    public int getRaceId(){

        return this.raceId;
    }
    /**
     * Function to return the stages of a race.
     * @return A hashmap of the stages contained in the race.
     */
    public HashMap<Integer,Stage> getStages(){

        return this.stages;
    }
    /**
     * Function to add a stage object to the hashmap of stages in the object.
     * @param stage A new stage to be added to the stage attribute.
     */
    public void addStageToStages(Stage stage){

        this.stages.put(stage.getStageId(), stage);
    }
    /**
     * A function to get all riders in the race, points or mountain points.
     * @param isMountainPoints Boolean to whether to return the mountain points or just normal points.
     * @return riderResultsArray, an array of objects and containing riderId, and races points.
     */
    public ArrayList<RiderResult> getRidersPointsOrMountainPointsInRace(boolean isMountainPoints){

        ArrayList<RiderResult> riderResultsArray = new ArrayList<>();

        for (Stage stage : this.getStages().values()){
            stage.getStageResults().clearAllPointsObtained();
            stage.pushTotalSegmentPointsToStageResults();
            stage.pushFinishPointsToStageResults();

            StageResults results = stage.getStageResults();
            int [] ridersIDs = results.returnRankedRiderIdbyTime();
            for (int riderId: ridersIDs){

                ArrayList<LocalTime> timeArrayList = results.getStageTimesObtained().get(riderId);
                LocalTime finishTime = timeArrayList.get(timeArrayList.size() -1);
                int points = 0;

                if(!isMountainPoints){
                    points = results.getStagePointsObtained().get(riderId);
                }else if(isMountainPoints){
                    if(results.getStageMountainPointsObtained().containsKey(riderId)){
                        points = results.getStageMountainPointsObtained().get(riderId);
                    }else{
                        points = 0;
                    }
                }
                boolean inArray = false;
                for (RiderResult riderResult : riderResultsArray){
                    if(riderResult.getRiderId() == riderId){
                        //Adding points
                        riderResult.setPoints(riderResult.getPoints() + points);
                        //creating the new time
                        LocalTime originalTime = riderResult.getFinishTime();
                        LocalTime newTime = originalTime.plusHours(finishTime.getHour())
                                .plusMinutes(finishTime.getMinute()).plusSeconds(finishTime.getSecond());
                        riderResult.setFinishTime(newTime);
                        inArray = true;
                        break;
                    }
                }
                if(!inArray){
                    riderResultsArray.add(new RiderResult(riderId,finishTime,points));
                }
            }
        }
        return riderResultsArray;
    }
    /**
     * Function that creates an ArrayList of RiderResult objects and accumulates each riders elapsed times from all stages
     * @return RiderResult arraylist sorted by time
     */
    public ArrayList<RiderResult> getGeneralClassificationInRace(){


        ArrayList<RiderResult> riderRankedArray = new ArrayList<>();

        for (Stage stage : this.getStages().values()){
            StageResults stageResults = stage.getStageResults();
            int [] ridersIDs = stageResults.returnRankedRiderIdbyTime();
            for (int riderId: ridersIDs){
                boolean inArray = false;
                LocalTime elapsedTime = stageResults.returnRiderIdAndAdjustedElapsedTime(riderId).getFinishTime();
                for (RiderResult riderResult : riderRankedArray){
                    if(riderResult.getRiderId() == riderId){
                        //creating the new time
                        LocalTime originalTime = riderResult.getFinishTime();
                        LocalTime newTime = originalTime.plusHours(elapsedTime.getHour())
                                .plusMinutes(elapsedTime.getMinute()).plusSeconds(elapsedTime.getSecond());
                        riderResult.setFinishTime(newTime);
                        inArray = true;
                        break;
                    }
                }
                if(!inArray){
                    riderRankedArray.add(new RiderResult(riderId, elapsedTime));
                }
            }
        }

        try{
            return bubbleSortRiderResultsByTime(riderRankedArray);
        }catch(Exception e){
            return new ArrayList<RiderResult>();
        }
    }
    /**
     * Function to perform a bubble sort on an arraylist of RiderResult objects based on the finish time within the object
     * @return A sorted arraylist of RiderResult
     * @param arrayList RiderResult arraylist
     */
    private ArrayList<RiderResult> bubbleSortRiderResultsByTime(ArrayList<RiderResult> arrayList){

        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = i + 1; j < arrayList.size(); j++) {
                if (arrayList.get(i).getFinishTime().isAfter(arrayList.get(j).getFinishTime())) {
                    RiderResult R1 = arrayList.get(j);
                    arrayList.set(j, arrayList.get(i));
                    arrayList.set(i, R1);
                }
            }
        }

        return arrayList;

    }


}
