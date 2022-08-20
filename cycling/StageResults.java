package cycling;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class for managing and storing the results of a stage
 *Contains methods and attributes for calculating, storing and retrieving stage results
 */
public class StageResults implements Serializable {
    /**A hashmap for the stage times obtained by the riders*/
    private HashMap<Integer, ArrayList<LocalTime>> stageTimesObtained = new HashMap<Integer, ArrayList<LocalTime>>();
    /**A hashmap for the stage points obtained by the riders*/
    private HashMap<Integer, Integer> stagePointsObtained = new HashMap<Integer, Integer>();
    /**A hashamp for the stages mountain points obtained by the ridsers*/
    private HashMap<Integer, Integer> stageMountainPointsObtained = new HashMap<Integer, Integer>();

    /**
     * A method to perform a bubble sort on an arraylist containing times
     * @param arrayList
     */
    public void bubbleSort (ArrayList < LocalTime > arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = i + 1; j < arrayList.size(); j++) {
                if (arrayList.get(i).isAfter(arrayList.get(j))) {
                    LocalTime holder = arrayList.get(j);
                    arrayList.set(j, arrayList.get(i));
                    arrayList.set(i, holder);
                }
            }
        }
    }

    /**
     * A method to clear the points obtained in the hashmaps above
     *
     */
    public void clearAllPointsObtained(){
        stagePointsObtained.clear();
        stageMountainPointsObtained.clear();
    }
    /**
     * A function to return the results of the stage
     * @return stageTimesObtained
     */
    public StageResults() {
        this.stageTimesObtained = stageTimesObtained;
    }
    /**
     * A function to return the stage points obtained
     * @return stagePointsObtained
     */
    public HashMap<Integer, Integer> getStagePointsObtained() {
        return stagePointsObtained;
    }
    /**
     * A function to return the mountain points obtained in the stages results
     * @return stageMountainPointsObtained
     */
    public HashMap<Integer, Integer> getStageMountainPointsObtained() {
        return stageMountainPointsObtained;
    }
    /**
     * A method to add a result to the times obtained hashamp
     * @param riderID The ID of the rider
     * @param time The time they achieved in the stage
     */
    public void addResultToTimesObtained(int riderId , LocalTime[] time) throws DuplicatedResultException {
        if(stageTimesObtained.containsKey(riderId)){
            throw new DuplicatedResultException();
        }else{
            ArrayList<LocalTime> times = new ArrayList<LocalTime>();
            for (int i = 0; i < time.length ; i++) {
                times.add(time[i]);
            }
            //Adding the elapsed time to the arrayList
            LocalTime startTime = times.get(0);
            LocalTime finishTime = times.get(times.size()-1);
            Long difference = Duration.between(startTime, finishTime).toSeconds();
            String hours   = String.valueOf( (int) Math.floor(difference / 3600));
            String minutes = String.valueOf((int)Math.floor((difference - (Integer.parseInt(hours) * 3600)) / 60));
            String seconds = String.valueOf((int) Math.floor(difference - (Integer.parseInt(hours) * 3600) - (Integer.parseInt(minutes) * 60)));

            if (Integer.valueOf(hours)   < 10) {hours   = "0"+hours;}
            if (Integer.valueOf(minutes) < 10) {minutes = "0" + minutes;}
            if (Integer.valueOf(seconds) < 10) {seconds = "0"+seconds;}

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime appendElapsedTime = LocalTime.parse(String.valueOf(hours)+":"+String.valueOf(minutes)+":"+String.valueOf(seconds) , formatter);

            times.add(appendElapsedTime);

            stageTimesObtained.put(riderId , times);
        }
    }
    /**
     * A function to return the times obtained in its stage
     * @return stageTimesObtained
     */
    public HashMap<Integer, ArrayList<LocalTime>> getStageTimesObtained() {
        return stageTimesObtained;
    }
    /**
     * A function to return a riders time in the stage
     * @param riderId The rider ID
     * @return LocalTime[0]
     */
    public LocalTime[] returnTimeResults(int riderId){
        if(stageTimesObtained.containsKey(riderId)){
            return (LocalTime[]) stageTimesObtained.get(riderId).stream().toArray(LocalTime[]::new);
        }else{
            return new LocalTime[0];
        }

    }
    /**
     * A function to return the riders rank in the stage
     * @param stagePoints The riders points in the stage
     * @return int[] of rankedListOfPoints
     */
    public int[] getRankedRidersPointsInStage(HashMap<Integer, Integer> stagePoints){
        ArrayList<Integer> rankedListOfPoints = new ArrayList<>();
        int[] rankedIds = returnRankedRiderIdbyTime();
        for(Integer rankedId : rankedIds){
            rankedListOfPoints.add(stagePoints.get(rankedId));
        }

        return rankedListOfPoints.stream().mapToInt(Integer::intValue).toArray();
    }
    /**
     * A method to remove a riders results from the stage
     * @param riderId The rider being removed
     */
    public void removeRiderResult(int riderId){
        stageTimesObtained.remove(riderId);

    }
    /**
     * A function to return the rank of all riders in the stage on the bases of time
     * @return int[] of riders ranks ordered
     */
    public int[] returnRankedRiderIdbyTime(){
        HashMap<Integer, LocalTime> returnMap = new HashMap<>();
        stageTimesObtained.forEach((key, value) ->{
            returnMap.put(key, value.get(value.size() - 1));
        });


        for (Integer name: returnMap.keySet()) {
            String key = name.toString();
            String value = returnMap.get(name).toString();
        }

        ArrayList<Integer> returnArray = new ArrayList<>();

        ArrayList<LocalTime> allRiderTimes = new ArrayList<>();
        ArrayList<Integer> allRiderIds = new ArrayList<>();

        for(LocalTime time : returnMap.values()){
            allRiderTimes.add(time);
        }
        for (Integer ids : returnMap.keySet()){
            allRiderIds.add(ids);
        }

        for (int i = 0; i < allRiderTimes.size(); i++) {
            for (int j = i + 1; j < allRiderTimes.size(); j++) {
                if (allRiderTimes.get(i).isAfter(allRiderTimes.get(j))) {

                    LocalTime timeHolder = allRiderTimes.get(j);
                    Integer IdHolder = allRiderIds.get(j);

                    allRiderTimes.set(j, allRiderTimes.get(i));
                    allRiderIds.set(j, allRiderIds.get(i));

                    allRiderTimes.set(i, timeHolder);
                    allRiderIds.set(i , IdHolder);
                }
            }
        }

        return allRiderIds.stream().mapToInt(Integer::intValue).toArray();
    }
    /**
     * A function to return the riderId and their adjusted elapsed time in the stage
     * @param riderId the riders ID
     * @return RiderResult
     */
    public RiderResult returnRiderIdAndAdjustedElapsedTime(int riderId) {
        ArrayList<LocalTime> allRiderTimes = new ArrayList<>();

        //The riders time which we wish to adjust
        LocalTime[] riderTimes = this.returnTimeResults(riderId);
        LocalTime riderFinishTimeInStage = riderTimes[riderTimes.length - 1]; //get the finish time in the stage for a rider

        //Retrieving all other rider
        for (ArrayList<LocalTime> times : this.getStageTimesObtained().values()) {
            allRiderTimes.add(times.get(times.size() - 1));
        }

        //Only adding id it is after the chosen time
        ArrayList<LocalTime> allRiderTimesV2 = new ArrayList<>();
        for (int i = 0; i < allRiderTimes.size(); i++) {
            if (riderFinishTimeInStage.isAfter(allRiderTimes.get(i)) || riderFinishTimeInStage.equals(allRiderTimes.get(i))) {
                allRiderTimesV2.add(allRiderTimes.get(i));
            }
        }
        allRiderTimes = allRiderTimesV2;

        //bubble sorting the arraylist
        bubbleSort(allRiderTimes);

        for (int i = allRiderTimes.size() - 2; i >= 0; i--) {
            riderFinishTimeInStage = riderFinishTimeInStage.minusSeconds(1);
            if (!riderFinishTimeInStage.equals(allRiderTimes.get(i))) {
                riderFinishTimeInStage = riderFinishTimeInStage.plusSeconds(1);
                break;
                }
            }
        return new RiderResult(riderId , riderFinishTimeInStage);
    }


}




