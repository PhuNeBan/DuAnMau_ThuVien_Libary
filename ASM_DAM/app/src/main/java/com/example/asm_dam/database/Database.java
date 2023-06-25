package com.example.asm_dam.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    private static Database instance;
    public static synchronized Database getInstance(Context context){
        if (instance == null){
            instance = new Database(context);
        }
        return instance;
    }

    public Database( Context context){
        super(context, "MyDatabase", null, 8);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Tạo bảng ThuThu
        String createTableThuthu = "CREATE TABLE THUTHU (" +
                "MA_THUTHU TEXT PRIMARY KEY," +
                "HOTEN TEXT NOT NULL," +
                "MATKHAU TEXT NOT NULL)";
        db.execSQL(createTableThuthu);
        //insert cho bảng ThuThu
        String sqlInsertThuThu = "INSERT INTO THUTHU (MA_THUTHU ,HOTEN, MATKHAU)" +
                "VALUES ('Admin','Admin','admin');";
        db.execSQL(sqlInsertThuThu);

        //Tạo bảng thành ThanhVien
        String createTableThanhVien = "CREATE TABLE THANHVIEN (" +
                "MA_THANHVIEN INTEGER PRIMARY KEY AUTOINCREMENT," +
                "HOTEN TEXT NOT NULL," +
                "NAMSINH TEXT NOT NULL)";
        db.execSQL(createTableThanhVien);
        //insert bảng ThanhVien
        String sqlInsertThanhVien1 = "INSERT INTO THANHVIEN (HOTEN, NAMSINH)" +
                "VALUES ('Nguyễn Văn A', '2003');";
        db.execSQL(sqlInsertThanhVien1);

        String sqlInsertThanhVien2 = "INSERT INTO THANHVIEN (HOTEN, NAMSINH)" +
                "VALUES ('Nguyễn Văn B', '2002');";
        db.execSQL(sqlInsertThanhVien2);

        //Tạo bảng LoaiSach
        String createTableLoaiSach = "CREATE TABLE LOAISACH (" +
                "MA_LOAISACH INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TENLOAI TEXT NOT NULL)";
        db.execSQL(createTableLoaiSach);
        //insert bảng LoaiSach
        String sqlInsertLoaiSach1 = "INSERT INTO LOAISACH (TENLOAI)" +
                "VALUES ('Android');";
        db.execSQL(sqlInsertLoaiSach1);

        String sqlInsertLoaiSach2 = "INSERT INTO LOAISACH (TENLOAI)" +
                "VALUES ('Html');";
        db.execSQL(sqlInsertLoaiSach2);

        //Tạo bảng Sach
        String createTableSach = "CREATE TABLE SACH (" +
                "MA_SACH INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TENSACH TEXT NOT NULL," +
                "GIATHUE INTEGER NOT NULL," +
                "MA_LOAISACH INTEGER REFERENCES LOAISACH (MA_LOAISACH))";
        db.execSQL(createTableSach);
        //insert bảng Sach
        String sqlInsertSach1 = "INSERT INTO SACH (TENSACH, GIATHUE, MA_LOAISACH)" +
                "VALUES ('Java căn bản', 10000, 1);";
        db.execSQL(sqlInsertSach1);

        //Tạo bảng PhieuMuon
        String createTablePhieuMuon = "CREATE TABLE PHIEUMUON (" +
                "MA_PHIEU INTEGER PRIMARY KEY AUTOINCREMENT," +
                "MA_THANHVIEN INTEGER REFERENCES THANHVIEN (MA_THANHVIEN)," +
                "MA_THUTHU TEXT REFERENCES THUTHU (MA_THUTHU)," +
                "MA_SACH INTEGER REFERENCES SACH (MA_SACH)," +
                "NGAY DATE NOT NULL," +
                "TIENTHUE INTEGER NOT NULL," +
                "TRASACH INTEGER NOT NULL)"; //0: chưa trả  1: đã trả
        db.execSQL(createTablePhieuMuon);
        //insert bangPhieuMuon
        String sqlInsertPhieuMuon1 = "INSERT INTO PHIEUMUON (MA_THANHVIEN, MA_THUTHU, MA_SACH, NGAY, TIENTHUE, TRASACH)" +
                "VALUES (1,'Admin',1,'2023-05-15',10000,0);";
        db.execSQL(sqlInsertPhieuMuon1);

        String sqlInsertPhieuMuon2 = "INSERT INTO PHIEUMUON (MA_THANHVIEN, MA_THUTHU, MA_SACH, NGAY, TIENTHUE, TRASACH)" +
                "VALUES (1,'Admin',1,'2023-05-15',10000,1);";
        db.execSQL(sqlInsertPhieuMuon2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion != oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS PHIEUMUON");
            db.execSQL("DROP TABLE IF EXISTS SACH");
            db.execSQL("DROP TABLE IF EXISTS LOAISACH");
            db.execSQL("DROP TABLE IF EXISTS THANHVIEN");
            db.execSQL("DROP TABLE IF EXISTS THUTHU");
            onCreate(db);
        }
    }

}
