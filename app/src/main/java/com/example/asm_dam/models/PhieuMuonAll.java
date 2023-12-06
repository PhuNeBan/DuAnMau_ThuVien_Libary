package com.example.asm_dam.models;

public class PhieuMuonAll {
    public int maPM;
    public int maTV;
    public String maTT;
    public int maS;
    public String ngay;
    public int tienThue;
    public int traSach;
    public String tenThanhVien;
    public String tenSach;

    public PhieuMuonAll(int maPM, int maTV, String maTT, int maS, String ngay, int tienThue, int traSach, String tenThanhVien, String tenSach) {
        this.maPM = maPM;
        this.maTV = maTV;
        this.maTT = maTT;
        this.maS = maS;
        this.ngay = ngay;
        this.tienThue = tienThue;
        this.traSach = traSach;
        this.tenThanhVien = tenThanhVien;
        this.tenSach = tenSach;
    }

    public PhieuMuonAll() {
    }
}
