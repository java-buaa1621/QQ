package qq.ui.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.ScrollPaneLayout;
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
import qq.ui.component.UserInfoPanel;
import qq.ui.friend.FriTreeCellRenderer;
import qq.ui.friend.FriTreeNode;
import qq.util.Constant;
import qq.util.ResourceManagement;

import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JList;

import com.mysql.jdbc.Constants;

public class MainWindow extends JFrame {

	final int WINDOW_WIDTH = 283;
	final int WINDOW_HEIGHT = 617;
	// 加起来略小于WINDOW_HEIGHT, 因为窗口顶有高度
	final int headPaneHeight =  (int)(WINDOW_HEIGHT * 0.15);
	final int switchPaneHeight =  (int)(WINDOW_HEIGHT * 0.07);
	final int mainPaneHeight =  (int)(WINDOW_HEIGHT * 0.675);
	final int funcPaneHeight =  (int)(WINDOW_HEIGHT * 0.075);
	
	private JPanel contentPane;
	private UserInfoPanel headPane;
	private JPanel switchPane;
	private JPanel mainPane;
	private JPanel funcPane;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// ID 为0 必定查不到，走单元测试路线
					startUp(0);
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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		if(info == null) {
			// 此处if里面是为了单元化测试
			// TODO: throw new IllegalArgumentException();
			if(Constant.DEBUG){
				this.initHeadPane();
				this.initSwitchPane();
				this.initMainPane();
				this.initFuncPane();
			} else {
				throw new IllegalArgumentException();
			}
		} else {
			this.initHeadPane(info);
			this.initSwitchPane();
			this.initMainPane(info.getID());
			this.initFuncPane();
		}
	}
	
	protected void initHeadPane(final UserInfo info) {
		Rectangle pos = new Rectangle(0, 0, WINDOW_WIDTH, headPaneHeight);
		headPane = new UserInfoPanel(info, pos);
		contentPane.add(headPane);
	}
	
	protected void initSwitchPane() {
		final int BUTTON_NUMBER = 4;
		final int BUTTON_WIDTH = 50;
		final int BUTTON_HEIGHT = 35; 
		final int gapX = 15;
		final int gapY = 5;
		
		switchPane = new JPanel();
		switchPane.setBackground(Color.LIGHT_GRAY);
		switchPane.setBounds(0, headPaneHeight, WINDOW_WIDTH, switchPaneHeight);
		switchPane.setLayout(new FlowLayout(FlowLayout.LEFT, gapX, gapY)); // Flow布局
		this.contentPane.add(switchPane);
		
		// TODO add actionListener
		JButton switch1 = new JButton("1");
		switch1.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		switchPane.add(switch1);
		
		JButton switch2 = new JButton("2");
		switch2.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		switchPane.add(switch2);
		
		JButton switch3 = new JButton("3");
		switch3.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		switchPane.add(switch3);
		
		JButton switch4 = new JButton("4");
		switch4.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		switchPane.add(switch4);
		
	}
	
	private void initMainPane(int ID) {
		mainPane = new JPanel();
		mainPane.setBounds(0, headPaneHeight + switchPaneHeight,
				WINDOW_WIDTH, mainPaneHeight);
		mainPane.setLayout(new BorderLayout(0, 0)); // 相对布局，jScrollPane在中间
		this.contentPane.add(mainPane);
		// 初始化好友树
		FriTreeNode root = initFriTreeNode(ID);
		initFriTree(root);
	}
	
	/**
	 * 根据根节点组建JTree,加入一些配置(如加入JScrollPane),最后放入mainPane
	 * @param root 根节点
	 */
	private void initFriTree(final FriTreeNode root) {
		// 创建树并加入jMode, renderer等组件
		DefaultTreeModel jMode = new DefaultTreeModel(root);
		JTree tree = new JTree(jMode);
		tree.setCellRenderer(new FriTreeCellRenderer());
		tree.setRootVisible(false); // 不显示根节点
		tree.putClientProperty("JTree.lineStyle", "None"); // 不显示纵向连线
		tree.setToggleClickCount(1); //设置展开节点之前的鼠标单击数为1
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
		//searchButton.setContentAreaFilled(false); // 设置透明
		
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddFriendWindow.startUp();
			}
		});
		funcPane.add(searchButton);
	}
	
	// ------------------------- 逻辑部分 ------------------------- //
	
	/**
	 * 根据用户ID,在数据库查询创建树,返回根节点
	 * @param ID 用户ID
	 * @return 成功 根节点   失败 null
	 * @throws SQLException 
	 */
	private FriTreeNode initFriTreeNode(int userID) {
		FriTreeNode root = null;
		try {
			// 创建树的根节点
			root = new FriTreeNode();
			// 创建树的分组节点
			FriTreeNode groupNode = new FriTreeNode("我的好友");
			root.addChild(groupNode);	
			// 创建树的叶节点
			ArrayList<UserInfo> friUserInfos = DBManager.getFriUserInfos(userID);
			ArrayList<FriTreeNode> friNodes = new ArrayList<FriTreeNode>();
			for(UserInfo info : friUserInfos) {
				FriTreeNode friNode = new FriTreeNode(info); 
				friNodes.add(friNode);
			}
			groupNode.addChild(friNodes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return root;
	}
	
	
	
	// ------------------------- 重载方法用于单元测试 ------------------------- //
	
	protected void initHeadPane() {
		Rectangle pos = new Rectangle(0, 0, WINDOW_WIDTH, headPaneHeight);
		headPane = new UserInfoPanel(new UserInfo(
				331079072, "zzx", "男", 20, "this is a motto", 1), pos);
		contentPane.add(headPane);
	}
	
	protected void initMainPane() {
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
						UserInfo info = node.getUserInfo();
						info.getID();
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
