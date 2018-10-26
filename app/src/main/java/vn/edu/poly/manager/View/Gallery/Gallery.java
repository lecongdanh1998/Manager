package vn.edu.poly.manager.View.Gallery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import vn.edu.poly.manager.Adapter.GalleryAdapterGridview;
import vn.edu.poly.manager.Adapter.GalleryAdapterListview;
import vn.edu.poly.manager.Component.BaseActivity;
import vn.edu.poly.manager.Model.GalleryContructor;
import vn.edu.poly.manager.R;
import vn.edu.poly.manager.Server.ApiConnect;
import vn.edu.poly.manager.View.MainActivity;

public class Gallery extends Fragment implements View.OnClickListener {
    View view;
    ImageView img_back,img_find,img_lietke,img_danhsach;
    TextView txtGallery;
    EditText edtCategory,edtSearch;
    Button btnUpload;
    ArrayList<GalleryContructor> arrayList;
    ListView listView;
    GridView gridView;
    GalleryAdapterGridview adapterGridview;
    GalleryAdapterListview adapterListview;
    String Site = "";
    String Url = "";
    String URL_CONNECT_GALLERY = "";
    String URL_CONNECT_GALLERY_IMAGES = "";
    String LinkImages = "";
    private ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_gallery, container, false);
        initControl();
        initData();
        return view;
    }

    private void setContentDialog(String title, String messager) {
        progressDialog.setTitle(title);
        progressDialog.setMessage(messager);
    }

    private void initData() {
        progressDialog = new ProgressDialog(getContext());
        img_danhsach.setOnClickListener(this);
        img_lietke.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
        BaseActivity.editor = BaseActivity.dataLogin.edit();
        Site = BaseActivity.dataLogin.getString("SITESignIn","");
        Url = BaseActivity.dataLogin.getString("URLSignIn","");
        URL_CONNECT_GALLERY = ApiConnect.URL_CONNECT_GALLERYS(Site,Url);
        URL_CONNECT_GALLERY_IMAGES = ApiConnect.URL_CONNECT_GALLERYS_IMAGES(Site,Url);
        listview(URL_CONNECT_GALLERY);





    }


    private void listview(String url_connect_gallery) {
        listView.setVisibility(View.VISIBLE);
        gridView.setVisibility(View.GONE);
        img_lietke.setEnabled(false);
        img_danhsach.setEnabled(true);
        img_lietke.setImageResource(R.drawable.ic_format_list_bulleted_blue_40dp);
        img_danhsach.setImageResource(R.drawable.ic_apps_black_40dp);
        arrayList = new ArrayList<>();
        adapterListview = new GalleryAdapterListview(getContext(),arrayList);
        listView.setAdapter(adapterListview);
        adapterListview.notifyDataSetChanged();
        getJsonImageGallery(url_connect_gallery,1);



    }


    private void gridview(String url_connect_gallery) {
        gridView.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        img_lietke.setEnabled(true);
        img_danhsach.setEnabled(false);
        img_lietke.setImageResource(R.drawable.ic_format_list_bulleted_black_40dp);
        img_danhsach.setImageResource(R.drawable.ic_apps_blue_40dp);
        arrayList = new ArrayList<>();
        adapterGridview = new GalleryAdapterGridview(getContext(),arrayList);
        gridView.setAdapter(adapterGridview);
        adapterGridview.notifyDataSetChanged();
        getJsonImageGallery(url_connect_gallery,2);


    }

    private void getJsonImageGallery(String url_connect_gallery, final int so) {
        setContentDialog("Loading", "Please wait...");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url_connect_gallery, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            progressDialog.dismiss();
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i <jsonArray.length();i++){
                                String images = jsonArray.getString(i);
                                LinkImages = URL_CONNECT_GALLERY_IMAGES + images;
                                arrayList.add(new GalleryContructor(LinkImages));
                            }
                            if(so == 1){
                                adapterListview.notifyDataSetChanged();
                            }
                            if (so ==2) {
                                adapterGridview.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), ""+error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("Error",""+error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> stringMap = new HashMap<>();
                BaseActivity.token  = BaseActivity.dataLogin.getString("usertoken","");
                stringMap.put("Authorization",BaseActivity.token);
                return stringMap;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    private void initControl() {
        img_danhsach = view.findViewById(R.id.img_danhsach_gallery);
        img_lietke = view.findViewById(R.id.img_lietke_gallery);
        edtCategory = view.findViewById(R.id.ext_Category_Gallery);
        edtSearch = view.findViewById(R.id.ext_Search_Gallery);
        btnUpload = view.findViewById(R.id.btn_UploadGallery);
        listView = view.findViewById(R.id.lst_gallery);
        gridView = view.findViewById(R.id.grd_gallery);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_danhsach_gallery:
                arrayList.clear();
                gridview(URL_CONNECT_GALLERY);
                adapterGridview.notifyDataSetChanged();
                break;
            case R.id.img_lietke_gallery:
                arrayList.clear();
                listview(URL_CONNECT_GALLERY);
                adapterListview.notifyDataSetChanged();
                break;
            case R.id.btn_UploadGallery:

                break;
        }
    }
    private void intentView(Class c) {
        Intent intent = new Intent(getActivity(),c );
        startActivity(intent);
        getActivity().finish();
    }


}
