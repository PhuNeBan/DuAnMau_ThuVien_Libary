package com.example.asm_dam.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.asm_dam.DAO.ThuThuDAO;
import com.example.asm_dam.R;
import com.example.asm_dam.fragments.DoanhThuFragment;
import com.example.asm_dam.fragments.DoiMatKhauFragment;
import com.example.asm_dam.fragments.QuanLyLoaiSachFragment;
import com.example.asm_dam.fragments.QuanLyPhieuMuonFragment;
import com.example.asm_dam.fragments.QuanLySachFragment;
import com.example.asm_dam.fragments.QuanLyThanhVienFragment;
import com.example.asm_dam.fragments.ThemThanhVienFragment;
import com.example.asm_dam.fragments.TopFragment;
import com.example.asm_dam.models.ThuThu;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayoutAdmin);
        navigationView = findViewById(R.id.navigationAdmin);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        user = intent.getStringExtra("user");

        if (user.equalsIgnoreCase("admin")){
            navigationView.getMenu().findItem(R.id.fgThemNguoiDung).setVisible(true);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = new QuanLyPhieuMuonFragment(user);
        fragmentManager
                .beginTransaction()
                .replace(R.id.linearLayoutAdmin, fragment)
                .commit();
        setTitle("Quản lý phiếu mượn");

        getAction();


    }

    public void getAction(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = new QuanLyPhieuMuonFragment(user);

                switch (item.getItemId()){
                    case R.id.fgPhieuMuon:
                        setTitle("Quản lý phiếu mượn");
                        fragment = new QuanLyPhieuMuonFragment(user);
                        break;
                    case R.id.fgLoaiSach:
                        setTitle("Quản lý loại sách");
                        fragment = new QuanLyLoaiSachFragment();
                        break;
                    case R.id.fgSach:
                        setTitle("Quản lý sách");
                        fragment = new QuanLySachFragment();
                        break;
                    case R.id.fgThanhVien:
                        setTitle("Quản lý thành viên");
                        fragment = new QuanLyThanhVienFragment();
                        break;
                    case R.id.fgDoanhThu:
                        setTitle("Doanh thu");
                        fragment = new DoanhThuFragment();
                        break;
                    case R.id.fgTop:
                        setTitle("Top 10");
                        fragment = new TopFragment();
                        break;
                    case R.id.fgThemNguoiDung:
                        setTitle("Thêm người dùng");
                        fragment = new ThemThanhVienFragment();
                        break;
                    case R.id.fgDoiMatKhau:
                        setTitle("Đổi mật khẩu");
                        fragment = new DoiMatKhauFragment();
                        break;
                    case R.id.fgDangXuat:
                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    default:
                        setTitle("Quản lý phiếu mượn");
                        fragment = new QuanLyPhieuMuonFragment(user);
                        break;
                }

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.linearLayoutAdmin, fragment)
                        .commit();

                drawerLayout.closeDrawer(GravityCompat.START);

                setTitle(item.getTitle());

                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}