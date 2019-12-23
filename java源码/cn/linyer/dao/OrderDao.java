package cn.linyer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.linyer.entity.Drug;
import cn.linyer.entity.Order;
import cn.linyer.entity.SeeOrder;
import cn.linyer.util.BaseDao;

/**
 * @author Linyer(韩啸翔)
 * 客户操作
 */
public class OrderDao extends BaseDao {
	//查询所有药品信息
	public List<Drug> selAllYp() {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Drug drug = null;
		List<Drug> drugList = new ArrayList<Drug>();
		
		try {
			String sql = "select 药品编号,药品名称,药品功效,药品单价,有效期,药品类型,药品余量 from dbo.在售药品";
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
				drug.setGysNo(rs.getString("药品余量"));
				drugList.add(drug);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return drugList;
	}
	//下单
	public String addDd(Order order) {
		String ypPrice = this.ypPrice(order.getYpNo());
		Double toPr = Double.parseDouble(ypPrice)*Double.parseDouble(order.getYpQua());
		String totalPrice = toPr.toString();
		BaseDao bd = new BaseDao();
		String[] params = {order.getYpNo(),order.getYpQua(),totalPrice,order.getKhNo()};
		String sql = "insert into dbo.订单(订单号,下单日期,药品编号,数量,总额,客户编号) " +
					 "values(NEXT VALUE FOR 订单号序列,GETDATE(),?,?,?,?);";
		int i = bd.exeUpdate(sql, params);
		if(i == 0){
			return "下单失败！";
		}else{
			return "下单成功！\n且生成的订单号为：" + this.selNowSeq() + "，\n订单总额为：" + totalPrice;
		}
	}
	//当前序列
	private String selNowSeq() {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String nowSeq = null;
		
		try {
			String sql = "select current_value from sys.sequences where name='订单号序列'";
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
	//药品单价
	private String ypPrice(String ypNo) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String price = null;
		
		try {
			String sql = "select 药品单价 from dbo.在售药品 where 药品编号=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ypNo);
			rs = pstmt.executeQuery();
			while(rs.next()){
				price = rs.getString("药品单价");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return price;
	}
	//查看订单状态
	public SeeOrder selDd(String khNo,String ddNo) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SeeOrder seeOrder = null;
		
		try {
			String sql = "select 订单号,下单日期,药品编号,药品名称,数量,总额,订单状态,客户编号,客户姓名,客户地址 from dbo.客户看订单 where 客户编号=? and 订单号=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, khNo);
			pstmt.setString(2, ddNo);
			rs = pstmt.executeQuery();
			while(rs.next()){
				seeOrder = new SeeOrder();
				seeOrder.setDdNo(rs.getString("订单号"));
				seeOrder.setXdDate(rs.getString("下单日期"));
				seeOrder.setYpNo(rs.getString("药品编号"));
				seeOrder.setYpName(rs.getString("药品名称"));
				seeOrder.setQua(rs.getString("数量"));
				seeOrder.setTotalPrice(rs.getString("总额"));
				seeOrder.setDdStatus(rs.getString("订单状态"));
				seeOrder.setKhNo(rs.getString("客户编号"));
				seeOrder.setKhName(rs.getString("客户姓名"));
				seeOrder.setKhAddress(rs.getString("客户地址"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return seeOrder;
	}
	//退货申请
	public boolean th(String thNo) {
		BaseDao bd = new BaseDao();
		String[] params = {thNo};
		String sql = "update dbo.订单 set 订单状态=2 where 订单号=?";
		int i = bd.exeUpdate(sql, params);
		if(i == 0){
			return false;
		}else{
			return true;
		}
	}
}
