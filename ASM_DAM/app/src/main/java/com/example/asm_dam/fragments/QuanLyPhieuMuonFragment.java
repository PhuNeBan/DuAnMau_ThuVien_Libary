package com.example.asm_dam.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.asm_dam.DAO.PhieuMuonDAO;
import com.example.asm_dam.DAO.SachDAO;
import com.example.asm_dam.DAO.ThanhVienDAO;
import com.example.asm_dam.R;
import com.example.asm_dam.adapters.PhieuMuonAdapter;
import com.example.asm_dam.models.PhieuMuon;
import com.example.asm_dam.models.PhieuMuonAll;
import com.example.asm_dam.models.Sach;
import com.example.asm_dam.models.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class QuanLyPhieuMuonFragment extends Fragment {
    ListView listView;
    FloatingActionButton floatingActionButton;
    PhieuMuonDAO dao;
    ArrayList<PhieuMuon> lists;
    PhieuMuonAdapter adapter;
    ThanhVienDAO thanhVienDAO;
    SachDAO sachDAO;
    String maTT;
    AlertDialog dialog;

    public QuanLyPhieuMuonFragment(String maTT){
        this.maTT = maTT;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quanliphieumuon,container,false);

        listView = view.findViewById(R.id.lvPhieuMuon);
        floatingActionButton = view.findViewById(R.id.fltThemPhieuMuon);
        dao = new PhieuMuonDAO(getActivity());
        thanhVienDAO = new ThanhVienDAO(getContext());
        sachDAO = new SachDAO(getContext());

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                PhieuMuon phieuMuon = adapter.getPhieuMuon(position);
                updatePhieuMuon(phieuMuon);
                return false;
            }
        });

        capnhatLV();
        return view;
    }

    //đổ dữ liệu ra màn hình
    public  void capnhatLV(){
        ArrayList<PhieuMuon> listCheck = (ArrayList<PhieuMuon>) dao.getAll();
        for (int i = 0; i< listCheck.size(); i++){
            if (String.valueOf(listCheck.get(i).getMaTV()) == null){
                dao.delete(String.valueOf(listCheck.get(i).maPM));
            }
        }

        lists = (ArrayList<PhieuMuon>) dao.getAll();
        for (int i = 0; i< lists.size(); i++){
             System.out.println("Đây là list: "+lists.get(i).getMaPM() + "\n " + lists.get(i).getMaTV());
        }
        adapter = new PhieuMuonAdapter(getContext(),this,lists);
        listView.setAdapter(adapter);
    }

    //thêm phiếu mượn
    public void addData(){
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_phieu_muon,null);

        //ánh xạ
        TextView tvPhieuMuon = dialogLayout.findViewById(R.id.tvDialogPhieuMuon);
        Spinner spinnerTenPhieuMuon = dialogLayout.findViewById(R.id.spinnerTenPhieuMuon);
        Spinner spinnerSachMuon = dialogLayout.findViewById(R.id.spinnerSachMuon);
        EditText edtNgayMuon = dialogLayout.findViewById(R.id.edtNgayMuon);
        EditText edtTongTienThue = dialogLayout.findViewById(R.id.edtTongTienThue);
        CheckBox cbTraSach = dialogLayout.findViewById(R.id.cbTraSach);
        Button btnSavePhieuMuon = dialogLayout.findViewById(R.id.btnSavePhieuMuon);
        Button btnCancelPhieuMuon = dialogLayout.findViewById(R.id.btnCancelPhieuMuon);

        //khai báo

        tvPhieuMuon.setText("THÊM PHIẾU MƯỢN");

        //đổ dữ liệu ra sppiner tên
        SimpleAdapter simpleAdapter1 = new SimpleAdapter(
                getContext(), getListThanhVien(), android.R.layout.simple_list_item_1,
                new String[]{"tenTV"},
                new int[]{android.R.id.text1}
        );
        spinnerTenPhieuMuon.setAdapter(simpleAdapter1);

        //đổ dữ liệu ra sppiner sách
        SimpleAdapter simpleAdapter2 = new SimpleAdapter(
                getContext(), getListSach(), android.R.layout.simple_list_item_1,
                new String[]{"tenS"},
                new int[]{android.R.id.text1}
        );
        spinnerSachMuon.setAdapter(simpleAdapter2);

        //set thời gian ngày hiện tại cho edtNgayMuon
        DateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
        String date =dfDate.format(Calendar.getInstance().getTime());
        edtNgayMuon.setText(date);

        //set dữ liệu cho edtTongTienThue
        spinnerSachMuon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> selected = (HashMap<String, Object>) spinnerSachMuon.getSelectedItem();
                int tien = (int) selected.get("tienS");
                edtTongTienThue.setText(tien + "VND");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //sự kiệm nút Save
        btnSavePhieuMuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    HashMap<String, Object> selectedTV = (HashMap<String, Object>) spinnerTenPhieuMuon.getSelectedItem();
                    int maTV  = (int) selectedTV.get("maTV");
                    HashMap<String, Object> selectedS = (HashMap<String, Object>) spinnerSachMuon.getSelectedItem();
                    int maS = (int) selectedS.get("maS");
                    String ngay = edtNgayMuon.getText().toString();
                    int tienThue = (int) selectedS.get("tienS");
                    PhieuMuon phieuMuon = new PhieuMuon(1,maTV,maTT,maS,ngay,tienThue,0);
                    dao.insert(phieuMuon);
                    capnhatLV();
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    System.out.println("Lỗi thêm phiếu mượn: "+ e);
                }
            }
        });

        btnCancelPhieuMuon.setOnClickListener(new View.OnClickListener() {
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

    //cập nhật phiếu mượn
    public void updatePhieuMuon(PhieuMuon phieuMuon){
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_sua_phieu_muon,null);

        //ánh xạ
        TextView tvDialogSuaPhieuMuon = dialogLayout.findViewById(R.id.tvDialogSuaPhieuMuon);
        EditText edtSuaTen = dialogLayout.findViewById(R.id.edtSuaTen);
        EditText edtSuaSach = dialogLayout.findViewById(R.id.edtSuaSach);
        EditText edtSuaNgayMuon = dialogLayout.findViewById(R.id.edtSuaNgayMuon);
        EditText edtSuaTongTienThue = dialogLayout.findViewById(R.id.edtSuaTongTienThue);
        CheckBox cbSuaTraSach = dialogLayout.findViewById(R.id.cbSuaTraSach);
        Button btnSaveSuaPhieuMuon = dialogLayout.findViewById(R.id.btnSaveSuaPhieuMuon);
        Button btnCancelSuaPhieuMuon = dialogLayout.findViewById(R.id.btnCancelSuaPhieuMuon);


        tvDialogSuaPhieuMuon.setText("SỬA PHIẾU MƯỢN");

        //đổ dữ liệu ra edtSuaTen
        edtSuaTen.setText(phieuMuon.getTenTV());
        edtSuaTen.setEnabled(false);

        //đổ dữ liệu ra edtSuaSach
        edtSuaSach.setText(phieuMuon.getTenS());
        edtSuaSach.setEnabled(false);

        //đổ dữ liệu ra edtSuaNgayMuon
        edtSuaNgayMuon.setText(phieuMuon.getNgay());
        edtSuaNgayMuon.setEnabled(false);

        //đổ dữ liệu cho edtSuaTongTienThue
        edtSuaTongTienThue.setText(phieuMuon.getTienThue() + "VND");
        edtSuaTongTienThue.setEnabled(false);

        if (phieuMuon.traSach == 1){
            cbSuaTraSach.setChecked(true);
        }

        //sự kiệm nút Save
        btnSaveSuaPhieuMuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (cbSuaTraSach.isChecked()){
                        phieuMuon.setTraSach(1);
                    }else {
                        phieuMuon.setTraSach(0);
                    }
                    dao.update(phieuMuon);
                    capnhatLV();
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    System.out.println("Lỗi sửa phiếu mượn: "+ e);
                }
            }
        });

        btnCancelSuaPhieuMuon.setOnClickListener(new View.OnClickListener() {
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

    //xóa phiếu mượn
    public void deletePhieuMuon(String maPM){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Nếu xóa không thể hoàn tác")
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // START THE GAME!
                        dao.delete(maPM);
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

    //lấy danh sách thành viên
    private ArrayList<HashMap<String, Object>> getListThanhVien(){
        ArrayList<HashMap<String, Object>> listSpinner = new ArrayList<>();

        ArrayList<ThanhVien> listThanhVien = (ArrayList<ThanhVien>) thanhVienDAO.getAll();
        for (ThanhVien tv: listThanhVien){
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("maTV", tv.getMaTV());
            hashMap.put("tenTV", tv.getHoTen());
            listSpinner.add(hashMap);
        }
        return listSpinner;
    }

    //lấy danh sách sách
    private ArrayList<HashMap<String, Object>> getListSach(){
        ArrayList<HashMap<String, Object>> listSpinner = new ArrayList<>();
        ArrayList<Sach> listSach = (ArrayList<Sach>) sachDAO.getAll();
        for (Sach s: listSach){
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("maS", s.getMaS());
            hashMap.put("tenS", s.getTenSach());
            hashMap.put("tienS",s.getGiaThue());
            listSpinner.add(hashMap);
        }
        return listSpinner;
    }


}
