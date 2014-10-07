package dk.mekanix.prototype;



import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYSeriesFormatter;
import com.androidplot.xy.XYStepMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import dk.mekanix.data.Attribute;
import dk.mekanix.data.PackRat;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class SimpleLineGraphFragment extends Fragment {

    private String TAG = "SimpleLineGraphFragment";

    private SimpleLineGraphFragmentComlink mCallback;
    private Attribute[] mSelectedAttributes;
    private int selectedYScale;
    private int selectedXScale;
    private XYPlot mPlot;


    public SimpleLineGraphFragment() {
        // Required empty public constructor
    }

    // Container Activity must implement this interface
    public interface SimpleLineGraphFragmentComlink {
        public void simpleLineGraphComlink(Attribute[] mAttributes);
    }

    /**
     * This method ensures that the calling activity have implemented the callback interface
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (SimpleLineGraphFragmentComlink) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement SimpleLineGraphFragmentComlink");
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Retrieve mAttributes
        PackRat ratPackage = (PackRat) EventBus.getDefault().removeStickyEvent(PackRat.class); //removeSticky... getSticky instead?
        selectedXScale = ratPackage.getSelectedXScale();
        selectedYScale = ratPackage.getSelectedYScale();
        mSelectedAttributes = ratPackage.getSelectedAttributes();

        //inflate view
        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.simple_line_graph, container, false);

        mPlot = (XYPlot) view.findViewById(R.id.simple_line_graph);

        if (mSelectedAttributes == null) {
            Log.w(TAG, "mSelectedAttributes is null");
        };

        for (int i=0; i<mSelectedAttributes.length; i++) {
            mPlot.addSeries(getSeries(mSelectedAttributes[i]),getSeriesFormat(i));
        }

        switch (selectedXScale) {
            //Days
            case 0:
                mPlot.setDomainLabel("Days");
                mPlot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 7);
                break;
            //Weeks
            case 1:
                mPlot.setDomainLabel("Weeks");
                mPlot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1);
                break;
            //Month
            case 2:
                mPlot.setDomainLabel("Months");
                mPlot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1);
                break;
            default:
                break;
        }

        double[] yScaleMultiplier = {10, 5, 5};
        switch (selectedYScale) {
            case 0:
                mPlot.setRangeLabel("Seconds");
                mPlot.setRangeStep(XYStepMode.INCREMENT_BY_VAL, 100*yScaleMultiplier[selectedXScale]);
                mPlot.setRangeValueFormat(new DecimalFormat("#"));
                break;
            case 1:
                mPlot.setRangeLabel("Minutes");
                mPlot.setRangeStep(XYStepMode.INCREMENT_BY_VAL, 1*yScaleMultiplier[selectedXScale]);
                mPlot.setRangeValueFormat(new DecimalFormat("#"));
                break;
            case 2:
                mPlot.setRangeLabel("Hours");
                mPlot.setRangeStep(XYStepMode.INCREMENT_BY_VAL, 0.01*yScaleMultiplier[selectedXScale]);
                mPlot.setRangeValueFormat(new DecimalFormat("0.0#"));
                break;
            default:
                break;
        }


//        mPlot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 7);

        // get rid of the decimal place on the display:
        mPlot.setDomainValueFormat(new DecimalFormat("#"));

        mPlot.setRangeLowerBoundary(0, BoundaryMode.FIXED);



        return view;
    }

    private XYSeries getSeries(Attribute mSelectedAttribute) {
        float[] divisor = {1,60,3600}; //!!!!! skal bruges
        int[] stepper = {1,7,30};
        int lengthOfArray = mSelectedAttribute.getNumberOfData();

        int newArrayLength = lengthOfArray / stepper[selectedXScale];
        int remainder = lengthOfArray % stepper[selectedXScale];
        if (remainder > 0) {
            newArrayLength = newArrayLength + stepper[selectedXScale] - remainder ;
        }

        float[] numbers = new float[newArrayLength];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = 0;
            if ( (i+1) * stepper[selectedXScale] > lengthOfArray) {
                for (int j = i*stepper[selectedXScale]; j < newArrayLength; j++) {
                    numbers[i] = numbers[i] + mSelectedAttribute.getValue(j);
                    Log.w("Calculating A", Float.toString(numbers[i]) + ">>" + i + "," + j);
                }
                numbers[i] =  numbers[i] / (float) (stepper[selectedXScale] - remainder);
            } else {
                for (int j = i * stepper[selectedXScale]; j < (i+1) * stepper[selectedXScale] ; j++) {
                    numbers[i] = numbers[i] + mSelectedAttribute.getValue(j);
                    Log.w("Calculating B", Float.toString(numbers[i])+">>"+i+","+j);
                }
                numbers[i] = numbers[i] / (float) stepper[selectedXScale];
            }
            numbers[i] = numbers[i] / divisor[selectedYScale];
            Log.w("Final:", Float.toString(numbers[i]));
        }

        //float[] array = (float[]) value;
        List<Float> result = new ArrayList<Float>(numbers.length);
        for (float f : numbers) {
            result.add(Float.valueOf(f));
        }

        // Turn the above arrays into XYSeries':
        XYSeries returnObject = new SimpleXYSeries(
                result,          // SimpleXYSeries takes a List so turn our array into a List
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                mSelectedAttribute.getLabel());


//        XYSeries returnObject = new SimpleXYSeries(al,SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,mSelectedAttribute.getLabel());
        return returnObject;
    }

    private XYSeriesFormatter getSeriesFormat(int number) {
        int[] mColor = {Color.GREEN, Color.RED, Color.BLUE, Color.YELLOW, Color.CYAN, Color.MAGENTA};
        LineAndPointFormatter mFormatter = new LineAndPointFormatter();
        mFormatter.setPointLabelFormatter(new PointLabelFormatter());
        mFormatter.configure(getActivity(),R.xml.line_point_formatter_with_plf1);
        mFormatter.getLinePaint().setColor(mColor[number]);
        mFormatter.getPointLabelFormatter().getTextPaint().setColor(Color.TRANSPARENT);
        return mFormatter;
    }


}
