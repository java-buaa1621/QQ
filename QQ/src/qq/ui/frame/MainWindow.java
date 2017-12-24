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
	// ��������С��WINDOW_HEIGHT, ��Ϊ���ڶ��и߶�
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
		intitSearchButton(BUTTON_WIDTH, BUTTON_HEIGHT);
	}	
	
	protected void intitSearchButton(int BUTTON_WIDTH, int BUTTON_HEIGHT) {
		ImageIcon icon = ResourceManagement.getScaledIcon(
				"search_icon.png", BUTTON_WIDTH, BUTTON_HEIGHT);
		JButton searchButton = new JButton(icon);
		searchButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		searchButton.setBorder(null);
		//searchButton.setContentAreaFilled(false); // ����͸��
		
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddFriendWindow.startUp();
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
		Rectangle pos = new Rectangle(0, 0, WINDOW_WIDTH, headPaneHeight);
		headPane = new UserInfoPanel(new UserInfo(
				331079072, "zzx", "��", 20, "this is a motto", 1), pos);
		contentPane.add(headPane);
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
		UserInfo info1 = new UserInfo(123, "zhangsan", "��", 1, "5201314", 2);
		UserInfo info2 = new UserInfo(2, "wangsaer", "Ů", 5, "hhhhhh", 16);
		FriTreeNode zhangsan = new FriTreeNode(info1);
		FriTreeNode wangsaer = new FriTreeNode(info2);
		node1.addChild(zhangsan);
		node2.addChild(wangsaer);

		
		// ������������jMode, renderer�����
		DefaultTreeModel jMode = new DefaultTreeModel(root);
		final JTree tree = new JTree(jMode);
		tree.setCellRenderer(new FriTreeCellRenderer());
		tree.setRootVisible(false); // ����ʾ���ڵ�
		tree.putClientProperty("JTree.lineStyle", "None"); // ����ʾ��������
		tree.setToggleClickCount(1); //����չ���ڵ�֮ǰ����굥����Ϊ1
		
		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// ˫���¼�
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
		// ����JScrollPane
		JScrollPane treeView = new JScrollPane(tree);
		mainPane.add(treeView, BorderLayout.CENTER);
	}
	
}
