package com.example.fouthapp.entity;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

import org.litepal.crud.LitePalSupport;

import java.util.Date;

@SmartTable(name = "学生打卡信息表")
public class Student extends LitePalSupport {
    @SmartColumn(id = 1,name = "学号")
    private String Sno;
    @SmartColumn(id = 2,name = "姓名")
    private String Sname;
    private int Sage;
    private String Ssex;
    private String Sdept;
    private String Sclass;
    @SmartColumn(id = 3,name = "是否打卡")
    private int isSign;
    @SmartColumn(id = 4,name = "打卡时间")
    private Date signTime;

    public Student(String sno, String sname, int isSign, Date signTime) {
        Sno = sno;
        Sname = sname;
        this.isSign = isSign;
        this.signTime = signTime;
    }

    public Student(String sno, String sname, int sage, String ssex, String sdept, String sclass, int isSign, Date signTime) {
        Sno = sno;
        Sname = sname;
        Sage = sage;
        Ssex = ssex;
        Sdept = sdept;
        Sclass = sclass;
        this.isSign = isSign;
        this.signTime = signTime;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public Student() {
    }

    public Student(String sno, String sname, int sage, String ssex, String sdept, String sclass, int isSign) {
        Sno = sno;
        Sname = sname;
        Sage = sage;
        Ssex = ssex;
        Sdept = sdept;
        Sclass = sclass;
        this.isSign = isSign;
    }

    public String getSno() {
        return Sno;
    }

    public void setSno(String sno) {
        Sno = sno;
    }

    public String getSname() {
        return Sname;
    }

    public void setSname(String sname) {
        Sname = sname;
    }

    public int getSage() {
        return Sage;
    }

    public void setSage(int sage) {
        Sage = sage;
    }

    public String getSsex() {
        return Ssex;
    }

    public void setSsex(String ssex) {
        Ssex = ssex;
    }

    public String getSdept() {
        return Sdept;
    }

    public void setSdept(String sdept) {
        Sdept = sdept;
    }

    public String getSclass() {
        return Sclass;
    }

    public void setSclass(String sclass) {
        Sclass = sclass;
    }

    public int getIsSign() {
        return isSign;
    }

    public void setIsSign(int isSign) {
        this.isSign = isSign;
    }
}
