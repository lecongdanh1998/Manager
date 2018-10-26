package vn.edu.poly.manager.View.Setting.SettingDetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import vn.edu.poly.manager.Component.BaseActivity;
import vn.edu.poly.manager.R;
import vn.edu.poly.manager.Server.ApiConnect;

public class SetttingDetails extends AppCompatActivity {

    TextView txt_display_name_setting_detail, txt_details_setting_detail,
            txt_value_setting_detail;
    String Site, Url, URL_CONNECT_WEBSITE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_details);
        initView();
        renderData();
    }

    private void initView() {
        txt_display_name_setting_detail = findViewById(R.id.txt_display_name_setting_detail);
        txt_details_setting_detail = findViewById(R.id.txt_details_setting_detail);
        txt_value_setting_detail = findViewById(R.id.txt_value_setting_detail);
    }

    private void initData(String url) {
        RequestQueue requestSetting = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonOjectSetting = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject("data");
                            txt_display_name_setting_detail.setText(jsonObject.getString("display_name"));
                            txt_details_setting_detail.setText(jsonObject.getString("details"));
                            txt_value_setting_detail.setText(jsonObject.getString("value"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> stringMap = new HashMap<>();
                BaseActivity.token = BaseActivity.dataLogin.getString("usertoken", "");
                stringMap.put("Authorization", BaseActivity.token);
                return stringMap;
            }
        };
        requestSetting.add(jsonOjectSetting);
    }

    public void renderData() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        BaseActivity.editor = BaseActivity.dataLogin.edit();
        Site = BaseActivity.dataLogin.getString("SITESignIn", "");
        Url = BaseActivity.dataLogin.getString("URLSignIn", "");
        URL_CONNECT_WEBSITE = ApiConnect.URL_GET_POST_SETTING_DETAIL(Site, Url, id);
        initData(URL_CONNECT_WEBSITE);
    }
}
