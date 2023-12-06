package com.example.asm_dam.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm_dam.database.Database;
import com.example.asm_dam.models.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class ThuThuDAO {
    private SQLiteDatabase db;

    public ThuThuDAO(Context context){
        Database database = new Database(context);
        db = database.getWritableDatabase();
    }

    public long insert(ThuThu obj){
        ContentValues values = new ContentValues();
        values.put("MA_THUTHU",obj.maTT);
        values.put("HOTEN",obj.hoTen);
        values.put("MATKHAU",obj.matKhau);
        return db.insert("THUTHU",null,values);
    }

    public int update(ThuThu obj){
        ContentValues values = new ContentValues();
        values.put("HOTEN",obj.hoTen);
        values.put("MATKHAU",obj.matKhau);
        return db.update("THUTHU",values,"MA_THUTHU=?",new String[]{String.valueOf(obj.maTT)});
    }

//    public int updatePass(ThuThu obj){
//        ContentValues values = new ContentValues();
//        values.put("HOTEN",obj.hoTen);
//        values.put("MATKHAU",obj.matKhau);
//        return db.update("THUTHU",values,"MA_THUTHU=?",new String[]{String.valueOf(obj.maTT)});
//    }

    public int delete(String id){
        return db.delete("THUTHU","MA_THuTHU=?",new String[]{id});
    }

    public List<ThuThu> getAll(){
        String sql = "SELECT * FROM THUTHU";
        return getData(sql);
    }

    public ThuThu getID(String id){
        String sql = "SELECT * FROM THUTHU WHERE MA_THUTHU=?";
        List<ThuThu> list = getData(sql,id);
        return list.get(0);
    }

    private List<ThuThu> getData (String sql, String...selectionArgs){
        List<ThuThu> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        while (cursor.moveToNext()){
            ThuThu obj = new ThuThu();
            obj.maTT = cursor.getString(0);
            obj.hoTen = cursor.getString(1);
            obj.matKhau = cursor.getString(2);
            list.add(obj);
        }
        return list;
    }

    public int checkLogin(String id, String pass){
        System.out.println("USer trong DAO: " + id);
        System.out.println("Pass trong DAO: " + pass);
        String sql = "SELECT * FROM THUTHU WHERE MA_THUTHU= ? AND MATKHAU= ?";
        List<ThuThu> list = getData(sql,id,pass);
        if(list.size() == 0){
            return -1;
        }
        return 1;
    }

}
