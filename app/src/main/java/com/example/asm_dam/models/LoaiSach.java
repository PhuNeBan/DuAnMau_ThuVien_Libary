package com.example.asm_dam.models;

public class LoaiSach {
    public int maLS;
    public String tenLoai;

    public LoaiSach(int maLS, String tenLoai) {
        this.maLS = maLS;
        this.tenLoai = tenLoai;
    }

    public LoaiSach() {
    }

    public int getMaLS() {
        return maLS;
    }

    public void setMaLS(int maLS) {
        this.maLS = maLS;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }
}
