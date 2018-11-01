package vn.edu.poly.manager.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import vn.edu.poly.manager.Adapter.MySiteAdapter;
import vn.edu.poly.manager.Component.BaseActivity;
import vn.edu.poly.manager.Model.MySiteContructor;
import vn.edu.poly.manager.R;

public class MySiteActivity extends BaseActivity implements View.OnClickListener {
    ArrayList<MySiteContructor> arrayList;
    MySiteAdapter adapter;
    ListView lst_Mysite;
    Button btn_addSite;
    String Site = "";
    String Url = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_site);
        initControl();
        initData();
        initOnClick();
    }

    private void initOnClick() {
        btn_addSite.setOnClickListener(this);
    }

    private void initData() {
        Site = dataLogin.getString("SITE","");
        Url = http+dataLogin.getString("URL","");
        arrayList = new ArrayList<>();
        if(Site != ""&& Url != ""){
            arrayList.add(new MySiteContructor(Site,Url));
        }
        arrayList.add(new MySiteContructor("NKS","nks.com.vn"));
        arrayList.add(new MySiteContructor("Tìm Zì Ta","timzita.com"));
        adapter = new MySiteAdapter(this,arrayList);
        lst_Mysite.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        lst_Mysite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editor = dataLogin.edit();
                editor.putString("SITESignIn",arrayList.get(position).getTitleSite());
                editor.putString("URLSignIn",arrayList.get(position).getLinkeSite());
                editor.commit();
                intentView(SignIn.class);
            }
        });
    }

    private void intentView(Class c) {
        Intent intent = new Intent(MySiteActivity.this, c);
        startActivity(intent);
        finish();
    }

    private void initControl() {
        lst_Mysite = (ListView) findViewById(R.id.lst_Site_MySite);
        btn_addSite = (Button) findViewById(R.id.btn_addSite_MySite);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btn_addSite_MySite:
                intentView(MySiteAddSiteActivity.class);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        intentView(SignIn.class);
        super.onBackPressed();
    }
}
