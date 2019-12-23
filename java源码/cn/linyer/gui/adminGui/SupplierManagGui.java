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

import cn.linyer.dao.SupplierDao;
import cn.linyer.entity.Supplier;

/**
 * @author Linyer
 * 管理员管理管理员基本信息界面
 * 
 */
public class SupplierManagGui {
	//窗口定义
	private JFrame frame = new JFrame("供应商信息管理");
	//主体部分定义
	private JLabel label = new JLabel("供应商信息管理",JLabel.CENTER);
	private JPanel panel = new JPanel();
	//具体操作 部分组件定义
	private JPanel inputPanel = new JPanel();
	
	private JLabel czTypeLabel = new JLabel("操作类型：");
	private JComboBox<String> czType = new JComboBox<String>();
	
	private JLabel gysNoLabel = new JLabel("供应商编号：");
	private JLabel gysNameLabel = new JLabel("供应商名称：");
	private JLabel gysPhoneLabel = new JLabel("供应商联系方式：");
	private JLabel gysAddressLabel = new JLabel("供应商地址：");
	
	private JTextField gysNo = new JTextField(8);
	private JTextField gysName = new JTextField(8);
	private JTextField gysPhone = new JTextField(8);
	private JTextField gysAddress = new JTextField(8);
	private JTextArea gysArea = new JTextArea(20,25);
	private JScrollPane gysScroll = new JScrollPane(gysArea);
	
	private JButton yesBtn = new JButton("确定 操作");
	
	//返回按钮
	private JButton backBtn = new JButton("返回上一界面");
	//颜色字体定义
	private Color myBlue = new Color(52, 152, 219);
	private Color myGreen = new Color(19, 141, 117);
	private Color myPink = new Color(171, 40, 199);
	
	private Font fontBig = new Font("华文琥珀",Font.BOLD,36);
	private Font fontSmal = new Font("华文楷体",Font.PLAIN,24);
	
	public SupplierManagGui() {
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
		gysNoLabel.setFont(fontSmal);
		gysNoLabel.setForeground(myPink);
		gysNo.setFont(fontSmal);
		gysNo.setForeground(myPink);
		gysNameLabel.setFont(fontSmal);
		gysName.setFont(fontSmal);
		gysPhoneLabel.setFont(fontSmal);
		gysPhone.setFont(fontSmal);
		gysAddressLabel.setFont(fontSmal);
		gysAddress.setFont(fontSmal);
		gysArea.setFont(fontSmal);
		yesBtn.setFont(fontSmal);
		backBtn.setFont(fontSmal);
		//设置文本域不可编辑
		gysArea.setEditable(false);
		
		//添加组件
		frame.add(label,BorderLayout.NORTH);
		frame.add(panel,BorderLayout.CENTER);
		frame.add(backBtn,BorderLayout.SOUTH);
		panel.add(inputPanel,BorderLayout.NORTH);
		panel.add(gysScroll,BorderLayout.CENTER);
		panel.add(yesBtn,BorderLayout.SOUTH);
		inputPanel.add(czTypeLabel);
		inputPanel.add(czType);
		inputPanel.add(gysNoLabel);
		inputPanel.add(gysNo);
		inputPanel.add(gysNameLabel);
		inputPanel.add(gysName);
		inputPanel.add(gysPhoneLabel);
		inputPanel.add(gysPhone);
		inputPanel.add(gysAddressLabel);
		inputPanel.add(gysAddress);
		
		czType.addItem(null);
		czType.addItem("查询所有供应商信息");
		czType.addItem("查询单个供应商信息");
		czType.addItem("增加供应商信息");
		czType.addItem("删除供应商信息");
		czType.addItem("修改供应商信息");
		gysArea.setText("提示：\n请先选择操作类型，\n然后按照提示操作！");
		
		SupplierDao spd = new SupplierDao();
		
		//确定操作，按钮处理
		class YesButton implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if(czType.getSelectedIndex()==1) {
					//查询所有供应商信息
					List<Supplier> supList = spd.selAllGys();
					StringBuffer supMsgBuffer = new StringBuffer();
					if(supList.size()==0) {
						gysArea.setText("暂无结果！");
					}else {
						for(int i=0; i<supList.size(); i++) {
							supMsgBuffer.append("供应商编号：" + supList.get(i).getGysNo() +
										 	   "\n供应商名称：" + supList.get(i).getGysName() +
										 	   "\n供应商联系方式：" + supList.get(i).getGysPhone() +
										 	   "\n供应商地址：" + supList.get(i).getGysAddress() +
										 	   "\n===============================\n");
						}
						String supMsg = supMsgBuffer.toString();
						gysArea.setText(supMsg);
					}
				}else if(czType.getSelectedIndex()==2) {
					//查询单个供应商信息
					String no = gysNo.getText();
					if(no.length()==0) {
						JOptionPane.showMessageDialog(frame,"请输入供应商编号！","错误提示",JOptionPane.ERROR_MESSAGE);
					}else {
						Supplier oneSup = spd.selOneGys(no);
						if(oneSup != null) {
							String oneSupMsg = "供应商编号：" + oneSup.getGysNo() +
											 "\n供应商名称：" + oneSup.getGysName() +
											 "\n供应商联系方式：" + oneSup.getGysPhone() +
											 "\n供应商地址：" + oneSup.getGysAddress() +
											 "\n===============================\n";
							gysArea.setText(oneSupMsg);
						}else {
							gysArea.setText("暂无结果！");
						}
					}
				}else if(czType.getSelectedIndex()==3) {
					//增加供应商信息
					Supplier gys = new Supplier();
					gys.setGysName(gysName.getText());
					gys.setGysPhone(gysPhone.getText());
					gys.setGysAddress(gysAddress.getText());
					if(gys.getGysName().length()==0 || gys.getGysPhone().length()==0 || gys.getGysAddress().length()==0) {
						JOptionPane.showMessageDialog(frame,"请输入完整增加内容！","错误提示",JOptionPane.ERROR_MESSAGE);
					}else {
						String show = spd.addGys(gys);
						if(show.equals("添加供应商信息失败！")) {
							JOptionPane.showMessageDialog(frame,show,"错误提示",JOptionPane.ERROR_MESSAGE);
						}else {
							JOptionPane.showMessageDialog(frame,show,"成功提示",JOptionPane.PLAIN_MESSAGE);
						}
					}
				}else if(czType.getSelectedIndex()==4) {
					//删除供应商信息
					String no = gysNo.getText();
					if(no.length()==0) {
						JOptionPane.showMessageDialog(frame,"请输入供应商编号！","错误提示",JOptionPane.ERROR_MESSAGE);
					}else {
						if(spd.deleteGys(no)) {
							JOptionPane.showMessageDialog(frame,"删除供应商信息成功！","成功提示",JOptionPane.PLAIN_MESSAGE);
						}else {
							JOptionPane.showMessageDialog(frame,"删除供应商信息失败！\n无此供应商！","错误提示",JOptionPane.ERROR_MESSAGE);
						}
					}
				}else if(czType.getSelectedIndex()==5) {
					//修改供应商信息
					Supplier updateSup = new Supplier();
					updateSup.setGysNo(gysNo.getText());
					updateSup.setGysName(gysName.getText());
					updateSup.setGysPhone(gysPhone.getText());
					updateSup.setGysAddress(gysAddress.getText());
					if(updateSup.getGysNo().length()==0) {
						JOptionPane.showMessageDialog(frame,"请输入供应商编号！","错误提示",JOptionPane.ERROR_MESSAGE);
					}else {
						if(updateSup.getGysName().length()==0 && updateSup.getGysPhone().length()==0 && updateSup.getGysAddress().length()==0) {
							JOptionPane.showMessageDialog(frame,"请输入至少一项修改内容！","错误提示",JOptionPane.ERROR_MESSAGE);
						}else {
							if(spd.updateGys(updateSup)) {
								JOptionPane.showMessageDialog(frame,"修改供应商信息成功！","成功提示",JOptionPane.PLAIN_MESSAGE);
							}else {
								JOptionPane.showMessageDialog(frame,"修改供应商信息失败！","错误提示",JOptionPane.ERROR_MESSAGE);
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
					gysArea.setText("提示：\n请点击'确定操作'按钮！");
					gysNo.setEditable(false);
					gysNo.setText(null);
					gysName.setEditable(false);
					gysName.setText(null);
					gysPhone.setEditable(false);
					gysPhone.setText(null);
					gysAddress.setEditable(false);
					gysAddress.setText(null);
				}else if(czType.getSelectedIndex()==2) {
					gysArea.setText("提示：\n请输入要查询供应商编号，\n然后点击'确定操作'按钮！");
					gysNo.setEditable(true);
					gysNo.setText(null);
					gysName.setEditable(false);
					gysName.setText(null);
					gysPhone.setEditable(false);
					gysPhone.setText(null);
					gysAddress.setEditable(false);
					gysAddress.setText(null);
				}else if(czType.getSelectedIndex()==3) {
					gysArea.setText("提示：\n请输入增加的供应商所有信息，\n然后点击'确定操作'按钮！");
					gysNo.setEditable(false);
					gysNo.setText("自动生成供应商编号");
					gysName.setEditable(true);
					gysName.setText(null);
					gysPhone.setEditable(true);
					gysPhone.setText(null);
					gysAddress.setEditable(true);
					gysAddress.setText(null);
				}else if(czType.getSelectedIndex()==4) {
					gysArea.setText("提示：\n请输入要删除供应商编号，\n然后点击'确定操作'按钮！");
					gysNo.setEditable(true);
					gysNo.setText(null);
					gysName.setEditable(false);
					gysName.setText(null);
					gysPhone.setEditable(false);
					gysPhone.setText(null);
					gysAddress.setEditable(false);
					gysAddress.setText(null);
				}else if(czType.getSelectedIndex()==5) {
					gysArea.setText("提示：\n请输入要修改供应商编号以及要修改的内容，\n不修改的内容留空即可，\n然后点击'确定操作'按钮！");
					gysNo.setEditable(true);
					gysNo.setText(null);
					gysName.setEditable(true);
					gysName.setText(null);
					gysPhone.setEditable(true);
					gysPhone.setText(null);
					gysAddress.setEditable(true);
					gysAddress.setText(null);
				}else {
					gysArea.setText("提示：\n请先选择操作类型，\n然后按照提示操作！");
					gysNo.setEditable(true);
					gysNo.setText(null);
					gysName.setEditable(true);
					gysName.setText(null);
					gysPhone.setEditable(true);
					gysPhone.setText(null);
					gysAddress.setEditable(true);
					gysAddress.setText(null);
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
