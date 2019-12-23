package cn.linyer.gui.customerGui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import cn.linyer.dao.CustomerDao;
import cn.linyer.entity.Customer;
import cn.linyer.gui.LoginGui;

/**
 * @author Linyer(韩啸翔)
 * 客户注册界面
 */
public class CusRegGui {
	//窗口定义
	private JFrame frame = new JFrame("客户注册");
	
	private JLabel label = new JLabel("客户注册",JLabel.CENTER);
	private JPanel panel = new JPanel();
	private JPanel BtnPanel = new JPanel();
	
	private JLabel khNameLabel = new JLabel("姓名：");
	private JLabel khPhoneLabel = new JLabel("联系方式：");
	private JLabel khPwdLabel = new JLabel("登录密码：");
	private JLabel khYesPwdLabel = new JLabel("登录密码：");
	private JLabel khAddressLabel = new JLabel("地址：");
	
	private JTextField khName = new JTextField(8);
	private JTextField khPhone = new JTextField(8);
	private JPasswordField khPwd = new JPasswordField(8);
	private JPasswordField khYesPwd = new JPasswordField(8);
	private JTextField khAddress = new JTextField(8);
	
	private JButton yesBtn = new JButton("确定 注册");
	private JButton bakBtn = new JButton("返回");
	//颜色字体定义
	private Color myGreen = new Color(19, 141, 117);
	
	private Font fontBig = new Font("华文琥珀",Font.BOLD,36);
	private Font fontSmal = new Font("华文楷体",Font.PLAIN,24);
	
	public CusRegGui() {
		//布局
		frame.setLayout(new BorderLayout());
		panel.setLayout(new GridLayout(5,2));
		BtnPanel.setLayout(new GridLayout(2,1));
		//设置各个组件的属性
		label.setFont(fontBig);
		label.setForeground(myGreen);
		khNameLabel.setFont(fontSmal);
		khName.setFont(fontSmal);
		khPhoneLabel.setFont(fontSmal);
		khPhone.setFont(fontSmal);
		khPwdLabel.setFont(fontSmal);
		khPwd.setFont(fontSmal);
		khYesPwdLabel.setFont(fontSmal);
		khYesPwd.setFont(fontSmal);
		khAddressLabel.setFont(fontSmal);
		khAddress.setFont(fontSmal);
		yesBtn.setFont(fontSmal);
		bakBtn.setFont(fontSmal);
		//添加组件
		frame.add(label,BorderLayout.NORTH);
		frame.add(panel,BorderLayout.CENTER);
		frame.add(BtnPanel,BorderLayout.SOUTH);
		
		panel.add(khNameLabel);
		panel.add(khName);
		panel.add(khPhoneLabel);
		panel.add(khPhone);
		panel.add(khPwdLabel);
		panel.add(khPwd);
		panel.add(khYesPwdLabel);
		panel.add(khYesPwd);
		panel.add(khAddressLabel);
		panel.add(khAddress);
		
		BtnPanel.add(yesBtn);
		BtnPanel.add(bakBtn);
		
		CustomerDao csd = new CustomerDao();
		
		//确定操作，按钮处理
		class YesButton implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				//增加客户信息
				String pwd1 = String.valueOf(khPwd.getPassword());
				String pwd2 = String.valueOf(khYesPwd.getPassword());
				if(khName.getText().length()==0 || khPhone.getText().length()==0 || pwd1.length()==0 || pwd2.length()==0 || khAddress.getText().length()==0) {
					JOptionPane.showMessageDialog(frame,"请输入完整增加内容！","错误提示",JOptionPane.ERROR_MESSAGE);
				}else {
					if(pwd1.equals(pwd2)) {
						Customer customer = new Customer();
						customer.setcName(khName.getText());
						customer.setcPhone(khPhone.getText());
						customer.setcPwd(pwd1);
						customer.setcAddress(khAddress.getText());
						String show = csd.addKh(customer);
						if(show.equals("添加客户信息失败！")) {
							JOptionPane.showMessageDialog(frame,show,"错误提示",JOptionPane.ERROR_MESSAGE);
						}else {
							JOptionPane.showMessageDialog(frame,show,"成功提示",JOptionPane.PLAIN_MESSAGE);
							JOptionPane.showMessageDialog(frame,"你现在可以登录了！","成功提示",JOptionPane.PLAIN_MESSAGE);
							frame.setVisible(false);
							LoginGui.frame.setVisible(true);
						}
					}else {
						JOptionPane.showMessageDialog(frame,"请两次输入的密码不一致！","错误提示",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
		
		//返回操作，按钮处理
		class BakButton implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				LoginGui.frame.setVisible(true);
			}
		}
		YesButton yesButton = new YesButton();
		yesBtn.addActionListener(yesButton);
		
		BakButton bakButton = new BakButton();
		bakBtn.addActionListener(bakButton);
		
		frame.pack();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
}
