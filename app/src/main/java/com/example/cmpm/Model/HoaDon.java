package com.example.cmpm.Model;

import java.sql.Date;

public class HoaDon {
    private String maHoaDon;
    private String ngayThue;
    private String hanThue;
    private int tienCoc;
    private String maKH;
    private int tongTien;
    private String TenKh;

    public HoaDon(){}

    public HoaDon(String maHoaDon, String ngayThue, String maKH, int tongTien,String TenKh) {
        this.maHoaDon = maHoaDon;
        this.ngayThue = ngayThue;
        this.maKH = maKH;
        this.tongTien = tongTien;
        this.TenKh = TenKh;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getNgayThue() {
        return ngayThue;
    }

    public void setNgayThue(String ngayThue) {
        this.ngayThue = ngayThue;
    }

    public String getHanThue() {
        return hanThue;
    }

    public void setHanThue(String hanThue) {
        this.hanThue = hanThue;
    }

    public int getTienCoc() {
        return tienCoc;
    }

    public void setTienCoc(int tienCoc) {
        this.tienCoc = tienCoc;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public String getTenKh() {
        return TenKh;
    }

    public void setTenKh(String tenKh) {
        TenKh = tenKh;
    }
}
