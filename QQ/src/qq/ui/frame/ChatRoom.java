package qq.ui.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import qq.ui.component.FlowComponentScrollPanel;
import qq.ui.component.UserInfoPanel;
import qq.ui.componentFactory.ButtonFactory;
import qq.ui.componentFactory.PanelFactory;
import qq.ui.componentFunc.ButtonFunc;
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

	// ��JFrame����������߳�
	private SocketWriterThread writerThread;
	private SocketReaderThread readerThread;
	// ͼ�����
	private JPanel contentPane;
	private UserInfoPanel headPane;
	// �û���������
	/** ֻ����һ��ͬʱ��ѡȡ */
	private FlowComponentScrollPanel<JRadioButton> editPane;
	private JPanel inputPanel;
	private JTextArea inputArea;
	// ��ʷ��Ϣչʾ��
	private JPanel historyPane;
	private JTextArea historyArea;

	
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
		return createWindow(new UserInfo(331079072, "zzx", "��", 20, "i'm brillient", 5));
	}
	
	public static ChatRoom createWindow(UserInfo info) {
		ChatRoom chatRoom = new ChatRoom(info);
		chatRoom.setVisible(true);
		return chatRoom;
	}
	
	// TODO:�ù���ʵ�֣�����Socket��Ҫ���ChatRoomʵ��
	public ChatRoom(UserInfo info) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 582, 532);
		setResizable(false);
		setTitle("QQ���԰�");
		setIconImage(ResourceManagement.getImage("qq_icon.png"));
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null); // ���Բ���
		
		this.initHeadPanel(info);
		this.initQQShow(ResourceManagement.getImage("touxiang.jpg"));
		this.initEditPanel();
		this.initInputPanel();
		this.initHistoryPanel();
	}

	protected void initHeadPanel(UserInfo info) {
		headPane = new UserInfoPanel(info);
		headPane.setBounds(0, 0, 581, 93);
		contentPane.add(headPane);
	}
	
	protected void initQQShow(Image qqShow){
		if(qqShow == null)
			throw new NullPointerException();
		
		qqShow = qqShow.getScaledInstance(125, 412, Image.SCALE_DEFAULT);
		JLabel qqShowPanel = new JLabel(new ImageIcon(qqShow));
		qqShowPanel.setBounds(456, 91, 125, 412);
		contentPane.add(qqShowPanel);
	}
	
	protected void initEditPanel() {

		Dimension iconSize = new Dimension(18, 18);
		ButtonGroup bGroup = new ButtonGroup();
		
		ImageIcon[] icons = {
			ResourceManagement.getScaledIcon("emoticon.png", iconSize),
			ResourceManagement.getScaledIcon("emoticon.png", iconSize),
			ResourceManagement.getScaledIcon("emoticon.png", iconSize),
		};
		
		ActionListener[] listeners = {
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
//					PanelFactory.createFlowComponentScrollPane(
//						panelPos, components, colNum, compSize);
				}
			},
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
				}
			}
		};

		Rectangle panelPos = new Rectangle(0, 360, 456, 25);
		int gapX = 5;
		int gapY = 0;
		int colNum = 10;
		Dimension compSize = new Dimension(22, 22);
		int compBrderWidth = 0;
//		editPane = PanelFactory.createFlowComponentScrollPane(
//				panelPos, emojiButton, gapX, gapY, colNum, compSize, compBrderWidth);
		editPane.banVerticalScroll();
		
		contentPane.add(editPane);
		// TODO:finish
		
//		editPane = new JPanel();
//		editPane.setBounds(0, 360, 456, 25);
//		editPane.setLayout(new FlowLayout(0, 5, FlowLayout.LEFT));
//		contentPane.add(editPane);
//		
//		// ����emojiButton
//		Dimension iconSize = new Dimension(18, 18);
//		Dimension compSize = new Dimension(22, 22);
//		emojiButton = new JButton(
//				ResourceManagement.getScaledIcon("emoticon.png", iconSize));
//		emojiButton.setBorder(new EmptyBorder(4, 0, 0, 0));
//		emojiButton.setPreferredSize(compSize);
//		editPane.add(emojiButton);
	}
	
	protected void initInputPanel(){
		inputPanel = new JPanel();
		inputPanel.setBounds(0, 385, 456, 116);
		contentPane.add(inputPanel);
		inputPanel.setLayout(null);
		// ���Ͱ�ť
		JButton sendButton = new JButton("����");
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dealMessage();
			}
		});
		sendButton.setBounds(363, 93, 93, 23);
		inputPanel.add(sendButton);
		
		// �����ı�����
		// TODO ʹ��textPaneȡ��textArea
		inputArea = new JTextArea();
		inputArea.setLineWrap(true);
		inputArea.setWrapStyleWord(true);
		// �������
        JScrollPane textScrollPane = new JScrollPane(inputArea);
        textScrollPane.setBounds(0,0, 456,94);
        textScrollPane.setHorizontalScrollBarPolicy(
        		JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // �������ֺ��������
		inputPanel.add(textScrollPane);
	}
	
	protected void initHistoryPanel(){
		historyPane = new JPanel();
		historyPane.setBackground(Color.WHITE);
		historyPane.setBounds(0, 91, 456, 270);
		contentPane.add(historyPane);
		historyPane.setLayout(null);
		
		historyArea = new JTextArea();
		historyArea.setBackground(Color.LIGHT_GRAY);
		historyArea.setBounds(0, 0, 456, 298);
		historyArea.setEditable(false);
		historyPane.add(historyArea);
	}
	
	public void displayMessage(String text) {
		// TODO ����
		historyArea.append("�Է�˵�� " + text + "\n");
	}
	
	/**
	 * �൱�ڷ�����Ϣ
	 * �������������writerThread������Ϣ
	 */
	private void dealMessage() {
		// ��ȡ�ı���Ϣ�������������
		String message = inputArea.getText();
		clearInputArea();
		// ������Ϣ���߳�
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
