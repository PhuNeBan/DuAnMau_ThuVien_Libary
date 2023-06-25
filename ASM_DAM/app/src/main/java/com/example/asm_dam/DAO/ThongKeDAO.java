package com.example.asm_dam.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm_dam.database.Database;
import com.example.asm_dam.models.Sach;
import com.example.asm_dam.models.Top;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ThongKeDAO {

    private SQLiteDatabase db;
    private Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public ThongKeDAO(Context context){
        this.context = context;
        Database database = new Database(context);
        db = database.getWritableDatabase();
    }

    public List<Top> getTop(){
        String sql = "SELECT MA_SACH, COUNT(MA_SACH) AS SOLUONG FROM PHIEUMUON GROUP BY MA_SACH " +
                "ORDER BY SOLUONG DESC LIMIT 10";
        List<Top> list = new ArrayList<>();
        SachDAO sachDAO = new SachDAO(context);
        Cursor cursor = db.rawQuery(sql,null);
        while (cursor.moveToNext()){
            Top top = new Top();
            Sach sach = sachDAO.getID(cursor.getString(0));
            top.tenSach = sach.tenSach;
            top.soLuong = Integer.parseInt(cursor.getString(1));
            list.add(top);
        }
        return list;
    }

    public int getDoanhThu(String tuNgay, String denNgay){
        String sql = "SELECT SUM(TIENTHUE) AS DOANHTHU FROM PHIEUMUON WHERE NGAY BETWEEN ? AND ?";
        List<Integer> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,new String[]{tuNgay,denNgay});
        while (cursor.moveToNext()){
            try {
                list.add(Integer.parseInt(cursor.getString(0)));
            }
            catch (Exception e){
                list.add(0);
            }
        }
        return list.get(0);
    }


}
