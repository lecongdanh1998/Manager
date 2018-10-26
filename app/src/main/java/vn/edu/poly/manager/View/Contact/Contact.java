package vn.edu.poly.manager.View.Contact;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import vn.edu.poly.manager.Adapter.ContactAdapter;
import vn.edu.poly.manager.Component.BaseActivity;
import vn.edu.poly.manager.Model.ContactsModel;
import vn.edu.poly.manager.R;
import vn.edu.poly.manager.Server.ApiConnect;
import vn.edu.poly.manager.View.Contact.ContactDetails.ContactDetails;


public class Contact extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private View view;
    private ListView listView_contact;
    private Button btn_done_contact, btn_pending_contact;
    ContactAdapter contactAdapter;
    ArrayList<ContactsModel> contactsModelArrayList;
    String Site, Url, URL_CONNECT_WEBSITE;
    private String id;
    private String fullName;
    private String email;
    private String phone;
    private String title;
    private String content;
    private String pos;
    private String status;
    private String create_at;
    ArrayList<ContactsModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contact, container, false);
        initView();
        initData();
        btnPending();
        renderListView("PENDING");
        return view;
    }

    private void initView() {
        listView_contact = view.findViewById(R.id.listView_contact);
        btn_pending_contact = view.findViewById(R.id.btn_pending_contact);
        btn_pending_contact.setOnClickListener(this);
        btn_done_contact = view.findViewById(R.id.btn_done_contact);
        btn_done_contact.setOnClickListener(this);
    }

    private void initData() {
        contactsModelArrayList = new ArrayList<>();
        contactAdapter = new ContactAdapter(contactsModelArrayList, getContext());
        listView_contact.setAdapter(contactAdapter);
        listView_contact.setOnItemClickListener(this);
        contactAdapter.notifyDataSetChanged();
    }

    private void getDataContact(String url, final String type) {
        RequestQueue requestContact = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonOjectContact = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                if (jsonObject.getString("status").equals(type)) {
                                    id = jsonObject.getString("id");
                                    fullName = jsonObject.getString("fullname");
                                    email = jsonObject.getString("email");
                                    phone = jsonObject.getString("phone");
                                    title = jsonObject.getString("title");
                                    content = jsonObject.getString("content");
                                    pos = jsonObject.getString("pos");
                                    status = jsonObject.getString("status");
                                    create_at = jsonObject.getString("created_at");

                                    contactsModelArrayList.add(new ContactsModel(id,
                                            fullName,
                                            email,
                                            phone,
                                            title,
                                            content,
                                            pos,
                                            status,
                                            create_at));
                                }
                            }
                            contactAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "" + error, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pending_contact:
                btnPending();
                renderListView("PENDING");
                break;
            case R.id.btn_done_contact:
                btnDone();
                renderListView("VIEWED");
                break;
        }
    }

    public void renderListView(String status) {
        contactsModelArrayList.clear();
        BaseActivity.editor = BaseActivity.dataLogin.edit();
        Site = BaseActivity.dataLogin.getString("SITESignIn", "");
        Url = BaseActivity.dataLogin.getString("URLSignIn", "");
        URL_CONNECT_WEBSITE = ApiConnect.URL_GET_POST_CONTACT(Site, Url);
        getDataContact(URL_CONNECT_WEBSITE, status);
        contactAdapter.notifyDataSetChanged();
    }

    private void btnPending() {
        btn_pending_contact.setEnabled(false);
        btn_done_contact.setEnabled(true);
        btn_pending_contact.setBackground(getResources().getDrawable(R.drawable.custom_background_button_tab));
        btn_pending_contact.setTextColor(getResources().getColor(R.color.colorPrimary));
        btn_done_contact.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        btn_done_contact.setTextColor(getResources().getColor(R.color.colorPrimary));

    }

    private void btnDone() {
        btn_pending_contact.setEnabled(true);
        btn_done_contact.setEnabled(false);
        btn_pending_contact.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        btn_pending_contact.setTextColor(getResources().getColor(R.color.colorPrimary));
        btn_done_contact.setBackground(getResources().getDrawable(R.drawable.custom_background_button_tab));
        btn_done_contact.setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(), ContactDetails.class);
        intent.putExtra("id", contactsModelArrayList.get(position).getId());
        startActivity(intent);
    }
}
