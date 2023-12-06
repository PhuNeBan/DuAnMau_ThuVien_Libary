package com.example.asm_dam.models;

public class Sach {
    public int maS;
    public String tenSach;
    public int giaThue;
    public int loai;

    public Sach(int maS, String tenSach, int giaThue, int loai) {
        this.maS = maS;
        this.tenSach = tenSach;
        this.giaThue = giaThue;
        this.loai = loai;
    }

    public Sach() {
    }

    public int getMaS() {
        return maS;
    }

    public void setMaS(int maS) {
        this.maS = maS;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public int getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(int giaThue) {
        this.giaThue = giaThue;
    }

    public int getLoai() {
        return loai;
    }

    public void setLoai(int loai) {
        this.loai = loai;
    }
}
