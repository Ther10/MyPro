package com.example.fouthapp.dao;

import com.example.fouthapp.entity.Student;
import com.example.fouthapp.util.DBUtilPre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    DBUtilPre conn = new DBUtilPre();

    //根据Sno查询用户
    public Student findUserBySno (String Sno){
        Student student = null;
        String[] params = {Sno};
        String sql = "select * from student where Sno = ?";
        ResultSet rs = conn.executeQueryRS(sql, params);
        try {
            while (rs.next()){
                student = new Student();
                student.setSno(rs.getString("Sno"));
                student.setSname(rs.getString("Sname"));
                student.setSsex(rs.getString("Ssex"));
                student.setSage(rs.getInt("Sage"));
                student.setSdept(rs.getString("Sdept"));
                student.setSclass(rs.getString("Class"));
                student.setIsSign(rs.getInt("isSign"));
                student.setSignTime(rs.getTimestamp("signTime"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (rs!=null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return student;
    }

    //设置打卡状态
    public boolean setSign(String userId){
        boolean result = false;
        String[] params = {userId};
        String sql = "update student set isSign = 1 where Sno = ?";
        int count = conn.executeUpdate(sql, params);
        if (count!=0)
            result = true;
        return result;
    }

    //设置打卡时间
    public boolean setSignTime(String userId,String date){
        boolean result = false;
        String[] params = {userId};
        String sql = "update student set signTime = '"+date+"' where Sno = ?";
        int count = conn.executeUpdate(sql, params);
        if (count!=0)
            result = true;
        return result;
    }

    //查询未打卡学生信息
    public List<Student> findNoSign(){
        List<Student> students = new ArrayList<Student>();
        String[] params = {};
        String sql = "select * from student where isSign = 0 ";
        ResultSet rs = conn.executeQueryRS(sql, params);

        try {
            while (rs.next()) {
                Student student = new Student();
                student.setSno(rs.getString("Sno"));
                student.setSname(rs.getString("Sname"));
                student.setSsex(rs.getString("Ssex"));
                student.setSage(rs.getInt("Sage"));
                student.setSdept(rs.getString("Sdept"));
                student.setSclass(rs.getString("Class"));
                student.setIsSign(rs.getInt("isSign"));
                student.setSignTime(rs.getTimestamp("signTime"));
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (rs!=null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return students;
    }

    //查询所有学生信息
    public List<Student> findAll(){
        List<Student> students = new ArrayList<Student>();
        String[] params = {};
        String sql = "select * from student";
        ResultSet rs = conn.executeQueryRS(sql, params);

        try {
            while (rs.next()) {
                Student student = new Student();
                student.setSno(rs.getString("Sno"));
                student.setSname(rs.getString("Sname"));
                student.setSsex(rs.getString("Ssex"));
                student.setSage(rs.getInt("Sage"));
                student.setSdept(rs.getString("Sdept"));
                student.setSclass(rs.getString("Class"));
                student.setIsSign(rs.getInt("isSign"));
                student.setSignTime(rs.getTimestamp("signTime"));
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (rs!=null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return students;

    }

    //查询自己的打卡自己记录
    public List<Student> outMessageBySno(String Sno){
        List<Student> list = new ArrayList<Student>();
        String[] params = {Sno};
        String sql = "select * from student where Sno = ? ";
        ResultSet rs = conn.executeQueryRS(sql, params);

        try {
            while (rs.next()) {
                Student student = new Student();
                student.setSno(rs.getString("Sno"));
                student.setSname(rs.getString("Sname"));
                student.setIsSign(rs.getInt("isSign"));
                student.setSignTime(rs.getTimestamp("signTime"));
                list.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (rs!=null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    //查询学生是否打卡及打卡时间
    public List<Student> outMessage(){
        List<Student> list = new ArrayList<Student>();
        String[] params = {};
        String sql = "select * from student";
        ResultSet rs = conn.executeQueryRS(sql, params);

        try {
            while (rs.next()) {
                Student student = new Student();
                student.setSno(rs.getString("Sno"));
                student.setSname(rs.getString("Sname"));
                student.setIsSign(rs.getInt("isSign"));
                student.setSignTime(rs.getTimestamp("signTime"));
                list.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (rs!=null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
}
