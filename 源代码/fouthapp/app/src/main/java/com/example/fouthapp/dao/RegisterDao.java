package com.example.fouthapp.dao;

import com.example.fouthapp.util.DBUtilPre;

public class RegisterDao {
    DBUtilPre conn = new DBUtilPre();

    public boolean register(String userId,String password,String username,String role){
        boolean result = false;
        String[] params = {userId, password,username,role};
        String sql = "insert into user(userId,password,username,role) values(?,?,?,?)";
        int count = conn.executeUpdate(sql,params);
        if (count!=0){
            result = true;
        }

        return result;
    }
}
