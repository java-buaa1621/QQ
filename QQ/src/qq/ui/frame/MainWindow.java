package qq.ui.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import java.awt.FlowLayout;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import qq.ui.friend.FriTreeCellRenderer;
import qq.ui.friend.FriTreeNode;
import qq.util.ResourceManagement;

import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JList;

public class MainWindow extends JFrame {

	final int WINDOW_WIDTH = 283;
	final int WINDOW_HEIGHT = 617;
	// ��������С��WINDOW_HEIGHT, ��Ϊ���ڶ��и߶�
	final int headPaneHeight =  (int)(WINDOW_HEIGHT * 0.15);
	final int switchPaneHeight =  (int)(WINDOW_HEIGHT * 0.07);
	final int mainPaneHeight =  (int)(WINDOW_HEIGHT * 0.375);
	final int funcPaneHeight =  (int)(WINDOW_HEIGHT * 0.075);
	
	private JPanel contentPane;
	private JPanel headPane;
	private JPanel switchPane;
	private JPanel mainPane;
	private JPanel funcPane;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// ID Ϊ0 �ض��鲻�����ߵ�Ԫ����·��
					startUp(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * @param ID ��½�ɹ��û���ID
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
		setTitle("QQ���԰�");
		setIconImage(ResourceManagement.getImage("qq_icon.png"));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		this.initComponents(info);
	}
	
	protected void initComponents(final UserInfo info) {
		if(info == null) {
			// �˴�if������Ϊ�˵�Ԫ������
			// TODO: throw new IllegalArgumentException();
			if(ResourceManagement.DEBUG){
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
		if (info == null)
			throw new IllegalArgumentException();
		
		ImageIcon headIcon = ResourceManagement.getHeadIcon(info.getHeadIconIndex());
		String userName = info.getName();
		String userMotto = info.getMotto();
		
		headPane = new JPanel();
		headPane.setBounds(0, 0, WINDOW_WIDTH, headPaneHeight);
		this.contentPane.add(headPane);
		headPane.setLayout(null); // ���Բ���
		
		JLabel imageLabel = new JLabel(headIcon);
		imageLabel.setBounds(10, 10, 
				ResourceManagement.HEAD_ICON_LENX, ResourceManagement.HEAD_ICON_LENY);
		headPane.add(imageLabel);
		
		JLabel userNameLabel = new JLabel(userName);
		userNameLabel.setBounds(96, 10, 48, 15);
		headPane.add(userNameLabel);
		
		JLabel userMottoLabel = new JLabel(userMotto);
		userMottoLabel.setBounds(96, 55, 180, 15);
		headPane.add(userMottoLabel);
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
		switchPane.setLayout(new FlowLayout(FlowLayout.LEFT, gapX, gapY)); // Flow����
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
		mainPane.setLayout(new BorderLayout(0, 0)); // ��Բ��֣�jScrollPane���м�
		this.contentPane.add(mainPane);
		// ��ʼ��������
		FriTreeNode root = initFriTreeNode(ID);
		initFriTree(root);
	}
	
	/**
	 * ���ݸ��ڵ��齨JTree,����һЩ����(�����JScrollPane),������mainPane
	 * @param root ���ڵ�
	 */
	private void initFriTree(final FriTreeNode root) {
		// ������������jMode, renderer�����
		DefaultTreeModel jMode = new DefaultTreeModel(root);
		JTree tree = new JTree(jMode);
		tree.setCellRenderer(new FriTreeCellRenderer());
		tree.setRootVisible(false); // ����ʾ���ڵ�
		tree.putClientProperty("JTree.lineStyle", "None"); // ����ʾ��������
		tree.setRowHeight(50);//���ڵ�ĸ߶�
		tree.setToggleClickCount(1); //����չ���ڵ�֮ǰ����굥����Ϊ1
		mainPane.add(tree);
		// ����JScrollPane
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
		funcPane.setLayout(new FlowLayout(FlowLayout.LEFT, gapX, gapY)); // Flow����
		this.contentPane.add(funcPane);
		
		// TODO add actionListener
		ImageIcon icon = ResourceManagement.getScaledIcon(
				"search_icon.png", BUTTON_WIDTH, BUTTON_HEIGHT);
		JButton searchButton = new JButton(icon);
		searchButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		searchButton.setBorder(null);
		//searchButton.setContentAreaFilled(false); // ����͸��
		
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		funcPane.add(searchButton);
	}	
	
	// ------------------------- �߼����� ------------------------- //
	
	/**
	 * �����û�ID,�����ݿ��ѯ������,���ظ��ڵ�
	 * @param ID �û�ID
	 * @return �ɹ� ���ڵ�   ʧ�� null
	 * @throws SQLException 
	 */
	private FriTreeNode initFriTreeNode(int userID) {
		FriTreeNode root = null;
		try {
			// �������ĸ��ڵ�
			root = new FriTreeNode();
			// �������ķ���ڵ�
			FriTreeNode groupNode = new FriTreeNode("�ҵĺ���");
			root.addChild(groupNode);	
			// ��������Ҷ�ڵ�
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
	
	
	
	// ------------------------- ���ط������ڵ�Ԫ���� ------------------------- //
	
	protected void initHeadPane() {
		ImageIcon headIcon = ResourceManagement.getHeadIcon(1);
		String userName = "zzx";
		String userMotto = "this is a long motto";
		
		headPane = new JPanel();
		headPane.setBounds(0, 0, WINDOW_WIDTH, headPaneHeight);
		this.contentPane.add(headPane);
		headPane.setLayout(null); // ���Բ���
		
		JLabel imageLabel = new JLabel();
		imageLabel.setBounds(10, 10, 
				ResourceManagement.HEAD_ICON_LENX, ResourceManagement.HEAD_ICON_LENY);
		imageLabel.setIcon(headIcon);
		headPane.add(imageLabel);
		
		JLabel userNameLabel = new JLabel(userName);
		userNameLabel.setBounds(96, 10, 48, 15);
		headPane.add(userNameLabel);
		
		JLabel userMottoLabel = new JLabel(userMotto);
		userMottoLabel.setBounds(96, 55, 180, 15);
		headPane.add(userMottoLabel);
	}
	
	protected void initMainPane() {
		mainPane = new JPanel();
		mainPane.setBounds(0, headPaneHeight + switchPaneHeight,
				WINDOW_WIDTH, mainPaneHeight);
		mainPane.setLayout(new BorderLayout(0, 0)); // ��Բ��֣�jScrollPane���м�
		this.contentPane.add(mainPane);
		
		// �������Ľڵ㼰Ҷ��
		FriTreeNode root = new FriTreeNode();
		FriTreeNode node1 = new FriTreeNode("�ҵĺ���");
		FriTreeNode node2 = new FriTreeNode("����");
		FriTreeNode node3 = new FriTreeNode("İ����");
		root.addChild(node1);	
		root.addChild(node2);	
		root.addChild(node3);	
		FriTreeNode zhangsan = new FriTreeNode("zhangsan", "5201314", 1);
		FriTreeNode wangsaer = new FriTreeNode("wangsaer", "1524110", 2);
		FriTreeNode mosheng1 = new FriTreeNode("mosheng1", "123", 3);
		FriTreeNode mosheng2 = new FriTreeNode("mosheng2", "234", 4);
		FriTreeNode mosheng3 = new FriTreeNode("mosheng3", "345", 5);
		FriTreeNode mosheng4 = new FriTreeNode("mosheng4", "456", 6);
		FriTreeNode mosheng5 = new FriTreeNode("mosheng5", "567", 7);
		FriTreeNode mosheng6 = new FriTreeNode("mosheng6", "678", 8);
		node1.addChild(zhangsan);
		node2.addChild(wangsaer);
		node3.addChild(mosheng1);
		node3.addChild(mosheng2);
		node3.addChild(mosheng3);
		node3.addChild(mosheng4);
		node3.addChild(mosheng5);
		node3.addChild(mosheng6);
		
		// ������������jMode, renderer�����
		DefaultTreeModel jMode = new DefaultTreeModel(root);
		JTree tree = new JTree(jMode);
		tree.setCellRenderer(new FriTreeCellRenderer());
		tree.setRootVisible(false); // ����ʾ���ڵ�
		tree.putClientProperty("JTree.lineStyle", "None"); // ����ʾ��������
		tree.setRowHeight(50);//���ڵ�ĸ߶�
		tree.setToggleClickCount(1); //����չ���ڵ�֮ǰ����굥����Ϊ1
		mainPane.add(tree);
		// ����JScrollPane
		JScrollPane treeView = new JScrollPane(tree);
		mainPane.add(treeView, BorderLayout.CENTER);
	}
	
}
