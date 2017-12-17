package qq.ui.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.TextArea;
import java.awt.TextField;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;

import qq.db.info.UserInfo;
import qq.db.util.UserInfoDAO;
import qq.exception.AlertException;
import qq.ui.component.*;
import qq.ui.componentFactory.AdapterFactory;
import qq.ui.componentFactory.ButtonFactory;
import qq.ui.componentFactory.PanelFactory;
import qq.ui.componentFunc.ButtonFunc;
import qq.ui.componentFunc.ComponentFunc;
import qq.util.Constant;
import qq.util.Func;
import qq.util.ResourceManagement;
import qq.ui.component.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.ContentHandlerFactory;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JRadioButton;
import javax.swing.JLabel;

public class AddFriendWindow extends JFrame {

	private JPanel contentPane;
	private IDTextField IDField;
	private FlowComponentScrollPanel<JPanel> showPane;
	private JButton searchButton;
	private JTextField textField;
	private ArrayList<JRadioButton> rButtons;
	private ButtonGroup bGroup;
	/** 本类的点击searchButton交互Action */
	private MyAction searchAction = new MyAction() {
		@Override
		public void executeAction() {
			dealClick();			
		}
	};
	
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
		AddFriendWindow frame = new AddFriendWindow();
		frame.setVisible(true);
	}
	
	/**
	 * Create the frame.
	 */
	public AddFriendWindow() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("查找用户");
		setBounds(100, 100, 450, 300);
		setIconImage(ResourceManagement.getImage("qq_icon.png"));
		
		// 默认contentPane
		contentPane = PanelFactory.createDafaultContentPanel();
		setContentPane(contentPane);
		
		initRadioButton();
		initIDField();
		initShowPane();
		initSearchButton();
	}
	
	public void initRadioButton() {
		JLabel label = new JLabel(" 查找方式");
		label.setBounds(31, 10, 69, 15);
		contentPane.add(label);
		
		// 初始化bGroup
		bGroup = new ButtonGroup();
		// rButtons文字
		String[] contents = {
			"ID索引",
			"昵称索引"
		};
		// rButtons位置
		Rectangle[] positions = {
			new Rectangle(129, 6, 121, 23),
			new Rectangle(286, 6, 121, 23)
		};
		rButtons = ButtonFactory.createJRadioButtons(
				2, contents, positions, contentPane, bGroup, false);
		ButtonFunc.setIsSelected(rButtons, 0);
	}
	
	public void initIDField() {
		IDField = new IDTextField();
		IDField.setBounds(31, 39, 376, 21);
		IDField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		IDField.addFocusListener(
				AdapterFactory.createTextFieldFocusAdapter(IDField, " 请输入查找信息"));
		// 回车发送信息
		IDField.addKeyListener(
				AdapterFactory.createKeyTypedAdapter(KeyEvent.VK_ENTER, searchAction));
		
		contentPane.add(IDField);
	}
	
	public void initShowPane() {
		Rectangle panelPos = new Rectangle(31, 85, 376, 122);
		ArrayList<JPanel> panels = new ArrayList<JPanel>();
		Dimension panelSize = UserInfoPanel.minPanelSize;
		
		showPane = PanelFactory.createFlowComponentScrollPane(
				panelPos, panels, 3, panelSize);
		showPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		contentPane.add(showPane);
	}
	
	public void initSearchButton() {
		searchButton = new JButton("查找");
		
		// 回应鼠标选择
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				dealClick();
			}
		});
		// 添加快捷键
		ComponentFunc.setShortCutKey(searchButton, KeyEvent.VK_ENTER, searchAction);
		
		searchButton.setBounds(314, 228, 93, 23);
		contentPane.add(searchButton);
	}
	
	
	// -------------------------- 逻辑部分 -------------------------- //
	
	private void dealClick() {
		try {
			int kind = ButtonFunc.getSelectedIndex(rButtons);
			switch (kind) {
			case 0:
				int ID = IDField.getID();
				updateShowPane(UserInfoDAO.getInfoByID(ID));
				break;
			case 1:
				String name = IDField.getText();
				updateShowPane(UserInfoDAO.getInfosByName(name));
				break;
			default:
				break;
			}
		} catch (SQLException | AlertException e1) {
			AlertWindow.startUp("查找错误！可能的原因是输入信息与检索方式不匹配");
		}
	}
	
	/**
	 * 
	 * @param userInfo null表示只清除全部，不新增
	 */
	private void updateShowPane(UserInfo userInfo) {
		ArrayList<UserInfo> userInfos;
		if(userInfo != null) {
			userInfos = Func.toArrayList(userInfo);
		} else {
			userInfos = new ArrayList<UserInfo>();
		}
		updateShowPane(userInfos);
	}
	
	/**
	 * 
	 * @param userInfos 不能为空
	 */
	private void updateShowPane(ArrayList<UserInfo> userInfos) {
		if(userInfos == null)
			throw new IllegalArgumentException();
		
		showPane.removeAll();
		for(UserInfo info : userInfos){
			UserInfoPanel infoPane = new UserInfoPanel(info);
			infoPane.setBorder(BorderFactory.createLineBorder(Color.black));
			showPane.addComp(infoPane);
		}
		showPane.updateUI();
	}
}
