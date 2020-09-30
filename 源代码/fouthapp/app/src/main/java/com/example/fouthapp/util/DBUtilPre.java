package com.example.fouthapp.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
public class DBUtilPre {
 
	
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URLSTR = "jdbc:mysql://123.57.200.39:3306/sign2";
	private static final String USERNAME = "root";
	private static final String USERPASSWORD = "root";
	private Connection connnection = null; 
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
 
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("驱动连接失败");
			System.out.println(e.getMessage());
		}
	}

	public Connection getConnection() {
		try {
			connnection = DriverManager.getConnection(URLSTR, USERNAME,
					USERPASSWORD);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return connnection;
	}
	public int executeDelete(String sql,Object[] params) {
		int affectedLine = 0;
		try {
			connnection = this.getConnection();
			preparedStatement = connnection.prepareStatement(sql);
			for (int i = 0; i < 4; i++) {
				preparedStatement.setObject(i + 1, params[i]);
			}
 
			affectedLine = preparedStatement.executeUpdate();
 
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			closeAll();
		}
		return affectedLine;
	}
 
	public int executeUpdate(String sql, Object[] params) {
		int affectedLine = 0;
		try {
			connnection = this.getConnection();
			preparedStatement = connnection.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				preparedStatement.setObject(i + 1, params[i]);
			}
 
			affectedLine = preparedStatement.executeUpdate();
 
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			closeAll();
		}
		return affectedLine;
	}

 
	public ResultSet executeQueryRS(String sql, Object...params) {
		try {
			connnection = this.getConnection();
			preparedStatement = connnection.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				preparedStatement.setObject(i + 1, params[i]);
			}
 
			resultSet = preparedStatement.executeQuery();
 
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} 
		
		return resultSet;
	}
	

	public List<Object> excuteQuery(String sql, Object[] params)
	{ 
		ResultSet rs = executeQueryRS(sql,params);
		ResultSetMetaData rsmd = null;
		int columnCount = 0;
		try {
			rsmd = rs.getMetaData();
			columnCount = rsmd.getColumnCount();
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
		}
		
		List<Object> list = new ArrayList<Object>();
		
		try {
			while(rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for(int i = 1; i <= columnCount; i++) {
					map.put(rsmd.getColumnLabel(i), rs.getObject(i));				
				}
				list.add(map);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			closeAll();
		}
		
		return list;
	}

	public void closeAll() {
 
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
 
		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		
		if (connnection != null) {
			try {
				connnection.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}