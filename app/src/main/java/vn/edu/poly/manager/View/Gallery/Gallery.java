package vn.edu.poly.manager.View.Gallery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import net.alhazmy13.mediagallery.library.activity.MediaGallery;
import net.alhazmy13.mediagallery.library.views.MediaGalleryView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import vn.edu.poly.manager.Component.BaseActivity;
import vn.edu.poly.manager.Model.GalleryContructor;
import vn.edu.poly.manager.R;
import vn.edu.poly.manager.Server.ApiConnect;

public class Gallery extends Fragment implements View.OnClickListener {
    View view;
    ImageView img_back, img_find, img_lietke, img_danhsach;
    TextView txtGallery;
    EditText edtCategory, edtSearch;
    Button btnUpload;
    ArrayList<GalleryContructor> arrayList;
    String Site = "";
    String Url = "";
    String URL_CONNECT_GALLERY = "";
    String URL_CONNECT_GALLERY_IMAGES = "";
    String LinkImages = "";
    private ProgressDialog progressDialog;
    private ViewFlipper simpleViewFlipper;
    MediaGalleryView viewGallery;
    ArrayList<String> list;
    String imagesCode;

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
        viewGallery.setOnClickListener(this);
        BaseActivity.editor = BaseActivity.dataLogin.edit();
        Site = BaseActivity.dataLogin.getString("SITESignIn", "");
        Url = BaseActivity.dataLogin.getString("URLSignIn", "");
        URL_CONNECT_GALLERY = ApiConnect.URL_CONNECT_GALLERYS(Site, Url);
        URL_CONNECT_GALLERY_IMAGES = ApiConnect.URL_CONNECT_GALLERYS_IMAGES(Site, Url);
        list = new ArrayList<>();
        Lietke(URL_CONNECT_GALLERY);

        viewGallery.setOnImageClickListener(new MediaGalleryView.OnImageClicked() {
            @Override
            public void onImageClicked(int pos) {
                MediaGallery.Builder(getActivity(), list)
                        .title("Media Gallery")
                        .backgroundColor(R.color.color_extHintGray)
                        .placeHolder(R.drawable.media_gallery_placeholder)
                        .selectedImagePosition(pos)
                        .show();
            }
        });
    }

    public String bitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }


    private void Lietke(String url_connect_gallery) {
        img_lietke.setEnabled(false);
        img_danhsach.setEnabled(true);
        img_lietke.setImageResource(R.drawable.ic_format_list_bulleted_blue_40dp);
        img_danhsach.setImageResource(R.drawable.ic_apps_black_40dp);
        list.clear();
        getJsonImageGallery(URL_CONNECT_GALLERY, 1);
        viewGallery.notifyDataSetChanged();


    }


    private void DanhSach(String url_connect_gallery) {
        img_lietke.setEnabled(true);
        img_danhsach.setEnabled(false);
        img_lietke.setImageResource(R.drawable.ic_format_list_bulleted_black_40dp);
        img_danhsach.setImageResource(R.drawable.ic_apps_blue_40dp);
        list.clear();
        getJsonImageGallery(URL_CONNECT_GALLERY, 3);
        viewGallery.notifyDataSetChanged();
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
                            for (int i = 0; i < jsonArray.length(); i++) {
                                imagesCode = jsonArray.getString(i);
                                LinkImages = URL_CONNECT_GALLERY_IMAGES + imagesCode;
                                list.add(LinkImages);
                            }
                            viewGallery.setImages(list);
                            if (so == 1) {
                                viewGallery.setSpanCount(1);
                                viewGallery.setImageSize(ViewGroup.LayoutParams.MATCH_PARENT,300);
                                viewGallery.setPlaceHolder(R.drawable.media_gallery_placeholder);
                                viewGallery.setOrientation(MediaGalleryView.VERTICAL);
                                viewGallery.notifyDataSetChanged();
                            }
                            else if (so == 3) {
                                viewGallery.setSpanCount(3);
                                viewGallery.setImageSize(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                                viewGallery.setPlaceHolder(R.drawable.media_gallery_placeholder);
                                viewGallery.setOrientation(MediaGalleryView.VERTICAL);
                                viewGallery.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "" + error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("Error", "" + error.toString());
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
        requestQueue.add(jsonObjectRequest);
    }

    private void initControl() {
        img_danhsach = view.findViewById(R.id.img_danhsach_gallery);
        img_lietke = view.findViewById(R.id.img_lietke_gallery);
        edtCategory = view.findViewById(R.id.ext_Category_Gallery);
        edtSearch = view.findViewById(R.id.ext_Search_Gallery);
        btnUpload = view.findViewById(R.id.btn_UploadGallery);
        simpleViewFlipper = (ViewFlipper) view.findViewById(R.id.pager);
        viewGallery = (MediaGalleryView) view.findViewById(R.id.gallery);

        // Pager listener over indicator

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_danhsach_gallery:
                list.clear();
                DanhSach(URL_CONNECT_GALLERY);
                break;
            case R.id.img_lietke_gallery:
                list.clear();
                Lietke(URL_CONNECT_GALLERY);
                break;
            case R.id.btn_UploadGallery:

                break;

        }
    }




    private void intentView(Class c) {
        Intent intent = new Intent(getActivity(), c);
        startActivity(intent);
        getActivity().finish();
    }


}
