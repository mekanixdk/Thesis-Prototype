package dk.mekanix.data;

/**
 * Created by mekanix on 22/09/14.
 */
public class PopulateData {

    public PopulateData() {
    }

    public int[] getRunning () {
        int[] mArray = {
                0,0,4769,1458,4663,2407,0,0,0,0,6399,0,0,1878,0,0,2085,0,7339,1781,0,0,0,8534,0,0,0,0};
        return mArray;
    }

    public int[] getSleep() {
        int[] mArray = {
                3,3,3,3,2,2,5,2,3,2,2,3,3,3,4,2,3,3,2,3,1,2,3,4,5,4,5,3};
        return mArray;
    }

    public int[] getPain() {
        int[] mArray = {
                2,1,2,1,2,1,1,2,2,1,1,1,1,2,1,1,2,1,2,2,2,1,2,2,2,1,1,1};
        return mArray;
    }

    public int[] getMood() {
        int [] mArray = {
                3,3,3,3,2,2,3,3,2,3,2,3,3,3,2,3,3,4,2,3,3,2,2,3,3,3,4,3
        };
        return mArray;
    }

    public int[] getBiking() {
        int [] mArray = {
                0,0,0,0,0,0,0,1556,1461,1368,0,0,0,0,2829,3443,0,6959,0,0,0,1517,1715,0,0,2866,0,0
        };
        return mArray;
    }

}
