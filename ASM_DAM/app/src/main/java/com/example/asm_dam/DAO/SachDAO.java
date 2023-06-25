package com.example.asm_dam.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm_dam.database.Database;
import com.example.asm_dam.models.Sach;

import java.util.ArrayList;
import java.util.List;

public class SachDAO {
    private SQLiteDatabase db;

    public SachDAO(Context context){
        Database database = new Database(context);
        db = database.getWritableDatabase();
    }

    public long insert(Sach obj){
        ContentValues values = new ContentValues();
        values.put("TENSACH",obj.tenSach);
        values.put("GIATHUE",obj.giaThue);
        values.put("MA_LOAISACH",obj.loai);
        return db.insert("SACH",null,values);
    }

    public int update(Sach obj){
        ContentValues values = new ContentValues();
        values.put("TENSACH",obj.tenSach);
        values.put("GIATHUE",obj.giaThue);
        values.put("MA_LOAISACH",obj.loai);
        return db.update("SACH",values,"MA_SACH=?",new String[]{String.valueOf(obj.maS)});
    }

    public int delete(String id){
        return db.delete("SACH","MA_SACH=?",new String[]{id});
    }

    public List<Sach> getAll(){
        String sql = "SELECT * FROM SACH";
        return getData(sql);
    }

    public Sach getID(String id){
        String sql = "SELECT * FROM SACH WHERE MA_SACH=?";
        List<Sach> list = getData(sql,id);
        return list.get(0);
    }

    private List<Sach> getData (String sql, String...selectionArgs){
        List<Sach> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        while (cursor.moveToNext()){
            Sach obj = new Sach();
            obj.maS = Integer.parseInt(cursor.getString(0));
            obj.tenSach = cursor.getString(1);
            obj.giaThue = Integer.parseInt(cursor.getString(2));
            obj.loai = Integer.parseInt(cursor.getString(3));
            list.add(obj);
        }
        return list;
    }
}
