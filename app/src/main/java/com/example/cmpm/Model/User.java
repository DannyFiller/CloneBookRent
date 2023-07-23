package com.example.cmpm.Model;

public class User {
    private String id;
    private String Gmail;
    private String password;
    private int vaiTro;
    private String ten;
    private String sdt;

//    public User(String id, String gmail, String password, int vaiTro) {
//        this.id = id;
//        this.gmail = gmail;
//        this.password = password;
//        this.vaiTro = vaiTro;
//    }

    public User(){}

    public User(String gmail, int vaiTro) {
        this.Gmail = gmail;
        this.vaiTro = vaiTro;
    }

    public User(String gmail, String password, int vaiTro, String ten, String sdt) {
        Gmail = gmail;
        this.password = password;
        this.vaiTro = vaiTro;
        this.ten = ten;
        this.sdt = sdt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(int vaiTro) {
        this.vaiTro = vaiTro;
    }

    public String getGmail() {
        return Gmail;
    }

    public void setGmail(String gmail) {
        this.Gmail = gmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
