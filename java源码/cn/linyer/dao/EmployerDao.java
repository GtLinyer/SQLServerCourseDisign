package cn.linyer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.linyer.entity.Employer;
import cn.linyer.util.BaseDao;

/**
 * @author Linyer(韩啸翔)
 * 员工信息操作
 */
public class EmployerDao extends BaseDao {
	//查询所有员工信息
	public List<Employer> selAllYg() {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Employer employer = null;
		List<Employer> employerList = new ArrayList<Employer>();
		
		try {
			String sql = "select 员工工号,员工姓名,员工联系方式,员工登录密码,员工职务,是否管理员 from dbo.员工";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				employer = new Employer();
				employer.setaNo(rs.getString("员工工号"));
				employer.setaName(rs.getString("员工姓名"));
				employer.setaPhone(rs.getString("员工联系方式"));
				employer.setaPwd(rs.getString("员工登录密码"));
				employer.setWork(rs.getString("员工职务"));
				employer.setIsAdmin(rs.getString("是否管理员"));
				employerList.add(employer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return employerList;
	}
	//查询单个员工信息
	public Employer selOneYg(String ygNo) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Employer employer = null;
		
		try {
			String sql = "select 员工工号,员工姓名,员工联系方式,员工登录密码,员工职务,是否管理员 from dbo.员工 where 员工工号=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ygNo);
			rs = pstmt.executeQuery();
			while(rs.next()){
				employer = new Employer();
				employer.setaNo(rs.getString("员工工号"));
				employer.setaName(rs.getString("员工姓名"));
				employer.setaPhone(rs.getString("员工联系方式"));
				employer.setaPwd(rs.getString("员工登录密码"));
				employer.setWork(rs.getString("员工职务"));
				employer.setIsAdmin(rs.getString("是否管理员"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return employer;
	}
	//增加员工信息
	public String addYg(Employer yg) {
		BaseDao bd = new BaseDao();
		String[] params = {yg.getaName(),yg.getaPhone(),yg.getaPwd(),yg.getWork(),yg.getIsAdmin()};
		String sql = "insert into dbo.员工(员工工号,员工姓名,员工联系方式,员工登录密码,员工职务,是否管理员) " + 
					 "values(NEXT VALUE FOR 员工工号序列,?,?,?,?,?);";
		int i = bd.exeUpdate(sql, params);
		if(i == 0){
			return "添加员工信息失败！";
		}else{
			return "添加员工信息成功！且生成的员工工号为：" + this.selNowSeq();
		}
	}
	//当前序列
	private String selNowSeq() {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String nowSeq = null;
		
		try {
			String sql = "select current_value from sys.sequences where name='员工工号序列'";
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
	//删除员工信息
	public boolean deleteYg(String ygNo) {
		BaseDao bd = new BaseDao();
		String[] params = {ygNo};
		String sql = "delete from dbo.员工 where 员工工号=?";
		int i = bd.exeUpdate(sql, params);
		if(i == 0){
			return false;
		}else{
			return true;
		}
	}
	//修改员工信息
	public boolean updateYg(Employer yg) {
		BaseDao bd = new BaseDao();
		StringBuffer set = new StringBuffer();
		int i = 0;
		List<String> paramList = new ArrayList<String>();
		if(yg.getaName().length()!=0) {
			set.append("员工姓名=?,");
			paramList.add(0, yg.getaName());
			i++;
		}
		if(yg.getaPhone().length()!=0) {
			set.append("员工联系方式=?,");
			if(i == 0) {
				paramList.add(0, yg.getaPhone());
				i++;
			}else {
				paramList.add(i, yg.getaPhone());
				i++;
			}
		}
		if(yg.getaPwd().length()!=0) {
			set.append("员工登录密码=?,");
			if(i == 0) {
				paramList.add(0, yg.getaPwd());
				i++;
			}else {
				paramList.add(i, yg.getaPwd());
				i++;
			}
		}
		if(yg.getWork().length()!=0) {
			set.append("员工职务=?,");
			if(i == 0) {
				paramList.add(0, yg.getWork());
				i++;
			}else {
				paramList.add(i, yg.getWork());
				i++;
			}
		}
		if(yg.getIsAdmin().length()!=0) {
			set.append("是否管理员=?,");
			if(i == 0) {
				paramList.add(0, yg.getIsAdmin());
				i++;
			}else {
				paramList.add(i, yg.getIsAdmin());
				i++;
			}
		}
		
		String[] params = new String[paramList.size()+1];
		
		for(int k=0; k<paramList.size() ; k++) {
			params[k] = paramList.get(k);
		}
		params[i] = yg.getaNo();
		
		set.deleteCharAt(set.lastIndexOf(","));
		
		String sql = "update dbo.员工 set "+ set +" where 员工工号=?";
		int j = bd.exeUpdate(sql, params);
		if(j == 0){
			return false;
		}else{
			return true;
		}
	}
}
