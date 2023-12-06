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
import com.example.asm_dam.fragments.TopFragment;
import com.example.asm_dam.models.Top;

import java.util.ArrayList;

public class TopAdapter extends ArrayAdapter<Top> {
    private Context context;
    TopFragment fragment;
    private ArrayList<Top> lists;
    TextView tvTop,tvTenTop,tvSoLuong;

    public TopAdapter(@NonNull Context context, TopFragment fragment,
                            ArrayList<Top> list) {
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
            view = inflater.inflate(R.layout.item_top,null);
        }

        final Top item = lists.get(position);

        if (item != null){
            tvTop = view.findViewById(R.id.tvTop);
            tvTop.setText(String.valueOf(position + 1 ));

            tvTenTop = view.findViewById(R.id.tvTenTop);
            tvTenTop.setText(item.tenSach);

            tvSoLuong = view.findViewById(R.id.tvSoLuong);
            tvSoLuong.setText(item.soLuong+"");

        }
        return view;
    }
}
