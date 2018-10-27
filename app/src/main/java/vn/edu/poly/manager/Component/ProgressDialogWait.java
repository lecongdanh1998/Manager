package vn.edu.poly.manager.Component;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

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
    Dialog dialog;
    int position = 0;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialog = new Dialog(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.custom_progressdialog_wait, null);
        dialog.setCancelable(false);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        txt_wait_1 = dialog.findViewById(R.id.txt_wait_1);
        txt_wait_2 = dialog.findViewById(R.id.txt_wait_2);
        txt_wait_3 = dialog.findViewById(R.id.txt_wait_3);
        txt_wait_4 = dialog.findViewById(R.id.txt_wait_4);
        txt_wait_5 = dialog.findViewById(R.id.txt_wait_5);
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        changeColor();
    }

    private void changeImageSlider() {
        position++;
        switch (position) {
            case 1: {
                txt_wait_1.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
            break;
            case 2: {
                txt_wait_2.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
            break;
            case 3: {
                txt_wait_3.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
            break;
            case 4: {
                txt_wait_4.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
            case 5: {
                txt_wait_5.setTextColor(getResources().getColor(R.color.colorPrimary));
                position = 0;
            }
            break;
            default: {
                position = 0;
            }
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeImageSlider();
            }
        }, 100);
    }

    public void changeColor(){
        changeImageSlider();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            changeImageSlider();
            changeColor();
        } catch (Exception e){

        }
    }
}
