package com.example.asm_dam.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.asm_dam.DAO.LoaiSachDAO;
import com.example.asm_dam.R;
import com.example.asm_dam.fragments.QuanLySachFragment;
import com.example.asm_dam.models.LoaiSach;
import com.example.asm_dam.models.Sach;

import java.util.ArrayList;

public class SachAdapter extends ArrayAdapter<Sach> {
    private Context context;
    QuanLySachFragment fragment;
    private ArrayList<Sach> lists;
    TextView tvTenSach,tvMaSach,tvGiaMuon,tvTheLoai,tvXoaSach;


    public SachAdapter(@NonNull Context context, QuanLySachFragment fragment,
                            ArrayList<Sach> list) {
        super(context, 0, list);
        this.context = context;
        this.fragment = fragment;
        this.lists = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_sach,null);
        }

        final Sach item = lists.get(position);

        if (item != null){
            tvTenSach = view.findViewById(R.id.tvTenSach);
            tvTenSach.setText(item.tenSach);

            position += 1;
            tvMaSach = view.findViewById(R.id.tvMaSach);
            tvMaSach.setText("Mã: "+ position);

            tvGiaMuon = view.findViewById(R.id.tvGiaMuon);
            tvGiaMuon.setText("Giá: " +item.giaThue);

            tvTheLoai = view.findViewById(R.id.tvTheLoai);
            LoaiSachDAO loaiSachDAO = new LoaiSachDAO(getContext());
            LoaiSach loaiSach = loaiSachDAO.getID(String.valueOf(item.getLoai()));
            tvTheLoai.setText("Thể loại: "+ loaiSach.getTenLoai());

            tvXoaSach = view.findViewById(R.id.tvXoaSach);
            tvXoaSach.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragment.deleteSach(String.valueOf(item.getMaS()));
                }
            });

        }
        return view;
    }
}
