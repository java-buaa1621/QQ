package qq.ui.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import javax.sound.midi.MidiDevice.Info;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import java.awt.FlowLayout;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;

import qq.db.info.FriendInfo;
import qq.db.info.UserInfo;
import qq.db.util.DBConnector;
import qq.db.util.DBManager;
import qq.db.util.FriendListDAO;
import qq.db.util.LoginInfoDAO;
import qq.db.util.UserInfoDAO;
import qq.socket.Client;
import qq.socket.Server;
import qq.ui.component.UserInfoPanel;
import qq.ui.componentFactory.AdapterFactory;
import qq.ui.componentFactory.PanelFactory;
import qq.ui.friend.FriTreeCellRenderer;
import qq.ui.friend.FriTreeNode;
import qq.util.Constant;
import qq.util.Func;
import qq.util.ResourceManagement;

import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JList;

import com.mysql.jdbc.Constants;

public class MainWindow extends JFrame {

	final int WINDOW_WIDTH = 283;
	final int WINDOW_HEIGHT = 650;
	// 加起来略小于WINDOW_HEIGHT, 因为窗口顶有高度
	final int headPaneHeight =  (int)(WINDOW_HEIGHT * 0.21);
	final int switchPaneHeight =  (int)(WINDOW_HEIGHT * 0.05);
	final int mainPaneHeight =  (int)(WINDOW_HEIGHT * 0.65);
	final int funcPaneHeight =  (int)(WINDOW_HEIGHT * 0.08);
	// 主要面板
	private JPanel contentPane = null;
	private JPanel switchPane = null;
	private JPanel mainPane = null;
	// 头面板
	private JPanel headContentPane = null;
	private UserInfoPanel headPane = null;
	private JTextField searchField = null;
	// 方法面板
	private JPanel funcPane;
	// 辅助监听
	private MainWindow thisWindow;
	// 数据信息
	private UserInfo caller;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * @param ID 登陆成功用户的ID
	 * @throws SQLException 
	 */
	public static void startUp(int ID) throws SQLException {
		MainWindow mainWindow = new MainWindow(UserInfoDAO.getInfoByID(ID));
		mainWindow.setVisible(true);
	}
	
	private MainWindow(final UserInfo info) {
		this.caller = info;
		this.thisWindow = this;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 关闭窗口程序结束
		setResizable(false);
		setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
		setTitle("QQ电脑版");
		setIconImage(ResourceManagement.getImage("qq_icon.png"));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		this.initComponents(info);
	}
	
	protected void initComponents(final UserInfo info) {
		if(info == null)
				throw new IllegalArgumentException();
			
		initHeadPane(info);
		initSwitchPane();
		loadMainPane();
		initFuncPane();
	}
	
	protected void initHeadPane(final UserInfo info) {
		// 设置背景
		Rectangle pos = new Rectangle(0, 0, WINDOW_WIDTH, headPaneHeight);
		headContentPane = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				Image bgImage = ResourceManagement.getImage(
						"background.jpg");
				g.drawImage(bgImage, 0, 0, this.getWidth(), this.getHeight(), this);
			};
		};
		headContentPane.setBounds(pos);
		headContentPane.setLayout(new BorderLayout());
		int downBias = 30;
		// userInfoPane
		headPane = new UserInfoPanel(info);
		headPane.setPreferredSize(UserInfoPanel.minPanelSize);
		headPane.setOpaque(false); // 设置透明
		headContentPane.add(Box.createVerticalStrut(downBias), BorderLayout.NORTH); // 向下偏移
		headContentPane.add(headPane, BorderLayout.WEST); // 位于左侧
		// searchField
		JLabel iconLabel = new JLabel(
				ResourceManagement.getScaledIcon("search_icon.png", 20, 20));
		searchField = new JTextField();
		searchField.setBorder(BorderFactory.createEmptyBorder());
		String defaultText = "  搜索:  联系人";
		searchField.addFocusListener(
				AdapterFactory.createTextFieldFocusAdapter(searchField, defaultText));
		searchField.setOpaque(false); // 设置透明
		
		Box searchBox = Box.createHorizontalBox();
		searchBox.add(iconLabel);		
		searchBox.add(searchField);
		searchBox.setBorder(BorderFactory.createEtchedBorder()); // 边框
		headContentPane.add(searchBox, BorderLayout.SOUTH); // 位于下侧
		
		contentPane.add(headContentPane);
	}
	
	protected void initSwitchPane() {
		final int BUTTON_NUMBER = 4;
		final Dimension SIZE = new Dimension(60, 33); // 增大点击面积
		final int gapX = 8;
		final int gapY = 0;
		
		switchPane = new JPanel();
		switchPane.setBackground(Color.WHITE);
		switchPane.setBounds(0, headPaneHeight, WINDOW_WIDTH, switchPaneHeight);
		switchPane.setLayout(new FlowLayout(FlowLayout.LEFT, gapX, gapY)); // Flow布局
		this.contentPane.add(switchPane);
		
		// 好友
		JLabel friendLabel = initSwitchLabel(
				"friendIcon.png", "friendIconEntered.png", "联系人", SIZE);
		switchPane.add(friendLabel);
		// 群组
		JLabel groupLabel = initSwitchLabel(
				"groupIcon.png", "groupIconEntered.png", "群/讨论组", SIZE);
		switchPane.add(groupLabel);
		// 对话
		JLabel dialogLabel = initSwitchLabel(
				"dialogIcon.png", "dialogIconEntered.png", "会话", SIZE);
		switchPane.add(dialogLabel);
		// 空间
		JLabel spaceLabel = initSwitchLabel(
				"spaceIcon.png", "spaceIconEntered.png", "QQ空间", SIZE);
		switchPane.add(spaceLabel);
	}
	
	private JLabel initSwitchLabel(String exitIcon, String enterIcon, String tipText, Dimension size) {
		final ImageIcon exitImg = 
				ResourceManagement.getImageIcon(exitIcon);
		final ImageIcon enterImg = 
				ResourceManagement.getImageIcon(enterIcon);
		final JLabel label = new JLabel(exitImg);
		label.setPreferredSize(size);
		label.setToolTipText(tipText);
		
		label.addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent e) {
				label.setIcon(exitImg);
			}
			public void mouseEntered(MouseEvent e) {
				label.setIcon(enterImg);				
			}
		});
		return label;
	}
	
	/**
	 * 不添加，只更新MainPane，以及JTree相关组件
	 * @param ID
	 */
	private void refreshMainPane(int ID) {
		mainPane = new JPanel();
		mainPane.setBounds(0, headPaneHeight + switchPaneHeight,
				WINDOW_WIDTH, mainPaneHeight);
		mainPane.setLayout(new BorderLayout(0, 0)); // 相对布局，jScrollPane在中间
		// 初始化好友树
		FriTreeNode root = refreshFriTreeNode(ID);
		refreshFriTree(root);
	}
	
	/**
	 * 根据根节点组建JTree,加入一些配置(如加入JScrollPane),最后放入mainPane
	 * @param root 根节点
	 */
	private void refreshFriTree(final FriTreeNode root) {
		// 创建树并加入jMode, renderer等组件
		DefaultTreeModel jMode = new DefaultTreeModel(root);
		final JTree tree = new JTree(jMode);
		tree.setCellRenderer(new FriTreeCellRenderer());
		tree.setRootVisible(false); // 不显示根节点
		tree.putClientProperty("JTree.lineStyle", "None"); // 不显示纵向连线
		tree.setToggleClickCount(1); //设置展开节点之前的鼠标单击数为1
		tree.addMouseListener(new MouseAdapter() {
			FriTreeNode lastNode = null;
			public void mouseClicked(MouseEvent e) {
				// 双击事件
				if(e.getClickCount() == 2) {
					if(lastNode != null) {
						lastNode.setOnClickPaint(false);
					}
					FriTreeNode node = (FriTreeNode)tree.getLastSelectedPathComponent();
					if(node.isThirdLayer()) {
						node.setOnClickPaint(true);
						tree.repaint();
						UserInfo beCaller = node.getUserInfo();
						invokeChat(caller, beCaller); // 发起聊天
						lastNode = node;
					}
				}
			}
		});
		mainPane.add(tree);
		
		// 加入JScrollPane
		JScrollPane treeView = new JScrollPane(tree);
		mainPane.add(treeView, BorderLayout.CENTER);
	}

	protected void initFuncPane() {
		final int gapX = 5;
		final int gapY = 5;
		final int BUTTON_WIDTH = 25;
		final int BUTTON_HEIGHT = 25;
				
		funcPane = new JPanel();
		funcPane.setBounds(0, headPaneHeight + switchPaneHeight + mainPaneHeight, 
				WINDOW_WIDTH, funcPaneHeight);
		funcPane.setLayout(new FlowLayout(FlowLayout.LEFT, gapX, gapY)); // Flow布局
		this.contentPane.add(funcPane);
		intitSearchButton(BUTTON_WIDTH, BUTTON_HEIGHT);
	}	
	
	protected void intitSearchButton(int BUTTON_WIDTH, int BUTTON_HEIGHT) {
		ImageIcon icon = ResourceManagement.getScaledIcon(
				"search_icon.png", BUTTON_WIDTH, BUTTON_HEIGHT);
		JButton searchButton = new JButton(icon);
		searchButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		searchButton.setBorder(null);
		searchButton.setToolTipText("查找好友");
		//searchButton.setContentAreaFilled(false); // 设置透明
		
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddFriendWindow.startUp(caller, thisWindow);
			}
		});
		funcPane.add(searchButton);
	}
	
	// ------------------------- 逻辑部分 ------------------------- //
	
	/** 初始化加载mainPane */
	private void loadMainPane() {
		refreshMainPane(caller.getID()); // 添加好友后刷新
		contentPane.add(mainPane);
	}
	
	/** 刷新并加载mainPane */
	public void reloadMainPane() {
		ResourceManagement.debug("reload");
		if(mainPane == null)
			throw new IllegalArgumentException();
		contentPane.remove(mainPane);
		refreshMainPane(caller.getID()); // 添加好友后刷新
		contentPane.add(mainPane);
		contentPane.updateUI(); // 重新绘制
	}
	
	/**
	 * 根据用户ID,在数据库查询创建树,返回根节点
	 * @param ID 用户ID
	 * @return 成功 根节点   失败 null
	 * @throws SQLException 
	 */
	private FriTreeNode refreshFriTreeNode(int userID) {
		FriTreeNode root = null;
		try {
			// 创建树的根节点
			root = new FriTreeNode();
			// 创建树的分组节点
			FriTreeNode groupNode = new FriTreeNode("我的好友");
			root.addChild(groupNode);	
			// 创建树的叶节点
			ArrayList<FriTreeNode> friNodes = new ArrayList<FriTreeNode>();
			ArrayList<UserInfo> friUserInfos = DBManager.getFriUserInfos(userID);
			for(UserInfo info : friUserInfos) {
				FriTreeNode friNode = new FriTreeNode(info); 
				ResourceManagement.debug("好友ID: " + info.getID());
				friNodes.add(friNode);
			}
			groupNode.addChild(friNodes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return root;
	}
	
	private void invokeChat(final UserInfo caller, final UserInfo beCaller) {
		ResourceManagement.debug(caller.getID());
		ResourceManagement.debug(beCaller.getID());
		
		Thread server = new Thread() {
			public void run() {
				new Server(caller, beCaller);
			}
		};
		Thread client = new Thread() {
			public void run() {
				new Client(beCaller, caller);
			}
		};
		server.start();
		client.start();
	}
	
	// ------------------------- 重载方法用于单元测试 ------------------------- //
	
	private MainWindow() {
		caller = new UserInfo(
				331079072, "zzx", "男", 20, "this is a motto", 1);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
		setTitle("QQ电脑版");
		setIconImage(ResourceManagement.getImage("qq_icon.png"));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.initComponents();
	}
	private void initComponents() {
		this.initHeadPane();
		this.initSwitchPane();
		this.refreshMainPane(); 
		this.initFuncPane();
	}
	
	protected void initHeadPane() {
		Rectangle pos = new Rectangle(0, 0, WINDOW_WIDTH, headPaneHeight);
		headPane = new UserInfoPanel(new UserInfo(
				331079072, "zzx", "男", 20, "this is a motto", 1), pos);
		contentPane.add(headPane);
	}
	
	protected void refreshMainPane() {
		mainPane = new JPanel();
		mainPane.setBounds(0, headPaneHeight + switchPaneHeight,
				WINDOW_WIDTH, mainPaneHeight);
		mainPane.setLayout(new BorderLayout(0, 0)); // 相对布局，jScrollPane在中间
		this.contentPane.add(mainPane);
		
		// 创建树的节点及叶子
		FriTreeNode root = new FriTreeNode();
		FriTreeNode node1 = new FriTreeNode("我的好友");
		FriTreeNode node2 = new FriTreeNode("家人");
		FriTreeNode node3 = new FriTreeNode("陌生人");
		root.addChild(node1);	
		root.addChild(node2);	
		root.addChild(node3);	
		UserInfo info1 = new UserInfo(123, "zhangsan", "男", 1, "5201314", 2);
		UserInfo info2 = new UserInfo(2, "wangsaer", "女", 5, "hhhhhh", 16);
		FriTreeNode zhangsan = new FriTreeNode(info1);
		FriTreeNode wangsaer = new FriTreeNode(info2);
		node1.addChild(zhangsan);
		node2.addChild(wangsaer);

		
		// 创建树并加入jMode, renderer等组件
		DefaultTreeModel jMode = new DefaultTreeModel(root);
		final JTree tree = new JTree(jMode);
		tree.setCellRenderer(new FriTreeCellRenderer());
		tree.setRootVisible(false); // 不显示根节点
		tree.putClientProperty("JTree.lineStyle", "None"); // 不显示纵向连线
		tree.setToggleClickCount(1); //设置展开节点之前的鼠标单击数为1
		
		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// 双击事件
				if(e.getClickCount() == 2) {
					FriTreeNode node = (FriTreeNode)tree.getLastSelectedPathComponent();
					if(node.isThirdLayer()) {
						UserInfo beCaller = node.getUserInfo();
						invokeChat(caller, beCaller);
						new Server(caller, beCaller);
						new Client(caller, beCaller);
					}
				}
			}
		});
		/*
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent evt) {
				FriTreeNode node = (FriTreeNode)evt.getPath().getLastPathComponent();
				if(node.isThirdLayer()) {
					node.getPanel().setBackground(Color.WHITE);
				}
			}
			
		});
		*/
		
		mainPane.add(tree);
		// 加入JScrollPane
		JScrollPane treeView = new JScrollPane(tree);
		mainPane.add(treeView, BorderLayout.CENTER);
	}
	
}
