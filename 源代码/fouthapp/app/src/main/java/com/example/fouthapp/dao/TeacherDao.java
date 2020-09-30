package com.example.fouthapp.dao;

import com.example.fouthapp.entity.Teacher;
import com.example.fouthapp.util.DBUtilPre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherDao {
    DBUtilPre conn = new DBUtilPre();

    //根据Sno查询用户
    public Teacher findUserByTno (String Tno){
        Teacher teacher = null;
        String[] params = {Tno};
        String sql = "select * from teacher where Tno = ?";
        ResultSet rs = conn.executeQueryRS(sql, params);
        try {
            while (rs.next()){
                teacher = new Teacher();
                teacher.setTno(rs.getString("Tno"));
                teacher.setTname(rs.getString("Tname"));
                teacher.setTsex(rs.getString("Tsex"));
                teacher.setTage(rs.getInt("Tage"));
                teacher.setTdept(rs.getString("Tdept"));
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
        return teacher;
    }
}
