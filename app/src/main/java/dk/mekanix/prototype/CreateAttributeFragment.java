package dk.mekanix.prototype;



import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import de.greenrobot.event.EventBus;
import dk.mekanix.data.Attribute;
import dk.mekanix.data.PackRat;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class CreateAttributeFragment extends Fragment {

    private static int idCount = 1;
    private Attribute[] mSelectedAttributes;
    private Attribute mSingleAttribute;
    private String TAG = "CreateAttributeFragment";
    private EditText[] mInput;
    private EditText mLabel;
    private CreateAttributeFragmentComlink mCallback;


    public CreateAttributeFragment() {
        // Required empty public constructor
    }

    // Container Activity must implement this interface
    public interface CreateAttributeFragmentComlink {
        public void createAttributeComlink(Attribute mSingleAttribute);
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
            mCallback = (CreateAttributeFragmentComlink) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement CreateAttributeFragmentComlink");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.fragment_create_attribute, container, false);
        LinearLayout lView = (LinearLayout) view.findViewById(R.id.create_attribute_ll);
        LayoutInflater lViewInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        //Retrieve mAttributes
        PackRat ratPackage = (PackRat) EventBus.getDefault().removeStickyEvent(PackRat.class); //removeSticky... getSticky instead?
        mSingleAttribute = ratPackage.getSingleAttribute();

        //Populate default values if creating new Attribute
        if (mSingleAttribute == null) {
            //We are creating a new attribute - populate default values
            mSelectedAttributes = ratPackage.getSelectedAttributes();
            mSingleAttribute = new Attribute("<change this>", mSelectedAttributes);
        }

        //Create elements in inner_layout and add to lView
        mInput = new EditText[mSingleAttribute.getNumberOfAttributes()];
        for (int i = 0; i < mSingleAttribute.getNumberOfAttributes(); i++) {
            final View mView = lViewInflater.inflate(R.layout.inner_attribute,null);
            mInput[i] = (EditText) mView.findViewById(R.id.edit_inner);
            mInput[i].setText(Integer.toString(mSingleAttribute.getWeight(i)));
            TextView mText = (TextView) mView.findViewById(R.id.inner_text);
            mText.setText(mSingleAttribute.getAttributeLabel(i));
            lView.addView(mView);
        }

        Button mButtonCreate = (Button) view.findViewById(R.id.button_create_attribute);
        mButtonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //LinearLayout lView = (LinearLayout) view.findViewById(R.id.create_attribute_ll);
                for (int i = 0; i < mSingleAttribute.getNumberOfAttributes(); i++) {
                    mSingleAttribute.setWeight(i, Integer.parseInt(mInput[0].getText().toString()));
                }
                //TODO implement logic for Sum/Average
                if (mLabel == null) {
                    Log.w(TAG, "mLabel is NULL!");
                }
                mSingleAttribute.setLabel(mLabel.getText().toString());

                //Return mAttributes with selected mAttributes.
                mCallback.createAttributeComlink(mSingleAttribute);
            }
        });

        mLabel = (EditText) view.findViewById(R.id.edit_create_attribute_label);
        mLabel.setText(mSingleAttribute.getLabel());
        Log.w(TAG, "LABEL:"+mLabel.getText().toString());

        return view;
    }


}
