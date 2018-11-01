package vn.edu.poly.manager.View.Post;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import vn.edu.poly.manager.Adapter.POSTAdapter;
import vn.edu.poly.manager.Component.BaseActivity;
import vn.edu.poly.manager.Model.POSTContructor;
import vn.edu.poly.manager.R;
import vn.edu.poly.manager.Server.ApiConnect;
import vn.edu.poly.manager.View.Post.PostDetail.PostDetails;
import vn.edu.poly.manager.View.SignIn;

public class Post extends Fragment implements View.OnClickListener {
    private View view;
    private int index_back_fragment;
    private FragmentManager fm;
    ArrayList<POSTContructor> arrayList;
    POSTAdapter adapter;
    String linkimages = "";
    String linkimagesAvatar = "";
    String URL_CONNECT_WEBSITE = "";
    String URL_CONNECT_AVATAR = "";
    ListView lstPost;
    Button btnDraff, btnPending, btnPublic;
    private ProgressDialog progressDialog;
    String TrangThai = "";
    String Site = "";
    String Url = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_post, container, false);
        initView();
        initData();
        return view;
    }

    private void setContentDialog(String title, String messager) {
        progressDialog.setTitle(title);
        progressDialog.setMessage(messager);
    }

    private void initData() {

        progressDialog = new ProgressDialog(getContext());
        BaseActivity.editor = BaseActivity.dataLogin.edit();
        Site = BaseActivity.dataLogin.getString("SITESignIn", "");
        Url = BaseActivity.dataLogin.getString("URLSignIn", "");
        URL_CONNECT_WEBSITE = ApiConnect.URL_CONNECT_WEBSITE(Url);
        URL_CONNECT_AVATAR = ApiConnect.URL_CONNECT_AVATAR(Url);
        btnDraff.setOnClickListener(this);
        btnPending.setOnClickListener(this);
        btnPublic.setOnClickListener(this);
        arrayList = new ArrayList<>();
        adapter = new POSTAdapter(getActivity(), arrayList);
        lstPost.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        TrangThai = "draft";
        TrangThai = BaseActivity.dataLogin.getString("TRANGTHAI", "");
        btnDraff();
        if (TrangThai.toString().equals("draft") || TrangThai.toString().equals("DRAFT")) {
            btnDraff();
            arrayList.clear();
            TrangThai = "draft";
            getJson(URL_CONNECT_WEBSITE, TrangThai);
        }
        if (TrangThai.toString().equals("pending") || TrangThai.toString().equals("PENDING")) {
            btnPending();
            arrayList.clear();
            TrangThai = "pending";
            getJson(URL_CONNECT_WEBSITE, TrangThai);
        }
        if (TrangThai.toString().equals("PUBLISHED")) {
            btnPublic();
            arrayList.clear();
            getJson(URL_CONNECT_WEBSITE, TrangThai);
        }


        lstPost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BaseActivity.editor.putString("id", arrayList.get(position).getId());
                BaseActivity.editor.putString("status", arrayList.get(position).getStatus());
                if(TrangThai.toString().equals("draft") || TrangThai.toString().equals("pending")){
                    BaseActivity.editor.putString("adminTrue", "true");
                }
                BaseActivity.editor.commit();
                intentView(PostDetails.class);
            }
        });
    }

    private void intentView(Class c) {
        Intent intent = new Intent(getActivity(), c);
        startActivity(intent);
        getActivity().finish();
    }

    /*
     * register view
     * */
    private void initView() {
        lstPost = (ListView) view.findViewById(R.id.lstPost);
        btnDraff = (Button) view.findViewById(R.id.btnDraff);
        btnPending = (Button) view.findViewById(R.id.btnPending);
        btnPublic = (Button) view.findViewById(R.id.btnPublic);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDraff:
                btnDraff();
                arrayList.clear();
                TrangThai = "draft";
                getJson(URL_CONNECT_WEBSITE, TrangThai);

                break;
            case R.id.btnPending:
                btnPending();
                arrayList.clear();
                TrangThai = "pending";
                getJson(URL_CONNECT_WEBSITE, TrangThai);
                break;
            case R.id.btnPublic:
                btnPublic();
                arrayList.clear();
                TrangThai = "PUBLISHED";
                getJson(URL_CONNECT_WEBSITE, TrangThai);

                break;
        }
    }

    private void btnDraff() {
        btnDraff.setEnabled(false);
        btnPublic.setEnabled(true);
        btnPending.setEnabled(true);
        btnDraff.setBackground(getActivity().getDrawable(R.drawable.custom_background_button_tab));
        btnPending.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        btnPublic.setBackgroundColor(getResources().getColor(R.color.colorWhite));

    }

    private void btnPending() {
        btnDraff.setEnabled(true);
        btnPublic.setEnabled(true);
        btnPending.setEnabled(false);
        btnDraff.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        btnPending.setBackground(getActivity().getDrawable(R.drawable.custom_background_button_tab));
        btnPublic.setBackgroundColor(getResources().getColor(R.color.colorWhite));

    }

    private void btnPublic() {
        btnDraff.setEnabled(true);
        btnPublic.setEnabled(false);
        btnPending.setEnabled(true);
        btnDraff.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        btnPending.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        btnPublic.setBackground(getActivity().getDrawable(R.drawable.custom_background_button_tab));
    }


    private void getJson(String url, final String TrangThai) {
        setContentDialog("Loading", "Please wait...");
        progressDialog.show();
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        JSONObject jsonObject1 = null;
                        try {
                            jsonObject1 = new JSONObject(response);
                            JSONArray jsonArray = jsonObject1.getJSONArray("data");
                            for (int i = 0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                BaseActivity.id = jsonObject.getString("id");
                                BaseActivity.author_id = jsonObject.getString("author_id");
                                BaseActivity.author = jsonObject.getString("author");
                                BaseActivity.category_id = jsonObject.getString("category_id");
                                BaseActivity.title = jsonObject.getString("title");
                                BaseActivity.seo_title = jsonObject.getString("seo_title");
                                BaseActivity.excerpt = jsonObject.getString("excerpt");
                                BaseActivity.body = jsonObject.getString("body");
                                BaseActivity.image = jsonObject.getString("image");
                                BaseActivity.slug = jsonObject.getString("slug");
                                BaseActivity.meta_description = jsonObject.getString("meta_description");
                                BaseActivity.meta_keywords = jsonObject.getString("meta_keywords");
                                BaseActivity.status = jsonObject.getString("status");;
                                BaseActivity.featured = jsonObject.getString("featured");
//                                    BaseActivity.created_at = jsonObject.getString("created_at");
//                                    BaseActivity.updated_at = jsonObject.getString("updated_at");
//                                    BaseActivity.name = jsonObject.getString("name");
                                BaseActivity.avatar = jsonObject.getString("avatar");
                                BaseActivity.category = jsonObject.getString("category");
//                                linkimages = URL_CONNECT_AVATAR + BaseActivity.image;
                                linkimagesAvatar = URL_CONNECT_AVATAR + BaseActivity.avatar;
                                String BodyHtml = String.valueOf(Html.fromHtml(BaseActivity.body));
//                                    String time1 = BaseActivity.created_at.substring(0,4);
//                                    String time2 = BaseActivity.created_at.substring(5,7);
//                                    String time3 = BaseActivity.created_at.substring(8,10);
//                                    String time4 = BaseActivity.created_at.substring(11,16);
//                                    String timeTong = time4+" "+time3+"/"+time2+"/"+time1;
                                arrayList.add(new POSTContructor(BaseActivity.image, BaseActivity.title, BodyHtml
                                        , linkimagesAvatar, BaseActivity.id,BaseActivity.status));

                            }
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "" + error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("Error", "" + error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> stringMap = new HashMap<>();
                stringMap.put("status", TrangThai);
                return stringMap;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> stringMap = new HashMap<>();
                BaseActivity.token = BaseActivity.dataLogin.getString("usertoken", "");
                stringMap.put("Authorization", BaseActivity.token);
                return stringMap;
            }
        };
        requestQueue.add(stringRequest);
    }


}
