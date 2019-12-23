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

import cn.linyer.dao.DrugDao;
import cn.linyer.entity.Drug;

/**
 * @author Linyer
 * 管理员管理患者信息界面
 * 
 */
public class DrugManagGui {
	//窗口定义
	private JFrame frame = new JFrame("药品信息管理");
	//主体部分定义
	private JLabel label = new JLabel("药品信息管理",JLabel.CENTER);
	private JPanel panel = new JPanel();
	//具体操作 部分组件定义
	private JPanel inputPanel = new JPanel();
	
	private JLabel czTypeLabel = new JLabel("操作类型：");
	private JComboBox<String> czType = new JComboBox<String>();
	
	private JLabel ypNoLabel = new JLabel("药品编号：");
	private JLabel ypNameLabel = new JLabel("药品名称：");
	private JLabel ypEffectLabel = new JLabel("药品功效：");
	private JLabel ypPriceLabel = new JLabel("药品单价：");
	private JLabel ypDateLabel = new JLabel("有效期：");
	private JLabel ypTypeLabel = new JLabel("药品类型：");
	private JLabel gysNoLabel = new JLabel("供应商编号：");
	
	private JTextField ypNo = new JTextField(8);
	private JTextField ypName = new JTextField(8);
	private JTextField ypEffect = new JTextField(8);
	private JTextField ypPrice = new JTextField(8);
	private JTextField ypDate = new JTextField(8);
	private JTextField ypType = new JTextField(8);
	private JTextField gysNo = new JTextField(8);
	private JTextArea ypArea = new JTextArea(20,25);
	private JScrollPane ypScroll = new JScrollPane(ypArea);
	
	private JButton yesBtn = new JButton("确定 操作");
	
	//返回按钮
	private JButton backBtn = new JButton("返回上一界面");
	//颜色字体定义
	private Color myBlue = new Color(52, 152, 219);
	private Color myGreen = new Color(19, 141, 117);
	private Color myPink = new Color(171, 40, 199);
	
	private Font fontBig = new Font("华文琥珀",Font.BOLD,36);
	private Font fontSmal = new Font("华文楷体",Font.PLAIN,24);
	
	public DrugManagGui() {
		//布局
		frame.setLayout(new BorderLayout());
		panel.setLayout(new BorderLayout());
		inputPanel.setLayout(new GridLayout(8,2));
		//设置各个组件的属性
		label.setFont(fontBig);
		label.setForeground(myGreen);
		czTypeLabel.setFont(fontSmal);
		czTypeLabel.setForeground(myBlue);
		czType.setFont(fontSmal);
		czType.setForeground(myBlue);
		ypNoLabel.setFont(fontSmal);
		ypNoLabel.setForeground(myPink);
		ypNo.setFont(fontSmal);
		ypNo.setForeground(myPink);
		ypNameLabel.setFont(fontSmal);
		ypName.setFont(fontSmal);
		ypEffectLabel.setFont(fontSmal);
		ypEffect.setFont(fontSmal);
		ypPriceLabel.setFont(fontSmal);
		ypPrice.setFont(fontSmal);
		ypDateLabel.setFont(fontSmal);
		ypDate.setFont(fontSmal);
		ypTypeLabel.setFont(fontSmal);
		ypType.setFont(fontSmal);
		gysNoLabel.setFont(fontSmal);
		gysNo.setFont(fontSmal);
		ypArea.setFont(fontSmal);
		yesBtn.setFont(fontSmal);
		backBtn.setFont(fontSmal);
		//设置文本域不可编辑
		ypArea.setEditable(false);
		
		//添加组件
		frame.add(label,BorderLayout.NORTH);
		frame.add(panel,BorderLayout.CENTER);
		frame.add(backBtn,BorderLayout.SOUTH);
		panel.add(inputPanel,BorderLayout.NORTH);
		panel.add(ypScroll,BorderLayout.CENTER);
		panel.add(yesBtn,BorderLayout.SOUTH);
		inputPanel.add(czTypeLabel);
		inputPanel.add(czType);
		inputPanel.add(ypNoLabel);
		inputPanel.add(ypNo);
		inputPanel.add(ypNameLabel);
		inputPanel.add(ypName);
		inputPanel.add(ypEffectLabel);
		inputPanel.add(ypEffect);
		inputPanel.add(ypPriceLabel);
		inputPanel.add(ypPrice);
		inputPanel.add(ypDateLabel);
		inputPanel.add(ypDate);
		inputPanel.add(ypTypeLabel);
		inputPanel.add(ypType);
		inputPanel.add(gysNoLabel);
		inputPanel.add(gysNo);
		
		czType.addItem(null);
		czType.addItem("查询所有药品信息");
		czType.addItem("查询单个药品信息");
		czType.addItem("增加药品信息");
		czType.addItem("删除药品信息");
		czType.addItem("修改药品信息");
		ypArea.setText("提示：\n请先选择操作类型，\n然后按照提示操作！");
		
		DrugDao dd = new DrugDao();
		
		//确定操作，按钮处理
		class YesButton implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if(czType.getSelectedIndex()==1) {
					//查询所有供应商信息
					List<Drug> drugList = dd.selAllYp();
					StringBuffer drugMsgBuffer = new StringBuffer();
					if(drugList.size()==0) {
						ypArea.setText("暂无结果！");
					}else {
						for(int i=0; i<drugList.size(); i++) {
							drugMsgBuffer.append("药品编号：" + drugList.get(i).getYpNo() +
										 	   "\n药品名称：" + drugList.get(i).getYpName() +
										 	   "\n药品功效：" + drugList.get(i).getYpEffect() +
										 	   "\n药品单价：" + drugList.get(i).getYpPrice() +
										 	   "\n有效期：" + drugList.get(i).getYpDate() +
										 	   "\n药品类型：" + drugList.get(i).getYpType() +
										 	   "\n供应商编号：" + drugList.get(i).getGysNo() +
										 	   "\n==============================\n");
						}
						String drugMsg = drugMsgBuffer.toString();
						ypArea.setText(drugMsg);
					}
				}else if(czType.getSelectedIndex()==2) {
					//查询单个供应商信息
					String no = ypNo.getText();
					if(no.length()==0) {
						JOptionPane.showMessageDialog(frame,"请输入药品编号！","错误提示",JOptionPane.ERROR_MESSAGE);
					}else {
						Drug oneDrug = dd.selOneYp(no);
						if(oneDrug != null) {
							String oneDrugMsg = "药品编号：" + oneDrug.getYpNo() +
											 "\n药品名称：" + oneDrug.getYpName() +
											 "\n药品功效：" + oneDrug.getYpEffect() +
											 "\n药品单价：" + oneDrug.getYpPrice() +
											 "\n有效期：" + oneDrug.getYpDate() +
											 "\n药品类型：" + oneDrug.getYpType() +
											 "\n供应商编号：" + oneDrug.getGysNo() +
											 "\n==============================\n";
							ypArea.setText(oneDrugMsg);
						}else {
							ypArea.setText("暂无结果！");
						}
					}
				}else if(czType.getSelectedIndex()==3) {
					//增加供应商信息
					Drug yp = new Drug();
					yp.setYpName(ypName.getText());
					yp.setYpEffect(ypEffect.getText());
					yp.setYpPrice(ypPrice.getText());
					yp.setYpDate(ypDate.getText());
					yp.setYpType(ypType.getText());
					yp.setGysNo(gysNo.getText());
					if(yp.getYpName().length()==0 || yp.getYpEffect().length()==0 || yp.getYpPrice().length()==0 || yp.getYpDate().length()==0 || yp.getYpType().length()==0 || yp.getGysNo().length()==0) {
						JOptionPane.showMessageDialog(frame,"请输入完整增加内容！","错误提示",JOptionPane.ERROR_MESSAGE);
					}else {
						String show = dd.addYp(yp);
						if(show.equals("添加药品信息失败！")) {
							JOptionPane.showMessageDialog(frame,show,"错误提示",JOptionPane.ERROR_MESSAGE);
						}else {
							JOptionPane.showMessageDialog(frame,show,"成功提示",JOptionPane.PLAIN_MESSAGE);
						}
					}
				}else if(czType.getSelectedIndex()==4) {
					//删除供应商信息
					String no = ypNo.getText();
					if(no.length()==0) {
						JOptionPane.showMessageDialog(frame,"请输入药品编号！","错误提示",JOptionPane.ERROR_MESSAGE);
					}else {
						if(dd.deleteYp(no)) {
							JOptionPane.showMessageDialog(frame,"删除药品信息成功！","成功提示",JOptionPane.PLAIN_MESSAGE);
						}else {
							JOptionPane.showMessageDialog(frame,"删除药品信息失败！\n无此药品！","错误提示",JOptionPane.ERROR_MESSAGE);
						}
					}
				}else if(czType.getSelectedIndex()==5) {
					//修改供应商信息
					Drug updateYp = new Drug();
					updateYp.setYpNo(ypNo.getText());
					updateYp.setYpName(ypName.getText());
					updateYp.setYpEffect(ypEffect.getText());
					updateYp.setYpPrice(ypPrice.getText());
					updateYp.setYpDate(ypDate.getText());
					updateYp.setYpType(ypType.getText());
					updateYp.setGysNo(gysNo.getText());
					if(updateYp.getYpNo().length()==0) {
						JOptionPane.showMessageDialog(frame,"请输入药品编号！","错误提示",JOptionPane.ERROR_MESSAGE);
					}else {
						if(updateYp.getYpName().length()==0 && updateYp.getYpEffect().length()==0 && updateYp.getYpPrice().length()==0 && updateYp.getYpDate().length()==0 && updateYp.getYpType().length()==0 && updateYp.getGysNo().length()==0) {
							JOptionPane.showMessageDialog(frame,"请输入至少一项修改内容！","错误提示",JOptionPane.ERROR_MESSAGE);
						}else {
							if(dd.updateYp(updateYp)) {
								JOptionPane.showMessageDialog(frame,"修改药品信息成功！","成功提示",JOptionPane.PLAIN_MESSAGE);
							}else {
								JOptionPane.showMessageDialog(frame,"修改药品信息失败！","错误提示",JOptionPane.ERROR_MESSAGE);
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
					ypArea.setText("提示：\n请点击'确定操作'按钮！");
					ypNo.setEditable(false);
					ypNo.setText(null);
					ypName.setEditable(false);
					ypName.setText(null);
					ypEffect.setEditable(false);
					ypEffect.setText(null);
					ypPrice.setEditable(false);
					ypPrice.setText(null);
					ypDate.setEditable(false);
					ypDate.setText(null);
					ypType.setEditable(false);
					ypType.setText(null);
					gysNo.setEditable(false);
					gysNo.setText(null);
				}else if(czType.getSelectedIndex()==2) {
					ypArea.setText("提示：\n请输入要查询药品编号，\n然后点击'确定操作'按钮！");
					ypNo.setEditable(true);
					ypNo.setText(null);
					ypName.setEditable(false);
					ypName.setText(null);
					ypEffect.setEditable(false);
					ypEffect.setText(null);
					ypPrice.setEditable(false);
					ypPrice.setText(null);
					ypDate.setEditable(false);
					ypDate.setText(null);
					ypType.setEditable(false);
					ypType.setText(null);
					gysNo.setEditable(false);
					gysNo.setText(null);
				}else if(czType.getSelectedIndex()==3) {
					ypArea.setText("提示：\n请输入增加的药品所有信息，\n然后点击'确定操作'按钮！");
					ypNo.setEditable(false);
					ypNo.setText("自动生成药品编号");
					ypName.setEditable(true);
					ypName.setText(null);
					ypEffect.setEditable(true);
					ypEffect.setText(null);
					ypPrice.setEditable(true);
					ypPrice.setText(null);
					ypDate.setEditable(true);
					ypDate.setText(null);
					ypType.setEditable(true);
					ypType.setText(null);
					gysNo.setEditable(true);
					gysNo.setText(null);
				}else if(czType.getSelectedIndex()==4) {
					ypArea.setText("提示：\n请输入要删除药品编号，\n然后点击'确定操作'按钮！");
					ypNo.setEditable(true);
					ypNo.setText(null);
					ypName.setEditable(false);
					ypName.setText(null);
					ypEffect.setEditable(false);
					ypEffect.setText(null);
					ypPrice.setEditable(false);
					ypPrice.setText(null);
					ypDate.setEditable(false);
					ypDate.setText(null);
					ypType.setEditable(false);
					ypType.setText(null);
					gysNo.setEditable(false);
					gysNo.setText(null);
				}else if(czType.getSelectedIndex()==5) {
					ypArea.setText("提示：\n请输入要修改药品编号以及要修改的内容，\n不修改的内容留空即可，\n然后点击'确定操作'按钮！");
					ypNo.setEditable(true);
					ypNo.setText(null);
					ypName.setEditable(true);
					ypName.setText(null);
					ypEffect.setEditable(true);
					ypEffect.setText(null);
					ypPrice.setEditable(true);
					ypPrice.setText(null);
					ypDate.setEditable(true);
					ypDate.setText(null);
					ypType.setEditable(true);
					ypType.setText(null);
					gysNo.setEditable(true);
					gysNo.setText(null);
				}else {
					ypArea.setText("提示：\n请先选择操作类型，\n然后按照提示操作！");
					ypNo.setEditable(true);
					ypNo.setText(null);
					ypName.setEditable(true);
					ypName.setText(null);
					ypEffect.setEditable(true);
					ypEffect.setText(null);
					ypPrice.setEditable(true);
					ypPrice.setText(null);
					ypDate.setEditable(true);
					ypDate.setText(null);
					ypType.setEditable(true);
					ypType.setText(null);
					gysNo.setEditable(true);
					gysNo.setText(null);
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
