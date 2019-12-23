package cn.linyer.gui.adminGui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
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

import cn.linyer.dao.EmployerDao;
import cn.linyer.entity.Employer;

/**
 * @author Linyer(韩啸翔)
 * 员工信息管理
 */
public class EmployerManagGui {
	//窗口定义
	private JFrame frame = new JFrame("员工信息管理");
	//主体部分定义
	private JLabel label = new JLabel("员工信息管理",JLabel.CENTER);
	private JPanel panel = new JPanel();
	//具体操作 部分组件定义
	private JPanel inputPanel = new JPanel();
	
	private JLabel czTypeLabel = new JLabel("操作类型：");
	private JComboBox<String> czType = new JComboBox<String>();
	
	private JLabel ygNoLabel = new JLabel("员工工号：");
	private JLabel ygNameLabel = new JLabel("员工姓名：");
	private JLabel ygPhoneLabel = new JLabel("员工联系方式：");
	private JLabel ygPwdLabel = new JLabel("员工登录密码：");
	private JLabel ygWorkLabel = new JLabel("员工职务：");
	private JLabel isAdminLabel = new JLabel("是否管理员：");
	
	private JTextField ygNo = new JTextField(8);
	private JTextField ygName = new JTextField(8);
	private JTextField ygPhone = new JTextField(8);
	private JTextField ygPwd = new JTextField(8);
	private JTextField ygWork = new JTextField(8);
	private JComboBox<String> isAdmin = new JComboBox<String>();
	private JTextArea ygArea = new JTextArea(20,25);
	private JScrollPane ygScroll = new JScrollPane(ygArea);
	
	private JButton yesBtn = new JButton("确定 操作");
	
	//返回按钮
	private JButton backBtn = new JButton("返回上一界面");
	//颜色字体定义
	private Color myBlue = new Color(52, 152, 219);
	private Color myGreen = new Color(19, 141, 117);
	private Color myPink = new Color(171, 40, 199);
	
	private Font fontBig = new Font("华文琥珀",Font.BOLD,36);
	private Font fontSmal = new Font("华文楷体",Font.PLAIN,24);
	
	public EmployerManagGui() {
		//布局
		frame.setLayout(new BorderLayout());
		panel.setLayout(new BorderLayout());
		inputPanel.setLayout(new GridLayout(7,2));
		//设置各个组件的属性
		label.setFont(fontBig);
		label.setForeground(myGreen);
		czTypeLabel.setFont(fontSmal);
		czTypeLabel.setForeground(myBlue);
		czType.setFont(fontSmal);
		czType.setForeground(myBlue);
		ygNoLabel.setFont(fontSmal);
		ygNoLabel.setForeground(myPink);
		ygNo.setFont(fontSmal);
		ygNo.setForeground(myPink);
		ygNameLabel.setFont(fontSmal);
		ygName.setFont(fontSmal);
		ygPhoneLabel.setFont(fontSmal);
		ygPhone.setFont(fontSmal);
		ygPwdLabel.setFont(fontSmal);
		ygPwd.setFont(fontSmal);
		ygWorkLabel.setFont(fontSmal);
		ygWork.setFont(fontSmal);
		isAdminLabel.setFont(fontSmal);
		isAdmin.setFont(fontSmal);
		ygArea.setFont(fontSmal);
		yesBtn.setFont(fontSmal);
		backBtn.setFont(fontSmal);
		//设置文本域不可编辑
		ygArea.setEditable(false);
		
		//添加组件
		frame.add(label,BorderLayout.NORTH);
		frame.add(panel,BorderLayout.CENTER);
		frame.add(backBtn,BorderLayout.SOUTH);
		panel.add(inputPanel,BorderLayout.NORTH);
		panel.add(ygScroll,BorderLayout.CENTER);
		panel.add(yesBtn,BorderLayout.SOUTH);
		inputPanel.add(czTypeLabel);
		inputPanel.add(czType);
		inputPanel.add(ygNoLabel);
		inputPanel.add(ygNo);
		inputPanel.add(ygNameLabel);
		inputPanel.add(ygName);
		inputPanel.add(ygPhoneLabel);
		inputPanel.add(ygPhone);
		inputPanel.add(ygPwdLabel);
		inputPanel.add(ygPwd);
		inputPanel.add(ygWorkLabel);
		inputPanel.add(ygWork);
		inputPanel.add(isAdminLabel);
		inputPanel.add(isAdmin);
		
		czType.addItem(null);
		czType.addItem("查询所有员工信息");
		czType.addItem("查询单个员工信息");
		czType.addItem("增加员工信息");
		czType.addItem("删除员工信息");
		czType.addItem("修改员工信息");
		ygArea.setText("提示：\n请先选择操作类型，\n然后按照提示操作！");
		
		isAdmin.addItem(null);
		isAdmin.addItem("是");
		isAdmin.addItem("否");
		
		EmployerDao epd = new EmployerDao();
		
		//确定操作，按钮处理
		class YesButton implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if(czType.getSelectedIndex()==1) {
					//查询所有供应商信息
					List<Employer> employerList = epd.selAllYg();
					StringBuffer empMsgBuffer = new StringBuffer();
					if(employerList.size()==0) {
						ygArea.setText("暂无结果！");
					}else {
						for(int i=0; i<employerList.size(); i++) {
							empMsgBuffer.append("员工工号：" + employerList.get(i).getaNo() +
										 	   "\n员工姓名：" + employerList.get(i).getaName() +
										 	   "\n员工联系方式：" + employerList.get(i).getaPhone() +
										 	   "\n员工登录密码：" + employerList.get(i).getaPwd() +
										 	   "\n员工职务：" + employerList.get(i).getWork() +
										 	   "\n是否管理员：" + employerList.get(i).getIsAdmin() +
										 	   "\n==============================\n");
						}
						String empMsg = empMsgBuffer.toString();
						ygArea.setText(empMsg);
					}
				}else if(czType.getSelectedIndex()==2) {
					//查询单个供应商信息
					String no = ygNo.getText();
					if(no.length()==0) {
						JOptionPane.showMessageDialog(frame,"请输入员工工号！","错误提示",JOptionPane.ERROR_MESSAGE);
					}else {
						Employer emp = epd.selOneYg(no);
						if(emp != null) {
							String oneEmpMsg = "员工工号：" + emp.getaNo() +
								 	   "\n员工姓名：" + emp.getaName() +
								 	   "\n员工联系方式：" + emp.getaPhone() +
								 	   "\n员工登录密码：" + emp.getaPwd() +
								 	   "\n员工职务：" + emp.getWork() +
								 	   "\n是否管理员：" + emp.getIsAdmin() +
								 	   "\n==============================\n";
							ygArea.setText(oneEmpMsg);
						}else {
							ygArea.setText("暂无结果！");
						}
					}
				}else if(czType.getSelectedIndex()==3) {
					//增加供应商信息
					Employer employ = new Employer();
					employ.setaName(ygName.getText());
					employ.setaPhone(ygPhone.getText());
					employ.setaPwd(ygPwd.getText());
					employ.setWork(ygWork.getText());
					if(isAdmin.getSelectedItem().equals("1")) {
						employ.setIsAdmin("1");
					}else {
						employ.setIsAdmin("0");
					}
					if(employ.getaName().length()==0 || employ.getaPhone().length()==0 || employ.getaPwd().length()==0 || employ.getWork().length()==0 || employ.getIsAdmin().length()==0) {
						JOptionPane.showMessageDialog(frame,"请输入完整增加内容！","错误提示",JOptionPane.ERROR_MESSAGE);
					}else {
						String show = epd.addYg(employ);
						if(show.equals("添加员工信息失败！")) {
							JOptionPane.showMessageDialog(frame,show,"错误提示",JOptionPane.ERROR_MESSAGE);
						}else {
							JOptionPane.showMessageDialog(frame,show,"成功提示",JOptionPane.PLAIN_MESSAGE);
						}
					}
				}else if(czType.getSelectedIndex()==4) {
					//删除供应商信息
					String no = ygNo.getText();
					if(no.length()==0) {
						JOptionPane.showMessageDialog(frame,"请输入员工工号号！","错误提示",JOptionPane.ERROR_MESSAGE);
					}else {
						if(epd.deleteYg(no)) {
							JOptionPane.showMessageDialog(frame,"删除员工信息成功！","成功提示",JOptionPane.PLAIN_MESSAGE);
						}else {
							JOptionPane.showMessageDialog(frame,"删除员工信息失败！\n无此员工！","错误提示",JOptionPane.ERROR_MESSAGE);
						}
					}
				}else if(czType.getSelectedIndex()==5) {
					//修改供应商信息
					Employer updateYg = new Employer();
					updateYg.setaNo(ygNo.getText());
					updateYg.setaName(ygName.getText());
					updateYg.setaPhone(ygPhone.getText());
					updateYg.setaPwd(ygPwd.getText());
					updateYg.setWork(ygWork.getText());
					if(isAdmin.getSelectedItem().equals("1")) {
						updateYg.setIsAdmin("1");
					}else {
						updateYg.setIsAdmin("0");
					}
					if(updateYg.getaNo().length()==0) {
						JOptionPane.showMessageDialog(frame,"请输入员工工号！","错误提示",JOptionPane.ERROR_MESSAGE);
					}else {
						if(updateYg.getaName().length()==0 && updateYg.getaPhone().length()==0 && updateYg.getaPwd().length()==0 && updateYg.getWork().length()==0 && updateYg.getIsAdmin().length()==0) {
							JOptionPane.showMessageDialog(frame,"请输入至少一项修改内容！","错误提示",JOptionPane.ERROR_MESSAGE);
						}else {
							if(epd.updateYg(updateYg)) {
								JOptionPane.showMessageDialog(frame,"修改员工信息成功！","成功提示",JOptionPane.PLAIN_MESSAGE);
							}else {
								JOptionPane.showMessageDialog(frame,"修改员工信息失败！","错误提示",JOptionPane.ERROR_MESSAGE);
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
					ygArea.setText("提示：\n请点击'确定操作'按钮！");
					ygNo.setEditable(false);
					ygNo.setText(null);
					ygName.setEditable(false);
					ygName.setText(null);
					ygPhone.setEditable(false);
					ygPhone.setText(null);
					ygPwd.setEditable(false);
					ygPwd.setText(null);
					ygWork.setEditable(false);
					ygWork.setText(null);
					isAdmin.setEnabled(false);
					isAdmin.setSelectedItem(null);
				}else if(czType.getSelectedIndex()==2) {
					ygArea.setText("提示：\n请输入要查询员工工号，\n然后点击'确定操作'按钮！");
					ygNo.setEditable(true);
					ygNo.setText(null);
					ygName.setEditable(false);
					ygName.setText(null);
					ygPhone.setEditable(false);
					ygPhone.setText(null);
					ygPwd.setEditable(false);
					ygPwd.setText(null);
					ygWork.setEditable(false);
					ygWork.setText(null);
					isAdmin.setEnabled(false);
					isAdmin.setSelectedItem(null);
				}else if(czType.getSelectedIndex()==3) {
					ygArea.setText("提示：\n请输入增加的员工所有信息，\n然后点击'确定操作'按钮！");
					ygNo.setEditable(false);
					ygNo.setText("自动生成员工工号");
					ygName.setEditable(true);
					ygName.setText(null);
					ygPhone.setEditable(true);
					ygPhone.setText(null);
					ygPwd.setEditable(true);
					ygPwd.setText(null);
					ygWork.setEditable(true);
					ygWork.setText(null);
					isAdmin.setEnabled(true);
					isAdmin.setSelectedItem(null);
				}else if(czType.getSelectedIndex()==4) {
					ygArea.setText("提示：\n请输入要删除员工工号，\n然后点击'确定操作'按钮！");
					ygNo.setEditable(true);
					ygNo.setText(null);
					ygName.setEditable(false);
					ygName.setText(null);
					ygPhone.setEditable(false);
					ygPhone.setText(null);
					ygPwd.setEditable(false);
					ygPwd.setText(null);
					ygWork.setEditable(false);
					ygWork.setText(null);
					isAdmin.setEnabled(false);
					isAdmin.setSelectedItem(null);
				}else if(czType.getSelectedIndex()==5) {
					ygArea.setText("提示：\n请输入要修改员工工号以及要修改的内容，\n不修改的内容留空即可，\n然后点击'确定操作'按钮！");
					ygNo.setEditable(true);
					ygNo.setText(null);
					ygName.setEditable(true);
					ygName.setText(null);
					ygPhone.setEditable(true);
					ygPhone.setText(null);
					ygPwd.setEditable(true);
					ygPwd.setText(null);
					ygWork.setEditable(true);
					ygWork.setText(null);
					isAdmin.setEnabled(true);
					isAdmin.setSelectedItem(null);
				}else {
					ygArea.setText("提示：\n请先选择操作类型，\n然后按照提示操作！");
					ygNo.setEditable(true);
					ygNo.setText(null);
					ygName.setEditable(true);
					ygName.setText(null);
					ygPhone.setEditable(true);
					ygPhone.setText(null);
					ygPwd.setEditable(true);
					ygPwd.setText(null);
					ygWork.setEditable(true);
					ygWork.setText(null);
					isAdmin.setEnabled(true);
					isAdmin.setSelectedItem(null);
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
		
		Toolkit kit = Toolkit.getDefaultToolkit(); 
		Image icon = kit.getImage("src/logo.ico");
		frame.setIconImage(icon);
		frame.pack();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
}