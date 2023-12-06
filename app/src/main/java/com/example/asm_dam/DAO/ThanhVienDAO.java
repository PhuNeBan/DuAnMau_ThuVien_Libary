package com.example.asm_dam.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm_dam.database.Database;
import com.example.asm_dam.models.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienDAO {
    private SQLiteDatabase db;

    public ThanhVienDAO(Context context){
        Database database = new Database(context);
        db = database.getWritableDatabase();
    }

    public long insert(ThanhVien obj){
        ContentValues values = new ContentValues();
        values.put("HOTEN",obj.hoTen);
        values.put("NAMSINH",obj.namSinh);
        return db.insert("THANHVIEN",null,values);
    }

    public int update(ThanhVien obj){
        ContentValues values = new ContentValues();
        values.put("HOTEN",obj.hoTen);
        values.put("NAMSINH",obj.namSinh);
        return db.update("THANHVIEN",values,"MA_THANHVIEN=?",new String[]{String.valueOf(obj.maTV)});
    }

    public int delete(String id){
        return db.delete("THANHVIEN","MA_THANHVIEN=?",new String[]{id});
    }

    public List<ThanhVien> getAll(){
        String sql = "SELECT * FROM THANHVIEN";
        return getData(sql);
    }

    public ThanhVien getID(String id){
        String sql = "SELECT * FROM THANHVIEN WHERE MA_THANHVIEN=?";
        List<ThanhVien> list = getData(sql,id);
        return list.get(0);
    }

    private List<ThanhVien> getData (String sql, String...selectionArgs){
        List<ThanhVien> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        while (cursor.moveToNext()){
            ThanhVien obj = new ThanhVien();
            obj.maTV = Integer.parseInt(cursor.getString(0));
            obj.hoTen = cursor.getString(1);
            obj.namSinh = cursor.getString(2);
            list.add(obj);
        }
        return list;
    }
}
