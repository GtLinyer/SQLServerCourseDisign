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

import cn.linyer.dao.CustomerDao;
import cn.linyer.entity.Customer;

/**
 * @author Linyer(韩啸翔)
 * 客户信息管理
 */
public class CustomerManagGui {
	//窗口定义
	private JFrame frame = new JFrame("客户信息管理");
	//主体部分定义
	private JLabel label = new JLabel("客户信息管理",JLabel.CENTER);
	private JPanel panel = new JPanel();
	//具体操作 部分组件定义
	private JPanel inputPanel = new JPanel();
	
	private JLabel czTypeLabel = new JLabel("操作类型：");
	private JComboBox<String> czType = new JComboBox<String>();
	
	private JLabel khNoLabel = new JLabel("客户编号：");
	private JLabel khNameLabel = new JLabel("客户姓名：");
	private JLabel khPhoneLabel = new JLabel("客户联系方式：");
	private JLabel khPwdLabel = new JLabel("客户登录密码：");
	private JLabel khAddressLabel = new JLabel("客户地址：");
	
	private JTextField khNo = new JTextField(8);
	private JTextField khName = new JTextField(8);
	private JTextField khPhone = new JTextField(8);
	private JTextField khPwd = new JTextField(8);
	private JTextField khAddress = new JTextField(8);
	private JTextArea khArea = new JTextArea(20,25);
	private JScrollPane khScroll = new JScrollPane(khArea);
	
	private JButton yesBtn = new JButton("确定 操作");
	
	//返回按钮
	private JButton backBtn = new JButton("返回上一界面");
	//颜色字体定义
	private Color myBlue = new Color(52, 152, 219);
	private Color myGreen = new Color(19, 141, 117);
	private Color myPink = new Color(171, 40, 199);
	
	private Font fontBig = new Font("华文琥珀",Font.BOLD,36);
	private Font fontSmal = new Font("华文楷体",Font.PLAIN,24);
	
	public CustomerManagGui() {
		//布局
		frame.setLayout(new BorderLayout());
		panel.setLayout(new BorderLayout());
		inputPanel.setLayout(new GridLayout(6,2));
		//设置各个组件的属性
		label.setFont(fontBig);
		label.setForeground(myGreen);
		czTypeLabel.setFont(fontSmal);
		czTypeLabel.setForeground(myBlue);
		czType.setFont(fontSmal);
		czType.setForeground(myBlue);
		khNoLabel.setFont(fontSmal);
		khNoLabel.setForeground(myPink);
		khNo.setFont(fontSmal);
		khNo.setForeground(myPink);
		khNameLabel.setFont(fontSmal);
		khName.setFont(fontSmal);
		khPhoneLabel.setFont(fontSmal);
		khPhone.setFont(fontSmal);
		khPwdLabel.setFont(fontSmal);
		khPwd.setFont(fontSmal);
		khAddressLabel.setFont(fontSmal);
		khAddress.setFont(fontSmal);
		khArea.setFont(fontSmal);
		yesBtn.setFont(fontSmal);
		backBtn.setFont(fontSmal);
		//设置文本域不可编辑
		khArea.setEditable(false);
		
		//添加组件
		frame.add(label,BorderLayout.NORTH);
		frame.add(panel,BorderLayout.CENTER);
		frame.add(backBtn,BorderLayout.SOUTH);
		panel.add(inputPanel,BorderLayout.NORTH);
		panel.add(khScroll,BorderLayout.CENTER);
		panel.add(yesBtn,BorderLayout.SOUTH);
		inputPanel.add(czTypeLabel);
		inputPanel.add(czType);
		inputPanel.add(khNoLabel);
		inputPanel.add(khNo);
		inputPanel.add(khNameLabel);
		inputPanel.add(khName);
		inputPanel.add(khPhoneLabel);
		inputPanel.add(khPhone);
		inputPanel.add(khPwdLabel);
		inputPanel.add(khPwd);
		inputPanel.add(khAddressLabel);
		inputPanel.add(khAddress);
		
		czType.addItem(null);
		czType.addItem("查询所有客户信息");
		czType.addItem("查询单个客户信息");
		czType.addItem("增加客户信息");
		czType.addItem("删除客户信息");
		czType.addItem("修改客户信息");
		khArea.setText("提示：\n请先选择操作类型，\n然后按照提示操作！");
		
		CustomerDao ctd = new CustomerDao();
		
		//确定操作，按钮处理
		class YesButton implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if(czType.getSelectedIndex()==1) {
					//查询所有客户信息
					List<Customer> customerList = ctd.selAllKh();
					StringBuffer empMsgBuffer = new StringBuffer();
					if(customerList.size()==0) {
						khArea.setText("暂无结果！");
					}else {
						for(int i=0; i<customerList.size(); i++) {
							empMsgBuffer.append("客户编号：" + customerList.get(i).getcNo() +
										 	   "\n客户姓名：" + customerList.get(i).getcName() +
										 	   "\n客户联系方式：" + customerList.get(i).getcPhone() +
										 	   "\n客户登录密码：" + customerList.get(i).getcPwd() +
										 	   "\n客户地址：" + customerList.get(i).getcAddress() +
										 	   "\n==============================\n");
						}
						String cusMsg = empMsgBuffer.toString();
						khArea.setText(cusMsg);
					}
				}else if(czType.getSelectedIndex()==2) {
					//查询单个客户信息
					String no = khNo.getText();
					if(no.length()==0) {
						JOptionPane.showMessageDialog(frame,"请输入员工工号！","错误提示",JOptionPane.ERROR_MESSAGE);
					}else {
						Customer cus = ctd.selOneKh(no);
						if(cus != null) {
							String oneCusMsg = "客户工号：" + cus.getcNo() +
											"\n客户姓名：" + cus.getcName() +
											"\n客户联系方式：" + cus.getcPhone() +
											"\n客户登录密码：" + cus.getcPwd() +
											"\n客户地址：" + cus.getcAddress() +
											"\n==============================\n";
							khArea.setText(oneCusMsg);
						}else {
							khArea.setText("暂无结果！");
						}
					}
				}else if(czType.getSelectedIndex()==3) {
					//增加客户信息
					Customer customer = new Customer();
					customer.setcName(khName.getText());
					customer.setcPhone(khPhone.getText());
					customer.setcPwd(khPwd.getText());
					customer.setcAddress(khAddress.getText());
					if(customer.getcName().length()==0 || customer.getcPhone().length()==0 || customer.getcPwd().length()==0 || customer.getcAddress().length()==0) {
						JOptionPane.showMessageDialog(frame,"请输入完整增加内容！","错误提示",JOptionPane.ERROR_MESSAGE);
					}else {
						String show = ctd.addKh(customer);
						if(show.equals("添加客户信息失败！")) {
							JOptionPane.showMessageDialog(frame,show,"错误提示",JOptionPane.ERROR_MESSAGE);
						}else {
							JOptionPane.showMessageDialog(frame,show,"成功提示",JOptionPane.PLAIN_MESSAGE);
						}
					}
				}else if(czType.getSelectedIndex()==4) {
					//删除供应商信息
					String no = khNo.getText();
					if(no.length()==0) {
						JOptionPane.showMessageDialog(frame,"请输入客户编号！","错误提示",JOptionPane.ERROR_MESSAGE);
					}else {
						if(ctd.deleteKh(no)) {
							JOptionPane.showMessageDialog(frame,"删除客户信息成功！","成功提示",JOptionPane.PLAIN_MESSAGE);
						}else {
							JOptionPane.showMessageDialog(frame,"删除客户信息失败！\n无此客户！","错误提示",JOptionPane.ERROR_MESSAGE);
						}
					}
				}else if(czType.getSelectedIndex()==5) {
					//修改供应商信息
					Customer updateKh = new Customer();
					updateKh.setcNo(khNo.getText());
					updateKh.setcName(khName.getText());
					updateKh.setcPhone(khPhone.getText());
					updateKh.setcPwd(khPwd.getText());
					updateKh.setcAddress(khAddress.getText());
					if(updateKh.getcNo().length()==0) {
						JOptionPane.showMessageDialog(frame,"请输入客户编号！","错误提示",JOptionPane.ERROR_MESSAGE);
					}else {
						if(updateKh.getcName().length()==0 && updateKh.getcPhone().length()==0 && updateKh.getcPwd().length()==0 && updateKh.getcAddress().length()==0) {
							JOptionPane.showMessageDialog(frame,"请输入至少一项修改内容！","错误提示",JOptionPane.ERROR_MESSAGE);
						}else {
							if(ctd.updateKh(updateKh)) {
								JOptionPane.showMessageDialog(frame,"修改客户信息成功！","成功提示",JOptionPane.PLAIN_MESSAGE);
							}else {
								JOptionPane.showMessageDialog(frame,"修改客户信息失败！","错误提示",JOptionPane.ERROR_MESSAGE);
							}
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
					khArea.setText("提示：\n请点击'确定操作'按钮！");
					khNo.setEditable(false);
					khNo.setText(null);
					khName.setEditable(false);
					khName.setText(null);
					khPhone.setEditable(false);
					khPhone.setText(null);
					khPwd.setEditable(false);
					khPwd.setText(null);
					khAddress.setEditable(false);
					khAddress.setText(null);
				}else if(czType.getSelectedIndex()==2) {
					khArea.setText("提示：\n请输入要查询客户工号，\n然后点击'确定操作'按钮！");
					khNo.setEditable(true);
					khNo.setText(null);
					khName.setEditable(false);
					khName.setText(null);
					khPhone.setEditable(false);
					khPhone.setText(null);
					khPwd.setEditable(false);
					khPwd.setText(null);
					khAddress.setEditable(false);
					khAddress.setText(null);
				}else if(czType.getSelectedIndex()==3) {
					khArea.setText("提示：\n请输入增加的客户所有信息，\n然后点击'确定操作'按钮！");
					khNo.setEditable(false);
					khNo.setText("自动生成员工工号");
					khName.setEditable(true);
					khName.setText(null);
					khPhone.setEditable(true);
					khPhone.setText(null);
					khPwd.setEditable(true);
					khPwd.setText(null);
					khAddress.setEditable(true);
					khAddress.setText(null);
				}else if(czType.getSelectedIndex()==4) {
					khArea.setText("提示：\n请输入要删除客户编号，\n然后点击'确定操作'按钮！");
					khNo.setEditable(true);
					khNo.setText(null);
					khName.setEditable(false);
					khName.setText(null);
					khPhone.setEditable(false);
					khPhone.setText(null);
					khPwd.setEditable(false);
					khPwd.setText(null);
					khAddress.setEditable(false);
					khAddress.setText(null);
				}else if(czType.getSelectedIndex()==5) {
					khArea.setText("提示：\n请输入要修改客户编号以及要修改的内容，\n不修改的内容留空即可，\n然后点击'确定操作'按钮！");
					khNo.setEditable(true);
					khNo.setText(null);
					khName.setEditable(true);
					khName.setText(null);
					khPhone.setEditable(true);
					khPhone.setText(null);
					khPwd.setEditable(true);
					khPwd.setText(null);
					khAddress.setEditable(true);
					khAddress.setText(null);
				}else {
					khArea.setText("提示：\n请先选择操作类型，\n然后按照提示操作！");
					khNo.setEditable(true);
					khNo.setText(null);
					khName.setEditable(true);
					khName.setText(null);
					khPhone.setEditable(true);
					khPhone.setText(null);
					khPwd.setEditable(true);
					khPwd.setText(null);
					khAddress.setEditable(true);
					khAddress.setText(null);
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
