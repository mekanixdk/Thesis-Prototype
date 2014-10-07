package dk.mekanix.prototype;



import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.greenrobot.event.EventBus;
import dk.mekanix.data.Attribute;
import dk.mekanix.data.PackRat;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ViewGraphFragment extends Fragment {
//
//    private ViewGraphFragmentComlink mCallback;
//    private Attribute[] mSelectedAttributes;


    public ViewGraphFragment() {
        // Required empty public constructor
    }

//    // Container Activity must implement this interface
//    public interface ViewGraphFragmentComlink {
//        public void viewGraphComlink(Attribute[] mAttributes);
//    }

//    /**
//     * This method ensures that the calling activity have implemented the callback interface
//     * @param activity
//     */
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//
//        // This makes sure that the container activity has implemented
//        // the callback interface. If not, it throws an exception
//        try {
//            mCallback = (ViewGraphFragmentComlink) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement ViewGraphFragmentComlink");
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        //Retrieve mAttributes
//        PackRat ratPackage = (PackRat) EventBus.getDefault().removeStickyEvent(PackRat.class); //removeSticky... getSticky instead?
//        //mAttributes = ratPackage.getAttributes();
//        mSelectedAttributes = ratPackage.getSelectedAttributes();
//        int selectedXScale = ratPackage.getSelectedXScale();
//        int selectedYScale = ratPackage.getSelectedYScale();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_graph, container, false);
    }


}
