package vn.edu.poly.manager.View.Account;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
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

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.poly.manager.Component.BaseActivity;
import vn.edu.poly.manager.R;
import vn.edu.poly.manager.Server.ApiConnect;
import vn.edu.poly.manager.View.MainActivity;

public class Account extends BaseActivity implements View.OnClickListener {
    private ProgressDialog progressDialog;
    String email = "";
    String URL_GET_USER_INFOR = "";
    String URL_UPDATE_USER_INFOR = "";
    String URL_UPDATE_USER_AVATAR = "";
    String password = "";
    String Url = "";
    private EditText edt_password_account, edt_user_account, edt_email_account;
    CircleImageView imageViewAvatar, avatar_BackgroundCamera, avatar_Background;
    private TextInputLayout textInput_username_account, textInput_password_account, textInput_email_account;
    Button btnSaveAccount;
    ImageView imageViewBack, imageViewFind;
    TextView txtTitle, txt_passwordrandom_account;
    private int PIC_CROP = 3;
    private Uri picUri;
    private int CAMERA_REQUEST = 1;
    private int CAMERA_REQUEST_MAX = 1999;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private static final int PERMISSION_REQUEST_CAMERA = 0;
    Dialog dialog, dialog1;
    TextView txt_edit_MySiteToobar;
    int MAX_CHAR = 8;
    int backNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        initControl();
        initData();
    }

    private void initData() {
        backNumber = 0;
        editor = dataLogin.edit();
        dialog = new Dialog(this);
        dialog1 = new Dialog(this);
        imageViewFind.setVisibility(View.INVISIBLE);
        txt_edit_MySiteToobar.setVisibility(View.VISIBLE);
        edt_user_account.setEnabled(false);
        edt_password_account.setEnabled(false);
        edt_email_account.setEnabled(false);
        txtTitle.setText(getResources().getString(R.string.Str_btnSaveAccount));
        edt_password_account.setTypeface(Typeface.DEFAULT);
        edt_password_account.setTransformationMethod(new PasswordTransformationMethod());
        email = dataLogin.getString("useremail", "");
        password = dataLogin.getString("userpassword", "");
        Url = dataLogin.getString("URLSignIn", "");
        URL_GET_USER_INFOR = ApiConnect.URL_GET_USER_INFOR(Url);
        URL_UPDATE_USER_INFOR = ApiConnect.URL_UPDATE_USER_INFOR(Url);
        URL_UPDATE_USER_AVATAR = ApiConnect.URL_UPDATE_USER_AVATAR(Url);
        getJsonINFOR(URL_GET_USER_INFOR);
        btnSaveAccount.setOnClickListener(this);
        imageViewBack.setOnClickListener(this);
        imageViewAvatar.setOnClickListener(this);
        txt_edit_MySiteToobar.setOnClickListener(this);
        txt_passwordrandom_account.setOnClickListener(this);
        avatar_BackgroundCamera.setOnClickListener(this);
    }

    private void initControl() {
        progressDialog = new ProgressDialog(this);
        textInput_username_account = findViewById(R.id.textInput_username_Account);
        textInput_password_account = findViewById(R.id.textInput_password_Account);
        textInput_email_account = findViewById(R.id.textInput_email_Account);
        edt_email_account = findViewById(R.id.edt_email_Account);
        edt_password_account = findViewById(R.id.edt_password_Account);
        edt_user_account = findViewById(R.id.edt_username_Account);
        imageViewAvatar = findViewById(R.id.avatar_user_header_navigation_main);
        btnSaveAccount = findViewById(R.id.btn_save_account);
        txtTitle = findViewById(R.id.txt_name_MySiteToobar);
        imageViewBack = findViewById(R.id.img_back_MysiteToobar);
        imageViewFind = findViewById(R.id.img_find_MysiteToobar);
        txt_edit_MySiteToobar = findViewById(R.id.txt_edit_MySiteToobar);
        txt_passwordrandom_account = findViewById(R.id.txt_passwordrandom_account);
        avatar_BackgroundCamera = findViewById(R.id.avatar_BackgroundCamera);
        avatar_Background = findViewById(R.id.avatar_Background);
    }

    private void intentView(Class c) {
        Intent intent = new Intent(Account.this, c);
        startActivity(intent);
        finish();
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
                            editor.putString("userpassword", passwordUser);
                            editor.commit();
                            editor.putString("userpassword", password);
                            editor.commit();
                            edt_email_account.setText(emailUser);
                            edt_password_account.setText(password);
                            edt_user_account.setText(nameUser);
                            Picasso.get()
                                    .load(avatarUser)
                                    .placeholder(R.drawable.ic_supervisor_account_black_120dp)
                                    .error(R.drawable.ic_supervisor_account_black_120dp)
                                    .into(imageViewAvatar);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Account.this, "" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save_account:
                String emailUpdate = edt_email_account.getText().toString().trim();
                String nameUpdate = edt_user_account.getText().toString().trim();
                String passwordUpdate = edt_password_account.getText().toString().trim();
                getJsonUpdateInfo(URL_UPDATE_USER_INFOR, emailUpdate, nameUpdate, passwordUpdate);
                break;
            case R.id.img_back_MysiteToobar:
                onBackPressed();
                break;
            case R.id.avatar_user_header_navigation_main:
                break;
            case R.id.txt_camera_Upload:
                SDK_Camera();
                break;
            case R.id.txt_gallery_Upload:
                galleryUpload();
                break;
            case R.id.txt_edit_MySiteToobar:
                backNumber = 1;
                Edit();
                break;
            case R.id.txt_passwordrandom_account:
                PasswordRanDom();
                break;
            case R.id.avatar_BackgroundCamera:
                alertDiaLogUploadImages();
                break;

        }
    }

    private void PasswordRanDom() {
        dialog1.setContentView(R.layout.dialogpasswordrandom);
        dialog1.setTitle("Tạo mật khẩu");
        final TextView txt_PasswordRandom = (TextView) dialog1.findViewById(R.id.ext_PasswordRandom);
        final CheckBox checkBoxSymbols = (CheckBox) dialog1.findViewById(R.id.checkbox_Symbols);
        final CheckBox checkBoxNumbers = (CheckBox) dialog1.findViewById(R.id.checkbox_Numbers);
        final CheckBox checkBoxLowercase = (CheckBox) dialog1.findViewById(R.id.checkbox_Lowercase);
        final CheckBox checkBoxAmbiguous = (CheckBox) dialog1.findViewById(R.id.checkbox_Ambiguous);
        final CheckBox checkBoxUppercase = (CheckBox) dialog1.findViewById(R.id.checkbox_Uppercase);
        final RelativeLayout RLT_passwordGenare = (RelativeLayout) dialog1.findViewById(R.id.RLT_passwordGenare);
        final CheckBox check_save_password = (CheckBox) dialog1.findViewById(R.id.check_save_password);
        Button btn_save_passwordRandom = (Button) dialog1.findViewById(R.id.btn_save_passwordRandom);
        TextView txt_advance = (TextView) dialog1.findViewById(R.id.txt_advance);
        txt_advance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RLT_passwordGenare.setVisibility(View.VISIBLE);
            }
        });
        SeekBar seekBar = (SeekBar) dialog1.findViewById(R.id.SB_length_random);
        checkBoxSymbols.setChecked(true);
        try {
            String allowedCharacters = "";
            if (checkBoxUppercase.isChecked()) {
                allowedCharacters += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            }
            if (checkBoxLowercase.isChecked()) {
                allowedCharacters += "abcdefghijklmnopqrstuvwxyz";
            }
            if (checkBoxNumbers.isChecked()) {
                allowedCharacters += "0123456789";
            }
            if (checkBoxSymbols.isChecked()) {
                allowedCharacters += "!@#$%^&*()_-+=<>?/{}~|";
            }
            if (checkBoxAmbiguous.isChecked()) {
                allowedCharacters += "{[',;:.<\"";
            }
            final Random random = new Random();
            final StringBuilder sb = new StringBuilder();

            for (int i = 0; i < MAX_CHAR; ++i) {
                sb.append(allowedCharacters.charAt(random.nextInt(allowedCharacters.length())));
            }
            txt_PasswordRandom.setText(sb);
        } catch (Exception e) {
            e.printStackTrace();
        }
        seekBar.setProgress(MAX_CHAR);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("Length: ", "" + progress);
                MAX_CHAR = seekBar.getProgress();
                Log.d("MAX_CHAR ", "" + MAX_CHAR);
                if (progress >= 8) {
                    try {
                        String allowedCharacters = "";
                        if (checkBoxUppercase.isChecked()) {
                            allowedCharacters += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                        }
                        if (checkBoxLowercase.isChecked()) {
                            allowedCharacters += "abcdefghijklmnopqrstuvwxyz";
                        }
                        if (checkBoxNumbers.isChecked()) {
                            allowedCharacters += "0123456789";
                        }
                        if (checkBoxSymbols.isChecked()) {
                            allowedCharacters += "!@#$%^&*()_-+=<>?/{}~|";
                        }
                        if (checkBoxAmbiguous.isChecked()) {
                            allowedCharacters += "{[',;:.<\"";
                        }
                        final Random random = new Random();
                        final StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < MAX_CHAR; ++i) {
                            sb.append(allowedCharacters.charAt(random.nextInt(allowedCharacters.length())));
                        }
                        txt_PasswordRandom.setText(sb);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() == 0)
                    MAX_CHAR = 8;
                seekBar.setProgress(MAX_CHAR);
            }
        });

        checkBoxUppercase.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String allowedCharacters = "";
                    if (checkBoxUppercase.isChecked()) {
                        allowedCharacters += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                    }
                    if (checkBoxLowercase.isChecked()) {
                        allowedCharacters += "abcdefghijklmnopqrstuvwxyz";
                    }
                    if (checkBoxNumbers.isChecked()) {
                        allowedCharacters += "0123456789";
                    }
                    if (checkBoxSymbols.isChecked()) {
                        allowedCharacters += "!@#$%^&*()_-+=<>?/{}~|";
                    }
                    if (checkBoxAmbiguous.isChecked()) {
                        allowedCharacters += "{[',;:.<\"";
                    }
                    final Random random = new Random();
                    final StringBuilder sb = new StringBuilder();

                    for (int i = 0; i < MAX_CHAR; ++i) {
                        sb.append(allowedCharacters.charAt(random.nextInt(allowedCharacters.length())));
                    }
                    txt_PasswordRandom.setText(sb);
                } else {
                    try {
                        String allowedCharacters = "";
                        if (checkBoxUppercase.isChecked()) {
                            allowedCharacters += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                        }
                        if (checkBoxLowercase.isChecked()) {
                            allowedCharacters += "abcdefghijklmnopqrstuvwxyz";
                        }
                        if (checkBoxNumbers.isChecked()) {
                            allowedCharacters += "0123456789";
                        }
                        if (checkBoxSymbols.isChecked()) {
                            allowedCharacters += "!@#$%^&*()_-+=<>?/{}~|";
                        }
                        if (checkBoxAmbiguous.isChecked()) {
                            allowedCharacters += "{[',;:.<\"";
                        }
                        final Random random = new Random();
                        final StringBuilder sb = new StringBuilder();

                        for (int i = 0; i < MAX_CHAR; ++i) {
                            sb.append(allowedCharacters.charAt(random.nextInt(allowedCharacters.length())));
                        }
                        txt_PasswordRandom.setText(sb);
                    } catch (Exception e) {
                        txt_PasswordRandom.setText("Your password");
                    }
                }
            }
        });
        checkBoxLowercase.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String allowedCharacters = "";
                    if (checkBoxUppercase.isChecked()) {
                        allowedCharacters += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                    }
                    if (checkBoxLowercase.isChecked()) {
                        allowedCharacters += "abcdefghijklmnopqrstuvwxyz";
                    }
                    if (checkBoxNumbers.isChecked()) {
                        allowedCharacters += "0123456789";
                    }
                    if (checkBoxSymbols.isChecked()) {
                        allowedCharacters += "!@#$%^&*()_-+=<>?/{}~|";
                    }
                    if (checkBoxAmbiguous.isChecked()) {
                        allowedCharacters += "{[',;:.<\"";
                    }
                    final Random random = new Random();
                    final StringBuilder sb = new StringBuilder();

                    for (int i = 0; i < MAX_CHAR; ++i) {
                        sb.append(allowedCharacters.charAt(random.nextInt(allowedCharacters.length())));
                    }
                    txt_PasswordRandom.setText(sb);
                } else {
                    try {
                        String allowedCharacters = "";
                        if (checkBoxUppercase.isChecked()) {
                            allowedCharacters += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                        }
                        if (checkBoxLowercase.isChecked()) {
                            allowedCharacters += "abcdefghijklmnopqrstuvwxyz";
                        }
                        if (checkBoxNumbers.isChecked()) {
                            allowedCharacters += "0123456789";
                        }
                        if (checkBoxSymbols.isChecked()) {
                            allowedCharacters += "!@#$%^&*()_-+=<>?/{}~|";
                        }
                        if (checkBoxAmbiguous.isChecked()) {
                            allowedCharacters += "{[',;:.<\"";
                        }
                        final Random random = new Random();
                        final StringBuilder sb = new StringBuilder();

                        for (int i = 0; i < MAX_CHAR; ++i) {
                            sb.append(allowedCharacters.charAt(random.nextInt(allowedCharacters.length())));
                        }
                        txt_PasswordRandom.setText(sb);
                    } catch (Exception e) {
                        txt_PasswordRandom.setText("Your password");
                    }
                }

            }
        });
        checkBoxNumbers.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String allowedCharacters = "";
                    if (checkBoxUppercase.isChecked()) {
                        allowedCharacters += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                    }
                    if (checkBoxLowercase.isChecked()) {
                        allowedCharacters += "abcdefghijklmnopqrstuvwxyz";
                    }
                    if (checkBoxNumbers.isChecked()) {
                        allowedCharacters += "0123456789";
                    }
                    if (checkBoxSymbols.isChecked()) {
                        allowedCharacters += "!@#$%^&*()_-+=<>?/{}~|";
                    }
                    if (checkBoxAmbiguous.isChecked()) {
                        allowedCharacters += "{[',;:.<\"";
                    }
                    final Random random = new Random();
                    final StringBuilder sb = new StringBuilder();

                    for (int i = 0; i < MAX_CHAR; ++i) {
                        sb.append(allowedCharacters.charAt(random.nextInt(allowedCharacters.length())));
                    }
                    txt_PasswordRandom.setText(sb);
                } else {
                    try {
                        String allowedCharacters = "";
                        if (checkBoxUppercase.isChecked()) {
                            allowedCharacters += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                        }
                        if (checkBoxLowercase.isChecked()) {
                            allowedCharacters += "abcdefghijklmnopqrstuvwxyz";
                        }
                        if (checkBoxNumbers.isChecked()) {
                            allowedCharacters += "0123456789";
                        }
                        if (checkBoxSymbols.isChecked()) {
                            allowedCharacters += "!@#$%^&*()_-+=<>?/{}~|";
                        }
                        if (checkBoxAmbiguous.isChecked()) {
                            allowedCharacters += "{[',;:.<\"";
                        }

                        final Random random = new Random();
                        final StringBuilder sb = new StringBuilder();

                        for (int i = 0; i < MAX_CHAR; ++i) {
                            sb.append(allowedCharacters.charAt(random.nextInt(allowedCharacters.length())));
                        }
                        txt_PasswordRandom.setText(sb);
                    } catch (Exception e) {
                        txt_PasswordRandom.setText("Your password");
                    }
                }

            }
        });
        checkBoxSymbols.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String allowedCharacters = "";
                    if (checkBoxUppercase.isChecked()) {
                        allowedCharacters += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                    }
                    if (checkBoxLowercase.isChecked()) {
                        allowedCharacters += "abcdefghijklmnopqrstuvwxyz";
                    }
                    if (checkBoxNumbers.isChecked()) {
                        allowedCharacters += "0123456789";
                    }
                    if (checkBoxSymbols.isChecked()) {
                        allowedCharacters += "!@#$%^&*()_-+=<>?/{}~|";
                    }
                    if (checkBoxAmbiguous.isChecked()) {
                        allowedCharacters += "{[',;:.<\"";
                    }
                    final Random random = new Random();
                    final StringBuilder sb = new StringBuilder();

                    for (int i = 0; i < MAX_CHAR; ++i) {
                        sb.append(allowedCharacters.charAt(random.nextInt(allowedCharacters.length())));
                    }
                    txt_PasswordRandom.setText(sb);
                } else {
                    try {
                        String allowedCharacters = "";
                        if (checkBoxUppercase.isChecked()) {
                            allowedCharacters += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                        }
                        if (checkBoxLowercase.isChecked()) {
                            allowedCharacters += "abcdefghijklmnopqrstuvwxyz";
                        }
                        if (checkBoxNumbers.isChecked()) {
                            allowedCharacters += "0123456789";
                        }
                        if (checkBoxSymbols.isChecked()) {
                            allowedCharacters += "!@#$%^&*()_-+=<>?/{}~|";
                        }
                        if (checkBoxAmbiguous.isChecked()) {
                            allowedCharacters += "{[',;:.<\"";
                        }

                        final Random random = new Random();
                        final StringBuilder sb = new StringBuilder();

                        for (int i = 0; i < MAX_CHAR; ++i) {
                            sb.append(allowedCharacters.charAt(random.nextInt(allowedCharacters.length())));
                        }
                        txt_PasswordRandom.setText(sb);
                    } catch (Exception e) {
                        txt_PasswordRandom.setText("Your password");
                    }
                }
            }
        });
        checkBoxAmbiguous.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String allowedCharacters = "";
                    if (checkBoxUppercase.isChecked()) {
                        allowedCharacters += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                    }
                    if (checkBoxLowercase.isChecked()) {
                        allowedCharacters += "abcdefghijklmnopqrstuvwxyz";
                    }
                    if (checkBoxNumbers.isChecked()) {
                        allowedCharacters += "0123456789";
                    }
                    if (checkBoxSymbols.isChecked()) {
                        allowedCharacters += "!@#$%^&*()_-+=<>?/{}~|";
                    }
                    if (checkBoxAmbiguous.isChecked()) {
                        allowedCharacters += "{[',;:.<\"";
                    }
                    final Random random = new Random();
                    final StringBuilder sb = new StringBuilder();

                    for (int i = 0; i < MAX_CHAR; ++i) {
                        sb.append(allowedCharacters.charAt(random.nextInt(allowedCharacters.length())));
                    }
                    txt_PasswordRandom.setText(sb);
                } else {
                    try {
                        String allowedCharacters = "";
                        if (checkBoxUppercase.isChecked()) {
                            allowedCharacters += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                        }
                        if (checkBoxLowercase.isChecked()) {
                            allowedCharacters += "abcdefghijklmnopqrstuvwxyz";
                        }
                        if (checkBoxNumbers.isChecked()) {
                            allowedCharacters += "0123456789";
                        }
                        if (checkBoxSymbols.isChecked()) {
                            allowedCharacters += "!@#$%^&*()_-+=<>?/{}~|";
                        }
                        if (checkBoxAmbiguous.isChecked()) {
                            allowedCharacters += "{[',;:.<\"";
                        }
                        final Random random = new Random();
                        final StringBuilder sb = new StringBuilder();

                        for (int i = 0; i < MAX_CHAR; ++i) {
                            sb.append(allowedCharacters.charAt(random.nextInt(allowedCharacters.length())));
                        }
                        txt_PasswordRandom.setText(sb);
                    } catch (Exception e) {
                        txt_PasswordRandom.setText("Your password");
                    }
                }

            }
        });
        btn_save_passwordRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_save_password.isChecked()) {
                    String passrandom = txt_PasswordRandom.getText().toString();
                    edt_password_account.setText(passrandom);
                    dialog1.dismiss();
                } else {
                    Toast.makeText(Account.this, "Vui lòng đồng ý điều khoản", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog1.show();
    }

    private void Edit() {
        txt_edit_MySiteToobar.setVisibility(View.INVISIBLE);
        edt_user_account.setEnabled(true);
        edt_user_account.setBackground(getResources().getDrawable(R.drawable.textlines));
        edt_password_account.setBackground(getResources().getDrawable(R.drawable.textlines));
        edt_email_account.setBackground(getResources().getDrawable(R.drawable.textlines));
        edt_email_account.setEnabled(true);
        edt_password_account.setEnabled(true);
        txt_passwordrandom_account.setVisibility(View.VISIBLE);
        btnSaveAccount.setVisibility(View.VISIBLE);
        avatar_Background.setVisibility(View.VISIBLE);
        avatar_BackgroundCamera.setVisibility(View.VISIBLE);
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

    private void getJsonUpdateInfo(String URL_UPDATE_USER_INFOR, final String emailUpdate, final String nameUpdate, final String passwordUpdate) {
        setContentDialog("Loading", "Please wait...");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATE_USER_INFOR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Toast.makeText(Account.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        editor.putString("userpassword", passwordUpdate);
                        editor.commit();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Account.this, "Cập nhật thất bại \n" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> stringMap = new HashMap<>();
                stringMap.put("email", emailUpdate);
                stringMap.put("name", nameUpdate);
                stringMap.put("id", idUser);
                stringMap.put("password", passwordUpdate);
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

    private void getJsonUpdateImages(String url_update_image, final String image) {
        setContentDialog("Loading", "Please wait...");
        progressDialog.show();
        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, url_update_image,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Toast.makeText(Account.this, "Upload thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Account.this, "Upload thất bại", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                Log.d("Error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("id", idUser);
                headers.put("avatar", "data:image/png;base64," + image);
//                        headers.put("Content-Type", "application/json; charset=utf-8");

                return headers;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> stringMap = new HashMap<>();
                token = dataLogin.getString("usertoken", "");
                stringMap.put("Authorization", token);
                return stringMap;
            }
        };
        requestQueue1.add(stringRequest1);
    }


    private void SDK_Camera() {
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
            imageViewAvatar.setImageBitmap(image);
            final String image1 = decodeImage(image);
            getJsonUpdateImages(URL_UPDATE_USER_AVATAR, image1);

        }
        if (resultCode == this.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                // get the Uri for the captured image
                picUri = data.getData();
                performCrop();
            } else if (requestCode == CAMERA_REQUEST_MAX) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageViewAvatar.setImageBitmap(imageBitmap);
                Bitmap bitmap = ((BitmapDrawable) imageViewAvatar.getDrawable()).getBitmap();
                final String image = decodeImage(bitmap);
                getJsonUpdateImages(URL_UPDATE_USER_AVATAR, image);

            } else if (requestCode == PIC_CROP) {
                // get the returned data
                Bundle extras = data.getExtras();
                // get the cropped bitmap
                Bitmap thePic = extras.getParcelable("data");
                imageViewAvatar.setImageBitmap(thePic);
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

    private void galleryUpload() {
        Intent imageDownload = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imageDownload.putExtra("crop", "true");
        imageDownload.putExtra("aspectX", 200);
        imageDownload.putExtra("aspectY", 200);
        imageDownload.putExtra("outputX", 200);
        imageDownload.putExtra("outputY", 200);
        imageDownload.putExtra("return-data", true);
        startActivityForResult(imageDownload, 2);
    }

    @Override
    public void onBackPressed() {

        if(backNumber == 0){
            intentView(MainActivity.class);
        }
        else if(backNumber == 1){
            txt_edit_MySiteToobar.setVisibility(View.VISIBLE);
            edt_user_account.setEnabled(false);
            edt_user_account.setBackground(getResources().getDrawable(R.drawable.textline2));
            edt_password_account.setBackground(getResources().getDrawable(R.drawable.textline2));
            edt_email_account.setBackground(getResources().getDrawable(R.drawable.textline2));
            edt_email_account.setEnabled(false);
            edt_password_account.setEnabled(false);
            txt_passwordrandom_account.setVisibility(View.GONE);
            btnSaveAccount.setVisibility(View.GONE);
            avatar_Background.setVisibility(View.GONE);
            avatar_BackgroundCamera.setVisibility(View.GONE);
            backNumber = 0;
        }


    }
}
