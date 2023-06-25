package com.example.asm_dam.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.asm_dam.DAO.SachDAO;
import com.example.asm_dam.DAO.ThongKeDAO;
import com.example.asm_dam.R;
import com.example.asm_dam.adapters.SachAdapter;
import com.example.asm_dam.adapters.TopAdapter;
import com.example.asm_dam.models.Sach;
import com.example.asm_dam.models.Top;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TopFragment extends Fragment {
    ListView listView;
    ThongKeDAO dao;
    ArrayList<Top> lists;
    TopAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top10,container,false);

        listView = view.findViewById(R.id.lvTop);
        dao = new ThongKeDAO(getActivity());

        capnhatLV();
        return view;
    }

    public  void capnhatLV(){
        lists = (ArrayList<Top>) dao.getTop();
        System.out.println("Top : " + lists);
        adapter = new TopAdapter(getContext(),this,lists);
        listView.setAdapter(adapter);
    }
}
