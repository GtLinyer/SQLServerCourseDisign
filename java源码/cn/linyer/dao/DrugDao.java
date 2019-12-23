package cn.linyer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.linyer.entity.Drug;
import cn.linyer.util.BaseDao;

/**
 * @author Linyer(韩啸翔)
 * 药品信息
 */
public class DrugDao extends BaseDao {
	//查询所有药品信息
	public List<Drug> selAllYp() {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Drug drug = null;
		List<Drug> drugList = new ArrayList<Drug>();
		
		try {
			String sql = "select 药品编号,药品名称,药品功效,药品单价,有效期,药品类型,供应商编号 from dbo.药品";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				drug = new Drug();
				drug.setYpNo(rs.getString("药品编号"));
				drug.setYpName(rs.getString("药品名称"));
				drug.setYpEffect(rs.getString("药品功效"));
				drug.setYpPrice(rs.getString("药品单价"));
				drug.setYpDate(rs.getString("有效期"));
				drug.setYpType(rs.getString("药品类型"));
				drug.setGysNo(rs.getString("供应商编号"));
				drugList.add(drug);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return drugList;
	}
	//查询单个药品信息
	public Drug selOneYp(String ypNo) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Drug drug = null;
		
		try {
			String sql = "select 药品编号,药品名称,药品功效,药品单价,有效期,药品类型,供应商编号 from dbo.药品 where 药品编号=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ypNo);
			rs = pstmt.executeQuery();
			while(rs.next()){
				drug = new Drug();
				drug.setYpNo(rs.getString("药品编号"));
				drug.setYpName(rs.getString("药品名称"));
				drug.setYpEffect(rs.getString("药品功效"));
				drug.setYpPrice(rs.getString("药品单价"));
				drug.setYpDate(rs.getString("有效期"));
				drug.setYpType(rs.getString("药品类型"));
				drug.setGysNo(rs.getString("供应商编号"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return drug;
	}
	//增加药品信息
	public String addYp(Drug yp) {
		BaseDao bd = new BaseDao();
		String[] params = {yp.getYpName(),yp.getYpEffect(),yp.getYpPrice(),yp.getYpDate(),yp.getYpType(),yp.getGysNo()};
		String sql = "insert into dbo.药品(药品编号,药品名称,药品功效,药品单价,有效期,药品类型,供应商编号) " + 
					 "values(NEXT VALUE FOR 药品编号序列,?,?,?,?,?,?);";
		int i = bd.exeUpdate(sql, params);
		if(i == 0){
			return "添加药品信息失败！";
		}else{
			return "添加药品信息成功！且生成的药品编号为：" + this.selNowSeq();
		}
	}
	//当前序列
	private String selNowSeq() {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String nowSeq = null;
		
		try {
			String sql = "select current_value from sys.sequences where name='药品编号序列'";
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
	//删除药品信息
	public boolean deleteYp(String ypNo) {
		BaseDao bd = new BaseDao();
		String[] params = {ypNo};
		String sql = "delete from dbo.药品 where 药品编号=?";
		int i = bd.exeUpdate(sql, params);
		if(i == 0){
			return false;
		}else{
			return true;
		}
	}
	//修改药品信息
	public boolean updateYp(Drug yp) {
		BaseDao bd = new BaseDao();
		StringBuffer set = new StringBuffer();
		int i = 0;
		List<String> paramList = new ArrayList<String>();
		if(yp.getYpName().length()!=0) {
			set.append("药品名称=?,");
			paramList.add(0, yp.getYpName());
			i++;
		}
		if(yp.getYpEffect().length()!=0) {
			set.append("药品功效=?,");
			if(i == 0) {
				paramList.add(0, yp.getYpEffect());
				i++;
			}else {
				paramList.add(i, yp.getYpEffect());
				i++;
			}
		}
		if(yp.getYpPrice().length()!=0) {
			set.append("药品单价=?,");
			if(i == 0) {
				paramList.add(0, yp.getYpPrice());
				i++;
			}else {
				paramList.add(i, yp.getYpPrice());
				i++;
			}
		}
		if(yp.getYpDate().length()!=0) {
			set.append("有效期=?,");
			if(i == 0) {
				paramList.add(0, yp.getYpDate());
				i++;
			}else {
				paramList.add(i, yp.getYpDate());
				i++;
			}
		}
		if(yp.getYpType().length()!=0) {
			set.append("药品类型=?,");
			if(i == 0) {
				paramList.add(0, yp.getYpType());
				i++;
			}else {
				paramList.add(i, yp.getYpType());
				i++;
			}
		}
		if(yp.getGysNo().length()!=0) {
			set.append("供应商编号=?,");
			if(i == 0) {
				paramList.add(0, yp.getGysNo());
				i++;
			}else {
				paramList.add(i, yp.getGysNo());
				i++;
			}
		}
		
		String[] params = new String[paramList.size()+1];
		
		for(int k=0; k<paramList.size() ; k++) {
			params[k] = paramList.get(k);
		}
		params[i] = yp.getYpNo();
		
		set.deleteCharAt(set.lastIndexOf(","));
		
		String sql = "update dbo.药品 set "+ set +" where 药品编号=?";
		int j = bd.exeUpdate(sql, params);
		if(j == 0){
			return false;
		}else{
			return true;
		}
	}
}
