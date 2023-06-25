package com.example.asm_dam.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.asm_dam.R;
import com.example.asm_dam.fragments.QuanLyThanhVienFragment;
import com.example.asm_dam.models.ThanhVien;

import java.util.ArrayList;

public class ThanhVienAdapter extends ArrayAdapter<ThanhVien> {
    private Context context;
    QuanLyThanhVienFragment fragment;
    private ArrayList<ThanhVien> lists;
    TextView tvXoaThanhVien,tvMaThanhVien,tvTenThanhVien,tvNamSinh,tvSuaThanhVien;
    ThanhVien thanhVien;

    public ThanhVienAdapter(@NonNull Context context, QuanLyThanhVienFragment fragment,
                       ArrayList<ThanhVien> list) {
        super(context, 0, list);
        this.context = context;
        this.fragment = fragment;
        this.lists = list;
    }

    public ThanhVien getThanhVien(int po){
        thanhVien = lists.get(po);
        return thanhVien;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_thanhvien,null);
        }

        final ThanhVien item = lists.get(position);

        if (item != null){
            tvMaThanhVien = view.findViewById(R.id.tvMaThanhVien);
            position += 1;
            tvMaThanhVien.setText("Mã thành viên: " + position);

            tvTenThanhVien = view.findViewById(R.id.tvTenThanhVien);
            tvTenThanhVien.setText(item.hoTen);

            tvNamSinh = view.findViewById(R.id.tvNamSinh);
            tvNamSinh.setText("Năm sinh: " +item.namSinh);

            tvXoaThanhVien = view.findViewById(R.id.tvXoaThanhVien);
            tvXoaThanhVien.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragment.deleteThanhVien(String.valueOf(item.getMaTV()));
                }
            });

            tvSuaThanhVien = view.findViewById(R.id.tvSuaThanhVien);
            tvSuaThanhVien.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragment.updateData(item);
                }
            });

        }
        return view;
    }
}
