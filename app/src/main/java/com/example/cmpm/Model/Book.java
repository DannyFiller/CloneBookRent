package com.example.cmpm.Model;

public class Book {
    private String id;
    private String TenSach;
    private String TacGia;
    private String Loai;
    private int Gia;
    private int SoLuong;
    private String image;
    private String moTa;
    private int tinhTrang;
    private int giaThue;



    public Book(){}

    public Book(String id, String tenSach, String tacGia, String loai, int gia,int giaThue, String image,String moTa) {
        this.id = id;
        this.TenSach = tenSach;
        this.TacGia = tacGia;
        this.Loai = loai;
        this.Gia = gia;
        this.giaThue = giaThue;
        this.image = image;
        this.moTa = moTa;
    }

    public Book(String id, String tenSach, String tacGia, String loai, int gia, int soLuong, String image) {
        this.id = id;
        TenSach = tenSach;
        TacGia = tacGia;
        Loai = loai;
        Gia = gia;
        SoLuong = soLuong;
        this.image = image;
    }

    public Book(String id, String tenSach, String tacGia, String loai, int gia, String image) {
        this.id = id;
        TenSach = tenSach;
        TacGia = tacGia;
        Loai = loai;
        Gia = gia;
        this.image = image;
    }



    public Book(String id, String tenSach, String tacGia, String loai, int gia, String image, String moTa, int tinhTrang) {
        this.id = id;
        TenSach = tenSach;
        TacGia = tacGia;
        Loai = loai;
        Gia = gia;
        this.image = image;
        this.moTa = moTa;
        this.tinhTrang = tinhTrang;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenSach() {
        return TenSach;
    }

    public void setTenSach(String tenSach) {
        TenSach = tenSach;
    }

    public String getTacGia() {
        return TacGia;
    }

    public void setTacGia(String tacGia) {
        TacGia = tacGia;
    }

    public String getLoai() {
        return Loai;
    }

    public void setLoai(String loai) {
        Loai = loai;
    }

    public int getGia() {
        return Gia;
    }

    public void setGia(int gia) {
        Gia = gia;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(int tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public int getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(int giaThue) {
        this.giaThue = giaThue;
    }
}
