package vn.edu.poly.manager.Component;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import vn.edu.poly.manager.R;
import vn.edu.poly.manager.Server.ApiConnect;
import vn.edu.poly.manager.Util.ValidateForm;
import vn.edu.poly.manager.View.MainActivity;
import vn.edu.poly.manager.View.SignIn;

public class DialogFragmentSetting extends DialogFragment implements View.OnClickListener {
    Button btn_save_setting, btn_cancel_setting;
    EditText edt_content_setting;
    TextView txt_title_dialog_setting;
    ImageView img_logo_setting;
    View view;
    Dialog builder;
    String title;
    String contents;
    String id;
    private String Site, Url, URL_CONNECT_WEBSITE;
    String TAG = "DIALOG_SETTING";
    private ProgressDialog progressDialog;

    private DialogInterface.OnDismissListener onDismissListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        title = getArguments().getString("title");
        contents = getArguments().getString("value");
        id = getArguments().getString("id");
        builder = new Dialog(getActivity());
        view = LayoutInflater.from(getActivity()).inflate(R.layout.custom_dialog_change_setting, null);
        builder.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        builder.setContentView(view);

        btn_cancel_setting = builder.findViewById(R.id.btn_cancel_setting);
        btn_cancel_setting.setOnClickListener(this);
        btn_save_setting = builder.findViewById(R.id.btn_save_setting);
        btn_save_setting.setOnClickListener(this);
        edt_content_setting = builder.findViewById(R.id.edt_content_setting);
        txt_title_dialog_setting = builder.findViewById(R.id.txt_title_dialog_setting);

        edt_content_setting.setText(contents);
        edt_content_setting.setSelection(contents.length());
        txt_title_dialog_setting.setText(title);

        progressDialog = new ProgressDialog(getContext());
        return builder;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancel_setting:
                getDialog().dismiss();
                break;
            case R.id.btn_save_setting:
                updateData();
                break;
        }
    }

    private void updateData(){
        BaseActivity.editor = BaseActivity.dataLogin.edit();
        Site = BaseActivity.dataLogin.getString("SITESignIn", "");
        Url = BaseActivity.dataLogin.getString("URLSignIn", "");
        URL_CONNECT_WEBSITE = ApiConnect.URL_UPDATE_SETTING(Url);
        initData(URL_CONNECT_WEBSITE);
    }

    private void initData(String url){
        setContentDialog("Sign in", "Please wait...");
        contents = edt_content_setting.getText().toString().trim();
        RequestQueue requestSetting = Volley.newRequestQueue(getContext());
        StringRequest settingRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                getDialog().dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d(TAG, error + "");
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                BaseActivity.token = BaseActivity.dataLogin.getString("usertoken", "");
                params.put("Authorization", BaseActivity.token);
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                params.put("value", contents);
                return params;
            }
        };

        int errorCode = 0;

        if (new ValidateForm().validateTextEmpty(contents)){
            edt_content_setting.setHint("Enter your content");
            edt_content_setting.setHintTextColor(getResources().getColor(R.color.colorRed));
            errorCode ++;
        }

        if (errorCode == 0){
            requestSetting.add(settingRequest);
            progressDialog.show();
        }
    }

    private void setContentDialog(String title, String messager) {
        progressDialog.setTitle(title);
        progressDialog.setMessage(messager);
    }
}
