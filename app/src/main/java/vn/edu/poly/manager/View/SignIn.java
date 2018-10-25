package vn.edu.poly.manager.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
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

import vn.edu.poly.manager.Component.BaseActivity;
import vn.edu.poly.manager.Networking.NetworkStateMonitor;
import vn.edu.poly.manager.R;
import vn.edu.poly.manager.Server.ApiConnect;
import vn.edu.poly.manager.Util.ValidateForm;

public class SignIn extends BaseActivity implements View.OnClickListener {
    private Button btn_signin;
    private EditText edt_password_signIn, edt_user_signIn;
    private String useremail;
    private TextView txt_your_site_signIn,txt_select_signIn;
    private String userpassword;
    private ProgressDialog progressDialog;
    private RelativeLayout layout_your_site;
    BroadcastReceiver broadcastReceiver;
    String Site = "";
    String Url = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
//        final Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.custom_dialog_connect_internet);
//        dialog.setCancelable(false);
//        Button button = dialog.findViewById(R.id.btn_connect_internet);
//        button.setOnClickListener(this);
//        broadcastReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                if (new NetworkStateMonitor().checkInterNet(context)){
//                    dialog.dismiss();
//                    dialog.cancel();
//                    Toast.makeText(context, "Connected internet", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(context, "Vui long kiem tra ket noi inter net",
//                            Toast.LENGTH_SHORT).show();
//                    dialog.show();
//                }
//            }
//        };
        initView();
        initData();
        initEventButton();
    }

    private void initView() {
        checkInternetPermission(this);
        txt_your_site_signIn = findViewById(R.id.txt_your_site_signIn);
        txt_select_signIn = findViewById(R.id.txt_select_signIn);
        btn_signin = findViewById(R.id.btn_signIn);
        edt_user_signIn = findViewById(R.id.edt_user_signIn);
        edt_password_signIn = findViewById(R.id.edt_password_signIn);
        layout_your_site = findViewById(R.id.layout_your_site);
        progressDialog = new ProgressDialog(this);
    }

    private void setContentDialog(String title, String messager) {
        progressDialog.setTitle(title);
        progressDialog.setMessage(messager);
    }

    private void initData(){
        editor = dataLogin.edit();
        Site = dataLogin.getString("SITESignIn","Your site");
        Url = dataLogin.getString("URLSignIn","Select to cotinue");
        txt_your_site_signIn.setText(Site);
        txt_select_signIn.setText(Url);
    }

    private void initEventButton() {
        btn_signin.setOnClickListener(this);
        layout_your_site.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_signIn:
                SingIn();
                break;
            case R.id.layout_your_site:
                intentView(MySiteActivity.class);
                //add website url
                break;
            case R.id.btn_connect_internet:
//                startActivityForResult(new Intent(Settings.ACTION_SETTINGS) , 0);
                break;
            default:
                break;
        }
    }

    private void intentView(Class c) {
        Intent intent = new Intent(SignIn.this, c);
        startActivity(intent);
        finish();
    }

    private void SingIn(){
        setContentDialog("Sign in", "Please wait...");
        useremail = edt_user_signIn.getText().toString().trim();
        userpassword = edt_password_signIn.getText().toString().trim();
        RequestQueue requestSignIn = Volley.newRequestQueue(this);
        StringRequest signInRequest = new StringRequest(Request.Method.POST, ApiConnect.URL_SIGNIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.d("SUCCESS_SIGNIN", response + "");
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    editor.putString("useremail", useremail);
                    editor.putString("userpassword", userpassword);
                    editor.putString("usertoken", jsonObject.getString("token_type") + " " +
                            jsonObject.getString("access_token"));
                    editor.commit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(SignIn.this, "Login success!", Toast.LENGTH_SHORT).show();
                intentView(MainActivity.class);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d("ERROR_SIGNIN", error + "");
                Toast.makeText(SignIn.this, "Vui lòng nhập đúng email và password", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("username", useremail);
                params.put("password", userpassword);
                params.put("grant_type", "password");
                params.put("client_id", "2");
                params.put("client_secret", "rOU4FPWpj36XDlWvNZBn1S39BZaxFpGyAEtBHBLH");
                return params;
            }
        };

        int errorCode = 0;

        if (new ValidateForm().validateTextEmpty(useremail)){
            edt_user_signIn.setHint("Please enter your email!");
            edt_user_signIn.setHintTextColor(getResources().getColor(R.color.colorPrimary));
            errorCode ++;
        }

        if (new ValidateForm().validateTextEmpty(userpassword)){
            edt_password_signIn.setHint("Please enter your password!");
            edt_password_signIn.setHintTextColor(getResources().getColor(R.color.colorPrimary));
            errorCode ++;
        }

        if (errorCode == 0){
            requestSignIn.add(signInRequest);
            progressDialog.show();
        }
        intentView(MainActivity.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        registerReceiver(broadcastReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    @Override
    protected void onPause() {
        super.onPause();
//        unregisterReceiver(broadcastReceiver);
    }
}
