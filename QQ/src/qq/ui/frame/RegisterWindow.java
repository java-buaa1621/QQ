package qq.ui.frame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;

import qq.db.info.*;
import qq.db.util.*;
import qq.exception.*;
import qq.ui.component.*;
import qq.ui.componentFactory.AdapterFactory;
import qq.ui.componentFactory.PanelFactory;
import qq.util.*;

public class RegisterWindow extends JFrame {
	
	// 视图部分
	private JPanel contentPane;
	private IDTextField IDField; // 必须是正整形
	private JTextField nameField;
	private JComboBox<Integer> ageComboBox; // 年龄0-199;
	private ButtonGroup sexGroup; 
	private JRadioButton manButton;
	private JRadioButton womanButton;
	private JTextArea mottoArea;
	private PasswordField passwordField;
	private JButton selectHeadButton;
	private JButton registerButton;
	// 逻辑部分
	private int headIconIndex; // 初始化默认设置1号头像

	// ------------------------- 视图部分 ------------------------- //
	
	public static void startUp() {
		RegisterWindow registerWindow = new RegisterWindow();
		registerWindow.setVisible(true);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					startUp();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private RegisterWindow() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("注册用户");
		setBounds(100, 100, 409, 380);
		setIconImage(ResourceManagement.getImage("qq_icon.png"));
		
		// 默认contentPane
		contentPane = PanelFactory.createDafaultContentPanel();
		setContentPane(contentPane);
		
		initLabels();
		initIDField();
		initNameField();
		initSexButtons();
		initAgeComboBox();
		initMottoArea();
		initPasswordField();
		initSelectHeadButton();
		initRegisterButton();
		this.setHeadIconIndex(1); // 设置默认1号头像
	}
	
	private void initLabels() {
		JLabel label0 = new JLabel("用户ID");
		label0.setBounds(21, 11, 75, 29);
		contentPane.add(label0);
		
		JLabel label1 = new JLabel("用户昵称");
		label1.setBounds(21, 50, 75, 29);
		contentPane.add(label1);
		
		JLabel label2 = new JLabel("性别");
		label2.setBounds(21, 89, 75, 29);
		contentPane.add(label2);
		
		JLabel label3 = new JLabel("年龄");
		label3.setBounds(21, 128, 75, 29);
		contentPane.add(label3);
		
		JLabel label4 = new JLabel("个性签名");
		label4.setBounds(21, 167, 75, 29);
		contentPane.add(label4);
		
		JLabel label5 = new JLabel("密码");
		label5.setBounds(21, 283, 75, 29);
		contentPane.add(label5);
	}
	
	private void initIDField() {
		IDField = new IDTextField();
		IDField.setColumns(10);
		IDField.setBounds(106, 11, 263, 29);
		IDField.addFocusListener(AdapterFactory.createTextFieldFocusAdapter(IDField, " 请输入账号"));
		contentPane.add(IDField);
	}
	
	private void initNameField (){
		nameField = new JTextField();
		nameField.setBounds(106, 50, 263, 29);
		nameField.setColumns(10);
		nameField.addFocusListener(
				AdapterFactory.createTextFieldFocusAdapter(nameField, " 请输入昵称"));
		contentPane.add(nameField);
	}
	
	private void initAgeComboBox() {
		ageComboBox = new JComboBox<Integer>();
		ageComboBox.setBounds(106, 132, 64, 21);
		for(int i = 0; i < 200; i++) 
			ageComboBox.addItem(i);
		contentPane.add(ageComboBox);
	}
	
	private void initSexButtons() {
		manButton = new JRadioButton("男", true); // 男默认选择
		manButton.setBounds(102, 92, 121, 23);
		manButton.setContentAreaFilled(false); // 设置透明
		contentPane.add(manButton);
		
		womanButton = new JRadioButton("女");
		womanButton.setBounds(238, 92, 121, 23);
		womanButton.setContentAreaFilled(false); // 设置透明
		contentPane.add(womanButton);
		
		// 添加进ButtonGroup, ButtonGroup只负责逻辑，不负责显示，不用添加到contentPane
		sexGroup = new ButtonGroup();
		sexGroup.add(manButton);
		sexGroup.add(womanButton);
	}
	
	private void initMottoArea() {
		// 输入文本区域
		mottoArea = new JTextArea();
		mottoArea.setLineWrap(true);
		mottoArea.setWrapStyleWord(true);
		// 滚动面板
        JScrollPane textScrollPane = new JScrollPane(mottoArea);
        textScrollPane.setBounds(106, 170, 263, 91);
        textScrollPane.setHorizontalScrollBarPolicy(
        		JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // 永不出现横向滚动条
		contentPane.add(textScrollPane);
	}
	
	private void initPasswordField() {
		passwordField = new PasswordField();
		passwordField.setBounds(106, 283, 263, 29);
		passwordField.addFocusListener(
				AdapterFactory.createTextFieldFocusAdapter(passwordField, " 请输入密码"));
		contentPane.add(passwordField);
	}
	
	/**
	 * 切换到选择头像界面 
	 */
	private void initSelectHeadButton() {
		selectHeadButton = new JButton("选择头像");
		final RegisterWindow registerWindow = this;
		selectHeadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HeadIconWindow.startUp(registerWindow);
			}
		});
		selectHeadButton.setBounds(130, 318, 93, 23);
		contentPane.add(selectHeadButton);
	}
	
	private void initRegisterButton() {
		registerButton = new JButton("注册");
		registerButton.setBounds(258, 318, 93, 23);
		final RegisterWindow registerWindow = this; // 为了在添加账户成功后关闭窗口产生的句柄
		registerButton.addActionListener(new ActionListener() {
			// 处理注册时期遇到的一切因用户造成的AlertException并转换为窗口提示
			public void actionPerformed(ActionEvent e) {
				UserInfo userInfo = null;
				LoginInfo loginInfo = null;
				try {
					userInfo = buildUserInfo();
					loginInfo = buildLoginInfo();
					DBManager.updateUserRelatedDatabase(userInfo, loginInfo, userInfo.getID());
				} catch (AlertException e1) {
					AlertWindow.startUp(e1.getMessage());
					return; // 防止关闭窗口
				}
				// 关闭窗口
				registerWindow.dispose();
			}
		});
		contentPane.add(registerButton);
		
	}

	
	// ------------------------- 逻辑部分 ------------------------- //
	
	private UserInfo buildUserInfo() throws AlertException {
			int ID = IDField.getID();
		
			String name = nameField.getText();
			if (name.length() > UserInfo.MAX_LEN)
				throw new AlertException("昵称超过100字");
			else if (name.length() == 0) 
				throw new AlertException("昵称不能为空");
			
			int age = (int) ageComboBox.getSelectedItem();
			
			String sex = getSelectedSex();
			
			String motto = mottoArea.getText();
			if (motto.length() > UserInfo.MAX_LEN)
				throw new AlertException("个性签名超过100字");
			else if (motto.length() == 0) 
				throw new AlertException("个性签名不能为空");
			
			return new UserInfo(ID, name, sex, age, motto, headIconIndex);
	}
	
	private LoginInfo buildLoginInfo() throws AlertException {
		int ID = IDField.getID();
		String password = passwordField.getPasswordString();
		return new LoginInfo(ID, password);
	}
	
	/**
	 * @return "男"或"女"，
	 */
	private String getSelectedSex() {
		if (manButton.isSelected())
			return "男";
		else 
			return "女";
	}
	
	/**
	 * 设置headIconIndex的值
	 * @param headIconIndex 头像标号
	 */
	protected void setHeadIconIndex(int headIconIndex) {
		if(!Func.isValid(headIconIndex, Constant.START_HEAD_ICON, Constant.END_HEAD_ICON))
			throw new IllegalArgumentException("headIconIndex出范围");
		this.headIconIndex = headIconIndex;
	}
	
}
