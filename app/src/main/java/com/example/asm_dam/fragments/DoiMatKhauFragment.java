package com.example.asm_dam.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.asm_dam.DAO.ThuThuDAO;
import com.example.asm_dam.R;
import com.example.asm_dam.models.ThuThu;
import com.example.asm_dam.views.LoginActivity;
import com.example.asm_dam.views.MainActivity;

public class DoiMatKhauFragment extends Fragment {
    EditText edtPassCu,edtPassMoi,edtRePass;
    Button btnDoiMatKhau,btnHuyDoiMatKhau;
    ThuThuDAO dao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doimatkhau,container,false);

        edtPassCu = view.findViewById(R.id.edtPassCu);
        edtPassMoi = view.findViewById(R.id.edtPassMoi);
        edtRePass = view.findViewById(R.id.edtRePass);

        btnDoiMatKhau = view.findViewById(R.id.btnDoiMatKhau);
        btnHuyDoiMatKhau = view.findViewById(R.id.btnHuyDoiMatKhau);

        dao = new ThuThuDAO(getActivity());


        btnHuyDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtPassCu.setText("");
                edtPassMoi.setText("");
                edtRePass.setText("");
            }
        });

        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getActivity().getSharedPreferences(
                        "USER_FILE",Context.MODE_PRIVATE);
                String user = preferences.getString("USERNAME","");
                if (validate()>0){
                    ThuThu thuthu = dao.getID(user);
                    thuthu.matKhau = edtPassMoi.getText().toString();
                    dao.update(thuthu);
                    if (dao.update(thuthu) >0){
                        Toast.makeText(getActivity(), "Thay đổi thành công", Toast.LENGTH_SHORT).show();
                        edtPassCu.setText("");
                        edtPassMoi.setText("");
                        edtRePass.setText("");
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(getActivity(), "Thay đổi thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }

    public int validate(){
        int check = 1;
        if (edtPassCu.getText().length() == 0 || edtPassMoi.getText().length() == 0 || edtRePass.getText().length() == 0){
            Toast.makeText(getContext(), "Vui lòng không để trống", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        else {
            SharedPreferences preferences = getActivity().getSharedPreferences("USER_FILE",Context.MODE_PRIVATE);
            String passCu = preferences.getString("PASSWORD","");
            String passMoi = edtPassMoi.getText().toString();
            String rePass = edtRePass.getText().toString();
            if (!passCu.equals(edtPassCu.getText().toString())){
                Toast.makeText(getContext(), "Mật khẩu cũ sai", Toast.LENGTH_SHORT).show();
                check = -1;
            }
            if (!passMoi.equals(rePass)){
                Toast.makeText(getContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                check = -1;
            }

        }
        return check;
    }
}
