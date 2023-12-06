package com.example.asm_dam.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm_dam.database.Database;
import com.example.asm_dam.models.PhieuMuon;
import com.example.asm_dam.models.PhieuMuonAll;
import com.example.asm_dam.models.Sach;
import com.example.asm_dam.models.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class PhieuMuonDAO {
    private SQLiteDatabase db;

    public PhieuMuonDAO(Context context){
        Database database = new Database(context);
        db = database.getWritableDatabase();
    }

    public long insert(PhieuMuon obj){
        ContentValues values = new ContentValues();
        values.put("MA_THANHVIEN",obj.maTV);
        values.put("MA_THUTHU",obj.maTT);
        values.put("MA_SACH",obj.maS);
        values.put("NGAY",obj.ngay);
        values.put("TIENTHUE",obj.tienThue);
        values.put("TRASACH",obj.traSach);
        return db.insert("PHIEUMUON",null,values);
    }

    public int update(PhieuMuon obj){
        ContentValues values = new ContentValues();
        values.put("MA_THANHVIEN",obj.maTV);
        values.put("MA_THUTHU",obj.maTT);
        values.put("MA_SACH",obj.maS);
        values.put("NGAY",obj.ngay);
        values.put("TIENTHUE",obj.tienThue);
        values.put("TRASACH",obj.traSach);
        return db.update("PHIEUMUON",values,"MA_PHIEU=?",new String[]{String.valueOf(obj.maPM)});
    }

    public int delete(String id){
        return db.delete("PHIEUMUON","MA_PHIEU=?",new String[]{id});
    }

    public List<PhieuMuon> getAll(){
        String sql = "SELECT * FROM PHIEUMUON";
        return getData(sql);
    }

    public PhieuMuon getID(String id){
        String sql = "SELECT * FROM PHIEUMUON WHERE MA_PHIEU=?";

        List<PhieuMuon> list = getData(sql,id);
        return list.get(0);
    }

    private List<PhieuMuon> getData (String sql, String...selectionArgs){
        List<PhieuMuon> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        while (cursor.moveToNext()){
            PhieuMuon obj = new PhieuMuon();
            obj.maPM = Integer.parseInt(cursor.getString(0));
            obj.maTV = Integer.parseInt(cursor.getString(1));
            obj.maTT = cursor.getString(2);
            obj.maS = Integer.parseInt(cursor.getString(3));
            obj.ngay = cursor.getString(4);
            obj.tienThue = Integer.parseInt(cursor.getString(5));
            obj.traSach = Integer.parseInt(cursor.getString(6));
            list.add(obj);
        }
        return list;
    }

}
