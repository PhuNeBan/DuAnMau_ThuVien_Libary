package com.example.asm_dam.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm_dam.database.Database;
import com.example.asm_dam.models.LoaiSach;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachDAO {
    private SQLiteDatabase db;

    public LoaiSachDAO(Context context){
        Database database = new Database(context);
        db = database.getWritableDatabase();
    }

    public long insert(LoaiSach obj){
        ContentValues values = new ContentValues();
        values.put("TENLOAI",obj.tenLoai);
        return db.insert("LOAISACH",null,values);
    }

    public int update(LoaiSach obj){
        ContentValues values = new ContentValues();
        values.put("TENLOAI",obj.tenLoai);
        return db.update("LOAISACH",values,"MA_LOAISACH=?",new String[]{String.valueOf(obj.maLS)});
    }

    public int delete(String id){
        return db.delete("LOAISACH","MA_LOAISACH=?",new String[]{id});
    }

    public List<LoaiSach> getAll(){
        String sql = "SELECT * FROM LOAISACH";
        return getData(sql);
    }

    public LoaiSach getID(String id){
        String sql = "SELECT * FROM LOAISACH WHERE MA_LOAISACH=?";
        List<LoaiSach> list = getData(sql,id);
        return list.get(0);
    }

    private List<LoaiSach> getData (String sql, String...selectionArgs){
        List<LoaiSach> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        while (cursor.moveToNext()){
            LoaiSach obj = new LoaiSach();
            obj.maLS = Integer.parseInt(cursor.getString(0));
            obj.tenLoai = cursor.getString(1);
            list.add(obj);
        }
        return list;
    }
}
