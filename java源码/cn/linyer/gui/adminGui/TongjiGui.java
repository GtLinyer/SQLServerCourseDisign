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
import cn.linyer.dao.TongjiDao;
import cn.linyer.entity.Order;

/**
 * @author Linyer(韩啸翔)
 * 统计
 */
public class TongjiGui {
	//窗口定义
	private JFrame frame = new JFrame("统计信息");
	//主体部分定义
	private JLabel label = new JLabel("当月统计信息",JLabel.CENTER);;
	private JPanel panel = new JPanel();
	//具体操作 部分组件定义
	private JPanel inputPanel = new JPanel();
	
	private JLabel czTypeLabel = new JLabel("操作类型：");
	private JComboBox<String> czType = new JComboBox<String>();
	
	private JTextArea tjArea = new JTextArea(20,25);
	private JScrollPane tjScroll = new JScrollPane(tjArea);
	
	private JButton yesBtn = new JButton("确定 操作");
	
	//颜色字体定义
	private Color myBlue = new Color(52, 152, 219);
	private Color myGreen = new Color(19, 141, 117);
	
	private Font fontBig = new Font("华文琥珀",Font.BOLD,36);
	private Font fontSmal = new Font("华文楷体",Font.PLAIN,24);
	
	public TongjiGui() {
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
		tjArea.setFont(fontSmal);
		yesBtn.setFont(fontSmal);
		//设置文本域不可编辑
		tjArea.setEditable(false);
		
		//添加组件
		frame.add(label,BorderLayout.NORTH);
		frame.add(panel,BorderLayout.CENTER);

		panel.add(inputPanel,BorderLayout.NORTH);
		panel.add(tjScroll,BorderLayout.CENTER);
		panel.add(yesBtn,BorderLayout.SOUTH);
		inputPanel.add(czTypeLabel);
		inputPanel.add(czType);
		
		czType.addItem(null);
		czType.addItem("药物销量排行");
		czType.addItem("药物销售额排行");
		
		tjArea.setText("提示：\n请先选择操作类型，\n然后按照提示操作！");
		
		TongjiDao tjd = new TongjiDao();
		
		//确定操作，按钮处理
		class YesButton implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if(czType.getSelectedIndex()==1) {
					//药物销量排行
					List<Order> drugList = tjd.xl();
					if(drugList.size() > 0) {
						StringBuffer drugBuffer = new StringBuffer();
						for(int i=0; i<drugList.size(); i++) {
							drugBuffer.append("销售总量排行第"+(i+1)+"名：" +
											"\n药品名称：" + drugList.get(i).getYpNo() +
											"\n药品销售总数：" + drugList.get(i).getYpQua() +
											  "\n==========================\n");
						}
						String drug = drugBuffer.toString();
						tjArea.setText(drug);
					}else {
						tjArea.setText("暂无结果！");
					}
				}else if(czType.getSelectedIndex()==2) {
					//药物销售额排行
					List<Order> drugList = tjd.xsze();
					if(drugList.size() > 0) {
						StringBuffer drugBuffer = new StringBuffer();
						for(int i=0; i<drugList.size(); i++) {
							drugBuffer.append("销售总额排行第"+(i+1)+"名：" +
											"\n药品名称：" + drugList.get(i).getYpNo() +
											"\n药品销售总额：" + drugList.get(i).getTotalPrice() +
											"\n==========================\n");
						}
						String drug = drugBuffer.toString();
						tjArea.setText(drug);
					}else {
						tjArea.setText("暂无结果！");
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
					tjArea.setText("提示：\n请点击【确定操作】按钮！");
				}else if(czType.getSelectedIndex()==2) {
					tjArea.setText("提示：\n请点击【确定操作】按钮！");
				}else if(czType.getSelectedIndex()==3) {
					tjArea.setText("提示：\n请点击【确定操作】按钮！");
				}else {
					tjArea.setText("提示：\n请先选择操作类型，\n然后按照提示操作！");
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