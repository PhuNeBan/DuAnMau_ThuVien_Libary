package com.example.asm_dam.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.asm_dam.DAO.ThongKeDAO;
import com.example.asm_dam.DAO.ThuThuDAO;
import com.example.asm_dam.R;
import com.example.asm_dam.models.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class ThemThanhVienFragment extends Fragment {

    EditText edtNameUser,edtFullNameUser,edtPassUser;
    Button btnThemThanhVien, btnCanelThemThanhVien;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_themthanhvien,container,false);

        //ánh xạ
        edtNameUser = view.findViewById(R.id.edtNameUser);
        edtFullNameUser = view.findViewById(R.id.edtFullNameUser);
        edtPassUser = view.findViewById(R.id.edtPassUser);

        btnThemThanhVien = view.findViewById(R.id.btnThemThanhVien);
        btnCanelThemThanhVien = view.findViewById(R.id.btnCanelThemThanhVien);

        btnThemThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themThanhVien();
            }
        });

        btnCanelThemThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtNameUser.setText("");
                edtFullNameUser.setText("");
                edtPassUser.setText("");
            }
        });
        return view;
    }

    //thêm thủ thư mới
    public void themThanhVien(){
        try {
            String s = "";
            int check = 0;
            String name = edtNameUser.getText().toString().trim();
            String fullName = edtFullNameUser.getText().toString().trim();
            String pass = edtPassUser.getText().toString().trim();
            System.out.println("Name: " + name);
            System.out.println("FullName: " + fullName);
            System.out.println("pass: " + pass);

            //bắt validate
            if (name.isEmpty() || fullName.isEmpty() || pass.isEmpty() ){
                s = "Không được để trống";
            }else {
                ThuThuDAO thuThuDAO = new ThuThuDAO(getContext());
                ArrayList<ThuThu> lists = (ArrayList<ThuThu>) thuThuDAO.getAll();
                //kiểm tra đã có tài khoản này hay chưa
                for (int i =0; i<lists.size(); i++){
                    if (name.equals(lists.get(i).getMaTT())){
                        check = -1;
                        break;
                    }
                }
                if (check >= 0){
                    ThuThu thu = new ThuThu(name,fullName,pass);
                    thuThuDAO.insert(thu);
                    edtNameUser.setText("");
                    edtFullNameUser.setText("");
                    edtPassUser.setText("");
                    System.out.println("Thuthu: " + thu);
                    for (int i =0; i<lists.size(); i++){
                        System.out.println(lists.get(i).maTT);
                    }

                    s = "Thêm thành công";
                }else {
                    s = "Tên đăng nhập đã có";
                }
            }
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        }catch (Exception e){

        }
    }
}
