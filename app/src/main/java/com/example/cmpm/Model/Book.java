package com.example.cmpm.Model;

public class Book {
    private String id;
    private String TenSach;
    private String TacGia;
    private String Loai;
    private int Gia;
    private int SoLuong;
    private String image;




    public Book(){}


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
}
