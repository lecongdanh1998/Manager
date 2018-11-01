package vn.edu.poly.manager.View.Gallery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import vn.edu.poly.manager.Adapter.GalleryImageAdapter;
import vn.edu.poly.manager.Component.BaseActivity;
import vn.edu.poly.manager.Model.LietKeGalleryContructor;
import vn.edu.poly.manager.R;
import vn.edu.poly.manager.Server.ApiConnect;
import vn.edu.poly.manager.View.MainActivity;

public class GalleryImageShow extends BaseActivity {
    ImageView selectedImage;
    String URL_CONNECT_GALLERY = "";
    String URL_CONNECT_GALLERY_IMAGES = "";
    String Site = "";
    String Url = "";
    String imagesCode;
    int position ;
    Gallery gallery;
    GalleryImageAdapter galleryImageAdapter;
    ArrayList<LietKeGalleryContructor> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_image_show);
        editor = dataLogin.edit();
        Site = BaseActivity.dataLogin.getString("SITESignIn", "");
        Url = BaseActivity.dataLogin.getString("URLSignIn", "");
        URL_CONNECT_GALLERY = ApiConnect.URL_CONNECT_GALLERYS(Url);
        URL_CONNECT_GALLERY_IMAGES = ApiConnect.URL_CONNECT_GALLERYS_IMAGES(Url);
        position = dataLogin.getInt("POSITION", 0);
        gallery = findViewById(R.id.gallery);
        arrayList = new ArrayList<>();
        selectedImage = findViewById(R.id.imageView);
        gallery.setSpacing(5);
        getJsonImages(URL_CONNECT_GALLERY);
        galleryImageAdapter = new GalleryImageAdapter(arrayList, this);
        gallery.setAdapter(galleryImageAdapter);

        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Picasso.get()
                        .load(arrayList.get(position).getImages())
                        .into(selectedImage);


            }
        });





    }
        private void getJsonImages(String url_connect_gallery) {
        RequestQueue requestQueue = Volley.newRequestQueue(GalleryImageShow.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_connect_gallery,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            for(int i = 1;i<jsonObject1.length();i++){
                                imagesCode = jsonObject1.getString(String.valueOf(i));
                                arrayList.add(new LietKeGalleryContructor(imagesCode));
                            }
                            gallery.setSelection(position);
                            gallery.setSelected(true);
                            gallery.getSelectedItemPosition();
                            galleryImageAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GalleryImageShow.this, "" + error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("Error", "" + error.toString());

            }
        }){
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

    private void intentView(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
        finish();
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("screen", "gallery");
        startActivity(intent);
        finish();
    }
}
