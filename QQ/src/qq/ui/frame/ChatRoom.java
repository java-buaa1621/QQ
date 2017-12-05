package qq.ui.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import qq.util.ResourceManagement;

import java.awt.Color;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.Socket;

import javax.swing.JTextArea;
import qq.socket.*;

public class ChatRoom extends JFrame {

	private JPanel contentPane;
	// �û���������
	private JPanel inputPanel;
	private JTextArea inputArea;
	// ��ʷ��Ϣչʾ��
	private JPanel historyPane;
	private JTextArea historyArea;
	// ��JFrame����������߳�
	private SocketWriterThread writerThread;
	private SocketReaderThread readerThread;
	
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
		ChatRoom chatRoom = new ChatRoom();
		chatRoom.setVisible(true);
		return chatRoom;
	}
	/*
	public static void startUp() {
		ChatRoom frame = new ChatRoom();
		frame.setVisible(true);
	}*/
	
	// TODO:�ù���ʵ�֣�����Socket��Ҫ���ChatRoomʵ��
	public ChatRoom() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 582, 532);
		setResizable(false);
		setTitle("QQ���԰�");
		setIconImage(ResourceManagement.getImage("qq_icon.png"));
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null); // ���Բ���
		
		this.initHeadLabel("zzx", "i'm brillient", ResourceManagement.getImage("wolffy.jpg"));
		this.initQQShow(ResourceManagement.getImage("touxiang.jpg"));
		this.initInputPanel();
		this.initHistoryPanel();                   
	}

	protected void initHeadLabel(String userName, String userMotto, Image image) {
		if(userName == null || userMotto == null || image == null)
			throw new NullPointerException("�Է��û��������ǩ����ͷ��ʧ");
		
		JLabel headLabel = new JLabel();
		headLabel.setBounds(0, 0, 581, 93);
		contentPane.add(headLabel);
		
		// ����JLable��ͼƬ
        image = image.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        headLabel.setIcon(new ImageIcon(image));
        // ����JLable��ͼƬ������֮��ľ���	
        headLabel.setIconTextGap(20); 
		// ����JLable������
		String text = "<html>" + userName + "<br/>" + userMotto + "</html>";
		headLabel.setText(text);
	}
	
	protected void initQQShow(Image qqShow){
		if(qqShow == null)
			throw new NullPointerException("�Է�QQ�㶪ʧ");
		qqShow = qqShow.getScaledInstance(125, 412, Image.SCALE_DEFAULT);
		JLabel qqShowPanel = new JLabel(new ImageIcon(qqShow));
		qqShowPanel.setBounds(456, 91, 125, 412);
		contentPane.add(qqShowPanel);
	}
	
	protected void initInputPanel(){
		inputPanel = new JPanel();
		inputPanel.setBounds(0, 387, 456, 116);
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
		historyPane.setBounds(0, 91, 456, 298);
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
