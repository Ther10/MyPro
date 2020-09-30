package com.example.fouthapp.dao;

import com.example.fouthapp.entity.User;
import com.example.fouthapp.util.DBUtilPre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    DBUtilPre conn = new DBUtilPre();

    public User getUserThroughUserId(String userId){
        User user = null;
        String[] params = {userId};
        String sql = "select * from user where userId=?";
        ResultSet rs = conn.executeQueryRS(sql,params);

        try {
            while(rs.next()){
                user = new User();
                user.setUserId(rs.getString("userId"));
                user.setPassword(rs.getString("password"));
                user.setUsername(rs.getString("username"));
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

    public String getPassByUserId(String userId){
        String password = null;
        String[] params = {userId};
        String sql = "select password from user where userId = ?";
        ResultSet rs = conn.executeQueryRS(sql, params);
        try {
            while(rs.next()){

                password =rs.getString("password");

            }
            return password;
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

    public boolean changePass(String userId,String password){
        boolean result = false;
        String[] params = {password,userId};
        String sql = "update user set password = ? where userId = ?";
        int count = conn.executeUpdate(sql, params);
        if (count!=0){
            result = true;
        }
        return result;
    }

    public String getNameByUserId (String userId){
        String username = null;
        String[] params = {userId};
        String sql = "select * from user where userId=?";
        ResultSet rs = conn.executeQueryRS(sql,params);

        try {
            while(rs.next()){
                username = rs.getString("username");
            }
            return username;
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
