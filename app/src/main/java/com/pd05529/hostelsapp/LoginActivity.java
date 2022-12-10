package com.pd05529.hostelsapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.pd05529.hostelsapp.DAO.OwnerDAO;
import com.pd05529.hostelsapp.models.Owner;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private TextInputLayout tilUser, tilPass;
    private OwnerDAO ownerDAO;
    private SharedPreferences pref;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.black));
        }
        ownerDAO = new OwnerDAO(getApplicationContext());
        //mapping
        btnLogin = findViewById(R.id.btnLogin);
        tilUser = findViewById(R.id.tilUser);
        tilPass = findViewById(R.id.tilPass);
        checkBox = findViewById(R.id.checkBox);
        //Show data when remember
        pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        checkBox.setChecked(pref.getBoolean("CHECK", false));
        tilUser.getEditText().setText(pref.getString("USERNAME", ""));
        tilPass.getEditText().setText(pref.getString("PASSWORD", ""));

        //Click button login
        btnLogin.setOnClickListener(v -> {
            clickLogin();
        });
    }

    //Event button login
    private void clickLogin() {
        if (!checkInput(tilUser) || !checkInput(tilPass)) {
            return;
        }
        String username = tilUser.getEditText().getText().toString();
        String password = tilPass.getEditText().getText().toString();
        Owner owner = ownerDAO.checkLogin(username, password);
        if (owner == null) {
            Toast.makeText(this, "Tài khoản hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
        } else {
            rememberUser(username, password, checkBox.isChecked());
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("id",owner.getIdOwner());
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }

    //Check user and pass
    private boolean checkInput(TextInputLayout inputLayout) {
        //Lay du lieu input
        String input = inputLayout.getEditText().getText().toString().trim();
        //Kiem tra nhap vao tu ban phim
        if (input.isEmpty()) {
            inputLayout.setError("Không để trống dữ liệu!");
            return false;
        }
        inputLayout.setError(null);
        return true;
    }

    public void rememberUser(String strUser, String strPass, boolean checked) {
        pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if (!checked) {
            //xoa tinh trang luu tru
            editor.clear();
        } else {
            //luu du lieu
            editor.putString("USERNAME", strUser);
            editor.putString("PASSWORD", strPass);
            editor.putBoolean("CHECK", true);
        }
        editor.commit();
    }
}