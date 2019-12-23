package cn.linyer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.linyer.entity.Order;
import cn.linyer.util.BaseDao;

/**
 * @author Linyer(韩啸翔)
 * 统计数据库操作
 */
public class TongjiDao extends BaseDao {
	//查询当月药品销售数量排行
	public List<Order> xl() {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Order> drugList = new ArrayList<Order>();
		Order drug = null;
		
		try {
			String sql = "select 药品名称,sum(数量) as 总数 from dbo.当月统计 group by 药品名称 order by 总数 desc";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				drug = new Order();
				drug.setYpNo(rs.getString("药品名称"));
				drug.setYpQua(rs.getString("总数"));
				drugList.add(drug);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return drugList;
	}
	//查询当月药品销售总额排行
	public List<Order> xsze() {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Order> drugList = new ArrayList<Order>();
		Order drug = null;
		
		try {
			String sql = "select 药品名称,sum(总额) as 月总额 from dbo.当月统计 group by 药品名称 order by 月总额 desc";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				drug = new Order();
				drug.setYpNo(rs.getString("药品名称"));
				drug.setTotalPrice(rs.getString("月总额"));
				drugList.add(drug);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return drugList;
	}
}
