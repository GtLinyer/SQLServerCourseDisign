package cn.linyer.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
/**
 * @author Linyer
 * 数据库连接、增删改通用功能
 * 
 */
public class BaseDao {

	private static String driver;
	private static String url;
	private static String user;
	private static String password;
	
	static{
		init();
	}
	
	private static void init() {
		Properties pro = new Properties();
		
		InputStream is = BaseDao.class.getClassLoader().getResourceAsStream("database.properties");
		try {
			pro.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		driver = pro.getProperty("driver");
		url = pro.getProperty("url");
		user = pro.getProperty("user");
		password = pro.getProperty("password");
	}
	
	//获取连接
	public Connection getConnection(){
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	//关闭所有资源
	public void closeAll(Connection conn, PreparedStatement pstmt, ResultSet rs){
		try {
			if(rs != null){
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null){
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if(conn != null){
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//增删改通用
	public int exeUpdate(String sql, Object[] params){
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i+1, params[i]);
			}
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}