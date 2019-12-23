package cn.linyer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.linyer.entity.Customer;
import cn.linyer.util.BaseDao;

/**
 * @author Linyer(韩啸翔)
 * 客户信息数据库操作
 */
public class CustomerDao extends BaseDao {
	//查询所有客户信息
	public List<Customer> selAllKh() {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Customer customer = null;
		List<Customer> customerList = new ArrayList<Customer>();
		
		try {
			String sql = "select 客户编号,客户姓名,客户联系方式,客户登录密码,客户地址 from dbo.客户";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				customer = new Customer();
				customer.setcNo(rs.getString("客户编号"));
				customer.setcName(rs.getString("客户姓名"));
				customer.setcPhone(rs.getString("客户联系方式"));
				customer.setcPwd(rs.getString("客户登录密码"));
				customer.setcAddress(rs.getString("客户地址"));
				customerList.add(customer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return customerList;
	}
	//查询单个客户信息
	public Customer selOneKh(String khNo) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Customer customer = null;
		
		try {
			String sql = "select 客户编号,客户姓名,客户联系方式,客户登录密码,客户地址 from dbo.客户 where 客户编号=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, khNo);
			rs = pstmt.executeQuery();
			while(rs.next()){
				customer = new Customer();
				customer.setcNo(rs.getString("客户编号"));
				customer.setcName(rs.getString("客户姓名"));
				customer.setcPhone(rs.getString("客户联系方式"));
				customer.setcPwd(rs.getString("客户登录密码"));
				customer.setcAddress(rs.getString("客户地址"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return customer;
	}
	//增加客户信息
	public String addKh(Customer cus) {
		BaseDao bd = new BaseDao();
		String[] params = {cus.getcName(),cus.getcPhone(),cus.getcPwd(),cus.getcAddress()};
		String sql = "insert into dbo.客户(客户编号,客户姓名,客户联系方式,客户登录密码,客户地址) " + 
					 "values(NEXT VALUE FOR 客户编号序列,?,?,?,?);";
		int i = bd.exeUpdate(sql, params);
		if(i == 0){
			return "添加客户信息失败！";
		}else{
			return "添加客户信息成功！且生成的客户编号为：" + this.selNowSeq();
		}
	}
	//当前序列
	private String selNowSeq() {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String nowSeq = null;
		
		try {
			String sql = "select current_value from sys.sequences where name='客户编号序列'";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				nowSeq = rs.getString("current_value");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return nowSeq;
	}
	//删除客户信息
	public boolean deleteKh(String khNo) {
		BaseDao bd = new BaseDao();
		String[] params = {khNo};
		String sql = "delete from dbo.客户 where 客户编号=?";
		int i = bd.exeUpdate(sql, params);
		if(i == 0){
			return false;
		}else{
			return true;
		}
	}
	//修改客户信息
	public boolean updateKh(Customer kh) {
		BaseDao bd = new BaseDao();
		StringBuffer set = new StringBuffer();
		int i = 0;
		List<String> paramList = new ArrayList<String>();
		if(kh.getcName().length()!=0) {
			set.append("客户姓名=?,");
			paramList.add(0, kh.getcName());
			i++;
		}
		if(kh.getcPhone().length()!=0) {
			set.append("客户联系方式=?,");
			if(i == 0) {
				paramList.add(0, kh.getcPhone());
				i++;
			}else {
				paramList.add(i, kh.getcPhone());
				i++;
			}
		}
		if(kh.getcPwd().length()!=0) {
			set.append("客户登录密码=?,");
			if(i == 0) {
				paramList.add(0, kh.getcPwd());
				i++;
			}else {
				paramList.add(i, kh.getcPwd());
				i++;
			}
		}
		if(kh.getcAddress().length()!=0) {
			set.append("客户地址=?,");
			if(i == 0) {
				paramList.add(0, kh.getcAddress());
				i++;
			}else {
				paramList.add(i, kh.getcAddress());
				i++;
			}
		}
		
		String[] params = new String[paramList.size()+1];
		
		for(int k=0; k<paramList.size() ; k++) {
			params[k] = paramList.get(k);
		}
		params[i] = kh.getcNo();
		
		set.deleteCharAt(set.lastIndexOf(","));
		
		String sql = "update dbo.客户 set "+ set +" where 客户编号=?";
		int j = bd.exeUpdate(sql, params);
		if(j == 0){
			return false;
		}else{
			return true;
		}
	}
}
