package vn.edu.poly.manager.View.Setting;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import vn.edu.poly.manager.Adapter.SettingAdapter;
import vn.edu.poly.manager.Component.BaseActivity;
import vn.edu.poly.manager.Model.ContactsModel;
import vn.edu.poly.manager.Model.SettingModel;
import vn.edu.poly.manager.R;
import vn.edu.poly.manager.Server.ApiConnect;
import vn.edu.poly.manager.View.Contact.ContactDetails.ContactDetails;
import vn.edu.poly.manager.View.Setting.SettingDetails.SetttingDetails;

public class Setting extends Fragment implements AdapterView.OnItemClickListener {
    View view;
    ListView listView;
    ArrayList<SettingModel> settingModelArrayList;
    SettingAdapter settingAdapter;
    String Site, Url, URL_CONNECT_WEBSITE;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        initView();
        initData();
        renderListView();
        return view;
    }

    private void initView() {
        listView = view.findViewById(R.id.listView_setting);
    }

    private void initData() {
        settingModelArrayList = new ArrayList<>();
        settingAdapter = new SettingAdapter(settingModelArrayList, getContext());
        listView.setAdapter(settingAdapter);
        listView.setOnItemClickListener(this);
    }

    private void renderListView() {
        settingModelArrayList.clear();
        BaseActivity.editor = BaseActivity.dataLogin.edit();
        Site = BaseActivity.dataLogin.getString("SITESignIn", "");
        Url = BaseActivity.dataLogin.getString("URLSignIn", "");
        URL_CONNECT_WEBSITE = ApiConnect.URL_GET_SETTING(Site, Url);
        getDataSetting(URL_CONNECT_WEBSITE);
        settingAdapter.notifyDataSetChanged();
    }

    public void getDataSetting(String url) {
        RequestQueue requestSetting = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonOjectSetting = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                settingModelArrayList.add(new SettingModel(
                                        jsonObject.getString("id"),
                                        jsonObject.getString("key"),
                                        jsonObject.getString("display_name"),
                                        jsonObject.getString("value"),
                                        jsonObject.getString("details"),
                                        jsonObject.getString("type"),
                                        jsonObject.getString("order"),
                                        jsonObject.getString("group")
                                ));
                            }
                            settingAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
        requestSetting.add(jsonOjectSetting);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(), SetttingDetails.class);
        intent.putExtra("id", settingModelArrayList.get(position).getId());
        startActivity(intent);
    }
}
