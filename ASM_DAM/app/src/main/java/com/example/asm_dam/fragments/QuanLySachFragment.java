package com.example.asm_dam.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
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
import com.example.asm_dam.adapters.PhieuMuonAdapter;
import com.example.asm_dam.adapters.SachAdapter;
import com.example.asm_dam.models.LoaiSach;
import com.example.asm_dam.models.PhieuMuon;
import com.example.asm_dam.models.Sach;
import com.example.asm_dam.models.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class QuanLySachFragment extends Fragment {
    ListView listView;
    FloatingActionButton floatingActionButton;
    SachDAO dao;
    ArrayList<Sach> lists;
    SachAdapter adapter;
    AlertDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quanlysach,container,false);

        listView = view.findViewById(R.id.lvSach);
        floatingActionButton = view.findViewById(R.id.fltThemSach);
        dao = new SachDAO(getActivity());

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Sach sach = adapter.getItem(position);
                updateData(sach);
                return false;
            }
        });

        capnhatLV();
        return view;
    }

    public  void capnhatLV(){

        lists = (ArrayList<Sach>) dao.getAll();
        adapter = new SachAdapter(getContext(),this,lists);
        listView.setAdapter(adapter);
    }

    //thêm sách
    public void addData(){
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_sach,null);

        //ánh xạ
        TextView tvDialogSach = dialogLayout.findViewById(R.id.tvDialogSach);
        EditText edtTenLoai = dialogLayout.findViewById(R.id.edtTenLoai);
        Spinner spinnerLoaiSach = dialogLayout.findViewById(R.id.spinnerLoaiSach);
        EditText edtTienThue = dialogLayout.findViewById(R.id.edtTienThue);
        Button btnSaveSach = dialogLayout.findViewById(R.id.btnSaveSach);
        Button btnCancelSach = dialogLayout.findViewById(R.id.btnCancelSach);

        //đổi tên dialog
        tvDialogSach.setText("THÊM SÁCH");

        //đổ dữ liệu ra spinnerLoaiSach
        SimpleAdapter simpleAdapter1 = new SimpleAdapter(
                getContext(), getListLoaiSach(), android.R.layout.simple_list_item_1,
                new String[]{"tenLS"},
                new int[]{android.R.id.text1}
        );
        spinnerLoaiSach.setAdapter(simpleAdapter1);

        //sự kiệm nút Save
        btnSaveSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String s = "";
                    int check = 0;
                    String name = edtTenLoai.getText().toString().trim();
                    HashMap<String, Object> selected = (HashMap<String, Object>) spinnerLoaiSach.getSelectedItem();
                    int maLS = (int) selected.get("maLS");
                    int tien = Integer.parseInt(edtTienThue.getText().toString());

                    //bắt validate
                    if (name.isEmpty() || String.valueOf(tien).isEmpty() ){
                        s = "Không được để trống";
                    }
                    else{
                        for (int i =0; i<lists.size(); i++){
                            if (name.equals(lists.get(i).getTenSach())){
                                check = -1;
                                break;
                            }
                        }
                        if (check >=0 ){
                            s = "Thêm thành công";
                            dao.insert(new Sach(1,name,tien,maLS));
                            capnhatLV();
                            dialog.dismiss();
                        }else {
                            s = "Đã có sách này";
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
        btnCancelSach.setOnClickListener(new View.OnClickListener() {
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

    //sửa sách
    public void updateData(Sach sach){
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_sach,null);

        //ánh xạ
        TextView tvDialogSach = dialogLayout.findViewById(R.id.tvDialogSach);
        EditText edtTenLoai = dialogLayout.findViewById(R.id.edtTenLoai);
        Spinner spinnerLoaiSach = dialogLayout.findViewById(R.id.spinnerLoaiSach);
        EditText edtTienThue = dialogLayout.findViewById(R.id.edtTienThue);
        Button btnSaveSach = dialogLayout.findViewById(R.id.btnSaveSach);
        Button btnCancelSach = dialogLayout.findViewById(R.id.btnCancelSach);

        //đổi tên dialog
        tvDialogSach.setText("SỬA SÁCH");

        edtTenLoai.setText(sach.tenSach);
        edtTienThue.setText(String.valueOf(sach.giaThue));

        //đổ dữ liệu ra spinnerLoaiSach
        SimpleAdapter simpleAdapter1 = new SimpleAdapter(
                getContext(), getListLoaiSach(), android.R.layout.simple_list_item_1,
                new String[]{"tenLS"},
                new int[]{android.R.id.text1}
        );
        spinnerLoaiSach.setAdapter(simpleAdapter1);

        //sự kiệm nút Save
        btnSaveSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String s = "";
                    int check = 0;
                    String name = edtTenLoai.getText().toString().trim();
                    HashMap<String, Object> selected = (HashMap<String, Object>) spinnerLoaiSach.getSelectedItem();
                    int maLS = (int) selected.get("maLS");
                    int tien = Integer.parseInt(edtTienThue.getText().toString());

                    //bắt validate
                    if (name.isEmpty() || String.valueOf(tien).isEmpty() ){
                        s = "Không được để trống";
                    }
                    else{
                        for (int i =0; i<lists.size(); i++){
                            if (name.equals(lists.get(i).getTenSach())){
                                check += 1;
                            }
                        }
                        if (check <2 ){
                            s = "Thêm thành công";
                            sach.setTenSach(name);
                            sach.setGiaThue(tien);
                            sach.setLoai(maLS);
                            dao.update(sach);
                            capnhatLV();
                            dialog.dismiss();
                        }else {
                            s = "Đã có sách này";
                        }
                    }
                    System.out.println("Lỗi: " + s);
                    Toast.makeText(getActivity(),s , Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    System.out.println("Lỗi thêm thành viên: "+ e);
                    Toast.makeText(getContext(), "Không đúng định dạng", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //sự kiện nút Cancel
        btnCancelSach.setOnClickListener(new View.OnClickListener() {
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

    //xóa sách
    public void deleteSach(String maS){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Nếu xóa sẽ xóa các thông tin liên quan")
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // START THE GAME!
                        dao.delete(maS);
                        xoaPhieuMuon(Integer.parseInt(maS));
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



    //lấy danh sách loại sách
    private ArrayList<HashMap<String, Object>> getListLoaiSach(){
        ArrayList<HashMap<String, Object>> listSpinner = new ArrayList<>();
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(getContext());
        ArrayList<LoaiSach> listLoaiSach = (ArrayList<LoaiSach>) loaiSachDAO.getAll();
        for (LoaiSach s: listLoaiSach){
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("maLS", s.getMaLS());
            hashMap.put("tenLS", s.getTenLoai());
            listSpinner.add(hashMap);
        }
        return listSpinner;
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
