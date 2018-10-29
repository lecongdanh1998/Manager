package vn.edu.poly.manager.View;

import android.app.AlertDialog;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import vn.edu.poly.manager.Adapter.MenuAdapter;
import vn.edu.poly.manager.Component.BaseActivity;
import vn.edu.poly.manager.Model.MenuModel;
import vn.edu.poly.manager.R;
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
    private SharedPreferences dataLogin;
    private SharedPreferences.Editor editor;
    private ImageView img_back_MysiteToobar;
    private ImageView btn_cancel;
    private RelativeLayout btn_setting,btn_logout_main;
    private ImageView img_find_MysiteToobar;
    String screen;
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
    }

    /*
     * init data for layout
     * */
    private void initData() {
        dataLogin = getSharedPreferences("dataLogin", MODE_PRIVATE);
        editor = dataLogin.edit();
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
        menuAdapter = new MenuAdapter(this, menuModelArrayList, R.layout.row_menu);
        listview_menu.setAdapter(menuAdapter);
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
        super.onBackPressed();
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
        }
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
                        BaseActivity.editor = BaseActivity.dataLogin.edit();
                        BaseActivity.editor.putString("useremail", "");
                        BaseActivity.editor.putString("userpassword", "");
                        BaseActivity.editor.commit();
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
