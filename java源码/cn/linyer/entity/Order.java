package cn.linyer.entity;

/**
 * @author Linyer(韩啸翔)
 * 订单信息
 */
public class Order {
	private String ddNo = null;
	private String ddDate = null;
	private String ypNo = null;
	private String ypQua = null;
	private String totalPrice = null;
	private String ddStatus = null;
	private String khNo = null;
	
	public String getDdNo() {
		return ddNo;
	}
	public void setDdNo(String ddNo) {
		this.ddNo = ddNo;
	}
	public String getDdDate() {
		return ddDate;
	}
	public void setDdDate(String ddDate) {
		this.ddDate = ddDate;
	}
	public String getYpNo() {
		return ypNo;
	}
	public void setYpNo(String ypNo) {
		this.ypNo = ypNo;
	}
	public String getYpQua() {
		return ypQua;
	}
	public void setYpQua(String ypQua) {
		this.ypQua = ypQua;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getDdStatus() {
		return ddStatus;
	}
	public void setDdStatus(String ddStatus) {
		this.ddStatus = ddStatus;
	}
	public String getKhNo() {
		return khNo;
	}
	public void setKhNo(String khNo) {
		this.khNo = khNo;
	}
}
