package dk.mekanix.data;

/**
 * Created by mekanix on 17/09/14.
 */
public class Data {

    private int[] data;

    public Data () {
        super();
    }

    public Data (int[] data) {
        super();
        this.data = data;
    }

    public int getValue(int x_position) {
        return data[x_position];
    }

    public int getNumberOfData() {
        return data.length;
    }

//    public static int getValue(String attribute, int x_position) {
//
//        return 0;
//    }
}
