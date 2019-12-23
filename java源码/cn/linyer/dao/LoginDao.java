package cn.linyer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import cn.linyer.entity.Employer;
import cn.linyer.entity.Customer;
import cn.linyer.util.BaseDao;
/**
 * @author Linyer(韩啸翔)
 * 管理员数据库操作
 */
public class LoginDao extends BaseDao {
	//客户登录信息
	public Customer customerLogin(String no,String pwd) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Customer customer = null;
		
		try {
			String sql = "select 客户编号,客户姓名,客户登录密码 from dbo.客户登录 where 客户编号=? and 客户登录密码=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, no);
			pstmt.setString(2, pwd);
			rs = pstmt.executeQuery();
			while(rs.next()){
				customer = new Customer();
				customer.setcNo(rs.getString("客户编号"));
				customer.setcName(rs.getString("客户姓名"));
				customer.setcPwd(rs.getString("客户登录密码"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return customer;
	}
	//员工登录信息
	public Employer employerLogin(String no,String pwd) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Employer employer = null;
		
		try {
			String sql = "select 员工工号,员工姓名,员工登录密码 from dbo.员工登录  where 员工工号=? and 员工登录密码=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, no);
			pstmt.setString(2, pwd);
			rs = pstmt.executeQuery();
			while(rs.next()){
				employer = new Employer();
				employer.setaNo(rs.getString("员工工号"));
				employer.setaName(rs.getString("员工姓名"));
				employer.setaPwd(rs.getString("员工登录密码"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return employer;
	}
	//管理员登录信息
	public Employer adminLogin(String no,String pwd) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Employer admin = null;
		
		try {
			String sql = "select 管理员工号,管理员姓名,管理员登录密码 from dbo.管理员登录 where 管理员工号=? and 管理员登录密码=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, no);
			pstmt.setString(2, pwd);
			rs = pstmt.executeQuery();
			while(rs.next()){
				admin = new Employer();
				admin.setaNo(rs.getString("管理员工号"));
				admin.setaName(rs.getString("管理员姓名"));
				admin.setaPwd(rs.getString("管理员登录密码"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return admin;
	}
}
