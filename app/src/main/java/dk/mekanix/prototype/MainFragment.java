package dk.mekanix.prototype;



import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class MainFragment extends Fragment  {

//    private MainFragmentComlink mCallback;
    private String TAG = "MainFragment";

    public MainFragment() {
        // Required empty public constructor
    }

//    // Container Activity must implement this interface
//    public interface MainFragmentComlink {
//        public void mainFragmentComlink(int button_id);
//    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

//        //Set listeners to all buttons
//        Button mButton1 = (Button) view.findViewById(R.id.button_new_graph);
//        //Button mButton2 = (Button) view.findViewById(R.id.button_new_attribute);
//
//        mButton1.setOnClickListener(this);
//        //mButton2.setOnClickListener(this);


        return view;
    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.button_new_graph:
//                Toast.makeText(getActivity(), "button clicked - NewGraph", Toast.LENGTH_SHORT).show();
//                //mCallback.sendMyMessage("Message from fragment");
//                if (mCallback == null) Log.e(TAG, "mCallback == null");
//                mCallback.mainFragmentComlink(0);
//                break;
//            case R.id.button_new_attribute:
//                Toast.makeText(getActivity(), "button clicked - NewAttribute", Toast.LENGTH_SHORT).show();
//                break;
//            default:
//                Toast.makeText(getActivity(), "button clicked - Not Implemented", Toast.LENGTH_SHORT).show();
//                break;
//
//
//
//        }
//        return;
//    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//
//        // This makes sure that the container activity has implemented
//        // the callback interface. If not, it throws an exception
//        try {
//            mCallback = (MainFragmentComlink) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement MainFragmentComlink");
//        }
//    }
}
