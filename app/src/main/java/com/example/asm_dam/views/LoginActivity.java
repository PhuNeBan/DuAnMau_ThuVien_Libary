package com.example.asm_dam.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asm_dam.DAO.ThuThuDAO;
import com.example.asm_dam.R;

public class LoginActivity extends AppCompatActivity {
    EditText edtUserLogin,edtPassLogin;
    Button btnLogin;
    CheckBox ckReMenber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUserLogin = findViewById(R.id.edtUserLogin);
        edtPassLogin = findViewById(R.id.edtPassLogin);
        btnLogin     = findViewById(R.id.btnLogin);
        ckReMenber   = findViewById(R.id.ckRemember);

        ThuThuDAO dao = new ThuThuDAO(this);

        SharedPreferences preferences = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        edtUserLogin.setText(preferences.getString("USERNAME",""));
        edtPassLogin.setText(preferences.getString("PASSWORD",""));
        ckReMenber.setChecked(preferences.getBoolean("REMEMBER",false));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String user = edtUserLogin.getText().toString();
                    String pass = edtPassLogin.getText().toString();
                    if (user.isEmpty() || pass.isEmpty()){
                        Toast.makeText(LoginActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                    }else {
                        if (dao.checkLogin(user,pass)>0)
                        {
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            rememberUser(user,pass,ckReMenber.isChecked());

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("user",user);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(LoginActivity.this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception e){
                    System.out.println("Lỗi Login: "+ e);
                }
            }


        });
    }
    private void rememberUser(String u, String p, boolean status) {
        SharedPreferences preferences = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (!status){
            editor.clear();
        }else {
            editor.putString("USERNAME",u);
            editor.putString("PASSWORD",p);
            editor.putBoolean("REMEMBER",status);
        }
        editor.commit();

    }
}