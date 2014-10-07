package dk.mekanix.prototype;



import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import de.greenrobot.event.EventBus;
import dk.mekanix.data.Attribute;
import dk.mekanix.data.PackRat;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class SelectGraphFragment extends Fragment {

    private SelectGraphFragmentComlink mCallback;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioButton;



    public SelectGraphFragment() {
        // Required empty public constructor
    }

    // Container Activity must implement this interface
    public interface SelectGraphFragmentComlink {
        public void selectGraphComlink(String selectedGraph);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Retrieve attributes
        //PackRat ratPackage = (PackRat) EventBus.getDefault().removeStickyEvent(PackRat.class);
        //attributes = ratPackage.getmAttributes();

        //LinearLayout may prompt issues in the future - ie. how to scroll.
        //Inflate view
        RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.fragment_select_graph, container, false);

        //LinearLayout lview = (LinearLayout) view.findViewById(R.id.select_graph_ll);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.radiogroup_select_graph);

        //Set default check
        mRadioButton = (RadioButton) mRadioGroup.findViewById(R.id.radioButton_simple_line);
        mRadioGroup.check(mRadioButton.getId());

        //Catch the onClick listener so we can manage it here in the fragment
        Button mButton = (Button) view.findViewById(R.id.button_select_graph);
        mButton.setText("Next");
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = mRadioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                mRadioButton = (RadioButton) mRadioGroup.findViewById(selectedId);

                //Return attributes with selected attributes.
                mCallback.selectGraphComlink(mRadioButton.getText().toString());
            }
        });


        return view;
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
            mCallback = (SelectGraphFragmentComlink) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement SelectAttributeFragmentComlink");
        }
    }


}
