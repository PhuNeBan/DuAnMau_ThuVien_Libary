package com.example.asm_dam.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.asm_dam.DAO.SachDAO;
import com.example.asm_dam.DAO.ThanhVienDAO;
import com.example.asm_dam.R;
import com.example.asm_dam.fragments.QuanLyPhieuMuonFragment;
import com.example.asm_dam.models.PhieuMuon;
import com.example.asm_dam.models.Sach;
import com.example.asm_dam.models.ThanhVien;

import java.security.PublicKey;
import java.util.ArrayList;

public class PhieuMuonAdapter extends ArrayAdapter<PhieuMuon> {

    private Context context;
    QuanLyPhieuMuonFragment fragment;
    private ArrayList<PhieuMuon> lists;
    TextView tvMaPhieu,tvNgayMuon,tvThanhVienMuon,tvSachMuon,tvTienMuon,tvTrangThaiMuon,tvXoaPhieuMuon;
    LinearLayout linearLayout;
    PhieuMuon phieuMuon;


    public PhieuMuonAdapter(@NonNull Context context, QuanLyPhieuMuonFragment fragment,
                           ArrayList<PhieuMuon> list) {
        super(context, 0, list);
        this.context = context;
        this.fragment = fragment;
        this.lists = list;
    }

    public PhieuMuon getPhieuMuon(int po){
        phieuMuon = lists.get(po);
        getThanhVien();
        getSach();
        return phieuMuon;
    }

    public void getThanhVien(){
        ThanhVienDAO dao = new ThanhVienDAO(getContext());
        ThanhVien thanhVien = dao.getID(String.valueOf(phieuMuon.maTV));
        phieuMuon.setTenTV(thanhVien.getHoTen());
    }

    public void getSach(){
        SachDAO dao = new SachDAO(getContext());
        Sach sach = dao.getID(String.valueOf(phieuMuon.maS));
        phieuMuon.setTenS(sach.getTenSach());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_phieumuon,null);
        }

        final PhieuMuon item = lists.get(position);

        if (item != null){
            tvMaPhieu = view.findViewById(R.id.tvMaPhieu);
            position += 1;
            tvMaPhieu.setText("Mã phiếu: " + position);

            tvNgayMuon = view.findViewById(R.id.tvNgayMuon);
            tvNgayMuon.setText("Ngày: "+ item.ngay);

            tvThanhVienMuon = view.findViewById(R.id.tvThanhVienMuon);
            ThanhVienDAO thanhVienDAO = new ThanhVienDAO(context);
            ThanhVien thanhVien = thanhVienDAO.getID(String.valueOf(item.maTV));
            tvThanhVienMuon.setText("Tên thành viên: " +thanhVien.hoTen);

            tvSachMuon = view.findViewById(R.id.tvSachMuon);
            SachDAO sachDAO = new SachDAO(context);
            Sach sach = sachDAO.getID(String.valueOf(item.maS));
            tvSachMuon.setText("Tên sách: "+ sach.tenSach);

            tvTienMuon = view.findViewById(R.id.tvTienMuon);
            tvTienMuon.setText("Tiền: "+ item.tienThue);

            tvTrangThaiMuon = view.findViewById(R.id.tvTrangThaiMuon);

            linearLayout = view.findViewById(R.id.linearPhieuMuon);



            if (item.traSach == 1){
                tvTrangThaiMuon.setText("Trạng thái: Đã trả");
                linearLayout.setBackgroundResource(R.drawable.form_phieumuon_left_1);
            }
            else {
                tvTrangThaiMuon.setText("Trạng thái: Chưa trả");
                linearLayout.setBackgroundResource(R.drawable.form_phieumuon_left_0);
            }

            tvXoaPhieuMuon = view.findViewById(R.id.tvXoaPhieuMuon);
            tvXoaPhieuMuon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragment.deletePhieuMuon(String.valueOf(item.maPM));
                }
            });

        }
        return view;
    }

}
