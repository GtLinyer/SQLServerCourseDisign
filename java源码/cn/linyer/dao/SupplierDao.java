package cn.linyer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.linyer.entity.Supplier;
import cn.linyer.util.BaseDao;

/**
 * @author Linyer(韩啸翔)
 * 供应商数据库操作
 */
public class SupplierDao extends BaseDao {
	//查询所有供应商信息
	public List<Supplier> selAllGys() {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Supplier sup = null;
		List<Supplier> supList = new ArrayList<Supplier>();
		
		try {
			String sql = "select 供应商编号,供应商名称,供应商联系方式,供应商地址 from dbo.供应商";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				sup = new Supplier();
				sup.setGysNo(rs.getString("供应商编号"));
				sup.setGysName(rs.getString("供应商名称"));
				sup.setGysPhone(rs.getString("供应商联系方式"));
				sup.setGysAddress(rs.getString("供应商地址"));
				supList.add(sup);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return supList;
	}
	//查询单个供应商信息
	public Supplier selOneGys(String gysNo) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Supplier sup = null;
		
		try {
			String sql = "select 供应商编号,供应商名称,供应商联系方式,供应商地址 from dbo.供应商 where 供应商编号=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, gysNo);
			rs = pstmt.executeQuery();
			while(rs.next()){
				sup = new Supplier();
				sup.setGysNo(rs.getString("供应商编号"));
				sup.setGysName(rs.getString("供应商名称"));
				sup.setGysPhone(rs.getString("供应商联系方式"));
				sup.setGysAddress(rs.getString("供应商地址"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return sup;
	}
	//增加供应商信息
	public String addGys(Supplier gys) {
		BaseDao bd = new BaseDao();
		String[] params = {gys.getGysName(),gys.getGysPhone(),gys.getGysAddress()};
		String sql = "insert into dbo.供应商(供应商编号,供应商名称,供应商联系方式,供应商地址) " + 
					 "values(NEXT VALUE FOR 供应商编号序列,?,?,?);";
		int i = bd.exeUpdate(sql, params);
		if(i == 0){
			return "添加供应商信息失败！";
		}else{
			return "添加供应商信息成功！且生成的供应商编号为：" + this.selNowSeq();
		}
	}
	//当前序列
	private String selNowSeq() {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String nowSeq = null;
		
		try {
			String sql = "select current_value from sys.sequences where name='供应商编号序列'";
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
	//删除供应商信息
	public boolean deleteGys(String gysNo) {
		BaseDao bd = new BaseDao();
		String[] params = {gysNo};
		String sql = "delete from dbo.供应商 where 供应商编号=?";
		int i = bd.exeUpdate(sql, params);
		if(i == 0){
			return false;
		}else{
			return true;
		}
	}
	//修改供应商信息
	public boolean updateGys(Supplier sup) {
		BaseDao bd = new BaseDao();
		StringBuffer set = new StringBuffer();
		int i = 0;
		List<String> paramList = new ArrayList<String>();
		if(sup.getGysName().length()!=0) {
			set.append("供应商名称=?,");
			paramList.add(0, sup.getGysName());
			i++;
		}
		if(sup.getGysPhone().length()!=0) {
			set.append("供应商联系方式=?,");
			if(i == 0) {
				paramList.add(0, sup.getGysPhone());
				i++;
			}else {
				paramList.add(i, sup.getGysPhone());
				i++;
			}
		}
		if(sup.getGysAddress().length()!=0) {
			set.append("供应商地址=?,");
			if(i == 0) {
				paramList.add(0, sup.getGysAddress());
				i++;
			}else {
				paramList.add(i, sup.getGysAddress());
				i++;
			}
		}
		
		String[] params = new String[paramList.size()+1];
		
		for(int k=0; k<paramList.size() ; k++) {
			params[k] = paramList.get(k);
		}
		params[i] = sup.getGysNo();
		
		set.deleteCharAt(set.lastIndexOf(","));
		
		String sql = "update dbo.供应商 set "+ set +" where 供应商编号=?";
		int j = bd.exeUpdate(sql, params);
		if(j == 0){
			return false;
		}else{
			return true;
		}
	}
}
