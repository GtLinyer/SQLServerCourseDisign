package cn.linyer.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * @author Linyer(韩啸翔)
 * 用户类型选择界面
 */
public class SelectUserGui {
	private JFrame frame = new JFrame("欢迎！");
	private JPanel panelNorth = new JPanel();
	private JPanel panelWest = new JPanel();
	private JPanel panelCenter = new JPanel();
	private JPanel panelSouth = new JPanel();
	private JLabel label = new JLabel("欢迎使用医药管理系统！");
	private JLabel selectLabel = new JLabel("用户类型：");
	private JComboBox<String> userType = new JComboBox<String>();
	private JButton yes = new JButton("确定");
	private Color ftColor = new Color(220, 118, 51);
	private Font fontBig = new Font("华文楷体",Font.BOLD,36);
	private Font fontMid = new Font("华文楷体",Font.PLAIN,24);
	
	public SelectUserGui() {
		//布局
		frame.setLayout(new BorderLayout());
		//设置组件的字体、颜色
		label.setFont(fontBig);
		label.setForeground(ftColor);
		selectLabel.setFont(fontMid);
		userType.setFont(fontMid);
		yes.setFont(fontMid);
		//添加各个组件
		frame.add(panelNorth,BorderLayout.NORTH);
		frame.add(panelWest,BorderLayout.WEST);
		frame.add(panelCenter,BorderLayout.CENTER);
		frame.add(panelSouth,BorderLayout.SOUTH);
		panelNorth.add(label);
		panelWest.add(selectLabel);
		panelCenter.add(userType);
		panelSouth.add(yes);
		//添加下拉框内容
		userType.addItem("--请选择用户类型--");
		userType.addItem("客户");
		userType.addItem("员工");
		userType.addItem("管理员");
		//确定按钮监听器
		class YesButton implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				String userTp = (String) userType.getSelectedItem();
				if(userTp.equals("客户")) {
					new LoginGui("客户");
					frame.setVisible(false);
				}else if(userTp.equals("员工")){
					new LoginGui("员工");
					frame.setVisible(false);
				}else if(userTp.equals("管理员")){
					new LoginGui("管理员");
					frame.setVisible(false);
				}
			}
		}
		
		YesButton yesButton = new YesButton();
		yes.addActionListener(yesButton);
		
		frame.pack();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
}
