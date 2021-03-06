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
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
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
    private TextInputLayout textInput_username_signIn, textInput_password_signIn;
    BroadcastReceiver broadcastReceiver;
    String Site = "";
    String Url = "";
    String URL_SIGNIN = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
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
//        edt_password_signIn.setTypeface(edt_password_signIn.getTypeface());
        edt_password_signIn.setTypeface(Typeface.DEFAULT);
        edt_password_signIn.setTransformationMethod(new PasswordTransformationMethod());
        layout_your_site = findViewById(R.id.layout_your_site);
        textInput_username_signIn = findViewById(R.id.textInput_username_signIn);
        textInput_password_signIn = findViewById(R.id.textInput_password_signIn);
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
        URL_SIGNIN = ApiConnect.URL_SIGNIN(Url);
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
                SingIn(URL_SIGNIN);
                break;
            case R.id.layout_your_site:
                intentView(MySiteAddSiteActivity.class);
                //add website url
                break;
            case R.id.btn_connect_internet:
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

    private void SingIn(String URL_SIGNIN){
        setContentDialog("Sign in", "Please wait...");
        useremail = edt_user_signIn.getText().toString().trim();
        userpassword = edt_password_signIn.getText().toString().trim();
        RequestQueue requestSignIn = Volley.newRequestQueue(this);
        StringRequest signInRequest = new StringRequest(Request.Method.POST, URL_SIGNIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.d("SUCCESS_SIGNIN", response + "");
                try {
                    editor = dataLogin.edit();
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
                editor.putString("SITESignIn",Site);
                editor.putString("URLSignIn",Url);
                editor.commit();
                intentView(MainActivity.class);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d("ERROR_SIGNIN", error + "");
                Toast.makeText(SignIn.this, "Vui lòng nhập đúng email và password \n Hoặc URL bạn không đúng", Toast.LENGTH_SHORT).show();
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
            textInput_username_signIn.setHint("Enter your email!");
            textInput_username_signIn.setError("Please enter your email!");
            errorCode ++;
        }

        if (new ValidateForm().validateTextEmpty(userpassword)){
            textInput_password_signIn.setHint("Enter your password!");
            textInput_password_signIn.setError("Please enter your password!");
            errorCode ++;
        }

        if (errorCode == 0){
            requestSignIn.add(signInRequest);
            progressDialog.show();
        }
//        intentView(MainActivity.class);
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
