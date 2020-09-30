package com.example.fouthapp.dao;

import com.example.fouthapp.entity.User;
import com.example.fouthapp.util.DBUtilPre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDao {
    DBUtilPre conn = new DBUtilPre();

    public User checkUser(String userId,String password){
        User user = null;
        String[] params = {userId,password};
        String sql = "select * from user where userId = ? and password = ?";
        ResultSet rs = conn.executeQueryRS(sql,params);

        try {
            while(rs.next()){
                user = new User();
                user.setUserId(rs.getString("userId"));
                user.setPassword(rs.getString("password"));
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }finally {
            if (rs!=null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
