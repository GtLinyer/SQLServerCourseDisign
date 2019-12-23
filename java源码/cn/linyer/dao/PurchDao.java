package cn.linyer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.linyer.entity.RuKu;
import cn.linyer.entity.Warehouse;
import cn.linyer.util.BaseDao;

/**
 * @author Linyer(韩啸翔)
 * 入库数据库操作
 */
public class PurchDao extends BaseDao {
	//库存信息查询
	public List<Warehouse> selAllYp() {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Warehouse ware;
		List<Warehouse> wareList = new ArrayList<Warehouse>();
		
		try {
			String sql = "select 药品编号,药品数量 from dbo.仓库";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				ware = new Warehouse();
				ware.setYpNo(rs.getString("药品编号"));
				ware.setYpQua(rs.getString("药品数量"));
				wareList.add(ware);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return wareList;
	}
	//进货
	public String jinHuo(String ypNo,String rkQua) {
		BaseDao bd = new BaseDao();
		String[] params = {ypNo,rkQua};
		String sql = "insert into dbo.入库(入库单号,药品编号,入库数量,入库日期) " + 
					 "values(NEXT VALUE FOR 入库单号序列,?,?,GETDATE());";
		int i = bd.exeUpdate(sql, params);
		if(i == 0){
			return "进货失败！";
		}else{
			return "进货成功！且生成的入库单号为：" + this.selNowSeq();
		}
	}
	//当前序列
	private String selNowSeq() {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String nowSeq = null;
		
		try {
			String sql = "select current_value from sys.sequences where name='入库单号序列'";
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
	//入库单查询
	public RuKu selrkMsg(String rkNo) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RuKu rk = null;
		
		try {
			String sql = "select 入库单号,药品编号,入库数量,入库日期 from dbo.入库 where 入库单号=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, rkNo);
			rs = pstmt.executeQuery();
			while(rs.next()){
				rk = new RuKu();
				rk.setRkNo(rs.getString("入库单号"));
				rk.setYpNo(rs.getString("药品编号"));
				rk.setRkQua(rs.getString("入库数量"));
				rk.setRkDate(rs.getString("入库日期"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return rk;
	}
	//查询所有入库单
	public List<RuKu> selAllrkMsg() {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RuKu rk = null;
		List<RuKu> rkList = new ArrayList<RuKu>();
		
		try {
			String sql = "select 入库单号,药品编号,入库数量,入库日期 from dbo.入库";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				rk = new RuKu();
				rk.setRkNo(rs.getString("入库单号"));
				rk.setYpNo(rs.getString("药品编号"));
				rk.setRkQua(rs.getString("入库数量"));
				rk.setRkDate(rs.getString("入库日期"));
				rkList.add(rk);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return rkList;
	}
	//退货
	public boolean tuiHuo(String rkQua) {
		BaseDao bd = new BaseDao();
		String[] params = {rkQua};
		String sql = "delete from dbo.入库 where 入库单号=?";
		int i = bd.exeUpdate(sql, params);
		if(i == 0){
			return false;
		}else{
			return true;
		}
	}
}
