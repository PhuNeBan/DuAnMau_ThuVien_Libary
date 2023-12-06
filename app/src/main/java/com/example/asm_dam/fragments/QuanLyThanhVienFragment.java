package com.example.asm_dam.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.asm_dam.DAO.PhieuMuonDAO;
import com.example.asm_dam.DAO.ThanhVienDAO;
import com.example.asm_dam.R;
import com.example.asm_dam.adapters.PhieuMuonAdapter;
import com.example.asm_dam.adapters.ThanhVienAdapter;
import com.example.asm_dam.models.PhieuMuon;
import com.example.asm_dam.models.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class QuanLyThanhVienFragment extends Fragment {
    GridView gridView;
    FloatingActionButton floatingActionButton;
    ThanhVienDAO dao;
    ArrayList<ThanhVien> lists;
    ThanhVienAdapter adapter;
    AlertDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quanlythanhvien,container,false);

        //ánh xạ
        gridView = view.findViewById(R.id.gvThanhVien);
        floatingActionButton = view.findViewById(R.id.fltThemThanhVien);

        dao = new ThanhVienDAO(getActivity());

        //sự kiện nút floatingActionButton
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });

        capnhatLV();
        return view;
    }

    public  void capnhatLV(){

        lists = (ArrayList<ThanhVien>) dao.getAll();
        System.out.println("Đây là list: "+lists);
        adapter = new ThanhVienAdapter(getContext(),this,lists);
        gridView.setAdapter(adapter);
    }

    //thêm thành viên
    public void addData(){
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_thanh_vien,null);

        //ánh xạ
        TextView tvDialogThanhVien = dialogLayout.findViewById(R.id.tvDialogThanhVien);
        EditText edtTenThanhVien = dialogLayout.findViewById(R.id.edtTenThanhVien);
        EditText edtNamSinhThanhVien = dialogLayout.findViewById(R.id.edtNamSinhThanhVien);
        Button btnSaveThanhVien = dialogLayout.findViewById(R.id.btnSaveThanhVien);
        Button btnCancelThanhVien = dialogLayout.findViewById(R.id.btnCancelThanhVien);

        //đổi tên dialog
        tvDialogThanhVien.setText("THÊM THÀNH VIÊN");

        //sự kiệm nút Save
        btnSaveThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "";
                Calendar calendar = Calendar.getInstance();
                try {

                    int yearCheck = calendar.get(Calendar.YEAR);
                    String name = edtTenThanhVien.getText().toString().trim();
                    String namSinh = edtNamSinhThanhVien.getText().toString().trim();
                    //bắt validate
                    if (name.isEmpty() || namSinh.isEmpty() ){
                        s = "Không được để trống";
                    }
                    else{
                        int year = Integer.parseInt(namSinh);
                        if (year >=yearCheck || (yearCheck - year) >= 100 ){
                            s = "Năm sinh phải hợp lệ ";
                        }else {
                            s = "Thêm thành công";
                            dao.insert(new ThanhVien(1,name,namSinh));
                            capnhatLV();
                            dialog.dismiss();
                        }
                    }
                    System.out.println("Lỗi: " + s);
                    Toast.makeText(getActivity(),s , Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    System.out.println("Lỗi thêm thành viên: "+ e);
                }
            }
        });

        //sự kiện nút Cancel
        btnCancelThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        //Khởi tạo Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogLayout);
        dialog = builder.create();
        dialog.show();
    }

    //cập nhật thành viên
    public void updateData(ThanhVien thanhVien){
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_thanh_vien,null);

        //ánh xạ
        TextView tvDialogThanhVien = dialogLayout.findViewById(R.id.tvDialogThanhVien);
        EditText edtTenThanhVien = dialogLayout.findViewById(R.id.edtTenThanhVien);
        EditText edtNamSinhThanhVien = dialogLayout.findViewById(R.id.edtNamSinhThanhVien);
        Button btnSaveThanhVien = dialogLayout.findViewById(R.id.btnSaveThanhVien);
        Button btnCancelThanhVien = dialogLayout.findViewById(R.id.btnCancelThanhVien);

        //đổi tên dialog
        tvDialogThanhVien.setText("SỬA THÀNH VIÊN");

        edtTenThanhVien.setText(thanhVien.getHoTen());
        edtNamSinhThanhVien.setText(thanhVien.getNamSinh());

        //sự kiệm nút Save
        btnSaveThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String s = "";
                    Calendar calendar = Calendar.getInstance();
                    int yearCheck = calendar.get(Calendar.YEAR);
                    String name = edtTenThanhVien.getText().toString().trim();
                    int namSinh = Integer.parseInt(edtNamSinhThanhVien.getText().toString().trim());

                    //bắt validate
                    if (name.isEmpty() || String.valueOf(namSinh).isEmpty()){
                        s = "Không được để trống";
                    }
                    else{
                        if (namSinh >=yearCheck || (yearCheck - namSinh) >= 100 ){
                            s = "Năm sinh phải hợp lệ ";
                        }else {
                            s = "Cập nhật thành công";
                            thanhVien.setHoTen(name);
                            thanhVien.setNamSinh(String.valueOf(namSinh));
                            dao.update(thanhVien);
                            capnhatLV();
                            dialog.dismiss();
                        }
                    }
                    System.out.println("Lỗi: " + s);
                    Toast.makeText(getActivity(),s , Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    System.out.println("Lỗi sửa thành viên: "+ e);
                }
            }
        });

        //sự kiện nút Cancel
        btnCancelThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        //Khởi tạo Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogLayout);
        dialog = builder.create();
        dialog.show();
    }

    //xóa thành viên
    public void deleteThanhVien(String maTV){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Nếu xóa sẽ xóa các thông tin liên quan")
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // START THE GAME!
                        dao.delete(maTV);
                        xoaPhieuMuon(Integer.parseInt(maTV));
                        Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                        capnhatLV();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                }).create().show();
    }

    public void xoaPhieuMuon(int maTV){
        PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(getContext());
        List<PhieuMuon> listCheck = phieuMuonDAO.getAll();
        for (int i = 0; i< listCheck.size(); i++){
            if (listCheck.get(i).getMaTV() == maTV){
                phieuMuonDAO.delete(String.valueOf(listCheck.get(i).maPM));
            }
        }
    }
}
