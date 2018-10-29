package vn.edu.poly.manager.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import vn.edu.poly.manager.Component.BaseActivity;
import vn.edu.poly.manager.R;
import vn.edu.poly.manager.Util.ValidateForm;

public class MySiteAddSiteActivity extends BaseActivity implements View.OnClickListener {
    EditText extAddSite,extAddURL;
    Button btnAddCreate;
    private ProgressDialog progressDialog;
    String Site = "";
    String Url = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_site_add_site);
        initControl();
        initData();
        initOnClick();
    }

    private void setContentDialog(String title, String messager) {
        progressDialog.setTitle(title);
        progressDialog.setMessage(messager);
    }


    private void initOnClick() {
    }

    private void initData() {
        btnAddCreate.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        dataLogin = getSharedPreferences("data_login", MODE_PRIVATE);
        editor = dataLogin.edit();
    }

    private void initControl() {
        extAddSite = (EditText) findViewById(R.id.edt_Site_AddMySite);
        extAddURL = (EditText) findViewById(R.id.edt_URL_AddMySite);
        btnAddCreate = (Button) findViewById(R.id.btn_create_AddMySite);
    }
    private void intentView(Class c) {
        Intent intent = new Intent(MySiteAddSiteActivity.this, c);
        startActivity(intent);
        finish();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_create_AddMySite:
                Create();
                break;
        }
    }
    private void Create() {
        setContentDialog("Sign in", "Please wait...");
        progressDialog.show();
        Site = extAddSite.getText().toString().trim();
        Url = extAddURL.getText().toString().trim();
        editor.putString("SITE",Site);
        editor.putString("URL",Url);
        editor.commit();
        int errorCode = 0;

        if (new ValidateForm().validateTextEmpty(Site)){
            extAddSite.setHint("Please enter your Site!");
            extAddSite.setHintTextColor(getResources().getColor(R.color.colorPrimary));
            errorCode ++;
        }

        if (new ValidateForm().validateTextEmpty(Url)){
            extAddURL.setHint("Please enter your Url!");
            extAddURL.setHintTextColor(getResources().getColor(R.color.colorPrimary));
            errorCode ++;
        }

        if (errorCode == 0){
            intentView(MySiteActivity.class);
        }
//        intentView(MainActivity.class);
    }

}
