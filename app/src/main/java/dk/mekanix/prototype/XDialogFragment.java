package dk.mekanix.prototype;



import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class XDialogFragment extends DialogFragment {


    public XDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select X Scale")
                .setItems(R.array.x_scale, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        GraphActivity callingActivity = (GraphActivity) getActivity();
                        callingActivity.onUserSelectXValue(which);
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }

}
