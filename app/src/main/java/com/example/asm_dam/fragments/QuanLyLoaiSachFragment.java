package com.example.asm_dam.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.asm_dam.DAO.LoaiSachDAO;
import com.example.asm_dam.DAO.PhieuMuonDAO;
import com.example.asm_dam.DAO.SachDAO;
import com.example.asm_dam.R;
import com.example.asm_dam.adapters.LoaiSachAdapter;
import com.example.asm_dam.models.LoaiSach;
import com.example.asm_dam.models.PhieuMuon;
import com.example.asm_dam.models.Sach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class QuanLyLoaiSachFragment extends Fragment {

    ListView listView;
    FloatingActionButton floatingActionButton;
    LoaiSachDAO dao;
    ArrayList<LoaiSach> lists;
    LoaiSachAdapter adapter;
    LoaiSach loaiSach;
    AlertDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quanlyloaisach,container,false);


        listView = view.findViewById(R.id.lvLoaiSach);
        floatingActionButton = view.findViewById(R.id.fltThemLoaiSach);

        dao = new LoaiSachDAO(getActivity());

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                LoaiSach loaiSach = adapter.getItem(position);
                updateData(loaiSach);
                return false;
            }
        });

        capnhatLV();
        return view;
    }
    
    public  void capnhatLV(){
        lists = (ArrayList<LoaiSach>) dao.getAll();
        adapter = new LoaiSachAdapter(getActivity(),this,lists);
        listView.setAdapter(adapter);
    }

    //thêm loại sách
    public void addData(){
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_loai_sach,null);

        //ánh xạ
        TextView tvDialogLoaiSach = dialogLayout.findViewById(R.id.tvDialogLoaiSach);
        EditText edtTenLoaiSach = dialogLayout.findViewById(R.id.edtTenLoaiSach);
        Button btnSaveLoai = dialogLayout.findViewById(R.id.btnSaveLoai);
        Button btnCancelLoai = dialogLayout.findViewById(R.id.btnCancelLoai);

        //đổi tên dialog
        tvDialogLoaiSach.setText("THÊM LOẠI SÁCH");

        //sự kiệm nút Save
        btnSaveLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String s = "";
                    String name = edtTenLoaiSach.getText().toString().trim();
                    int check = 0;
                    //bắt validate
                    if (name.isEmpty()){
                        s = "Không được để trống";
                    }
                    else{
                        for (int i =0; i<lists.size(); i++){
                            if (name.equals(lists.get(i).getTenLoai())){
                                check = -1;
                                break;
                            }
                        }if (check >= 0){
                            s = "Thêm thành công";
                            dao.insert(new LoaiSach(1,name));
                            capnhatLV();
                            dialog.dismiss();
                        }
                        else {
                            s = "Đã có loại sách này";
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
        btnCancelLoai.setOnClickListener(new View.OnClickListener() {
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

    //sửa loại sách
    public void updateData(LoaiSach loaiSach){
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_loai_sach,null);

        //ánh xạ
        TextView tvDialogLoaiSach = dialogLayout.findViewById(R.id.tvDialogLoaiSach);
        EditText edtTenLoaiSach = dialogLayout.findViewById(R.id.edtTenLoaiSach);
        Button btnSaveLoai = dialogLayout.findViewById(R.id.btnSaveLoai);
        Button btnCancelLoai = dialogLayout.findViewById(R.id.btnCancelLoai);

        //đổi tên dialog
        tvDialogLoaiSach.setText("SỬA LOẠI SÁCH");
        edtTenLoaiSach.setText(loaiSach.getTenLoai());
        //sự kiệm nút Save
        btnSaveLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String s = "";
                    String name = edtTenLoaiSach.getText().toString().trim();
                    int check = 0;
                    //bắt validate
                    if (name.isEmpty()){
                        s = "Không được để trống";
                    }
                    else{
                        for (int i =0; i<lists.size(); i++){
                            if (name.equals(lists.get(i).getTenLoai())){
                                check += 1;
                            }
                        }
                        if (check < 2 ){
                            s = "Sửa thành công";
                            loaiSach.setTenLoai(name);
                            dao.update(loaiSach);
                            capnhatLV();
                            dialog.dismiss();
                        }
                        else {
                            s = "Đã có loại sách này";
                        }
                    }
                    Toast.makeText(getActivity(),s , Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    System.out.println("Lỗi sửa loại sách: "+ e);
                }
            }
        });

        //sự kiện nút Cancel
        btnCancelLoai.setOnClickListener(new View.OnClickListener() {
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
    public void deleteLoaiSach(String maLS){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Nếu xóa sẽ xóa các thông tin liên quan")
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // START THE GAME!
                        dao.delete(maLS);
                        xoaSach(Integer.parseInt(maLS));
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

    public void xoaSach(int maLS){
        SachDAO sachDAO = new SachDAO(getContext());
        List<Sach> listCheck = sachDAO.getAll();
        for (int i = 0; i< listCheck.size(); i++){
            if (listCheck.get(i).getLoai() == maLS){
                sachDAO.delete(String.valueOf(listCheck.get(i).maS));
                xoaPhieuMuon(listCheck.get(i).maS);
            }
        }
    }

    public void xoaPhieuMuon(int maS){
        PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(getContext());
        List<PhieuMuon> listCheck = phieuMuonDAO.getAll();
        for (int i = 0; i< listCheck.size(); i++){
            if (listCheck.get(i).getMaS() == maS){
                phieuMuonDAO.delete(String.valueOf(listCheck.get(i).maPM));
            }
        }
    }
}
