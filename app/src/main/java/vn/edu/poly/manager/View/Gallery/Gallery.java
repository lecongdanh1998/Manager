package vn.edu.poly.manager.View.Gallery;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.transition.Transition;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.components.IMarker;

import net.alhazmy13.mediagallery.library.activity.MediaGallery;
import net.alhazmy13.mediagallery.library.views.MediaGalleryView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import vn.edu.poly.manager.Adapter.DanhSachAdapter;
import vn.edu.poly.manager.Adapter.LietKeAdapter;
import vn.edu.poly.manager.Component.BaseActivity;
import vn.edu.poly.manager.Component.ItemClick;
import vn.edu.poly.manager.Model.DanhSachGalleryContructor;
import vn.edu.poly.manager.Model.GalleryContructor;
import vn.edu.poly.manager.Model.LietKeGalleryContructor;
import vn.edu.poly.manager.R;
import vn.edu.poly.manager.Server.ApiConnect;
import vn.edu.poly.manager.View.MainActivity;
import vn.edu.poly.manager.View.Post.PostDetail.PostDetails;

public class Gallery extends Fragment implements View.OnClickListener {
    View view;
    private int PIC_CROP = 3;
    private Uri picUri;
    private int CAMERA_REQUEST = 1;
    private int CAMERA_REQUEST_MAX = 1999;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private static final int PERMISSION_REQUEST_CAMERA = 0;
    ImageView img_back,img_find,img_lietke,img_danhsach,img_upload_gallery;
    TextView txtGallery;
    EditText edtCategory,edtSearch,edtMoPhong;
    Button btnUpload;
    ArrayList<GalleryContructor> arrayList;
    ListView listView;
    GridView gridView;
    String Site = "";
    String Url = "";
    String URL_CONNECT_GALLERY = "";
    String URL_CONNECT_GALLERY_IMAGES = "";
    String URL_UPDATE_IMAGE = "";
    String URL_DELETE_IMAGES = "";
    String LinkImages = "";
    private ProgressDialog progressDialog;
    private ViewFlipper simpleViewFlipper;
    GridView viewGallery;
    ListView viewGallery1;
    String imagesCode;
    ImageView img_find_MysiteToobar;
    int NumberFind;
    int ImageNumber;
    ArrayList<LietKeGalleryContructor> arrayListLietKe;
    LietKeAdapter adapter;
    ArrayList<DanhSachGalleryContructor> arrayListDanhSach;
    DanhSachAdapter adapterDanhSach;
    String filename;
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
        NumberFind = 0;
        arrayListLietKe = new ArrayList<>();
        arrayListDanhSach = new ArrayList<>();
        adapterDanhSach = new DanhSachAdapter(arrayListDanhSach,getContext());
        adapter = new LietKeAdapter(arrayListLietKe,getContext());
        viewGallery1.setAdapter(adapter);
        viewGallery.setAdapter(adapterDanhSach);
        adapter.notifyDataSetChanged();
        adapterDanhSach.notifyDataSetChanged();
        edtSearch.setVisibility(View.INVISIBLE);
        edtMoPhong.setVisibility(View.GONE);
        progressDialog = new ProgressDialog(getContext());
        img_danhsach.setOnClickListener(this);
        img_lietke.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
        img_find_MysiteToobar.setOnClickListener(this);
        BaseActivity.editor = BaseActivity.dataLogin.edit();
        Site = BaseActivity.dataLogin.getString("SITESignIn", "");
        Url = BaseActivity.dataLogin.getString("URLSignIn", "");
        URL_CONNECT_GALLERY = ApiConnect.URL_CONNECT_GALLERYS(Url);
        URL_UPDATE_IMAGE = ApiConnect.URL_UPDATE_IMAGES(Url);
        URL_DELETE_IMAGES = ApiConnect.URL_DELETE_IMAGES(Url);
        URL_CONNECT_GALLERY_IMAGES = ApiConnect.URL_CONNECT_GALLERYS_IMAGES(Url);
        Lietke(URL_CONNECT_GALLERY);
        viewGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BaseActivity.editor.putInt("POSITION",position);
                BaseActivity.editor.commit();
                intentView(GalleryImageShow.class);
            }
        });
        viewGallery1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BaseActivity.editor.putInt("POSITION",position);
                BaseActivity.editor.commit();
                intentView(GalleryImageShow.class);
            }
        });


        viewGallery.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                alertLogDetele(URL_DELETE_IMAGES,arrayListLietKe.get(position).getImages());
                return false;
            }
        });

        viewGallery1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                alertLogDetele(URL_DELETE_IMAGES,arrayListLietKe.get(position).getImages());
                return false;
            }
        });



    }

    private void alertLogDetele(final String url_delete_images, final String position) {
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                getContext());
        alertDialog2.setTitle("Xóa...");
        alertDialog2.setMessage("Bạn có chắc chắn xóa ảnh này không?");
        alertDialog2.setIcon(R.drawable.ic_delete_black_241dp);
        alertDialog2.setPositiveButton("Có",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        getJsonDelete(url_delete_images,position);
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

    private void getJsonDelete(String url_delete_images, final String position) {
        filename=position.substring(position.lastIndexOf("/")+1);
        setContentDialog("Loading", "Please wait...");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_delete_images,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Ảnh này đã được xóa", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.putExtra("screen", "gallery");
                        startActivity(intent);
                        getActivity().finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "" + error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("Error", "" + error.toString());

            }
        }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("image",filename);
//                        headers.put("Content-Type", "application/json; charset=utf-8");

                return headers;
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




    private void Lietke(String url_connect_gallery) {
        img_lietke.setEnabled(false);
        img_danhsach.setEnabled(true);
        viewGallery1.setVisibility(View.VISIBLE);
        viewGallery.setVisibility(View.GONE);
        img_lietke.setImageResource(R.drawable.ic_format_list_bulleted_blue_40dp);
        img_danhsach.setImageResource(R.drawable.ic_apps_white_40dp);
        arrayListLietKe.clear();
        getJsonImageGalleryLietKe(URL_CONNECT_GALLERY, 1);


    }

    private void DanhSach(String url_connect_gallery) {
        img_lietke.setEnabled(true);
        img_danhsach.setEnabled(false);
        viewGallery1.setVisibility(View.GONE);
        viewGallery.setVisibility(View.VISIBLE);
        img_lietke.setImageResource(R.drawable.ic_format_list_white_black_40dp);
        img_danhsach.setImageResource(R.drawable.ic_apps_blue_40dp);
        arrayListDanhSach.clear();
        getJsonImageGalleryDanhSach(URL_CONNECT_GALLERY, 3);
    }

    private void getJsonImageGalleryLietKe(String url_connect_gallery, int i) {
        setContentDialog("Loading", "Please wait...");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_connect_gallery,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            for(int i = 1;i<jsonObject1.length();i++){
                                imagesCode = jsonObject1.getString(String.valueOf(i));
//                                LinkImages = URL_CONNECT_GALLERY_IMAGES + imagesCode;
//                                Log.d("URL",""+LinkImages);
                                arrayListLietKe.add(new LietKeGalleryContructor(imagesCode));
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




    private void getJsonImageGalleryDanhSach(String url_connect_gallery, final int so) {
        setContentDialog("Loading", "Please wait...");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_connect_gallery,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            for(int i = 1;i<jsonObject1.length();i++){
                                imagesCode = jsonObject1.getString(String.valueOf(i));
                                arrayListDanhSach.add(new DanhSachGalleryContructor(imagesCode));
                            }
                            adapterDanhSach.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "" + error.toString(), Toast.LENGTH_SHORT).show();
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

    private void initControl() {
        img_find_MysiteToobar = getActivity().findViewById(R.id.img_find_MysiteToobar);
        img_upload_gallery =view.findViewById(R.id.img_upload_gallery);
        img_danhsach = view.findViewById(R.id.img_danhsach_gallery);
        img_lietke = view.findViewById(R.id.img_lietke_gallery);
        edtCategory = view.findViewById(R.id.ext_Category_Gallery);
        edtSearch = view.findViewById(R.id.ext_Search_Gallery);
        btnUpload = view.findViewById(R.id.btn_UploadGallery);
        simpleViewFlipper = (ViewFlipper) view.findViewById(R.id.pager);
        viewGallery = (GridView) view.findViewById(R.id.gallery);
        viewGallery1 = (ListView) view.findViewById(R.id.gallery1);
        edtMoPhong = (EditText) view.findViewById(R.id.ext_MoPhong_Gallery);

        // Pager listener over indicator

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_danhsach_gallery:
                arrayListDanhSach.clear();
                DanhSach(URL_CONNECT_GALLERY);
                break;
            case R.id.img_lietke_gallery:
                arrayListLietKe.clear();
                Lietke(URL_CONNECT_GALLERY);
                break;
            case R.id.btn_UploadGallery:
                dialogUpload();
                break;
            case R.id.txt_camera_Upload:
                SDK_Camera();
                break;
            case R.id.txt_gallery_Upload:
                galleryUpload();
                break;
            case R.id.img_find_MysiteToobar:
                if(NumberFind == 0){
                    edtSearch.setVisibility(View.VISIBLE);
                    edtMoPhong.setVisibility(View.VISIBLE);
                    edtSearch.startAnimation(AnimationUtils.loadAnimation(
                            getContext(), R.anim.left));
                    NumberFind = 1;
                }
                else if(NumberFind == 1){
                    edtSearch.setVisibility(View.INVISIBLE);
                    edtMoPhong.setVisibility(View.GONE);
                    edtSearch.startAnimation(AnimationUtils.loadAnimation(
                            getContext(), R.anim.right));
                    NumberFind = 0;
                }
                break;

        }
    }

    private void dialogUpload() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_uploadimages_gallery);
        dialog.setTitle(getResources().getString(R.string.cachthuchien_txtUploadImages));
        final TextView txt_camera_Upload = (TextView) dialog.findViewById(R.id.txt_camera_Upload);
        final TextView txt_gallery_Upload = (TextView) dialog.findViewById(R.id.txt_gallery_Upload);
        txt_camera_Upload.setOnClickListener(this);
        txt_gallery_Upload.setOnClickListener(this);
        dialog.show();

    }


    private void intentView(Class c) {
        Intent intent = new Intent(getActivity(), c);
        startActivity(intent);
        getActivity().finish();
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
    public String decodeImage(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] byteImage = outputStream.toByteArray();
        String encodeImage = Base64.encodeToString(byteImage, Base64.NO_WRAP);
        return encodeImage;
    }



    private void requestCameraPermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CAMERA)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
//            // Display a SnackBar with a button to request the missing permission.
            Toast.makeText(getContext(), "Camera access is required to display the camera preview.", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getContext(), "Permission is not available. Requesting camera permission.", Toast.LENGTH_SHORT).show();

            Snackbar.make(view,
                    "Permission is not available. Requesting camera permission.",
                    Snackbar.LENGTH_SHORT).show();
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CAMERA);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == getActivity().RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            Bitmap image = extras.getParcelable("data");
            img_upload_gallery.setImageBitmap(image);
            final String image1 = decodeImage(image);
            getJsonUpdateImages(URL_UPDATE_IMAGE,image1);

        }
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                // get the Uri for the captured image
                picUri = data.getData();
                performCrop();
            } else if (requestCode == CAMERA_REQUEST_MAX) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                img_upload_gallery.setImageBitmap(imageBitmap);
                Bitmap bitmap = ((BitmapDrawable) img_upload_gallery.getDrawable()).getBitmap();
                final String image = decodeImage(bitmap);
                getJsonUpdateImages(URL_UPDATE_IMAGE,image);

            }

            // user is returning from cropping the image
            else if (requestCode == PIC_CROP) {
                // get the returned data
                Bundle extras = data.getExtras();
                // get the cropped bitmap
                Bitmap thePic = extras.getParcelable("data");
                img_upload_gallery.setImageBitmap(thePic);
            }


        }
    }

    private void getJsonUpdateImages(String url_update_image, final String image) {
        RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, url_update_image,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(), "Upload thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.putExtra("screen", "gallery");
                        startActivity(intent);
                        getActivity().finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Upload thất bại", Toast.LENGTH_SHORT).show();
                Log.d("Error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("image", "data:image/png;base64," + image);
//                        headers.put("Content-Type", "application/json; charset=utf-8");

                return headers;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> stringMap = new HashMap<>();
                BaseActivity.token  = BaseActivity.dataLogin.getString("usertoken","");
                stringMap.put("Authorization",BaseActivity.token);
                return stringMap;
            }
        };
        requestQueue1.add(stringRequest1);
    }


    private void SDK_Camera(){
        if (Build.VERSION.SDK_INT > 21) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                // Permission is already available, start camera preview
                Toast.makeText(getContext(), "Starting preview.", Toast.LENGTH_SHORT).show();
//                            Snackbar.make(view_Account,
//                                    "Camera permission is available (API: " + Build.VERSION.SDK_INT + "). Starting preview.",
//                                    Snackbar.LENGTH_SHORT).show();

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {

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
    private void galleryUpload(){
        Intent imageDownload = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imageDownload.putExtra("crop", "true");
        imageDownload.putExtra("aspectX", 200);
        imageDownload.putExtra("aspectY", 200);
        imageDownload.putExtra("outputX", 200);
        imageDownload.putExtra("outputY", 200);
        imageDownload.putExtra("return-data", true);
        startActivityForResult(imageDownload, 2);
    }

}
