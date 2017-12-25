package qq.ui.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.LayoutFocusTraversalPolicy;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import qq.ui.component.FlowComponentScrollPanel;
import qq.ui.component.UserInfoPanel;
import qq.ui.componentFactory.ButtonFactory;
import qq.ui.componentFactory.PanelFactory;
import qq.ui.componentFunc.ButtonFunc;
import qq.util.Constant;
import qq.util.ResourceManagement;

import java.awt.Color;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JTextArea;

import qq.db.info.UserInfo;
import qq.socket.*;

public class ChatRoom extends JFrame {

	// 与JFrame对象对联的线程
	private SocketWriterThread writerThread = null;
	private SocketReaderThread readerThread = null;
	// qq秀
	private JLabel qqShowPanel = null;
	private final Dimension qqShowSize = Constant.qqShowSize;  
	// 头像
	private UserInfoPanel headPane = null;
	private final Dimension headPaneSize = Constant.userInfoPanelSize;
	/** mainBox用于装载展示区，编辑区，输入区
		大小由qq秀,头像确定 */
	private Box mainBox = null;
	// 历史消息展示区
	private JPanel historyPane = null;
	private JTextArea historyArea = null;
	private final Dimension historySize = new Dimension(450, 300);
	// 用户编辑区
	private JPanel editPanel = null;
	private ButtonGroup editGroup = null;
	private final Dimension iconSize = new Dimension(18, 18);
	// 用户输入区
	private Box inputBox = null;
	private JTextArea inputArea = null;
	private JButton sendButton = null;
	// emojiPane
	private FlowComponentScrollPanel<JButton> emojiPane = null;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					createWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static ChatRoom createWindow() {
		return createWindow(new UserInfo(331079072, "zzx", "男", 20, "i'm brillient", 5));
	}
	
	public static ChatRoom createWindow(UserInfo info) {
		ChatRoom chatRoom = new ChatRoom(info);
		chatRoom.setVisible(true);
		return chatRoom;
	}
	
	// TODO:用工厂实现，网络Socket需要获得ChatRoom实例
	public ChatRoom(UserInfo info) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 550);
		setResizable(false);
		setTitle("QQ电脑版");
		setIconImage(ResourceManagement.getImage("qq_icon.png"));
		
		this.initHeadPanel(info); 
		this.initQQShow();
		this.initEditPanel();
		this.initInputPanel();
		this.initHistoryPanel();
		
		this.getRootPane().setDefaultButton(sendButton); // 默认回车按钮
		
		getContentPane().add(headPane, BorderLayout.NORTH);
		getContentPane().add(qqShowPanel, BorderLayout.EAST);
		mainBox = Box.createVerticalBox();
		mainBox.add(historyPane);
		mainBox.add(editBox);
		mainBox.add(inputBox);
		getContentPane().add(mainBox, BorderLayout.CENTER);
	}

	protected void initHeadPanel(UserInfo info) {
		headPane = new UserInfoPanel(info);
		headPane.setPreferredSize(headPaneSize);
	}
	
	protected void initQQShow(){
		qqShowPanel = new JLabel( 
				ResourceManagement.getScaledIcon("qqshow.png", qqShowSize));
	}
	
	protected void initEditPanel() {
		// 创建facePanel
		initEmojiPanel();
		
		
		
		// emojiButton
		final JRadioButton emojiButton = new JRadioButton(
				ResourceManagement.getScaledIcon("emoticon.png", iconSize));
		emojiButton.setToolTipText("表情");
		emojiButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (emojiButton.isSelected()) {
					getContentPane().add(emojiPane); // 显示facePane
					getContentPane().remove(historyPane); // 两者不能共存
					emojiPane.updateUI();
				} else {
					getContentPane().remove(emojiPane); // 不显示facePane
					getContentPane().add(historyPane); // 两者不能共存
					((JComponent) getContentPane()).updateUI();
				}
			}
		});		
		// TODO:
		
	}
	
	protected void initEmojiPanel() {
		int gapX = 1; 
		int gapY = 1;
		int colNum = 8;
		Dimension compSize = new Dimension(32, 32);
		int compBorderWidth = 4;
		
		// 文件读取创建图片
		ArrayList<JButton> rButtons = new ArrayList<JButton>();
		//Dimension iconSize = new Dimension(24, 24);
		for(int i = 0; i <= Constant.MAX_FACE_ICON; i++) {
			final JButton faceButton = new JButton(
					ResourceManagement.getFaceIcon(i));
			faceButton.setName("[" + i + "]");
			faceButton.setBackground(Color.WHITE);
			faceButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					inputArea.append(faceButton.getName());
				}
			});
			rButtons.add(faceButton);
		}
		
		emojiPane = PanelFactory.createFlowComponentScrollPane(
				rButtons, gapX, gapY, colNum, compSize, compBorderWidth);
	}
	
	protected void initInputPanel(){
		inputBox = Box.createHorizontalBox();
		
		//inputPanel.setBounds(0, 385, 456, 116);
		// 输入文本区域
		// TODO 使用textPane取代textArea
		inputArea = new JTextArea();
		inputArea.setLineWrap(true);
		inputArea.setWrapStyleWord(true);
		// 滚动面板
        JScrollPane textScrollPane = new JScrollPane(inputArea);
        //textScrollPane.setBounds(0,0, 456,94);
        textScrollPane.setHorizontalScrollBarPolicy(
        		JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // 永不出现横向滚动条
        inputBox.add(textScrollPane);
        
		//sendButton.setBounds(363, 93, 93, 23);
		sendButton = new JButton("发送");
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dealMessage();
			}
		});
		inputBox.add(sendButton);
	}
	
	protected void initHistoryPanel(){
		historyPane = new JPanel();
		historyPane.setBackground(Color.WHITE);
		//historyPane.setBounds(0, 91, 456, 270);
		
		historyArea = new JTextArea();
		historyArea.setBackground(Color.LIGHT_GRAY);
		historyArea.setPreferredSize(historySize);
		historyArea.setEditable(false);
		historyPane.add(historyArea);
	}
	
	public void displayMessage(String text) {
		// TODO 完善
		historyArea.append("对方说： " + text + "\n");
	}
	
	/**
	 * 相当于发送信息
	 * 包含清除区域，向writerThread发送消息
	 */
	private void dealMessage() {
		// 获取文本信息并清空输入区域
		String message = inputArea.getText();
		clearInputArea();
		// 发送消息到线程
		writerThread.setMessage(message);
	}
	
	private void clearInputArea() {
		inputArea.setText("");
	}
	
	public void setWriterThread(SocketWriterThread writerThread) {
		this.writerThread = writerThread;
	}
	
	public void setReaderThread(SocketReaderThread readerThread) {
		this.readerThread = readerThread;
	}
}
