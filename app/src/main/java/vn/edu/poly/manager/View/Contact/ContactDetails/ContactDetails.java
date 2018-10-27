package vn.edu.poly.manager.View.Contact.ContactDetails;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import vn.edu.poly.manager.Component.BaseActivity;
import vn.edu.poly.manager.Model.ContactsModel;
import vn.edu.poly.manager.R;
import vn.edu.poly.manager.Server.ApiConnect;

public class ContactDetails extends AppCompatActivity {

    TextView txt_dateTime_contact, txt_phone_contact_detail, txt_email_contact_detail,
            txt_content_contact_detail, txt_title_contact_detail;
    String Site, Url, URL_CONNECT_WEBSITE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        initView();
        renderData();
    }

    private void initView() {
        txt_title_contact_detail = findViewById(R.id.txt_title_contact_detail);
        txt_content_contact_detail = findViewById(R.id.txt_content_contact_detail);
        txt_email_contact_detail = findViewById(R.id.txt_email_contact_detail);
        txt_phone_contact_detail = findViewById(R.id.txt_phone_contact_detail);
        txt_dateTime_contact = findViewById(R.id.txt_dateTime_contact);
    }

    private void initData(String url) {
        RequestQueue requestContact = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonOjectContact = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject("data");
                            txt_title_contact_detail.setText(jsonObject.getString("title"));
                            txt_content_contact_detail.setText(jsonObject.getString("content"));
                            txt_email_contact_detail.setText(jsonObject.getString("email") + " ");
                            txt_phone_contact_detail.setText(jsonObject.getString("phone"));
                            String dateTime = jsonObject.getString("created_at");
                            StringTokenizer tk = new StringTokenizer(dateTime);
                            String dateT = tk.nextToken();
                            String timeT = tk.nextToken();
                            String time = timeT;
                            String date = dateT;
                            SimpleDateFormat dateFormatTime = new SimpleDateFormat("hh:mm:ss");
                            SimpleDateFormat dateFormatTime2 = new SimpleDateFormat("hh:mm");
                            SimpleDateFormat dateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
                            SimpleDateFormat dateFormatDate2 = new SimpleDateFormat("dd/MM/yyyy");
                            try {
                                Date timeFormat = dateFormatTime.parse(time);
                                time = dateFormatTime2.format(timeFormat);
                                Date dateFormat = dateFormatDate.parse(date);
                                date = dateFormatDate2.format(dateFormat);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            txt_dateTime_contact.setText(time + " " + date);
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
        requestContact.add(jsonOjectContact);
    }

    public void renderData() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        BaseActivity.editor = BaseActivity.dataLogin.edit();
        Site = BaseActivity.dataLogin.getString("SITESignIn", "");
        Url = BaseActivity.dataLogin.getString("URLSignIn", "");
        URL_CONNECT_WEBSITE = ApiConnect.URL_GET_POST_CONTACT_DETAIL(Site, Url, id);
        initData(URL_CONNECT_WEBSITE);
    }
}
