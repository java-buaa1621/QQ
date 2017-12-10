package qq.ui.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JLabel;

import qq.db.info.LoginInfo;
import qq.db.util.DBConnector;
import qq.db.util.DBManager;
import qq.db.util.LoginInfoDAO;
import qq.db.util.UserInfoDAO;
import qq.exception.AlertException;
import qq.ui.component.*;
import qq.ui.componentFactory.AdapterFactory;
import qq.ui.componentFactory.PanelFactory;
import qq.util.ResourceManagement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class LoginWindow extends JFrame {

	private JPanel contentPane;
	private IDTextField IDField;
	private PasswordField passwordField;
	private JButton loginButton;
	private JLabel registerLabel;
	private JLabel findPasswordLabel;

	/**
	 * Launch the application.
	 */
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

	public static void startUp() {
		LoginWindow loginWindow = new LoginWindow();
		loginWindow.setVisible(true);
	}
	
	/**
	 * Create the frame.
	 */
	private LoginWindow() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("QQ电脑版");
		setBounds(100, 100, 450, 300);
		setIconImage(ResourceManagement.getImage("qq_icon.png"));
		
		// 默认contentPane
		contentPane = PanelFactory.createDafaultContentPanel();
		setContentPane(contentPane);
		
		// 初始化组件
		initUserNameField();
		initPasswordField();
		initLoginButton();
		initRegisterLabel();
		initFindPassWordLabel();
	}
	
	protected void initUserNameField() {
		IDField = new IDTextField();
		IDField.setBounds(148, 117, 151, 27);
		IDField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		IDField.addFocusListener(AdapterFactory.createTextFieldFocusAdapter(IDField, " QQ账号"));
		contentPane.add(IDField);
	}
	
	protected void initPasswordField() {
		passwordField = new PasswordField();
		passwordField.setBounds(148, 144, 151, 27);
		passwordField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		passwordField.addFocusListener(AdapterFactory.createTextFieldFocusAdapter(passwordField, " 密码"));
		getContentPane().add(passwordField);
	}
	
	protected void initLoginButton() {
		loginButton = new JButton("登录");
		loginButton.setBounds(183, 199, 93, 23);
		loginButton.setBackground(Color.WHITE);
		loginButton.setForeground(Color.BLACK);

		// 重载方法实现交互
		loginButton.addMouseListener(
				AdapterFactory.createMouseEnterAndExitAdapter(loginButton)); 
		
		final JFrame loginWindow = this; // loginWindow变量用于响应鼠标关闭窗口
		loginButton.addMouseListener(new MouseAdapter() {
			/**
			 * 处理登录时期遇到的一切因用户造成的AlertException并转换为窗口提示
			 */
			public void mouseClicked(MouseEvent e) {
				try {
					LoginInfo loginInfo = buildLoginInfo();
					if (DBManager.checkLogin(loginInfo) == true) {
						loginWindow.dispose();
						MainWindow.startUp(loginInfo.getID());
					} else {
						// 密码不匹配登陆失败
						throw new AlertException("输入密码错误!");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (AlertException e1) {
					// 以窗口形式向用户报错
					AlertWindow.startUp(e1.getMessage());
				}
			}
		});
		contentPane.add(loginButton);		
	}
	
	protected void initRegisterLabel() {
		registerLabel = new JLabel("注册账户");
		registerLabel.setBounds(364, 120, 54, 15);
		registerLabel.setBackground(Color.WHITE);
		registerLabel.setForeground(Color.BLACK);
		
		// 重载方法添加监听器
		registerLabel.addMouseListener(AdapterFactory.createMouseEnterAndExitAdapter(registerLabel));
		registerLabel.addMouseListener(new MouseAdapter() {
			// 点击弹出登录界面
			@Override
			public void mouseClicked(MouseEvent e) {
				RegisterWindow.startUp();
			}
		});
		contentPane.add(registerLabel);
	}
	
	protected void initFindPassWordLabel() {
		findPasswordLabel = new JLabel("忘记密码");
		findPasswordLabel.setBounds(364, 150, 54, 15);
		findPasswordLabel.setBackground(Color.WHITE);
		findPasswordLabel.setForeground(Color.BLACK);
		
		// 重载方法添加监听器
		findPasswordLabel.addMouseListener(
				AdapterFactory.createMouseEnterAndExitAdapter(findPasswordLabel));
		contentPane.add(findPasswordLabel);
	}
	
	
	// ------------------------- 逻辑部分 ------------------------- //
	
	private LoginInfo buildLoginInfo() throws AlertException {
		int ID = IDField.getID();
		String password = passwordField.getPasswordString();
		LoginInfo loginInfo = new LoginInfo(ID, password);
		return loginInfo;
	}
	
}
