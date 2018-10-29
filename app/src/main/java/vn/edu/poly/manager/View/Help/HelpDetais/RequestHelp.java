package vn.edu.poly.manager.View.Help.HelpDetais;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import vn.edu.poly.manager.R;
import vn.edu.poly.manager.View.MainActivity;

public class RequestHelp extends AppCompatActivity implements View.OnClickListener {

    private TextView toolbar_title;
    private ImageView img_back_MysiteToobar, img_find_MysiteToobar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_help);
        initView();
    }

    private void initView() {
        toolbar_title = findViewById(R.id.txt_name_MySiteToobar);
        img_back_MysiteToobar = findViewById(R.id.img_back_MysiteToobar);
        img_find_MysiteToobar = findViewById(R.id.img_find_MysiteToobar);
        img_find_MysiteToobar.setImageDrawable(getDrawable(R.drawable.ic_add_black_24dp));
        img_back_MysiteToobar.setOnClickListener(this);
        toolbar_title.setText(getResources().getString(R.string.infor_tiket));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back_MysiteToobar:
                Intent intent = new Intent(RequestHelp.this, MainActivity.class);
                intent.putExtra("screen", "help");
                startActivity(intent);
                finish();
                break;
        }
    }
}
