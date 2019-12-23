package cn.linyer.gui.customerGui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cn.linyer.dao.OrderDao;
import cn.linyer.entity.Drug;
import cn.linyer.entity.Order;
import cn.linyer.entity.SeeOrder;

/**
 * @author Linyer
 * 医师使用界面
 * 
 */
public class CustomerGui {
	//窗口定义
	private JFrame frame = new JFrame("客户购药");
	//主体部分定义
	private JLabel label;
	private JPanel panel = new JPanel();
	//具体操作 部分组件定义
	private JPanel inputPanel = new JPanel();
	
	private JLabel czTypeLabel = new JLabel("操作类型：");
	private JComboBox<String> czType = new JComboBox<String>();
	
	private JLabel ddNoLabel = new JLabel("订单号：");
	private JLabel ypNoLabel = new JLabel("药品编号：");
	private JLabel ypQuaLabel = new JLabel("数量：");
	
	private JTextField ddNo = new JTextField(8);
	private JTextField ypNo = new JTextField(8);
	private JTextField ypQua = new JTextField(8);
	private JTextArea ddArea = new JTextArea(20,25);
	private JScrollPane ddScroll = new JScrollPane(ddArea);
	
	private JButton yesBtn = new JButton("确定 操作");
	
	//颜色字体定义
	private Color myBlue = new Color(52, 152, 219);
	private Color myGreen = new Color(19, 141, 117);
	private Color myPink = new Color(171, 40, 199);
	
	private Font fontBig = new Font("华文琥珀",Font.BOLD,36);
	private Font fontSmal = new Font("华文楷体",Font.PLAIN,24);
	
	public CustomerGui(String khNo,String khName) {
		//欢迎客户
		label = new JLabel(khName+"，欢迎选购药品",JLabel.CENTER);
		//布局
		frame.setLayout(new BorderLayout());
		panel.setLayout(new BorderLayout());
		inputPanel.setLayout(new GridLayout(5,2));
		//设置各个组件的属性
		label.setFont(fontBig);
		label.setForeground(myGreen);
		czTypeLabel.setFont(fontSmal);
		czTypeLabel.setForeground(myBlue);
		czType.setFont(fontSmal);
		czType.setForeground(myBlue);
		ddNoLabel.setFont(fontSmal);
		ddNoLabel.setForeground(myPink);
		ddNo.setFont(fontSmal);
		ddNo.setForeground(myPink);
		ypNoLabel.setFont(fontSmal);
		ypNo.setFont(fontSmal);
		ypQuaLabel.setFont(fontSmal);
		ypQua.setFont(fontSmal);
		ddArea.setFont(fontSmal);
		yesBtn.setFont(fontSmal);
		//设置文本域不可编辑
		ddArea.setEditable(false);
		
		//添加组件
		frame.add(label,BorderLayout.NORTH);
		frame.add(panel,BorderLayout.CENTER);

		panel.add(inputPanel,BorderLayout.NORTH);
		panel.add(ddScroll,BorderLayout.CENTER);
		panel.add(yesBtn,BorderLayout.SOUTH);
		inputPanel.add(czTypeLabel);
		inputPanel.add(czType);
		inputPanel.add(ddNoLabel);
		inputPanel.add(ddNo);
		inputPanel.add(ypNoLabel);
		inputPanel.add(ypNo);
		inputPanel.add(ypQuaLabel);
		inputPanel.add(ypQua);
		
		czType.addItem(null);
		czType.addItem("查看在售药品");
		czType.addItem("开始下单");
		czType.addItem("查看订单信息");
		czType.addItem("申请退货");
		ddArea.setText("提示：\n请先选择操作类型，\n然后按照提示操作！");
		
		OrderDao odd = new OrderDao();
		
		//确定操作，按钮处理
		class YesButton implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if(czType.getSelectedIndex()==1) {
					//查询所有供应商信息
					List<Drug> drugList = odd.selAllYp();
					StringBuffer drugMsgBuffer = new StringBuffer();
					if(drugList.size()==0) {
						ddArea.setText("暂无结果！");
					}else {
						for(int i=0; i<drugList.size(); i++) {
							drugMsgBuffer.append("药品编号：" + drugList.get(i).getYpNo() +
										 	   "\n药品名称：" + drugList.get(i).getYpName() +
										 	   "\n药品功效：" + drugList.get(i).getYpEffect() +
										 	   "\n药品单价：" + drugList.get(i).getYpPrice() +
										 	   "\n有效期：" + drugList.get(i).getYpDate() +
										 	   "\n药品类型：" + drugList.get(i).getYpType() +
										 	   "\n药品余量：" + drugList.get(i).getGysNo() +
										 	   "\n==============================\n");
						}
						String drugMsg = drugMsgBuffer.toString();
						ddArea.setText(drugMsg);
					}
				}else if(czType.getSelectedIndex()==2) {
					//下单
					Order order = new Order();
					order.setKhNo(khNo);
					order.setYpNo(ypNo.getText());
					order.setYpQua(ypQua.getText());
					if(order.getYpNo().length()==0 || order.getYpQua().length()==0) {
						JOptionPane.showMessageDialog(frame,"请将两项内容输入完整！","错误提示",JOptionPane.ERROR_MESSAGE);
					}else {
						String show = odd.addDd(order);
						if(show.equals("下单失败！")) {
							JOptionPane.showMessageDialog(frame,show,"错误提示",JOptionPane.ERROR_MESSAGE);
						}else {
							JOptionPane.showMessageDialog(frame,show,"成功提示",JOptionPane.PLAIN_MESSAGE);
						}
					}
				}else if(czType.getSelectedIndex()==3) {
					//查看订单状态
					SeeOrder so = odd.selDd(khNo,ddNo.getText());
					if(so != null) {
						String soMsg = "订单号：" + so.getDdNo() +
									   "\n下单日期：" + so.getXdDate() +
									   "\n药品编号：" + so.getYpNo() +
									   "\n药品名称：" + so.getYpName() +
									   "\n数量：" + so.getYpNo() +
									   "\n总额：" + so.getTotalPrice() +
									   "\n订单状态：" + so.getDdStatus() +
									   "\n客户编号：" + so.getKhNo() +
									   "\n客户姓名：" + so.getKhName() +
									   "\n客户地址：" + so.getKhAddress() +
									   "\n==============================\n";
						ddArea.setText(soMsg);
					}else {
						ddArea.setText("您还没有订单！");
					}
				}else if(czType.getSelectedIndex()==4) {
					//退货
					String thNo = ddNo.getText();
					if(odd.th(thNo)) {
						JOptionPane.showMessageDialog(frame,"退货申请，提交成功！","成功提示",JOptionPane.PLAIN_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(frame,"退货申请，提交失败！","错误提示",JOptionPane.ERROR_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(frame,"请先选择操作类型！","错误提示",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		//判断选择的操作类型
		class CzTpBox implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if(czType.getSelectedIndex()==1) {
					ddArea.setText("提示：\n请点击【确定操作】按钮！");
					ddNo.setEditable(false);
					ddNo.setText(null);
					ypNo.setEditable(false);
					ypNo.setText(null);
					ypQua.setEditable(false);
					ypQua.setText(null);
				}else if(czType.getSelectedIndex()==2) {
					ddArea.setText("提示：\n请输入药品编号及数量，\n然后点击【确定操作】按钮！\n点击确定按钮后，系统会自动算出总额！");
					ddNo.setEditable(false);
					ddNo.setText("自动生成订单号");
					ypNo.setEditable(true);
					ypNo.setText(null);
					ypQua.setEditable(true);
					ypQua.setText(null);
				}else if(czType.getSelectedIndex()==3) {
					ddArea.setText("提示：\n请输入要查询的订单号\n请点击【确定操作】按钮！");
					ddNo.setEditable(true);
					ddNo.setText(null);
					ypNo.setEditable(false);
					ypNo.setText(null);
					ypQua.setEditable(false);
					ypQua.setText(null);
				}else if(czType.getSelectedIndex()==4) {
					ddArea.setText("提示：\n请输入要退货的订单号\n请点击【确定操作】按钮！");
					ddNo.setEditable(true);
					ddNo.setText(null);
					ypNo.setEditable(false);
					ypNo.setText(null);
					ypQua.setEditable(false);
					ypQua.setText(null);
				}else {
					ddArea.setText("提示：\n请先选择操作类型，\n然后按照提示操作！");
					ddNo.setEditable(true);
					ddNo.setText(null);
					ypNo.setEditable(true);
					ypNo.setText(null);
					ypQua.setEditable(true);
					ypQua.setText(null);
				}
			}
		}
		
		YesButton yesButton = new YesButton();
		yesBtn.addActionListener(yesButton);
		
		CzTpBox czTpBox = new CzTpBox();
		czType.addActionListener(czTpBox);
		
		frame.pack();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
}
