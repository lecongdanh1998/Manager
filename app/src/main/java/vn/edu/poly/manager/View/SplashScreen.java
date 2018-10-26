package vn.edu.poly.manager.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import vn.edu.poly.manager.Component.BaseActivity;
import vn.edu.poly.manager.R;
import vn.edu.poly.manager.Util.ValidateForm;

import static vn.edu.poly.manager.Component.BaseActivity.dataLogin;

public class SplashScreen extends BaseActivity {

    private int SPLASH_DISPLAY_LENGTH = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        checkDataLogin();
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                checkDataLogin();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void checkDataLogin() {
        String useremail = dataLogin.getString("useremail", "");
        if (new ValidateForm().validateTextEmpty(useremail) == false){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else {
            Intent mainIntent = new Intent(SplashScreen.this,SignIn.class);
            SplashScreen.this.startActivity(mainIntent);
        }
        finish();
    }
}
