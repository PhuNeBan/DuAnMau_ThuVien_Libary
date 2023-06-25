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
import com.example.asm_dam.fragments.QuanLyLoaiSachFragment;
import com.example.asm_dam.models.LoaiSach;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LoaiSachAdapter extends ArrayAdapter<LoaiSach> {

    private Context context;
    QuanLyLoaiSachFragment fragment;
    private ArrayList<LoaiSach> lists;
    TextView tvMaLoai,tvTenLoai,tvXoaLoaiSach;

    public LoaiSachAdapter(@NonNull Context context, QuanLyLoaiSachFragment fragment,
                           ArrayList<LoaiSach> list) {
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
            view = inflater.inflate(R.layout.item_loaisach,null);
        }

        final LoaiSach item = lists.get(position);

        if (item != null){
            tvMaLoai = view.findViewById(R.id.tvMaLoai);
            tvMaLoai.setText("Mã: "+item.maLS);

            tvTenLoai = view.findViewById(R.id.tvTenLoaiSach);
            tvTenLoai.setText(item.tenLoai.toUpperCase(Locale.ROOT));

            tvXoaLoaiSach = view.findViewById(R.id.tvXoaLoaiSach);

        }

        tvXoaLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.deleteLoaiSach(String.valueOf(item.getMaLS()));
            }
        });
        return view;
    }
}
