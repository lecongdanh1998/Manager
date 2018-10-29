package vn.edu.poly.manager.Component;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LongDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;

import vn.edu.poly.manager.R;

public class ProgressDialogWait extends DialogFragment {

    TextView txt_wait_1;
    TextView txt_wait_2;
    TextView txt_wait_3;
    TextView txt_wait_4;
    TextView txt_wait_5;
    TextView txt_wait_6;
    TextView txt_wait_7;
    TextView txt_wait_8;
    TextView txt_wait_9;
    TextView txt_wait_10;
    ArrayList<TextView> textViewArrayList;
    Dialog dialog;
    private int SPLASH_DISPLAY_LENGTH = 1500;
    private String TAG = "SPLASH_DISPLAY_LENGTH";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialog = new Dialog(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.custom_progressdialog_wait, null);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        txt_wait_1 = dialog.findViewById(R.id.txt_wait_1);
        txt_wait_2 = dialog.findViewById(R.id.txt_wait_2);
        txt_wait_3 = dialog.findViewById(R.id.txt_wait_3);
        txt_wait_4 = dialog.findViewById(R.id.txt_wait_4);
        txt_wait_5 = dialog.findViewById(R.id.txt_wait_5);
        txt_wait_6 = dialog.findViewById(R.id.txt_wait_6);
        txt_wait_7 = dialog.findViewById(R.id.txt_wait_7);
        txt_wait_8 = dialog.findViewById(R.id.txt_wait_8);
        txt_wait_9 = dialog.findViewById(R.id.txt_wait_9);
        txt_wait_10 = dialog.findViewById(R.id.txt_wait_10);

        textViewArrayList = new ArrayList<>();
        textViewArrayList.add(txt_wait_1); //0
        textViewArrayList.add(txt_wait_2); //1
        textViewArrayList.add(txt_wait_3); //2
        textViewArrayList.add(txt_wait_4); //3
        textViewArrayList.add(txt_wait_5); //4
        textViewArrayList.add(txt_wait_6); //5
        textViewArrayList.add(txt_wait_7); //6
        textViewArrayList.add(txt_wait_8); //7
        textViewArrayList.add(txt_wait_9); //8
        textViewArrayList.add(txt_wait_10); //9
        return dialog;
    }

    public void loadingText() {
        final Handler handler = new Handler();
        final int[] count = {0};
        final Runnable runnable = new Runnable() {
            public void run() {
                int a = count[0];
                // need to do tasks on the UI thread
                Log.d(TAG, "run test" + count[0]);

                textViewArrayList.get(count[0]).setTextColor(getResources().getColor(R.color.colorPrimary));
                if (count[0]++ < 9) handler.postDelayed(this, 1000);
            }
        };

        // trigger first time
        handler.post(runnable);
    }

    @Override
    public void onAttach(Context context) {
        try {
            super.onAttach(context);
            loadingText();
        } catch (Exception e) {
            Log.d("POSITION_DIALOG", e.getMessage() + "");
        }
    }
}
