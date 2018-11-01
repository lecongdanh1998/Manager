package vn.edu.poly.manager.View.Post.PostDetail;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.poly.manager.Adapter.CategoryAdapter;
import vn.edu.poly.manager.Component.BaseActivity;
import vn.edu.poly.manager.Model.CategoryContructor;
import vn.edu.poly.manager.R;
import vn.edu.poly.manager.Server.ApiConnect;
import vn.edu.poly.manager.View.Account.Account;

public class PostDetailsUpdate extends BaseActivity implements View.OnClickListener {
    ImageView img_back, img_find;
    TextView txt_Update, txtid, txtauthor_id, txtSlug, txtseo_title;
    EditText edt_title, edt_content, edt_excerpt, edt_meta_description, edt_meta_keywords;
    Button btnSave;
    String Site = "";
    String Url = "";
    String URL_CONNECT_WEBSITE = "";
    String URL_CONNECT_UPDATE = "";
    private ProgressDialog progressDialog;
    Spinner spinner_Catelory;
    ArrayList<CategoryContructor> arrayList;
    CategoryAdapter arrayAdapter;
    String URL_CONNECT_CATEGORIES = "";
    String idCategory = "";
    String NaemCategory = "";
    String URL_CONNECT_UPDATE_IMAGES = "";
    ImageView img_images_editDetails;
    CircleImageView img_imagesedit_editDetails;
    private int PIC_CROP = 3;
    private Uri picUri;
    private int CAMERA_REQUEST = 1;
    private int CAMERA_REQUEST_MAX = 1999;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private static final int PERMISSION_REQUEST_CAMERA = 0;
    int so;
    Dialog dialog;
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
        img_imagesedit_editDetails.setOnClickListener(this);

    }

    private void setContentDialog(String title, String messager) {
        progressDialog.setTitle(title);
        progressDialog.setMessage(messager);
    }

    private void initData() {
        dialog = new Dialog(this);
        progressDialog = new ProgressDialog(this);
        txt_Update.setText("Cập nhật bài viết");
        img_find.setVisibility(View.INVISIBLE);
        editor = dataLogin.edit();
        Site = BaseActivity.dataLogin.getString("SITESignIn", "");
        Url = BaseActivity.dataLogin.getString("URLSignIn", "");
        id = dataLogin.getString("id", "");
        URL_CONNECT_WEBSITE = ApiConnect.URL_CONNECT_DETAILS(Url);
        URL_CONNECT_CATEGORIES = ApiConnect.URL_CONNECT_CATEGORIES(Url);
        URL_CONNECT_UPDATE_IMAGES = ApiConnect.URL_CONNECT_UPDATE_IMAGES(Url);
        arrayList = new ArrayList<>();
        arrayAdapter = new CategoryAdapter(this,arrayList);
        arrayAdapter.notifyDataSetChanged();
        getJsonURL_CONNECT_CATEGORIES(URL_CONNECT_CATEGORIES);
        getJson(URL_CONNECT_WEBSITE);
        spinner_Catelory.setAdapter(arrayAdapter);
        spinner_Catelory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                so = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edt_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtseo_title.setText(edt_title.getText().toString().trim());
                txtSlug.setText(deAccent(String.valueOf(edt_title.getText().toString().trim())));

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    public static String deAccent(String str) {
        str =  str.replaceAll(" ", "-");
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }


    private void getJsonURL_CONNECT_CATEGORIES(String url_connect_categories) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_connect_categories,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            JSONArray jsonArray = jsonObject1.getJSONArray("data");
                            for(int i = 0 ;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                idCategory = jsonObject.getString("id");
                                NaemCategory = jsonObject.getString("name");
                                arrayList.add(new CategoryContructor(idCategory,NaemCategory));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    arrayAdapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(PostDetailsUpdate.this, "" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> stringMap = new HashMap<>();
                token = dataLogin.getString("usertoken", "");
                stringMap.put("Authorization", token);
                return stringMap;
            }


        };
        requestQueue.add(stringRequest);
    }

    private void getJson(String url_connect_website) {
        setContentDialog("Loading", "Please wait...");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_connect_website,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            JSONObject jsonObject = jsonObject1.getJSONObject("data");
                            id = jsonObject.getString("id");
                            author_id = jsonObject.getString("author_id");
                            category_id = jsonObject.getString("category_id");
                            title = jsonObject.getString("title");
                            seo_title = jsonObject.getString("seo_title");
                            excerpt = jsonObject.getString("excerpt");
                            body = jsonObject.getString("body");
                            image = jsonObject.getString("image");
//                            author = jsonObject.getString("author");
                            slug = jsonObject.getString("slug");
                            meta_description = jsonObject.getString("meta_description");
                            meta_keywords = jsonObject.getString("meta_keywords");
//                                status = jsonObject.getString("status");
                            featured = jsonObject.getString("featured");
//                             created_at = jsonObject.getString("created_at");
//                             updated_at = jsonObject.getString("updated_at");
                            name = jsonObject.getString("name");
//                            avatar = jsonObject.getString("avatar");
//                            category = jsonObject.getString("category");
                            Picasso.get()
                                    .load(image)
                                    .placeholder(R.drawable.gallery100)
                                    .error(R.drawable.gallery100)
                                    .into(img_images_editDetails);
                            txtid.setText(id);
                            edt_title.setText(title);
                            spinner_Catelory.setSelection(Integer.parseInt(category_id)-1);
                            arrayAdapter.notifyDataSetChanged();
                            edt_excerpt.setText(Html.fromHtml(excerpt));
                            txtSlug.setText(slug);
                            edt_content.setText(Html.fromHtml(body));
                            txtauthor_id.setText(author_id);
                            txtseo_title.setText(seo_title);
                            edt_meta_description.setText(meta_description);
                            edt_meta_keywords.setText(meta_keywords);
//




//                            linkimages = URL_CONNECT_AVATAR + image;
//                            linkimagesAvatar = URL_CONNECT_AVATAR + avatar;
//                            String time1 = created_at.substring(0,4);
//                            String time2 = created_at.substring(5,7);
//                            String time3 = created_at.substring(8,10);
//                            String time4 = created_at.substring(11,16);
//                            String timeTong = time4+" "+time3+"/"+time2+"/"+time1;

//                            Picasso.get()
//                                    .load(linkimagesAvatar)
//                                    .placeholder(R.drawable.person40)
//                                    .error(R.drawable.person40)
//                                    .into(img_people);
//                            Picasso.get()
//                                    .load(linkimages)
//                                    .placeholder(R.drawable.gallery100)
//                                    .error(R.drawable.gallery100)
//                                    .into(img_title);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(PostDetailsUpdate.this, "" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> stringMap = new HashMap<>();
                String adminTrue = dataLogin.getString("adminTrue", "true");
                if(adminTrue.toString().equals("true")){
                    stringMap.put("admin", adminTrue);
                }
                stringMap.put("id", id);
                return stringMap;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> stringMap = new HashMap<>();
                token = dataLogin.getString("usertoken", "");
                stringMap.put("Authorization", token);
                return stringMap;
            }


        };
        requestQueue.add(stringRequest);
    }

    private void initControl() {
        img_images_editDetails = findViewById(R.id.img_images_editDetails);
        spinner_Catelory = findViewById(R.id.Spinner_category_editDetails);
        txtid = findViewById(R.id.ext_id_editDetails);
        txtauthor_id = findViewById(R.id.ext_author_id_editDetails);
        txtSlug = findViewById(R.id.ext_Slug_editDetails);
        txtseo_title = findViewById(R.id.ext_seo_title_editDetails);
        edt_excerpt = findViewById(R.id.ext_excerpt_editDetails);
        edt_meta_description = findViewById(R.id.ext_meta_description_editDetails);
        edt_meta_keywords = findViewById(R.id.ext_meta_keywords_editDetails);
        img_back = findViewById(R.id.img_back_MysiteToobar);
        img_find = findViewById(R.id.img_find_MysiteToobar);
        txt_Update = findViewById(R.id.txt_name_MySiteToobar);
        edt_title = findViewById(R.id.ext_title_editDetails);
        edt_content = findViewById(R.id.ext_content_editDetails);
        btnSave = findViewById(R.id.btn_browse_PostDetailsUpdate);
        img_imagesedit_editDetails = findViewById(R.id.img_imagesedit_editDetails);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back_MysiteToobar:
                intentView(PostDetails.class);
                break;
            case R.id.btn_browse_PostDetailsUpdate:
                String title = edt_title.getText().toString().trim();
                String content = edt_content.getText().toString().trim();
                String id = txtid.getText().toString().trim();
                String author_id = txtauthor_id.getText().toString().trim();
                String excerpt = edt_excerpt.getText().toString().trim();
                String Slug = txtSlug.getText().toString().trim();
                String seo_title = txtseo_title.getText().toString().trim();
                String meta_description = edt_meta_description.getText().toString().trim();
                String category_id = String.valueOf(arrayList.get(so).getIdCategory());
                String meta_keywords = edt_meta_keywords.getText().toString().trim();
                URL_CONNECT_UPDATE = ApiConnect.URL_CONNECT_UPDATE(Url);
                getJsonUpdate(URL_CONNECT_UPDATE, title, content,id,author_id,category_id,Slug,seo_title,meta_description,meta_keywords,excerpt);
                break;
            case R.id.img_imagesedit_editDetails:
                alertDiaLogUploadImages();
                break;
            case R.id.txt_camera_Upload:
                SDK_Camera();
                break;
            case R.id.txt_gallery_Upload:
                galleryUpload();
                break;
        }
    }
    private void getJsonUpdateImages(String url_update_image, final String image) {
        setContentDialog("Loading", "Please wait...");
        progressDialog.show();
        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, url_update_image,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Toast.makeText(PostDetailsUpdate.this, "Upload thành công", Toast.LENGTH_SHORT).show();
                        Log.d("URL","data:image/png;base64," + image);
                        dialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(PostDetailsUpdate.this, "Upload thất bại", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                Log.d("Error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("id", id);
                headers.put("photo", "data:image/png;base64," + image);
                return headers;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> stringMap = new HashMap<>();
                token  = dataLogin.getString("usertoken","");
                stringMap.put("Authorization",token);
                return stringMap;
            }
        };
        requestQueue1.add(stringRequest1);
    }



    private void SDK_Camera(){
        if (Build.VERSION.SDK_INT > 21) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                // Permission is already available, start camera preview
                Toast.makeText(this, "Starting preview.", Toast.LENGTH_SHORT).show();
//                            Snackbar.make(view_Account,
//                                    "Camera permission is available (API: " + Build.VERSION.SDK_INT + "). Starting preview.",
//                                    Snackbar.LENGTH_SHORT).show();

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {

                    startActivityForResult(takePictureIntent, CAMERA_REQUEST_MAX);
                }
            } else {
                // Permission is missing and must be requested.
                requestCameraPermission();
            }
        } else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePictureIntent, CAMERA_REQUEST);

        }
    }


    private void alertDiaLogUploadImages() {
        dialog.setContentView(R.layout.dialog_uploadimages_gallery);
        dialog.setTitle(getResources().getString(R.string.cachthuchien_txtUploadImages));
        final TextView txt_camera_Upload = (TextView) dialog.findViewById(R.id.txt_camera_Upload);
        final TextView txt_gallery_Upload = (TextView) dialog.findViewById(R.id.txt_gallery_Upload);
        txt_camera_Upload.setOnClickListener(this);
        txt_gallery_Upload.setOnClickListener(this);
        dialog.show();
    }


    private void requestCameraPermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
//            // Display a SnackBar with a button to request the missing permission.
            Toast.makeText(this, "Camera access is required to display the camera preview.", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Permission is not available. Requesting camera permission.", Toast.LENGTH_SHORT).show();
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CAMERA);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == this.RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            Bitmap image = extras.getParcelable("data");
            img_images_editDetails.setImageBitmap(image);
            final String image1 = decodeImage(image);
            getJsonUpdateImages(URL_CONNECT_UPDATE_IMAGES,image1);

        }
        if (resultCode == this.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                // get the Uri for the captured image
                picUri = data.getData();
                performCrop();
            } else if (requestCode == CAMERA_REQUEST_MAX) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                img_images_editDetails.setImageBitmap(imageBitmap);
                Bitmap bitmap = ((BitmapDrawable) img_images_editDetails.getDrawable()).getBitmap();
                final String image = decodeImage(bitmap);
                getJsonUpdateImages(URL_CONNECT_UPDATE_IMAGES,image);

            }
            else if (requestCode == PIC_CROP) {
                // get the returned data
                Bundle extras = data.getExtras();
                // get the cropped bitmap
                Bitmap thePic = extras.getParcelable("data");
                img_images_editDetails.setImageBitmap(thePic);
            }
        }
    }
    public String decodeImage(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
        byte[] byteImage = outputStream.toByteArray();
        String encodeImage = Base64.encodeToString(byteImage, Base64.NO_WRAP);
        return encodeImage;
    }

    private void performCrop() {
        // take care of exceptions
        try {
            // call the standard crop action intent (the user device may not
            // support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 200);
            cropIntent.putExtra("aspectY", 200);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 200);
            cropIntent.putExtra("outputY", 200);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {

        }
    }

    private void galleryUpload(){
        Intent imageDownload = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imageDownload.putExtra("crop", "true");
        imageDownload.putExtra("aspectX", 0);
        imageDownload.putExtra("aspectY", 0);
        imageDownload.putExtra("outputX", 200);
        imageDownload.putExtra("outputY", 150);
        imageDownload.putExtra("return-data", true);
        startActivityForResult(imageDownload, 2);
    }

    private void getJsonUpdate(String url_connect_update, final String title,final String content, final String id,final String author_id,final String category_id,final String Slug,final String seo_title,final String meta_description,final String meta_keywords,final String excerpt) {
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
                progressDialog.dismiss();
                Toast.makeText(PostDetailsUpdate.this, "" + error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("Error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> stringMap = new HashMap<>();
                stringMap.put("id", id);
                stringMap.put("title", title);
                stringMap.put("body", content);
                stringMap.put("excerpt", excerpt);
                stringMap.put("author_id", author_id);
                stringMap.put("category_id", category_id);
                stringMap.put("slug", Slug);
                stringMap.put("seo_title", seo_title);
                stringMap.put("meta_description", meta_description);
                stringMap.put("meta_keywords", meta_keywords);
                return stringMap;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> stringMap = new HashMap<>();
                token = dataLogin.getString("usertoken", "");
                stringMap.put("Authorization", token);
                stringMap.put("Accept", "application/json");
                return stringMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void intentView(Class c) {
        Intent intent = new Intent(PostDetailsUpdate.this, c);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        intentView(PostDetails.class);
        super.onBackPressed();
    }
}
