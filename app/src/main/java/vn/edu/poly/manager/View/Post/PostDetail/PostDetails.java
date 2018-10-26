package vn.edu.poly.manager.View.Post.PostDetail;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import vn.edu.poly.manager.Component.BaseActivity;
import vn.edu.poly.manager.R;
import vn.edu.poly.manager.Server.ApiConnect;
import vn.edu.poly.manager.View.MainActivity;
import vn.edu.poly.manager.View.Post.Post;

public class PostDetails extends BaseActivity implements View.OnClickListener {
    String Site = "";
    String Url = "";
    String Link = "";
    String linkimages = "";
    String linkimagesAvatar = "";
    String URL_CONNECT_WEBSITE = "";
    String URL_CONNECT_AVATAR = "";
    String URL_CONNECT_DRAFF_PENDING = "";
    String URL_CONNECT_PENDING_PUBLIC = "";
    ImageView img_title,img_people;
    TextView txtTstatus, txtTtitle,txtTime,txtContent;
    private ProgressDialog progressDialog;
    Button btnDuyet;
    ImageView imgBack,imgEdit,imgDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_postdetails);
        initControl();
        OnClick();
        initData();
    }

    private void OnClick() {
        imgBack.setOnClickListener(this);
        imgEdit.setOnClickListener(this);
        imgDelete.setOnClickListener(this);
        btnDuyet.setOnClickListener(this);
    }
    private void setContentDialog(String title, String messager) {
        progressDialog.setTitle(title);
        progressDialog.setMessage(messager);
    }

    private void initData() {
            progressDialog = new ProgressDialog(this);
            BaseActivity.editor = BaseActivity.dataLogin.edit();
            Site = BaseActivity.dataLogin.getString("SITESignIn","");
            Url = BaseActivity.dataLogin.getString("URLSignIn","");
            URL_CONNECT_AVATAR = ApiConnect.URL_CONNECT_AVATAR(Site,Url);
            URL_CONNECT_DRAFF_PENDING = ApiConnect.URL_CONNECT_DRAFF_PENDING(Site,Url);
            URL_CONNECT_PENDING_PUBLIC = ApiConnect.URL_CONNECT_PENDING_PUBLIC(Site,Url);
            id = dataLogin.getString("id","");
            Link = ApiConnect.URL_CONNECT_DETAILS(id,Site,Url);
            getJson(Link);




        }

    private void getJson(String link) {
        setContentDialog("Loading", "Please wait...");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null,
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
                            linkimages = URL_CONNECT_AVATAR + image;
                            linkimagesAvatar = URL_CONNECT_AVATAR + avatar;
                            String time1 = created_at.substring(0,4);
                            String time2 = created_at.substring(5,7);
                            String time3 = created_at.substring(8,10);
                            String time4 = created_at.substring(11,16);
                            String timeTong = time4+" "+time3+"/"+time2+"/"+time1;
                            if(status.toString().equals("PUBLISHED")){
                                btnDuyet.setVisibility(View.GONE);
                            }
                            txtTstatus.setText(status);
                            txtTtitle.setText(title);
                            txtTime.setText(timeTong);
                            txtContent.setText(Html.fromHtml(body));
                            Picasso.get()
                                    .load(linkimagesAvatar)
                                    .placeholder(R.drawable.ic_launcher_background)
                                    .error(R.drawable.ic_launcher_background)
                                    .into(img_people);
                            Picasso.get()
                                    .load(linkimages)
                                    .placeholder(R.drawable.ic_launcher_background)
                                    .error(R.drawable.ic_launcher_background)
                                    .into(img_title);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PostDetails.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
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
        img_title = findViewById(R.id.img_logo_postdetails);
        img_people = findViewById(R.id.img_people_postdetails);
        txtTstatus = findViewById(R.id.txt_titlestatus_postdetails);
        txtTtitle = findViewById(R.id.txt_title_postdetails);
        txtTime = findViewById(R.id.txt_time_postdetails);
        txtContent = findViewById(R.id.txt_content_postdetails);
        imgBack = findViewById(R.id.img_back_postdetail);
        imgEdit = findViewById(R.id.img_edit_postdetail);
        imgDelete = findViewById(R.id.img_delete_postdetail);
        btnDuyet = findViewById(R.id.btn_browse_PostDetails);
        }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back_postdetail:
                onBackPressed();
                break;
            case R.id.img_edit_postdetail:
                editor.putString("id",id);
                editor.commit();
                intentView(PostDetailsUpdate.class);
                break;
            case R.id.img_delete_postdetail:
                break;
            case R.id.btn_browse_PostDetails:
                if(status.toString().equals("DRAFT")){
                    alertDialog(URL_CONNECT_DRAFF_PENDING);
                }
                if(status.toString().equals("PENDING")){
                    alertDialog(URL_CONNECT_PENDING_PUBLIC);
                }

                break;
        }
    }

    private void alertDialog(final String url) {
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                PostDetails.this);

// Setting Dialog Title
        alertDialog2.setTitle("Duyệt bài...");

// Setting Dialog Message
        alertDialog2.setMessage("Bạn có chắc chắn duyệt bài viết này?");

// Setting Icon to Dialog
        alertDialog2.setIcon(R.drawable.ic_edit_black1_24dp);

// Setting Positive "Yes" Btn
        alertDialog2.setPositiveButton("Có",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        setContentDialog("Đang duyệt", "Vui lòng đợi...");
                        progressDialog.show();
                        RequestQueue requestQueue = Volley.newRequestQueue(PostDetails.this);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(PostDetails.this, MainActivity.class);
                                        intent.putExtra("screen", "post");
                                        editor.putString("TRANGTHAI",status);
                                        editor.commit();
                                        startActivity(intent);
                                        finish();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(PostDetails.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
                                Log.d("Error",""+error);
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> stringMap = new HashMap<>();
                                stringMap.put("id",id);
                                return stringMap;
                            }

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String,String> stringMap = new HashMap<>();
                                token = dataLogin.getString("usertoken","");
                                stringMap.put("Authorization",token);
                                return stringMap;
                            }
                        };
                    requestQueue.add(stringRequest);
                    }

                });

// Setting Negative "NO" Btn
        alertDialog2.setNegativeButton("Không",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog

                        dialog.cancel();
                    }
                });

// Showing Alert Dialog
        alertDialog2.show();
    }

    private void intentView(Class c) {
        Intent intent = new Intent(PostDetails.this,c );
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PostDetails.this, MainActivity.class);
        intent.putExtra("screen", "post");
        editor.putString("TRANGTHAI",status);
        editor.commit();
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
