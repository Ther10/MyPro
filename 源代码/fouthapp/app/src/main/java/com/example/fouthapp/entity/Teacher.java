package com.example.fouthapp.entity;

import org.litepal.crud.LitePalSupport;

public class Teacher extends LitePalSupport {
    private String Tno;
    private String Tname;
    private String Tsex;
    private int Tage;
    private String Tdept;

    public Teacher() {
    }

    public Teacher(String tno, String tname, String tsex, int tage, String tdept) {
        Tno = tno;
        Tname = tname;
        Tsex = tsex;
        Tage = tage;
        Tdept = tdept;
    }

    public String getTno() {
        return Tno;
    }

    public void setTno(String tno) {
        Tno = tno;
    }

    public String getTname() {
        return Tname;
    }

    public void setTname(String tname) {
        Tname = tname;
    }

    public String getTsex() {
        return Tsex;
    }

    public void setTsex(String tsex) {
        Tsex = tsex;
    }

    public int getTage() {
        return Tage;
    }

    public void setTage(int tage) {
        Tage = tage;
    }

    public String getTdept() {
        return Tdept;
    }

    public void setTdept(String tdept) {
        Tdept = tdept;
    }



}
