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

	// ��JFrame����������߳�
	private SocketWriterThread writerThread = null;
	private SocketReaderThread readerThread = null;
	// qq��
	private JLabel qqShowPanel = null;
	private final Dimension qqShowSize = Constant.qqShowSize;  
	// ͷ��
	private UserInfoPanel headPane = null;
	private final Dimension headPaneSize = Constant.userInfoPanelSize;
	/** mainBox����װ��չʾ�����༭����������
		��С��qq��,ͷ��ȷ�� */
	private Box mainBox = null;
	// ��ʷ��Ϣչʾ��
	private JPanel historyPane = null;
	private JTextArea historyArea = null;
	private final Dimension historySize = new Dimension(450, 300);
	// �û��༭��
	private JPanel editPanel = null;
	private ButtonGroup editGroup = null;
	private final Dimension iconSize = new Dimension(18, 18);
	// �û�������
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
		setBounds(100, 100, 600, 550);
		setResizable(false);
		setTitle("QQ���԰�");
		setIconImage(ResourceManagement.getImage("qq_icon.png"));
		
		this.initHeadPanel(info); 
		this.initQQShow();
		this.initEditPanel();
		this.initInputPanel();
		this.initHistoryPanel();
		
		this.getRootPane().setDefaultButton(sendButton); // Ĭ�ϻس���ť
		
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
		// ����facePanel
		initEmojiPanel();
		
		
		
		// emojiButton
		final JRadioButton emojiButton = new JRadioButton(
				ResourceManagement.getScaledIcon("emoticon.png", iconSize));
		emojiButton.setToolTipText("����");
		emojiButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (emojiButton.isSelected()) {
					getContentPane().add(emojiPane); // ��ʾfacePane
					getContentPane().remove(historyPane); // ���߲��ܹ���
					emojiPane.updateUI();
				} else {
					getContentPane().remove(emojiPane); // ����ʾfacePane
					getContentPane().add(historyPane); // ���߲��ܹ���
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
		
		// �ļ���ȡ����ͼƬ
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
		// �����ı�����
		// TODO ʹ��textPaneȡ��textArea
		inputArea = new JTextArea();
		inputArea.setLineWrap(true);
		inputArea.setWrapStyleWord(true);
		// �������
        JScrollPane textScrollPane = new JScrollPane(inputArea);
        //textScrollPane.setBounds(0,0, 456,94);
        textScrollPane.setHorizontalScrollBarPolicy(
        		JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // �������ֺ��������
        inputBox.add(textScrollPane);
        
		//sendButton.setBounds(363, 93, 93, 23);
		sendButton = new JButton("����");
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
