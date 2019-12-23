package cn.linyer.gui;

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

import cn.linyer.dao.OrderOkDao;
import cn.linyer.entity.Order;
import cn.linyer.entity.Warehouse;

/**
 * @author Linyer(韩啸翔)
 *
 */
public class EmployerGui {
	//窗口定义
	private JFrame frame = new JFrame("查看客户订单");
	//主体部分定义
	private JLabel label = new JLabel("查看客户订单",JLabel.CENTER);
	private JPanel panel = new JPanel();
	//具体操作 部分组件定义
	private JPanel inputPanel = new JPanel();
	
	private JLabel czTypeLabel = new JLabel("操作类型：");
	private JComboBox<String> czType = new JComboBox<String>();
	
	private JTextArea cxArea = new JTextArea(20,25);
	private JScrollPane cxScroll = new JScrollPane(cxArea);
	
	private JButton yesBtn = new JButton("确定 操作");
	
	//颜色字体定义
	private Color myBlue = new Color(52, 152, 219);
	private Color myGreen = new Color(19, 141, 117);
	
	private Font fontBig = new Font("华文琥珀",Font.BOLD,36);
	private Font fontSmal = new Font("华文楷体",Font.PLAIN,24);
	
	public EmployerGui() {
		//布局
		frame.setLayout(new BorderLayout());
		panel.setLayout(new BorderLayout());
		inputPanel.setLayout(new GridLayout(1,2));
		//设置各个组件的属性
		label.setFont(fontBig);
		label.setForeground(myGreen);
		czTypeLabel.setFont(fontSmal);
		czTypeLabel.setForeground(myBlue);
		czType.setFont(fontSmal);
		czType.setForeground(myBlue);
		cxArea.setFont(fontSmal);
		yesBtn.setFont(fontSmal);
		//设置文本域不可编辑
		cxArea.setEditable(false);
		
		//添加组件
		frame.add(label,BorderLayout.NORTH);
		frame.add(panel,BorderLayout.CENTER);
		panel.add(inputPanel,BorderLayout.NORTH);
		panel.add(cxScroll,BorderLayout.CENTER);
		panel.add(yesBtn,BorderLayout.SOUTH);
		inputPanel.add(czTypeLabel);
		inputPanel.add(czType);
		
		czType.addItem(null);
		czType.addItem("库存查询");
		czType.addItem("查看所有订单");
		czType.addItem("查看未完成订单");
		cxArea.setText("提示：\n请先选择操作类型，\n然后按照提示操作！");
		
		OrderOkDao ood = new OrderOkDao();
		
		//确定操作，按钮处理
		class YesButton implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if(czType.getSelectedIndex()==1) {
					//库存查询
					List<Warehouse> wareList = ood.selAllYp();
					StringBuffer wareMsgBuffer = new StringBuffer();
					if(wareList.size()==0) {
						cxArea.setText("暂无结果！");
					}else {
						for(int i=0; i<wareList.size(); i++) {
							wareMsgBuffer.append("药品编号：" + wareList.get(i).getYpNo() +
										 	   "\n药品数量：" + wareList.get(i).getYpQua() +
										 	   "\n==============================\n");
						}
						String wareMsg = wareMsgBuffer.toString();
						cxArea.setText(wareMsg);
					}
				}else if(czType.getSelectedIndex()==2) {
					//查看所有订单
					List<Order> orderList = ood.selAllDd();
					StringBuffer orderMsgBuffer = new StringBuffer();
					if(orderList.size()==0) {
						cxArea.setText("暂无结果！");
					}else {
						for(int i=0; i<orderList.size(); i++) {
							orderMsgBuffer.append("订单号：" + orderList.get(i).getDdNo() +
										 	   "\n下单日期：" + orderList.get(i).getDdDate() +
										 	   "\n药品编号：" + orderList.get(i).getYpNo() +
										 	   "\n数量：" + orderList.get(i).getYpNo() +
										 	   "\n总额：" + orderList.get(i).getTotalPrice() +
										 	   "\n订单状态：" + orderList.get(i).getDdStatus() +
										 	   "\n客户编号：" + orderList.get(i).getKhNo() +
										 	   "\n==============================\n");
						}
						String orderMsg = orderMsgBuffer.toString();
						cxArea.setText(orderMsg);
					}
				}else if(czType.getSelectedIndex()==3) {
					//查看未完成订单
					List<Order> notOkOrderList = ood.selNotOkDd();
					StringBuffer notOkOrderMsgBuffer = new StringBuffer();
					if(notOkOrderList.size()==0) {
						cxArea.setText("暂无结果！");
					}else {
						for(int i=0; i<notOkOrderList.size(); i++) {
							notOkOrderMsgBuffer.append("订单号：" + notOkOrderList.get(i).getDdNo() +
													 "\n下单日期：" + notOkOrderList.get(i).getDdDate() +
													 "\n药品编号：" + notOkOrderList.get(i).getYpNo() +
													 "\n数量：" + notOkOrderList.get(i).getYpNo() +
													 "\n总额：" + notOkOrderList.get(i).getTotalPrice() +
													 "\n订单状态：" + notOkOrderList.get(i).getDdStatus() +
													 "\n客户编号：" + notOkOrderList.get(i).getKhNo() +
													 "\n==============================\n");
						}
						String notOkOrderMsg = notOkOrderMsgBuffer.toString();
						cxArea.setText(notOkOrderMsg);
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
					cxArea.setText("提示：\n请点击'确定操作'按钮！");
				}else if(czType.getSelectedIndex()==2) {
					cxArea.setText("提示：\n请点击'确定操作'按钮！");
				}else if(czType.getSelectedIndex()==3) {
					cxArea.setText("提示：\n请点击'确定操作'按钮！");
				}else {
					cxArea.setText("提示：\n请先选择操作类型，\n然后按照提示操作！");
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
