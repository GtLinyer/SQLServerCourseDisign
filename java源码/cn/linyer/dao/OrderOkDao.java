package cn.linyer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.linyer.entity.Order;
import cn.linyer.entity.Warehouse;
import cn.linyer.util.BaseDao;

/**
 * @author Linyer(韩啸翔)
 * 订单管理操作数据库
 */
public class OrderOkDao extends BaseDao {
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
	//查看所有订单
	public List<Order> selAllDd() {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Order order = null;
		List<Order> orderList = new ArrayList<Order>();
		
		try {
			String sql = "select 订单号,下单日期,药品编号,数量,总额,订单状态,客户编号 from dbo.订单";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				order = new Order();
				order.setDdNo(rs.getString("订单号"));
				order.setDdDate(rs.getString("下单日期"));
				order.setYpNo(rs.getString("药品编号"));
				order.setYpQua(rs.getString("数量"));
				order.setTotalPrice(rs.getString("总额"));
				order.setDdStatus(rs.getString("订单状态"));
				order.setKhNo(rs.getString("客户编号"));
				orderList.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return orderList;
	}
	//查看未完成订单
	public List<Order> selNotOkDd() {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Order order = null;
		List<Order> orderList = new ArrayList<Order>();
		
		try {
			String sql = "select 订单号,下单日期,药品编号,数量,总额,订单状态,客户编号 from dbo.订单 where 订单状态=0";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				order = new Order();
				order.setDdNo(rs.getString("订单号"));
				order.setDdDate(rs.getString("下单日期"));
				order.setYpNo(rs.getString("药品编号"));
				order.setYpQua(rs.getString("数量"));
				order.setTotalPrice(rs.getString("总额"));
				order.setDdStatus(rs.getString("订单状态"));
				order.setKhNo(rs.getString("客户编号"));
				orderList.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return orderList;
	}
	//查看退货订单
	public List<Order> selThDd() {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Order order = null;
		List<Order> orderList = new ArrayList<Order>();
		
		try {
			String sql = "select 订单号,下单日期,药品编号,数量,总额,订单状态,客户编号 from dbo.订单 where 订单状态=2";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				order = new Order();
				order.setDdNo(rs.getString("订单号"));
				order.setDdDate(rs.getString("下单日期"));
				order.setYpNo(rs.getString("药品编号"));
				order.setYpQua(rs.getString("数量"));
				order.setTotalPrice(rs.getString("总额"));
				order.setDdStatus(rs.getString("订单状态"));
				order.setKhNo(rs.getString("客户编号"));
				orderList.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return orderList;
	}
	//确认完成订单
	public boolean okOrder(String okOrderNo) {
		BaseDao bd = new BaseDao();
		String[] params = {okOrderNo};
		String sql = "update dbo.订单 set 订单状态=1 where 订单号=?";
		int i = bd.exeUpdate(sql, params);
		if(i == 0){
			return false;
		}else{
			Order ord = this.selDdYpQua(okOrderNo);
			if(this.okOrderDao(ord.getYpQua(),ord.getYpNo())) {
				return true;
			}else {
				return false;
			}
		}
	}
	//完成订单，更新仓库信息
	private boolean okOrderDao(String qua,String ypNo) {
		int ypQuaInt = Integer.parseInt(this.selCkQua(ypNo))-Integer.parseInt(qua);
		String ypQua = Integer.toString(ypQuaInt);
		BaseDao bd = new BaseDao();
		String[] params = {ypQua,ypNo};
		String sql = "update dbo.仓库 set 药品数量=? where 药品编号=?";
		int i = bd.exeUpdate(sql, params);
		if(i == 0){
			return false;
		}else{
			return true;
		}
	}
	//查找订单药品数量
	private Order selDdYpQua(String ddNo) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Order ord = null;
		
		try {
			String sql = "select 数量,药品编号 from dbo.订单 where 订单号=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ddNo);
			rs = pstmt.executeQuery();
			while(rs.next()){
				ord = new Order();
				ord.setYpQua(rs.getString("数量"));
				ord.setYpNo(rs.getString("药品编号"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return ord;
	}
	//当前仓库数量
	private String selCkQua(String ypNo) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String ypQua = null;
		
		try {
			String sql = "select 药品数量 from dbo.仓库 where 药品编号=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ypNo);
			rs = pstmt.executeQuery();
			while(rs.next()){
				ypQua = rs.getString("药品数量");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return ypQua;
	}
	//确认客户退货
	public boolean thOkOrder(String thOkOrderNo) {
		BaseDao bd = new BaseDao();
		String[] params = {thOkOrderNo};
		String sql = "update dbo.订单 set 订单状态=3 where 订单号=?";
		int i = bd.exeUpdate(sql, params);
		if(i == 0){
			return false;
		}else{
			Order ord = this.selDdYpQua(thOkOrderNo);
			if(this.thOkOrderDao(ord.getYpQua(), ord.getYpNo())) {
				return true;
			}else {
				return false;
			}
		}
	}
	//完成退货订单，更新仓库信息
	private boolean thOkOrderDao(String qua,String ypNo) {
		int ypQuaInt = Integer.parseInt(this.selCkQua(ypNo))+Integer.parseInt(qua);
		String ypQua = Integer.toString(ypQuaInt);
		BaseDao bd = new BaseDao();
		String[] params = {ypQua,ypNo};
		String sql = "update dbo.仓库 set 药品数量=? where 药品编号=?";
		int i = bd.exeUpdate(sql, params);
		if(i == 0){
			return false;
		}else{
			return true;
		}
	}
}
