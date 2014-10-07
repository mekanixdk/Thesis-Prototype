package dk.mekanix.prototype;



import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import de.greenrobot.event.EventBus;
import dk.mekanix.data.Attribute;
import dk.mekanix.data.PackRat;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class SelectAttributeFragment extends Fragment {

    private Attribute[] mAttributes;
    private Attribute[] mSelectedAttributes;
    private String TAG = "SelectAttributeFragment";
    private CheckBox[] checkBoxes;
    private int checkboxIds = 1000;
    private boolean[] mCheckboxIsChecked;
    private SelectAttributeFragmentComlink mCallback;

    public SelectAttributeFragment() {
        // Required empty public constructor
    }

    // Container Activity must implement this interface
    public interface SelectAttributeFragmentComlink {
        public void selectAttributeComlink(Attribute[] mAttributes);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Retrieve mAttributes
        PackRat ratPackage = (PackRat) EventBus.getDefault().removeStickyEvent(PackRat.class); //removeSticky... getSticky instead?
        mAttributes = ratPackage.getAttributes();
        mSelectedAttributes = ratPackage.getSelectedAttributes();

        //LinearLayout may prompt issues in the future - ie. how to scroll.
        //Inflate view
        RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.fragment_select_attribute, container, false);

        LinearLayout lview = (LinearLayout) view.findViewById(R.id.select_attribute_ll);

        //Create the checkboxes
        mCheckboxIsChecked = new boolean[mAttributes.length];
        checkBoxes = new CheckBox[mAttributes.length];
        for (int i=0; i < mAttributes.length; i++) {
            checkBoxes[i] = new CheckBox(getActivity());
            checkBoxes[i].setText(mAttributes[i]+", Checkbox nr "+i);
            //Test whether I need to apply pre-checked boxes
            //If mSelectedAttributes == null create default pre-checked boxes
            if (mSelectedAttributes == null) {
                if (i == 0) {
                    mCheckboxIsChecked[i] = true;
                    checkBoxes[i].setChecked(mCheckboxIsChecked[i]);
                }
                else {
                    mCheckboxIsChecked[i] = false;
                    checkBoxes[i].setChecked(mCheckboxIsChecked[i]);
                }

            } else {
                //Logic to set pre-selection according to mSelectedAttributes
            }
            //Check whether each checkbox is pre-checked
//            if(mAttributes[i].isChecked()) {
//                checkBoxes[i].setChecked(true);
//            }
            checkBoxes[i].setId(checkboxIds++);
            lview.addView(checkBoxes[i]);
            final int j = i; //?!? In some context getId() needs to be final int?
            checkBoxes[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    mCheckboxIsChecked[j] = !mCheckboxIsChecked[j];
                    Toast.makeText(getActivity(),"CheckId"+checkBoxes[j].getId(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        //Catch the onClick listener so we can manage it here in the fragment
        Button mButton = (Button) view.findViewById(R.id.select_attribute_button);
        mButton.setText("Next");
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(),"Button Clicked", Toast.LENGTH_SHORT).show();
                //Return mAttributes with selected mAttributes.
                mCallback.selectAttributeComlink(generateSelectedAttributes());
                //mCallback.selectAttributeComlink(mAttributes);
            }
        });


        return view;
    }

    private Attribute[] generateSelectedAttributes() {
        int numberOfCheckedBoxes = 0;
        //Count number of selected attributes
        for (int i = 0; i < mAttributes.length; i++) {
            if (mCheckboxIsChecked[i]) { numberOfCheckedBoxes++; }
        }
        mSelectedAttributes = new Attribute[numberOfCheckedBoxes];
        int count = 0;
        for (int i = 0; i < mAttributes.length; i++) {
            if (mCheckboxIsChecked[i]) {
                mSelectedAttributes[count] = mAttributes[i];
                count++;
            }
        }
        return mSelectedAttributes;
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
            mCallback = (SelectAttributeFragmentComlink) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement SelectAttributeFragmentComlink");
        }
    }

}
