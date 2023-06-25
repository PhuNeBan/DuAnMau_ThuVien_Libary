package com.example.asm_dam.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.asm_dam.DAO.ThongKeDAO;
import com.example.asm_dam.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DoanhThuFragment extends Fragment {
    Button btnTuNgay, btnDenNgay, btnGetDoanhThu;
    TextView tvDoanhThu;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    int mYear,mMonth,mDay;
    EditText edtTuNgay,edtDenNgay;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doanhthu,container,false);

        btnTuNgay = view.findViewById(R.id.btnTuNgay);
        btnDenNgay = view.findViewById(R.id.btnDenNgay);
        tvDoanhThu = view.findViewById(R.id.tvDoanhThu);
        btnGetDoanhThu = view.findViewById(R.id.btnGetDoanhThu);
        edtTuNgay = view.findViewById(R.id.edtTuNgay);
        edtDenNgay = view.findViewById(R.id.edtDenNgay);

        btnTuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(),0,dateTuNgay,mYear,mMonth,mDay);
                d.show();

            }
        });

        btnDenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(),0,dateDenNgay,mYear,mMonth,mDay);
                d.show();
            }
        });

        btnGetDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDoanhthu();
            }
        });

        return view;
    }

    public void getDoanhthu(){
        String tuNgay = edtTuNgay.getText().toString();
        String denNgay = edtDenNgay.getText().toString();
        if (tuNgay.isEmpty() || denNgay.isEmpty()){
            Toast.makeText(getContext(), "Chưa chọn đủ thông tin", Toast.LENGTH_SHORT).show();
        }
        else {
            ThongKeDAO dao = new ThongKeDAO(getActivity());
            tvDoanhThu.setText("Doanh thu: "+ dao.getDoanhThu(tuNgay,denNgay)+ "VND");
        }
    }

    //Lấy thời gian btnTuNgay
    DatePickerDialog.OnDateSetListener dateTuNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar(mYear,mMonth,mDay);
            edtTuNgay.setText(sdf.format(c.getTime()));
            System.out.println("Từ ngày: "+sdf.format(c.getTime()));
        }
    };

    //lấy thời gian btnDenNgay
    DatePickerDialog.OnDateSetListener dateDenNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar(mYear,mMonth,mDay);
            edtDenNgay.setText(sdf.format(c.getTime()));
            System.out.println("Từ ngày: "+sdf.format(c.getTime()));
        }
    };

}
