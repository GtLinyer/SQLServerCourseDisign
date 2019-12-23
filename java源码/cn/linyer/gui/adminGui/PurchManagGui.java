package cn.linyer.gui.adminGui;

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

import cn.linyer.dao.PurchDao;
import cn.linyer.entity.RuKu;
import cn.linyer.entity.Warehouse;

/**
 * @author Linyer(韩啸翔)
 * 入库管理
 */
public class PurchManagGui {
	//窗口定义
	private JFrame frame = new JFrame("入库管理");
	//主体部分定义
	private JLabel label = new JLabel("入库管理",JLabel.CENTER);
	private JPanel panel = new JPanel();
	//具体操作 部分组件定义
	private JPanel inputPanel = new JPanel();
	
	private JLabel czTypeLabel = new JLabel("操作类型：");
	private JComboBox<String> czType = new JComboBox<String>();
	
	private JLabel rkNoLabel = new JLabel("入库单号：");
	private JLabel ypNoLabel = new JLabel("药品编号：");
	private JLabel rkQuaLabel = new JLabel("入库数量：");
	
	private JTextField rkNo = new JTextField(8);
	private JTextField ypNo = new JTextField(8);
	private JTextField rkQua = new JTextField(8);
	private JTextArea cxArea = new JTextArea(20,25);
	private JScrollPane cxScroll = new JScrollPane(cxArea);
	
	private JButton yesBtn = new JButton("确定 操作");
	
	//返回按钮
	private JButton backBtn = new JButton("返回上一界面");
	//颜色字体定义
	private Color myBlue = new Color(52, 152, 219);
	private Color myGreen = new Color(19, 141, 117);
	private Color myPink = new Color(171, 40, 199);
	
	private Font fontBig = new Font("华文琥珀",Font.BOLD,36);
	private Font fontSmal = new Font("华文楷体",Font.PLAIN,24);
	
	public PurchManagGui() {
		//布局
		frame.setLayout(new BorderLayout());
		panel.setLayout(new BorderLayout());
		inputPanel.setLayout(new GridLayout(4,2));
		//设置各个组件的属性
		label.setFont(fontBig);
		label.setForeground(myGreen);
		czTypeLabel.setFont(fontSmal);
		czTypeLabel.setForeground(myBlue);
		czType.setFont(fontSmal);
		czType.setForeground(myBlue);
		rkNoLabel.setFont(fontSmal);
		rkNoLabel.setForeground(myPink);
		rkNo.setFont(fontSmal);
		rkNo.setForeground(myPink);
		ypNoLabel.setFont(fontSmal);
		ypNo.setFont(fontSmal);
		rkQuaLabel.setFont(fontSmal);
		rkQua.setFont(fontSmal);
		cxArea.setFont(fontSmal);
		yesBtn.setFont(fontSmal);
		backBtn.setFont(fontSmal);
		//设置文本域不可编辑
		cxArea.setEditable(false);
		
		//添加组件
		frame.add(label,BorderLayout.NORTH);
		frame.add(panel,BorderLayout.CENTER);
		frame.add(backBtn,BorderLayout.SOUTH);
		panel.add(inputPanel,BorderLayout.NORTH);
		panel.add(cxScroll,BorderLayout.CENTER);
		panel.add(yesBtn,BorderLayout.SOUTH);
		inputPanel.add(czTypeLabel);
		inputPanel.add(czType);
		inputPanel.add(rkNoLabel);
		inputPanel.add(rkNo);
		inputPanel.add(ypNoLabel);
		inputPanel.add(ypNo);
		inputPanel.add(rkQuaLabel);
		inputPanel.add(rkQua);
		
		czType.addItem(null);
		czType.addItem("库存查询");
		czType.addItem("进货");
		czType.addItem("入库单查询");
		czType.addItem("显示所有入库单");
		czType.addItem("退货");
		cxArea.setText("提示：\n请先选择操作类型，\n然后按照提示操作！");
		
		PurchDao pcd = new PurchDao();
		
		//确定操作，按钮处理
		class YesButton implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if(czType.getSelectedIndex()==1) {
					//库存查询
					List<Warehouse> wareList = pcd.selAllYp();
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
					//进货
					String no = ypNo.getText();
					String qua = rkQua.getText();
					if(no.length()==0 || qua.length()==0) {
						JOptionPane.showMessageDialog(frame,"请输入完整进货信息！","错误提示",JOptionPane.ERROR_MESSAGE);
					}else {
						String show = pcd.jinHuo(no, qua);
						if(show.equals("进货失败！")) {
							JOptionPane.showMessageDialog(frame,show,"错误提示",JOptionPane.ERROR_MESSAGE);
						}else {
							JOptionPane.showMessageDialog(frame,show,"成功提示",JOptionPane.PLAIN_MESSAGE);
						}
					}
				}else if(czType.getSelectedIndex()==3) {
					//入库单查询
					String no = rkNo.getText();
					if(no.length()==0) {
						JOptionPane.showMessageDialog(frame,"请输入入库单号！","错误提示",JOptionPane.ERROR_MESSAGE);
					}else {
						RuKu rk = pcd.selrkMsg(no);
						if(rk != null) {
							String rkdcx = "入库单号：" + rk.getRkNo() +
										 "\n药品编号：" + rk.getYpNo() +
										 "\n入库数量：" + rk.getRkQua() +
										 "\n入库日期：" + rk.getRkDate();
							cxArea.setText(rkdcx);
						}else {
							cxArea.setText("暂无结果");
						}
					}
				}else if(czType.getSelectedIndex()==4) {
					//显示所有入库单
					List<RuKu> rkMsgList = pcd.selAllrkMsg();
					StringBuffer rkMsgBuffer = new StringBuffer();
					if(rkMsgList.size()==0) {
						cxArea.setText("暂无结果");
					}else {
						for(int i=0; i<rkMsgList.size(); i++) {
							rkMsgBuffer.append("入库单号：" + rkMsgList.get(i).getRkNo() +
									   "\n药品编号：" + rkMsgList.get(i).getYpNo() +
									   "\n入库数量：" + rkMsgList.get(i).getRkQua() +
									   "\n入库日期：" + rkMsgList.get(i).getRkDate() +
									   "\n==============================\n");
						}
						String rkMsg = rkMsgBuffer.toString();
						cxArea.setText(rkMsg);
					}
				}else if(czType.getSelectedIndex()==5) {
					//退货
					String no = ypNo.getText();
					if(no.length()==0) {
						JOptionPane.showMessageDialog(frame,"请输入入库单号！","错误提示",JOptionPane.ERROR_MESSAGE);
					}else {
						if(pcd.tuiHuo(no)) {
							JOptionPane.showMessageDialog(frame,"退货成功！","成功提示",JOptionPane.PLAIN_MESSAGE);
						}else {
							JOptionPane.showMessageDialog(frame,"退货失败！","错误提示",JOptionPane.ERROR_MESSAGE);
						}
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
					rkNo.setEditable(false);
					rkNo.setText(null);
					ypNo.setEditable(false);
					ypNo.setText(null);
					rkQua.setEditable(false);
					rkQua.setText(null);
				}else if(czType.getSelectedIndex()==2) {
					cxArea.setText("提示：\n请输入药品编号和药品数量，\n然后点击'确定操作'按钮！");
					rkNo.setEditable(false);
					rkNo.setText("自动生成入库单号");
					ypNo.setEditable(true);
					ypNo.setText(null);
					rkQua.setEditable(true);
					rkQua.setText(null);
				}else if(czType.getSelectedIndex()==3) {
					cxArea.setText("提示：\n请输入入库单号，\n然后点击'确定操作'按钮！");
					rkNo.setEditable(true);
					rkNo.setText(null);
					ypNo.setEditable(false);
					ypNo.setText(null);
					rkQua.setEditable(false);
					rkQua.setText(null);
				}else if(czType.getSelectedIndex()==4) {
					cxArea.setText("提示：\n请点击'确定操作'按钮！");
					rkNo.setEditable(false);
					rkNo.setText(null);
					ypNo.setEditable(false);
					ypNo.setText(null);
					rkQua.setEditable(false);
					rkQua.setText(null);
				}else if(czType.getSelectedIndex()==5) {
					cxArea.setText("提示：\n请输入入库单号，\n然后点击'确定操作'按钮！");
					rkNo.setEditable(true);
					rkNo.setText(null);
					ypNo.setEditable(false);
					ypNo.setText(null);
					rkQua.setEditable(false);
					rkQua.setText(null);
				}else {
					cxArea.setText("提示：\n请先选择操作类型，\n然后按照提示操作！");
					rkNo.setEditable(true);
					rkNo.setText(null);
					ypNo.setEditable(true);
					ypNo.setText(null);
					rkQua.setEditable(true);
					rkQua.setText(null);
				}
			}
		}
		//返回上一界面的按钮事件
		class BackBtn implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				AdminGui.frame.setVisible(true);
			}
		}
		
		
		YesButton yesButton = new YesButton();
		yesBtn.addActionListener(yesButton);
		
		CzTpBox czTpBox = new CzTpBox();
		czType.addActionListener(czTpBox);
		
		BackBtn bkBtn = new BackBtn();
		backBtn.addActionListener(bkBtn);
		
		frame.pack();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
}
