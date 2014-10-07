package dk.mekanix.data;

import java.util.ArrayList;

/**
 * Created by mekanix on 18/09/14.
 */
public class PackRat {

    private Attribute[] mAttributes;
    private Attribute[] mSelectedAttributes;
    private Attribute mSingleAttribute;
    private String mSelectedGraph;
    private int selectedXScale;
    private int selectedYScale;

    public PackRat () {
        super();
    }

    public PackRat (Attribute[] mAttributes) {
        super();
        this.mAttributes =mAttributes;
    }

    public PackRat(Attribute[] mAttributes, Attribute[] mSelectedAttributes, String mSelectedGraph) {
        super();
        this.mAttributes = mAttributes;
        this.mSelectedAttributes = mSelectedAttributes;
        this.mSelectedGraph = mSelectedGraph;
    }

    public PackRat(Attribute[] mSelectedAttributes, String mSelectedGraph, int selectedXScale, int selectedYScale) {
        super();
        this.mSelectedAttributes = mSelectedAttributes;
        this.mSelectedGraph = mSelectedGraph;
        this.selectedXScale = selectedXScale;
        this.selectedYScale = selectedYScale;
    }

    public PackRat(Attribute[] mSelectedAttributes, int selectedXScale, int selectedYScale) {
        super();
        this.mSelectedAttributes = mSelectedAttributes;
        this.selectedXScale = selectedXScale;
        this.selectedYScale = selectedYScale;
    }

    public Attribute[] getAttributes() { return mAttributes; }

    public void setSelectedAttributes(Attribute[] mSelectedAttributes) {
        this.mSelectedAttributes = mSelectedAttributes;
    }

    public Attribute[] getSelectedAttributes() { return mSelectedAttributes; }

    public String getSelectedGraph() { return mSelectedGraph; }


    public int getSelectedXScale() {
        return selectedXScale;
    }

    public int getSelectedYScale() {
        return selectedYScale;
    }

    public Attribute[] getmAttributes() {
        return mAttributes;
    }

    public void setmAttributes(Attribute[] mAttributes) {
        this.mAttributes = mAttributes;
    }

    public Attribute[] getmSelectedAttributes() {
        return mSelectedAttributes;
    }

    public void setmSelectedAttributes(Attribute[] mSelectedAttributes) {
        this.mSelectedAttributes = mSelectedAttributes;
    }

    public String getmSelectedGraph() {
        return mSelectedGraph;
    }

    public void setmSelectedGraph(String mSelectedGraph) {
        this.mSelectedGraph = mSelectedGraph;
    }

    public void setSelectedXScale(int selectedXScale) {
        this.selectedXScale = selectedXScale;
    }

    public void setSelectedYScale(int selectedYScale) {
        this.selectedYScale = selectedYScale;
    }

    public Attribute getSingleAttribute() {
        return mSingleAttribute;
    }

    public void setSingleAttribute(Attribute mSingleAttribute) {
        this.mSingleAttribute = mSingleAttribute;
    }
}
