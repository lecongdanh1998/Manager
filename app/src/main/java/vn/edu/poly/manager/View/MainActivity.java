package vn.edu.poly.manager.View;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.poly.manager.Adapter.MenuAdapter;
import vn.edu.poly.manager.Component.BaseActivity;
import vn.edu.poly.manager.Model.MenuModel;
import vn.edu.poly.manager.R;
import vn.edu.poly.manager.Server.ApiConnect;
import vn.edu.poly.manager.View.Account.Account;
import vn.edu.poly.manager.View.Contact.Contact;
import vn.edu.poly.manager.View.Dashboard.Dashboard;
import vn.edu.poly.manager.View.Gallery.Gallery;
import vn.edu.poly.manager.View.Help.Help;
import vn.edu.poly.manager.View.Post.Post;
import vn.edu.poly.manager.View.Post.PostDetail.PostDetails;
import vn.edu.poly.manager.View.Setting.Setting;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private DrawerLayout drawer_layout;
    private NavigationView nav_view;
    private Toolbar toolbar;
    private ListView listview_menu;
    private MenuAdapter menuAdapter;
    private ArrayList<MenuModel> menuModelArrayList;
    private TextView toolbar_title;
    private ImageView img_back_MysiteToobar;
    private ImageView btn_cancel;
    private RelativeLayout btn_setting,btn_logout_main;
    private ImageView img_find_MysiteToobar;
    String screen;
    String email = "";
    String password = "";
    CircleImageView avatarImages;
    TextView txtEmail,txtName;
    private ProgressDialog progressDialog;
    String URL_GET_USER_INFOR = "";
    String Url = "";
    RelativeLayout layout_header_navigation_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initEventButton();
        try {
            Intent intent = getIntent();
            screen = intent.getStringExtra("screen");
            if (screen.equalsIgnoreCase("post")){
                transactionFrangment(new Post(), "Post");
            } else if (screen.equalsIgnoreCase("help")){
                transactionFrangment(new Help(), "Help");
            }else if(screen.equalsIgnoreCase("gallery")){
                transactionFrangment(new Gallery(), "My Gallery");
            }
            else {
                transactionFrangment(new Dashboard(), "Dashboard");
            }
        } catch (Exception e){
            if (savedInstanceState == null) {
                transactionFrangment(new Dashboard(), "Dashboard");
            }
        }
    }

    /*
     * create and register layout
     * */
    private void initView() {
        progressDialog = new ProgressDialog(this);
        layout_header_navigation_main = findViewById(R.id.layout_header_navigation_main);
        avatarImages = findViewById(R.id.avatar_user_header_navigation_main);
        txtName = findViewById(R.id.txt_name_header_navigation_main);
        txtEmail = findViewById(R.id.txt_role_header_navigation_main);
        drawer_layout = findViewById(R.id.drawer_layout);
        nav_view = findViewById(R.id.nav_view);
        toolbar_title = findViewById(R.id.txt_name_MySiteToobar);
        listview_menu = findViewById(R.id.listview_menu);
        img_back_MysiteToobar = findViewById(R.id.img_back_MysiteToobar);
        img_find_MysiteToobar = findViewById(R.id.img_find_MysiteToobar);
        img_back_MysiteToobar.setImageResource(R.drawable.ic_menu_white);
        btn_setting = findViewById(R.id.btn_setting_main);
        btn_logout_main = findViewById(R.id.btn_logout_main);
    }

    /*
     * add event for button
     * list view
     * ...
     * */
    private void initEventButton() {
        listview_menu.setOnItemClickListener(this);
        img_back_MysiteToobar.setOnClickListener(this);
        btn_setting.setOnClickListener(this);
        img_find_MysiteToobar.setOnClickListener(this);
        btn_logout_main.setOnClickListener(this);
        layout_header_navigation_main.setOnClickListener(this);
    }

    /*
     * init data for layout
     * */
    private void initData() {
        editor = dataLogin.edit();
        email = dataLogin.getString("useremail","");
        password = dataLogin.getString("userpassword","");
        Url = dataLogin.getString("URLSignIn","");
        Log.d("URL123",""+Url);
        URL_GET_USER_INFOR = ApiConnect.URL_GET_USER_INFOR(Url);
        /*
         * create menu arraylist
         * */
        menuModelArrayList = new ArrayList<>();
        menuModelArrayList.add(new MenuModel("Dashboard"));
        menuModelArrayList.add(new MenuModel("Notifications"));
        menuModelArrayList.add(new MenuModel("Post"));
        menuModelArrayList.add(new MenuModel("Gallery"));
        menuModelArrayList.add(new MenuModel("Contact"));
        menuModelArrayList.add(new MenuModel("Help"));
        /*
         * create menu adapter
         * */
        menuAdapter = new MenuAdapter(this, menuModelArrayList, "3");
        listview_menu.setAdapter(menuAdapter);

        getJsonINFOR(URL_GET_USER_INFOR);


    }
    private void setContentDialog(String title, String messager) {
        progressDialog.setTitle(title);
        progressDialog.setMessage(messager);
    }

    private void getJsonINFOR(String url_get_user_infor) {
        setContentDialog("Loading", "Please wait...");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_get_user_infor,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            idUser = jsonObject1.getString("id");
                            role_idUser = jsonObject1.getString("role_id");
                            nameUser = jsonObject1.getString("name");
                            emailUser = jsonObject1.getString("email");
                            avatarUser = jsonObject1.getString("avatar");
                            email_verified_atUser = jsonObject1.getString("email_verified_at");
                            passwordUser = jsonObject1.getString("password");
                            remember_tokenUser = jsonObject1.getString("remember_token");
                            settingsUser = jsonObject1.getString("settings");
                            created_atUser = jsonObject1.getString("created_at");
                            updated_atUser = jsonObject1.getString("updated_at");
                            role_nameUser = jsonObject1.getString("role_name");
                            txtName.setText(nameUser);
                            txtEmail.setText(emailUser);
                            Picasso.get()
                                    .load(avatarUser)
                                    .placeholder(R.drawable.ic_supervisor_account_black_120dp)
                                    .error(R.drawable.ic_supervisor_account_black_120dp)
                                    .into(avatarImages);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> stringMap = new HashMap<>();
                stringMap.put("email", email);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
    /*
     * drawer menu open when click ic_menu
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
     * item menu onclick change fragment
     * */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new Dashboard();
                transactionFrangment(fragment, "Dashboard");
                break;
            case 1:
                //Notifications
                break;
            case 2:
                fragment = new Post();
                transactionFrangment(fragment, "Post");
                break;
            case 3:
                fragment = new Gallery();
                transactionFrangment(fragment, "My Gallery");
                break;
            case 4:
                fragment = new Contact();
                transactionFrangment(fragment, "Contact");
                break;
            case 5:
                fragment = new Help();
                transactionFrangment(fragment, "Help");
                break;
            case 7:
                break;
            default:
                break;
        }
    }

    /*
     * method change fragment
     * */
    public void transactionFrangment(Fragment f, String s) {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                f, s).commit();
        toolbar_title.setText(s);
        drawer_layout.closeDrawers();
    }

    @Override
    public void onBackPressed() {
        AlertDialogExit();
    }

    private void AlertDialogExit() {
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                MainActivity.this);
        alertDialog2.setTitle("Thoát...");
        alertDialog2.setMessage("Bạn có chắc chắn thoát không?");
        alertDialog2.setIcon(R.drawable.exit);
        alertDialog2.setPositiveButton("Có",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Fragment fragment;
        switch (id){
            case R.id.img_back_MysiteToobar:
                drawer_layout.openDrawer(GravityCompat.START);
                break;
            case R.id.btn_setting_main:
                transactionFrangment(new Setting(), "Setting");
                break;
            case R.id.img_find_MysiteToobar:
                break;
            case R.id.btn_logout_main:
                alertLogout();
                break;
            case R.id.layout_header_navigation_main:
                editor.putString("userpassword",password);
                editor.commit();
                intentView(Account.class);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void alertLogout() {
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                MainActivity.this);
        alertDialog2.setTitle("Đăng xuất...");
        alertDialog2.setMessage("Bạn có chắc chắn muốn đăng xuất không?");
        alertDialog2.setIcon(R.drawable.exit);
        alertDialog2.setPositiveButton("Có",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        editor = dataLogin.edit();
                        editor.putString("useremail", "");
                        editor.putString("userpassword", "");
                        editor.commit();
                        intentView(SignIn.class);
                    }
                });
        alertDialog2.setNegativeButton("Không",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog2.show();
    }
    private void intentView(Class c) {
        Intent intent = new Intent(MainActivity.this,c );
        startActivity(intent);
        finish();
    }

}
