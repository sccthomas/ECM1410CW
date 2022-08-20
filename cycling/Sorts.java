package cycling;

import java.util.ArrayList;

/**
 *A class that performs sorts on passed in arraylist and returns them as int arrays
 */
public interface Sorts {
    /**
     *A function that performs a bubbleSort on an arraylist of RiderResult objects
     *based on the time contained within the object
     * @param arrayList The data being bubble sorted
     * @return int[]
     */
    default int[] bubbleSortRiderResultsByTime(ArrayList<RiderResult> arrayList){
        //Function to perform a bubble sort on the bases of time. On an array list of RiderResult objects
        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = i + 1; j < arrayList.size(); j++) {
                if (arrayList.get(i).getFinishTime().isAfter(arrayList.get(j).getFinishTime())) {
                    RiderResult R1 = arrayList.get(j);
                    arrayList.set(j, arrayList.get(i));
                    arrayList.set(i, R1);
                }
            }
        }
        ArrayList<Integer> returnArray = new ArrayList<>();
        for (RiderResult rider : arrayList){
            returnArray.add(rider.getPoints());
        }
        return returnArray.stream().mapToInt(Integer::intValue).toArray();
    }
    /**
     *A function that performs a bubbleSort on an arraylist of RiderResult objects
     *based on the points contained within the object
     * @param arrayList The data being sorted with a bubble sort
     * @return int[]
     */
    default int[] bubbleSortRiderResultsByPoints(ArrayList<RiderResult> arrayList) {
        //Function to perform a bubble sort on the bases of points. On an array list of RiderResult objects
        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = i + 1; j < arrayList.size(); j++) {
                if (arrayList.get(i).getPoints() > (arrayList.get(j).getPoints())) {
                    RiderResult R1 = arrayList.get(j);
                    arrayList.set(j, arrayList.get(i));
                    arrayList.set(i, R1);
                }
            }
        }
        ArrayList<Integer> returnArray = new ArrayList<>();
        for (RiderResult rider : arrayList){
            returnArray.add(rider.getRiderId());
        }
        return returnArray.stream().mapToInt(Integer::intValue).toArray();
    }
    /**
     *A function that performs an insertionSort on the Segment objects based on their location within the stage
     * @param arrayList The data having an insertion sort being performed on
     * @return int[]
     */
    default int[] segmentInsertionSort(ArrayList<Segment> arrayList){
        //A function to perform an insertion sort
        for (int i = 1; i < arrayList.size(); i++) {
            double key = arrayList.get(i).getLocation();
            int j = i - 1;
            while(j >= 0 && key < arrayList.get(j).getLocation()) {
                Segment temp = arrayList.get(j);
                arrayList.set(j, arrayList.get(j + 1));
                arrayList.set(j + 1, temp);
                j--;
            }
        }
        ArrayList<Integer> returnArrayList = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            returnArrayList.add(arrayList.get(i).getSegmentId());
        }
        return returnArrayList.stream().mapToInt(Integer::intValue).toArray();
    }
}
