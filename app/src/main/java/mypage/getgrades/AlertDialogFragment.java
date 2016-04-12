package mypage.getgrades;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by haltayeb on 09-Apr-16.
 */
public class AlertDialogFragment extends DialogFragment{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("Sorry!")
                .setMessage("Try again!")
                .setPositiveButton("OK", null);

        AlertDialog dialog = builder.create();

        return dialog;
    }
}
