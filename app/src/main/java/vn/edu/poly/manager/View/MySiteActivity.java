package vn.edu.poly.manager.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import vn.edu.poly.manager.Adapter.MySiteAdapter;
import vn.edu.poly.manager.Model.MySiteContructor;
import vn.edu.poly.manager.R;

public class MySiteActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<MySiteContructor> arrayList;
    MySiteAdapter adapter;
    ListView lst_Mysite;
    Button btn_addSite;
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
        arrayList = new ArrayList<>();
        arrayList.add(new MySiteContructor("NKS","nks.com.vn"));
        arrayList.add(new MySiteContructor("Tìm Zì Ta","timzita.com"));
        adapter = new MySiteAdapter(this,arrayList);
        lst_Mysite.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
                intent = new Intent(MySiteActivity.this,MySiteAddSiteActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        onBackPressed();
        super.onBackPressed();
    }
}
