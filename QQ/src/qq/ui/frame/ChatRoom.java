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
	// 用户输入区域
	private JPanel inputPanel;
	private JTextArea inputArea;
	// 历史消息展示区
	private JPanel historyPane;
	private JTextArea historyArea;
	// 与JFrame对象对联的线程
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
	
	// TODO:用工厂实现，网络Socket需要获得ChatRoom实例
	public ChatRoom() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 582, 532);
		setResizable(false);
		setTitle("QQ电脑版");
		setIconImage(ResourceManagement.getImage("qq_icon.png"));
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null); // 绝对布局
		
		this.initHeadLabel("zzx", "i'm brillient", ResourceManagement.getImage("wolffy.jpg"));
		this.initQQShow(ResourceManagement.getImage("touxiang.jpg"));
		this.initInputPanel();
		this.initHistoryPanel();                   
	}

	protected void initHeadLabel(String userName, String userMotto, Image image) {
		if(userName == null || userMotto == null || image == null)
			throw new NullPointerException("对方用户名或个性签名或头像丢失");
		
		JLabel headLabel = new JLabel();
		headLabel.setBounds(0, 0, 581, 93);
		contentPane.add(headLabel);
		
		// 设置JLable的图片
        image = image.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        headLabel.setIcon(new ImageIcon(image));
        // 设置JLable的图片与文字之间的距离	
        headLabel.setIconTextGap(20); 
		// 设置JLable的文字
		String text = "<html>" + userName + "<br/>" + userMotto + "</html>";
		headLabel.setText(text);
	}
	
	protected void initQQShow(Image qqShow){
		if(qqShow == null)
			throw new NullPointerException("对方QQ秀丢失");
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
		// 发送按钮
		JButton sendButton = new JButton("发送");
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dealMessage();
			}
		});
		sendButton.setBounds(363, 93, 93, 23);
		inputPanel.add(sendButton);
		
		// 输入文本区域
		// TODO 使用textPane取代textArea
		inputArea = new JTextArea();
		inputArea.setLineWrap(true);
		inputArea.setWrapStyleWord(true);
		// 滚动面板
        JScrollPane textScrollPane = new JScrollPane(inputArea);
        textScrollPane.setBounds(0,0, 456,94);
        textScrollPane.setHorizontalScrollBarPolicy(
        		JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // 永不出现横向滚动条
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
