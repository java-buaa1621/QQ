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
	// ��������С��WINDOW_HEIGHT, ��Ϊ���ڶ��и߶�
	final int headPaneHeight =  (int)(WINDOW_HEIGHT * 0.21);
	final int switchPaneHeight =  (int)(WINDOW_HEIGHT * 0.05);
	final int mainPaneHeight =  (int)(WINDOW_HEIGHT * 0.65);
	final int funcPaneHeight =  (int)(WINDOW_HEIGHT * 0.08);
	// ��Ҫ���
	private JPanel contentPane = null;
	private JPanel switchPane = null;
	private JPanel mainPane = null;
	// ͷ���
	private JPanel headContentPane = null;
	private UserInfoPanel headPane = null;
	private JTextField searchField = null;
	// �������
	private JPanel funcPane;
	// ��������
	private MainWindow thisWindow;
	// ������Ϣ
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
	 * @param ID ��½�ɹ��û���ID
	 * @throws SQLException 
	 */
	public static void startUp(int ID) throws SQLException {
		MainWindow mainWindow = new MainWindow(UserInfoDAO.getInfoByID(ID));
		mainWindow.setVisible(true);
	}
	
	private MainWindow(final UserInfo info) {
		this.caller = info;
		this.thisWindow = this;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // �رմ��ڳ������
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
		if(info == null)
				throw new IllegalArgumentException();
			
		initHeadPane(info);
		initSwitchPane();
		loadMainPane();
		initFuncPane();
	}
	
	protected void initHeadPane(final UserInfo info) {
		// ���ñ���
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
		headPane.setOpaque(false); // ����͸��
		headContentPane.add(Box.createVerticalStrut(downBias), BorderLayout.NORTH); // ����ƫ��
		headContentPane.add(headPane, BorderLayout.WEST); // λ�����
		// searchField
		JLabel iconLabel = new JLabel(
				ResourceManagement.getScaledIcon("search_icon.png", 20, 20));
		searchField = new JTextField();
		searchField.setBorder(BorderFactory.createEmptyBorder());
		String defaultText = "  ����:  ��ϵ��";
		searchField.addFocusListener(
				AdapterFactory.createTextFieldFocusAdapter(searchField, defaultText));
		searchField.setOpaque(false); // ����͸��
		
		Box searchBox = Box.createHorizontalBox();
		searchBox.add(iconLabel);		
		searchBox.add(searchField);
		searchBox.setBorder(BorderFactory.createEtchedBorder()); // �߿�
		headContentPane.add(searchBox, BorderLayout.SOUTH); // λ���²�
		
		contentPane.add(headContentPane);
	}
	
	protected void initSwitchPane() {
		final int BUTTON_NUMBER = 4;
		final Dimension SIZE = new Dimension(60, 33); // ���������
		final int gapX = 8;
		final int gapY = 0;
		
		switchPane = new JPanel();
		switchPane.setBackground(Color.WHITE);
		switchPane.setBounds(0, headPaneHeight, WINDOW_WIDTH, switchPaneHeight);
		switchPane.setLayout(new FlowLayout(FlowLayout.LEFT, gapX, gapY)); // Flow����
		this.contentPane.add(switchPane);
		
		// ����
		JLabel friendLabel = initSwitchLabel(
				"friendIcon.png", "friendIconEntered.png", "��ϵ��", SIZE);
		switchPane.add(friendLabel);
		// Ⱥ��
		JLabel groupLabel = initSwitchLabel(
				"groupIcon.png", "groupIconEntered.png", "Ⱥ/������", SIZE);
		switchPane.add(groupLabel);
		// �Ի�
		JLabel dialogLabel = initSwitchLabel(
				"dialogIcon.png", "dialogIconEntered.png", "�Ự", SIZE);
		switchPane.add(dialogLabel);
		// �ռ�
		JLabel spaceLabel = initSwitchLabel(
				"spaceIcon.png", "spaceIconEntered.png", "QQ�ռ�", SIZE);
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
	 * ����ӣ�ֻ����MainPane���Լ�JTree������
	 * @param ID
	 */
	private void refreshMainPane(int ID) {
		mainPane = new JPanel();
		mainPane.setBounds(0, headPaneHeight + switchPaneHeight,
				WINDOW_WIDTH, mainPaneHeight);
		mainPane.setLayout(new BorderLayout(0, 0)); // ��Բ��֣�jScrollPane���м�
		// ��ʼ��������
		FriTreeNode root = refreshFriTreeNode(ID);
		refreshFriTree(root);
	}
	
	/**
	 * ���ݸ��ڵ��齨JTree,����һЩ����(�����JScrollPane),������mainPane
	 * @param root ���ڵ�
	 */
	private void refreshFriTree(final FriTreeNode root) {
		// ������������jMode, renderer�����
		DefaultTreeModel jMode = new DefaultTreeModel(root);
		final JTree tree = new JTree(jMode);
		tree.setCellRenderer(new FriTreeCellRenderer());
		tree.setRootVisible(false); // ����ʾ���ڵ�
		tree.putClientProperty("JTree.lineStyle", "None"); // ����ʾ��������
		tree.setToggleClickCount(1); //����չ���ڵ�֮ǰ����굥����Ϊ1
		tree.addMouseListener(new MouseAdapter() {
			FriTreeNode lastNode = null;
			public void mouseClicked(MouseEvent e) {
				// ˫���¼�
				if(e.getClickCount() == 2) {
					if(lastNode != null) {
						lastNode.setOnClickPaint(false);
					}
					FriTreeNode node = (FriTreeNode)tree.getLastSelectedPathComponent();
					if(node.isThirdLayer()) {
						node.setOnClickPaint(true);
						tree.repaint();
						UserInfo beCaller = node.getUserInfo();
						invokeChat(caller, beCaller); // ��������
						lastNode = node;
					}
				}
			}
		});
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
		searchButton.setToolTipText("���Һ���");
		//searchButton.setContentAreaFilled(false); // ����͸��
		
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddFriendWindow.startUp(caller, thisWindow);
			}
		});
		funcPane.add(searchButton);
	}
	
	// ------------------------- �߼����� ------------------------- //
	
	/** ��ʼ������mainPane */
	private void loadMainPane() {
		refreshMainPane(caller.getID()); // ��Ӻ��Ѻ�ˢ��
		contentPane.add(mainPane);
	}
	
	/** ˢ�²�����mainPane */
	public void reloadMainPane() {
		ResourceManagement.debug("reload");
		if(mainPane == null)
			throw new IllegalArgumentException();
		contentPane.remove(mainPane);
		refreshMainPane(caller.getID()); // ��Ӻ��Ѻ�ˢ��
		contentPane.add(mainPane);
		contentPane.updateUI(); // ���»���
	}
	
	/**
	 * �����û�ID,�����ݿ��ѯ������,���ظ��ڵ�
	 * @param ID �û�ID
	 * @return �ɹ� ���ڵ�   ʧ�� null
	 * @throws SQLException 
	 */
	private FriTreeNode refreshFriTreeNode(int userID) {
		FriTreeNode root = null;
		try {
			// �������ĸ��ڵ�
			root = new FriTreeNode();
			// �������ķ���ڵ�
			FriTreeNode groupNode = new FriTreeNode("�ҵĺ���");
			root.addChild(groupNode);	
			// ��������Ҷ�ڵ�
			ArrayList<FriTreeNode> friNodes = new ArrayList<FriTreeNode>();
			ArrayList<UserInfo> friUserInfos = DBManager.getFriUserInfos(userID);
			for(UserInfo info : friUserInfos) {
				FriTreeNode friNode = new FriTreeNode(info); 
				ResourceManagement.debug("����ID: " + info.getID());
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
	
	// ------------------------- ���ط������ڵ�Ԫ���� ------------------------- //
	
	private MainWindow() {
		caller = new UserInfo(
				331079072, "zzx", "��", 20, "this is a motto", 1);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
		setTitle("QQ���԰�");
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
				331079072, "zzx", "��", 20, "this is a motto", 1), pos);
		contentPane.add(headPane);
	}
	
	protected void refreshMainPane() {
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
		// ����JScrollPane
		JScrollPane treeView = new JScrollPane(tree);
		mainPane.add(treeView, BorderLayout.CENTER);
	}
	
}
