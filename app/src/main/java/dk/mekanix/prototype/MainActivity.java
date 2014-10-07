package dk.mekanix.prototype;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import de.greenrobot.event.EventBus;
import dk.mekanix.data.Attribute;
import dk.mekanix.data.Data;
import dk.mekanix.data.PackRat;
import dk.mekanix.data.PopulateData;


public class MainActivity extends Activity
        implements SelectAttributeFragment.SelectAttributeFragmentComlink,
        SelectGraphFragment.SelectGraphFragmentComlink, CreateAttributeFragment.CreateAttributeFragmentComlink {

    private String TAG = "MainActivity";
    private Attribute[] mAttributes;
    private Attribute[] mSelectedAttributes;

    private final int CREATE_GRAPH_PATH = 0;
    private final int CREATE_ATTRIBUTE_PATH = 1;
    private int mPath = -1;

    //put into global variables
    private String SELECTED_GRAPH = "selected_graph";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment())
                    .commit();
        }

        //Populate mAttributes
        if (mAttributes == null) {
            PopulateData mPopulate = new PopulateData();
            Data mData;
            mAttributes = new Attribute[] {
                    new Attribute("running", new Data(mPopulate.getRunning())),
                    new Attribute("biking", new Data(mPopulate.getBiking())),
                    new Attribute("sleep", new Data(mPopulate.getSleep())),
                    new Attribute("pain", new Data(mPopulate.getPain())),
                    new Attribute("mood", new Data(mPopulate.getMood())),
            };

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void selectCreateGraph(View view) {
        mPath = CREATE_GRAPH_PATH;
        selectAttributes(view);
    }


    public void selectAttributes(View view) {

        //Put mAttributes into EventBus
        PackRat ratPackage = new PackRat(mAttributes);
        EventBus.getDefault().postSticky(ratPackage);

        //Create SelectAttributeFragment()
        SelectAttributeFragment mSelectAttributeFragment = new SelectAttributeFragment();
        FragmentTransaction transaction = MainActivity.this.getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, mSelectAttributeFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void createNewAttribute(View view) {
        mPath = CREATE_ATTRIBUTE_PATH;
        selectAttributes(view);

        Toast.makeText(this, "MainActivity - NewAttribute", Toast.LENGTH_SHORT).show();

    }

    private void configureAttribute() {
        //Put mAttributes into EventBus
        PackRat ratPackage = new PackRat();
        ratPackage.setSelectedAttributes(mSelectedAttributes);
        EventBus.getDefault().postSticky(ratPackage);

        //Create SelectAttributeFragment()
        CreateAttributeFragment mCreateAttributeFragment = new CreateAttributeFragment();
        FragmentTransaction transaction = MainActivity.this.getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, mCreateAttributeFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void selectAttributeComlink(Attribute[] mSelectedAttributes) {
        this.mSelectedAttributes = mSelectedAttributes;
        Toast.makeText(this, "Button Clicked - returned to Activity", Toast.LENGTH_SHORT).show();
        switch (mPath) {
            case CREATE_GRAPH_PATH:
                selectGraph();
                break;
            case CREATE_ATTRIBUTE_PATH:
                configureAttribute();
                break;
            default:
                Toast.makeText(this, "Error - selectAttribute", Toast.LENGTH_SHORT).show();
                break;
        }
        mPath = -1;
        //selectGraph();
    }

    private void selectGraph() {
        //Create SelectAttributeFragment()
        SelectGraphFragment mSelectGraphFragment = new SelectGraphFragment();
        FragmentTransaction transaction = MainActivity.this.getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, mSelectGraphFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void selectGraphComlink(String mSelectedGraph) {

        Toast.makeText(this, "Selected:"+mSelectedGraph, Toast.LENGTH_SHORT).show();

        //Put mAttributes and mSelectedAttributes into Eventbus
        PackRat ratPackage = new PackRat(mAttributes, mSelectedAttributes, mSelectedGraph);
        EventBus.getDefault().postSticky(ratPackage);

        Intent intent = new Intent(this, GraphActivity.class);
//        intent.putExtra("test", selectedGraph);
        startActivity(intent);

    }

    @Override
    public void createAttributeComlink(Attribute mSingleAttribute) {
        //TODO implement logic to enable updating existing Attributes rather than just add a new one.
        Attribute[] tmpAttributes = new Attribute[mAttributes.length+1];
        for (int i = 0; i < mAttributes.length; i++) {
            tmpAttributes[i] = mAttributes[i];
        }
        tmpAttributes[tmpAttributes.length-1] = mSingleAttribute;
        mAttributes = tmpAttributes;
        mPath = -1;

        //Create SelectAttributeFragment()
        MainFragment mMainFragment = new MainFragment();
        FragmentTransaction transaction = MainActivity.this.getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, mMainFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
