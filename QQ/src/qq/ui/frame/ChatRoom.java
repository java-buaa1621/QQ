package qq.ui.frame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
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
import qq.ui.componentFactory.AdapterFactory;
import qq.ui.componentFactory.ButtonFactory;
import qq.ui.componentFactory.PanelFactory;
import qq.ui.componentFunc.ButtonFunc;
import qq.util.Constant;
import qq.util.ResourceManagement;

import java.awt.Color;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JTextArea;

import qq.db.info.UserInfo;
import qq.socket.*;

public class ChatRoom extends JFrame {

	private final ChatRoom thisRoom;
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
	private final Dimension historySize = new Dimension(450, 400);
	// �û��༭��
	private Box editBox = null;
	private final Dimension editIconSize = new Dimension(18, 18);
	private JRadioButton emojiButton = null;
	private JRadioButton pictureButton = null;
	// �û�������
	private Box inputBox = null;
	private JTextArea inputArea = null;
	private JButton sendButton = null;
	private Dimension inputSize = new Dimension(450, 150);
	
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
		thisRoom = this;
		
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
		// ��ʼ��editBox
		editBox = Box.createHorizontalBox();
		
		initEmojiButton();
		editBox.add(emojiButton);
		initPictureButton();
		editBox.add(pictureButton);
		
		// TODO:
		editBox.add(Box.createHorizontalGlue()); // ���ռλ
	}
	
	protected void initEmojiButton(){
		emojiButton = new JRadioButton(
				ResourceManagement.getScaledIcon("emoticon.png", editIconSize));
		emojiButton.setToolTipText("����");
		
		emojiButton.addMouseListener(
				AdapterFactory.createMouseEnterAndExitAdapter(emojiButton));
		emojiButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!EmojiDialog.haveDialog()) {
					EmojiDialog dialog = EmojiDialog.getInstance(thisRoom);
					Point pos = emojiButton.getLocationOnScreen();
					int width = EmojiDialog.size.width;
					int height = EmojiDialog.size.height;
					dialog.setBounds((int)pos.getX(), (int)pos.getY() - height, width, height);
				} else {
					EmojiDialog.close();
				}
			}
		});
		//��ֹ����һֱ��ʾ---------------------------
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				EmojiDialog.close();
			}
		});
		this.addWindowStateListener(new WindowStateListener() {
			@Override
			public void windowStateChanged(WindowEvent e) {
				EmojiDialog.close();				
			}
		});
		//---------------------------
		emojiButton.addFocusListener(new FocusAdapter() {
			// TODO:����
			@Override
			public void focusLost(FocusEvent e) {
				if(!(e.getOppositeComponent() instanceof EmojiDialog))
					EmojiDialog.close();
				// ResourceManagement.debug(e.getOppositeComponent().getClass().getName());
			}
		});
	}
	
	protected void initPictureButton(){
		pictureButton = new JRadioButton(
				ResourceManagement.getScaledIcon("picture.png", editIconSize));
		pictureButton.setToolTipText("ͼƬ");
		
		pictureButton.addMouseListener(
				AdapterFactory.createMouseEnterAndExitAdapter(pictureButton));
		pictureButton.addActionListener(new ActionListener() { // ����ͼƬ�¼�
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser f = new JFileChooser(); // �����ļ�
				f.showOpenDialog(null);
				File picture = f.getSelectedFile(); // TODO ����׺��
				if(picture != null) {
					insertPicture(picture); // ����ͼƬ					
				}
			}
		});
	}
	
	protected void initInputPanel(){
		inputBox = Box.createVerticalBox();
		
		// �����ı����� TODO ʹ��textPaneȡ��textArea
		inputArea = new JTextArea();
		inputArea.setLineWrap(true);
		inputArea.setWrapStyleWord(true);
		// �������
        JScrollPane textScrollPane = new JScrollPane(inputArea);
        textScrollPane.setPreferredSize(inputSize);
        textScrollPane.setHorizontalScrollBarPolicy(
        		JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // �������ֺ��������
        textScrollPane.setVerticalScrollBarPolicy(
        		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // ������������������
        inputBox.add(textScrollPane);
        // ���sendButton
        Box sendBox = Box.createHorizontalBox();
		sendButton = new JButton("����");
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dealMessage();
			}
		});
		sendBox.add(Box.createHorizontalGlue()); // ռλ��
		sendBox.add(sendButton);
		inputBox.add(sendBox);
	}
	
	protected void initHistoryPanel(){
		historyPane = new JPanel();
		historyPane.setBackground(Color.WHITE);
		
		historyArea = new JTextArea();
		historyArea.setBackground(Color.LIGHT_GRAY);
		historyArea.setPreferredSize(historySize);
		historyArea.setEditable(false);
		historyPane.add(historyArea);
	}
	
	
	
	// ------------------------------------- �߼����� ------------------------------- //
	
	public void insertText(String text) {
		inputArea.append(text);
	}
	
	public void insertPicture(File picture) {
		// TODO
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
		clearInputText();
		// ������Ϣ���߳�
		writerThread.setMessage(message);
	}
	
	private void clearInputText() {
		inputArea.setText("");
	}
	
	public void setWriterThread(SocketWriterThread writerThread) {
		this.writerThread = writerThread;
	}
	
	public void setReaderThread(SocketReaderThread readerThread) {
		this.readerThread = readerThread;
	}
}
