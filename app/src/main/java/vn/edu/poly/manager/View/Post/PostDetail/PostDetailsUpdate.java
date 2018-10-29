package vn.edu.poly.manager.View.Post.PostDetail;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import vn.edu.poly.manager.Component.BaseActivity;
import vn.edu.poly.manager.R;
import vn.edu.poly.manager.Server.ApiConnect;

public class PostDetailsUpdate extends BaseActivity implements View.OnClickListener {
    ImageView img_back,img_find;
    TextView txt_Update;
    EditText edt_title,edt_content;
    Button btnSave;
    String Site = "";
    String Url = "";
    String URL_CONNECT_WEBSITE = "";
    String URL_CONNECT_UPDATE = "";
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details_update);
        initControl();
        initOnClick();
        initData();
    }

    private void initOnClick() {
        btnSave.setOnClickListener(this);
        img_back.setOnClickListener(this);
    }

    private void setContentDialog(String title, String messager) {
        progressDialog.setTitle(title);
        progressDialog.setMessage(messager);
    }

    private void initData() {
        progressDialog = new ProgressDialog(this);
        txt_Update.setText("Cập nhật bài viết");
        img_find.setVisibility(View.INVISIBLE);
        editor = dataLogin.edit();
        Site = BaseActivity.dataLogin.getString("SITESignIn","");
        Url = BaseActivity.dataLogin.getString("URLSignIn","");
        id = dataLogin.getString("id","");
        URL_CONNECT_WEBSITE = ApiConnect.URL_CONNECT_DETAILS(id,Url);
        getJson(URL_CONNECT_WEBSITE);


    }

    private void getJson(String url_connect_website) {
        setContentDialog("Loading", "Please wait...");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url_connect_website, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            progressDialog.dismiss();
                            JSONObject jsonObject = response.getJSONObject("data");
                            id = jsonObject.getString("id");
                            author_id = jsonObject.getString("author_id");
                            category_id = jsonObject.getString("category_id");
                            title = jsonObject.getString("title");
                            seo_title = jsonObject.getString("seo_title");
                            excerpt = jsonObject.getString("excerpt");
                            body = jsonObject.getString("body");
                            image = jsonObject.getString("image");
                            slug = jsonObject.getString("slug");
                            meta_description = jsonObject.getString("meta_description");
                            meta_keywords = jsonObject.getString("meta_keywords");
                            status = jsonObject.getString("status");
                            featured = jsonObject.getString("featured");
                            created_at = jsonObject.getString("created_at");
                            updated_at = jsonObject.getString("updated_at");
                            name = jsonObject.getString("name");
                            avatar = jsonObject.getString("avatar");
                            edt_title.setText(title);
                            edt_content.setText(Html.fromHtml(body));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PostDetailsUpdate.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("Error",""+error);

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> stringMap = new HashMap<>();
                token  = dataLogin.getString("usertoken","");
                stringMap.put("Authorization",token);
                return stringMap;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    private void initControl() {
        img_back = findViewById(R.id.img_back_MysiteToobar);
        img_find = findViewById(R.id.img_find_MysiteToobar);
        txt_Update = findViewById(R.id.txt_name_MySiteToobar);
        edt_title = findViewById(R.id.ext_title_editDetails);
        edt_content = findViewById(R.id.ext_content_editDetails);
        btnSave = findViewById(R.id.btn_browse_PostDetailsUpdate);
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.img_back_MysiteToobar:
                    intentView(PostDetails.class);
                    break;
                case R.id.btn_browse_PostDetailsUpdate:
                    String title = edt_title.getText().toString().trim();
                    String content = edt_content.getText().toString().trim();
                    URL_CONNECT_UPDATE = ApiConnect.URL_CONNECT_UPDATE(Url);
                    getJsonUpdate(URL_CONNECT_UPDATE,title,content);
                    break;
            }
    }

    private void getJsonUpdate(String url_connect_update, final String title, final String content) {
        setContentDialog("Update", "Please wait...");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_connect_update,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Toast.makeText(PostDetailsUpdate.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PostDetailsUpdate.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("Error",""+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> stringMap = new HashMap<>();
                stringMap.put("id",id);
                stringMap.put("title",title);
                stringMap.put("body",content);
                return stringMap;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> stringMap = new HashMap<>();
                token = dataLogin.getString("usertoken","");
                stringMap.put("Authorization",token);
                stringMap.put("Accept","application/json");
                return stringMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void intentView(Class c) {
        Intent intent = new Intent(PostDetailsUpdate.this,c );
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        intentView(PostDetails.class);
        super.onBackPressed();
    }
}
