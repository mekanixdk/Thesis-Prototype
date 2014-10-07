package dk.mekanix.data;

import android.util.Log;

/**
 * Created by mekanix on 17/09/14.
 */
public class Attribute {

    private String label;
    private Attribute[] containsAttributes;
    private int[] weightOfAttributes;
    //private boolean isChecked = false; //Set default to unchecked.
    private boolean isSum = true; //false == average, true == sum
    //private boolean isDefault = false;
    private Data data; //if data = null --> default data.
    private String TAG = "Attribute.class";


    public Attribute () {
        super();
    }

    public Attribute (String label, Data data) {
        super();
        this.label = label;
        this.data = data;
    }

    public Attribute (String label, Attribute[] containsAttributes) {
        super();
        this.label = label;
        this.containsAttributes = containsAttributes;
        //No weight assigned --> set default to 1
        weightOfAttributes = new int[containsAttributes.length];
        for (int i=0; i < containsAttributes.length;i++){
            weightOfAttributes[i] = 1;
        }
    }

    public Attribute (String label, Attribute[] containsAttributes, int[] weightOfAttributes) {
        super();
        this.label = label;
        this.containsAttributes = containsAttributes;
        this.weightOfAttributes = weightOfAttributes;
    }

//    public Attribute (String label, String[] containsAttributes, int[] weightOfAttributes, boolean isChecked ) {
//        super();
//        this.label = label;
//        this.containsAttributes = containsAttributes;
//        this.weightOfAttributes = weightOfAttributes;
//        this.isChecked = isChecked;
//    }

    public int getNumberOfData() {

        if(data == null) {
            return containsAttributes[0].getNumberOfData();
        }
        return data.getNumberOfData();

    }

    public int getNumberOfAttributes() {
        return containsAttributes.length;
    }

    public String getLabel () {
      return label;
    }

    public void setLabel (String label) {
        this.label = label;
    }

    public int getWeight (int attributeNumber) {
        if (data != null) {
            Log.w(TAG, "default attribute - return 1");
            return 1;
        }
        Log.w(TAG, "non-default attribute");
        return weightOfAttributes[attributeNumber];
    }

    public int[] getWeight () {
        return weightOfAttributes;
    }

    public void setWeight (int attributeNumber, int attributeWeight) {
        this.weightOfAttributes[attributeNumber] = attributeWeight;
    }

    //Logic to handle y(x)
    public float getValue (int x_position) {
        //if data != null we have a default attribute with Data-object
        if (data != null) {
            return data.getValue(x_position);
        }
        float value = 0;
        int average = 0;
        int attributeLength = containsAttributes.length;
        for (int i = 0; i < attributeLength; i++) {
            value = value + (containsAttributes[i].getValue(x_position)) * weightOfAttributes[i] ;

        }
        if (!isSum) {
            value = value / containsAttributes.length;
        }
        return value;
    }

//    public boolean isChecked() {
//        return isChecked;
//    }
//    public void setChecked(boolean checked) {
//        this.isChecked = isChecked;
//    }

    public String toString() {
        return label ;
    }

    public String getAttributeLabel(int i) {
        return containsAttributes[i].getLabel();
    }


//    public void toggleChecked() {
//        isChecked = !isChecked ;
//    }





}
