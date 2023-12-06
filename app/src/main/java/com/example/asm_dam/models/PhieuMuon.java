package com.example.asm_dam.models;

public class PhieuMuon {
    public int maPM;
    public int maTV;
    public String maTT;
    public int maS;
    public String ngay;
    public int tienThue;
    public int traSach;
    public String tenTV;
    public String tenS;

    public PhieuMuon(int maPM, int maTV, String maTT, int maS, String ngay, int tienThue, int traSach, String tenTV, String tenS) {
        this.maPM = maPM;
        this.maTV = maTV;
        this.maTT = maTT;
        this.maS = maS;
        this.ngay = ngay;
        this.tienThue = tienThue;
        this.traSach = traSach;
        this.tenTV = tenTV;
        this.tenS = tenS;
    }


    public PhieuMuon(int maPM, int maTV, String maTT, int maS, String ngay, int tienThue, int traSach) {
        this.maPM = maPM;
        this.maTV = maTV;
        this.maTT = maTT;
        this.maS = maS;
        this.ngay = ngay;
        this.tienThue = tienThue;
        this.traSach = traSach;
    }

    public PhieuMuon() {
    }

    public int getMaPM() {
        return maPM;
    }

    public void setMaPM(int maPM) {
        this.maPM = maPM;
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public int getMaS() {
        return maS;
    }

    public void setMaS(int maS) {
        this.maS = maS;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public int getTienThue() {
        return tienThue;
    }

    public void setTienThue(int tienThue) {
        this.tienThue = tienThue;
    }

    public int getTraSach() {
        return traSach;
    }

    public void setTraSach(int traSach) {
        this.traSach = traSach;
    }

    public String getTenTV() {
        return tenTV;
    }

    public void setTenTV(String tenTV) {
        this.tenTV = tenTV;
    }

    public String getTenS() {
        return tenS;
    }

    public void setTenS(String tenS) {
        this.tenS = tenS;
    }
}
