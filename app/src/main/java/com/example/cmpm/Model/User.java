package com.example.cmpm.Model;

public class User {
    private String id;
    private String gmail;
    private String password;
    private int vaiTro;

//    public User(String id, String gmail, String password, int vaiTro) {
//        this.id = id;
//        this.gmail = gmail;
//        this.password = password;
//        this.vaiTro = vaiTro;
//    }

    public User(){}

    public User(String gmail, int vaiTro) {
        this.gmail = gmail;
        this.vaiTro = vaiTro;
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
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
