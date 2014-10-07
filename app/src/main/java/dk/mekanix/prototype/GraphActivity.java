package dk.mekanix.prototype;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidplot.Plot;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;

import java.text.DecimalFormat;
import java.util.Arrays;

import de.greenrobot.event.EventBus;
import dk.mekanix.data.Attribute;
import dk.mekanix.data.PackRat;


public class GraphActivity extends Activity implements SimpleLineGraphFragment.SimpleLineGraphFragmentComlink,
        SelectAttributeFragment.SelectAttributeFragmentComlink {

    //put into global variables
    // String SELECTED_GRAPH = "selected_graph";
    private Attribute[] mAttributes;
    private Attribute[] mSelectedAttributes;
    private String mSelectedGraph;
    private String[] yScale = {"seconds","minutes", "hours", "days"};
    private String[] xScale = {"day", "week", "month"};
    private int selectedYScale = 0; //seconds
    private int selectedXScale = 0; //day

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new ViewGraphFragment())
                    .commit();
        }

        //get values from eventbus
        PackRat ratPackage = (PackRat) EventBus.getDefault().removeStickyEvent(PackRat.class); //removeSticky... getSticky instead?
        selectedXScale = ratPackage.getSelectedXScale();
        selectedYScale = ratPackage.getSelectedYScale();
        mSelectedAttributes = ratPackage.getSelectedAttributes();
        mAttributes = ratPackage.getAttributes();
        mSelectedGraph = ratPackage.getSelectedGraph();

        drawGraph();






    }

    private void drawGraph() {
        //TODO implement logic to handle various graph types.


        //Put mAttributes into EventBus
        PackRat ratPackage = new PackRat(mSelectedAttributes,selectedXScale,selectedYScale);
        EventBus.getDefault().postSticky(ratPackage);

        //Create SelectAttributeFragment()
        SimpleLineGraphFragment mSimpleLineGraphFragment = new SimpleLineGraphFragment();
        FragmentTransaction transaction = GraphActivity.this.getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, mSimpleLineGraphFragment);
        transaction.addToBackStack(null);
        //transaction.add(R.id.simple_line_graph, mSimpleLineGraphFragment);
        transaction.commit();

    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.graph, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.change_attributes:
                changeAttributes();
                return true;
            case R.id.change_x_scale:
                changeXScale();
                return true;
            case R.id.change_y_scale:
                changeYScale();
                return true;
//            case R.id.edit_custom_attributes:
//                editAttributes();
//                return true;
//            case R.id.change_graph_style:
//                changeGraphStyle();
//                return true;
//            case R.id.create_bookmark:
//                createBookmark;
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }



//        return super.onOptionsItemSelected(item);
    }

    private void changeYScale() {
        FragmentManager fm = getFragmentManager();
        YDialogFragment mDialog = new YDialogFragment();
        mDialog.show(fm, "fragment_edit_name");
    }

    private void changeXScale() {
        FragmentManager fm = getFragmentManager();
        XDialogFragment mDialog = new XDialogFragment();
        mDialog.show(fm, "fragment_edit_name");
    }

    private void changeAttributes() {

    //Put mAttributes into EventBus
    PackRat ratPackage = new PackRat(mAttributes);
    EventBus.getDefault().postSticky(ratPackage);

    //Create SelectAttributeFragment()
    SelectAttributeFragment mSelectAttributeFragment = new SelectAttributeFragment();
    FragmentTransaction transaction = GraphActivity.this.getFragmentManager().beginTransaction();
    transaction.replace(R.id.container, mSelectAttributeFragment);
    transaction.addToBackStack(null);
    transaction.commit();
    }

    @Override
    public void selectAttributeComlink(Attribute[] mSelectedAttributes) {
        this.mSelectedAttributes = mSelectedAttributes;
        Toast.makeText(this, "Button Clicked - returned to Activity", Toast.LENGTH_SHORT).show();
        //New attributes selected now redraw graph
        drawGraph();
        //selectGraph();
    }

    @Override
    public void simpleLineGraphComlink(Attribute[] mAttributes) {

    }

    public void onUserSelectXValue(int which) {
        selectedXScale = which;
        drawGraph();
    }

    public void onUserSelectYValue(int which) {
        selectedYScale = which;
        drawGraph();
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private XYPlot plot;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {


            // fun little snippet that prevents users from taking screenshots
            // on ICS+ devices :-)
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
//                    WindowManager.LayoutParams.FLAG_SECURE);

            //setContentView(R.layout.simple_xy_plot_example);
            View rootView = inflater.inflate(R.layout.simple_xy_plot_example, container, false);

            // initialize our XYPlot reference:
            plot = (XYPlot) rootView.findViewById(R.id.mySimpleXYPlot);

            plot.setBorderStyle(Plot.BorderStyle.NONE, null, null);
//            plot.setPlotMargins(0, 0, 0, 0);
//            plot.setPlotPadding(0, 0, 0, 0);
//            plot.setGridPadding(0, 10, 5, 0);

            plot.setBackgroundColor(Color.WHITE);

            //Trying to avoid overlap
            //plot.getLegendWidget().setSize(new SizeMetrics(15, SizeLayoutType.ABSOLUTE,200, SizeLayoutType.ABSOLUTE));


//            plot.getGraphWidget().position(
//                    0,
//                    XLayoutStyle.ABSOLUTE_FROM_LEFT,
//                    0,
//                    YLayoutStyle.RELATIVE_TO_CENTER,
////                    0,
////                    YLayoutStyle.ABSOLUTE_FROM_BOTTOM,
//                    AnchorPosition.LEFT_MIDDLE);

            plot.getGraphWidget().getBackgroundPaint().setColor(Color.WHITE);
            plot.getGraphWidget().getGridBackgroundPaint().setColor(Color.WHITE);

            plot.getGraphWidget().getDomainLabelPaint().setColor(Color.WHITE);
            plot.getGraphWidget().getRangeLabelPaint().setColor(Color.BLACK);

            plot.getGraphWidget().getDomainOriginLabelPaint().setColor(Color.BLACK);
            plot.getGraphWidget().getDomainOriginLinePaint().setColor(Color.BLACK);
            plot.getGraphWidget().getRangeOriginLinePaint().setColor(Color.BLACK);

            //plot.getBackgroundPaint().setColor(Color.TRANSPARENT);



//            plot.setTitle("This Is My Graph");
//            plot.getGraphWidget().getBackgroundPaint().setColor(Color.WHITE);
//            plot.getGraphWidget().getGridBackgroundPaint().setColor(Color.WHITE);
            //plot.getGraphWidget().

            // Create a couple arrays of y-values to plot:
            Number[] series1Numbers = {1, 8, 5, 2, 7, 4};
            Number[] series2Numbers = {4, 6, 3, 8, 2, 10};

            // Turn the above arrays into XYSeries':
            XYSeries series1 = new SimpleXYSeries(
                    Arrays.asList(series1Numbers),          // SimpleXYSeries takes a List so turn our array into a List
                    SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                    "Series1");                             // Set the display title of the series

            // same as above
            XYSeries series2 = new SimpleXYSeries(Arrays.asList(series2Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series2");

            // Create a formatter to use for drawing a series using LineAndPointRenderer
            // and configure it from xml:
            LineAndPointFormatter series1Format = new LineAndPointFormatter();
            series1Format.setPointLabelFormatter(new PointLabelFormatter());
            series1Format.configure(getActivity(),
                    R.xml.line_point_formatter_with_plf1);

            // add a new series' to the xyplot:
            plot.addSeries(series1, series1Format);

            // same as above:
            LineAndPointFormatter series2Format = new LineAndPointFormatter();
            series2Format.setPointLabelFormatter(new PointLabelFormatter());
            series2Format.configure(getActivity(),
                    R.xml.line_point_formatter_with_plf2);
            plot.addSeries(series2, series2Format);

            // reduce the number of range labels
            //plot.setTicksPerRangeLabel(3);
            plot.getGraphWidget().setDomainLabelOrientation(-45);
            plot.getGraphWidget().getDomainLabelPaint().setColor(Color.BLACK);

            //plot.getLegendWidget().getBackgroundPaint().setColor(Color.WHITE);
            plot.getLegendWidget().getTextPaint().setColor(Color.BLACK);
            //plot.getLegendWidget().getBackgroundPaint().setColor(Color.WHITE);


            //Remove legend
            //plot.getLayoutManager().remove(plot.getLegendWidget());
            //plot.getLayoutManager().remove(plot.getDomainLabelWidget());
            //plot.getLayoutManager().remove(plot.getRangeLabelWidget());
            plot.getLayoutManager().remove(plot.getTitleWidget());


            plot.setDomainStep(XYStepMode.INCREMENT_BY_VAL,series1Numbers.length);
            plot.setDomainValueFormat(new DecimalFormat("0"));
            plot.setDomainStepValue(1);

            //This gets rid of the gray grid
            plot.getGraphWidget().getGridBackgroundPaint().setColor(Color.WHITE);

//This gets rid of the black border (up to the graph) there is no black border around the labels
            plot.getBackgroundPaint().setColor(Color.WHITE);

//This gets rid of the black behind the graph
            plot.getGraphWidget().getBackgroundPaint().setColor(Color.WHITE);

            plot.setBorderPaint(null);
            plot.setPlotMargins(0, 0, 0, 0);


            //plot.setMarkupEnabled(true);




            //View rootView = inflater.inflate(R.layout.fragment_graph, container, false);
            return rootView;
        }
    }
}
