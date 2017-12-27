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
import javax.swing.JComboBox;
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
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import qq.ui.component.FlowComponentScrollPanel;
import qq.ui.component.FontAttrib;
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
	private final Dimension historySize = new Dimension(450, 400);
	private JScrollPane historyScrollPane = null;
	private JTextPane historyPane = null;
	private StyledDocument doc = null; // �ô˹��߲���������ʽ��ͼƬ
	// �û��༭��
	private Box editBox = null;
	private final Dimension editIconSize = new Dimension(18, 18);
	private JRadioButton emojiButton = null;
	private JRadioButton pictureButton = null;
	// �û�������
	private Box fontBox = null;
	Dimension fontComboBoxSize = new Dimension(50,20); // �������ڲ��ִ�С
	private final int comboGap = 8;
	private JComboBox<String> fontName = null;
	private JComboBox<String> fontSize = null;
	private JComboBox<String> fontStyle = null;
	private JComboBox<String> fontColor = null;
	private JComboBox<String> fontBackColor = null;
	// �û�������
	private Box inputBox = null;
	private JTextArea inputArea = null;
	private JButton sendButton = null;
	private Dimension inputSize = new Dimension(450, 130);
	
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
	
	/**
	 * �ù���ʵ�֣�����Socket��Ҫ���ChatRoomʵ��
	 * @param info
	 * @param writerThread
	 * @param readerThread
	 */
	private ChatRoom(UserInfo info) {
		thisRoom = this; // Ԥ�����������Ҫ��final����
		
		try { // ʹ��Windows�Ľ�����
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 550);
		setResizable(false);
		setTitle("QQ���԰�");
		setIconImage(ResourceManagement.getImage("qq_icon.png"));
		
		this.initHeadPanel(info); 
		this.initQQShow();
		this.initEditPanel();
		this.initFontComboxes();
		this.initInputPanel();
		this.initHistoryPanel();
		
		this.getRootPane().setDefaultButton(sendButton); // Ĭ�ϻس���ť
		
		getContentPane().add(headPane, BorderLayout.NORTH);
		getContentPane().add(qqShowPanel, BorderLayout.EAST);
		mainBox = Box.createVerticalBox();
		mainBox.add(historyPane);
		mainBox.add(editBox);
		mainBox.add(fontBox);
		mainBox.add(inputBox);
		getContentPane().add(mainBox, BorderLayout.CENTER);
		
//		FontAttrib textInfo = new FontAttrib();
//		textInfo.setText("���");
//		textInfo.setName("����");
//		textInfo.setSize(40);
//		textInfo.setStyle(FontAttrib.BOLD);
//		textInfo.setColor(new Color(0, 0, 0));
//		textInfo.setBackColor(new Color(200, 0, 0));
//		displayText(textInfo);
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
		
		// ��ʼ��emoji, picture ��ť
		initEmojiButton();
		editBox.add(emojiButton);
		initPictureButton();
		editBox.add(pictureButton);
		
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
		// ------------��ֹ����һֱ��ʾ TODO:����
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
			@Override
			public void focusLost(FocusEvent e) {
				if(!(e.getOppositeComponent() instanceof EmojiDialog)) {
					EmojiDialog.close();
				}
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
	
	protected void initFontComboxes(){
		fontBox = Box.createVerticalBox();
		Box innerBox = Box.createHorizontalBox();
		
		String[] str_name = { "����", "����", "Dialog", "Gulim" };
		String[] str_Size = { "12", "14", "18", "22", "30", "40" };
		String[] str_Style = { "����", "б��", "����", "��б��" };
		String[] str_Color = { "��ɫ", "��ɫ", "��ɫ", "��ɫ", "��ɫ" };
		String[] str_BackColor = { "��ɫ", "��ɫ", "����", "����", "����", "����" };
		fontName = new JComboBox(str_name); // ��������
		fontSize = new JComboBox(str_Size); // �ֺ�
		fontStyle = new JComboBox(str_Style); // ��ʽ
		fontColor = new JComboBox(str_Color); // ��ɫ
		fontBackColor = new JComboBox(str_BackColor); // ������ɫ
		fontName.setPreferredSize(fontComboBoxSize);
		fontSize.setPreferredSize(fontComboBoxSize);
		fontStyle.setPreferredSize(fontComboBoxSize);
		fontColor.setPreferredSize(fontComboBoxSize);
		fontBackColor.setPreferredSize(fontComboBoxSize);
		/* 
		 * ��ʼ������ѡ����
		 * 1.�����ǩ
		 * 2.�������
		 * 3.���	 
		 */
		innerBox.add(new JLabel("���� ")); // �����ǩ
		innerBox.add(fontName); // �������
		innerBox.add(Box.createHorizontalStrut(comboGap)); // ������
		innerBox.add(new JLabel("��ʽ "));
		innerBox.add(fontStyle);
		innerBox.add(Box.createHorizontalStrut(comboGap));
		innerBox.add(new JLabel("�ֺ� "));
		innerBox.add(fontSize);
		innerBox.add(Box.createHorizontalStrut(comboGap));
		innerBox.add(new JLabel("��ɫ "));
		innerBox.add(fontColor);
		innerBox.add(Box.createHorizontalStrut(comboGap));
		innerBox.add(new JLabel("���� "));
		innerBox.add(fontBackColor);
		innerBox.add(Box.createHorizontalStrut(comboGap));
		
		fontBox.add(innerBox);
		fontBox.add(Box.createVerticalStrut(comboGap)); // �ײ���� 
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
		historyPane = new JTextPane();
		historyPane.setBackground(Color.LIGHT_GRAY);
		historyPane.setEditable(false);
		
		doc = historyPane.getStyledDocument(); // ���JTextPane��Document
		// ���ScrollPane
		historyScrollPane = new JScrollPane(historyPane);
		historyScrollPane.setPreferredSize(historySize);
	}
	
	
	
	// ------------------------------------- �߼����� ------------------------------- //
	
	public void insertText(String text) {
		inputArea.append(text);
	}
	
	public void sendPicture(ImageIcon picture) {
		// TODO
	}
	
	public void displayPicture(File file) {
		historyPane.setCaretPosition(doc.getLength()); // ���ò���λ��
		historyPane.insertIcon(new ImageIcon(file.getPath())); // ����ͼƬ
		displayText(new FontAttrib()); // ���������Ի���
	}
	
	public void displayText(FontAttrib textInfo) {
		if(textInfo == null)
			throw new IllegalArgumentException();
		
		ResourceManagement.debug("����չʾ��Ϣ");
		ResourceManagement.debug(textInfo);
		try { // �����ı�
			doc.insertString(doc.getLength(),
					textInfo.getText() + "\n", textInfo.getAttrSet());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��inputArea�Լ��༭����ȡ�ı���Ϣ
	 * @return �����͵�FontAttrib
	 */
	private FontAttrib getTextInfo() {
		FontAttrib textInfo = new FontAttrib();
		textInfo.setText(inputArea.getText());
		textInfo.setName((String) fontName.getSelectedItem());
		textInfo.setSize(Integer.parseInt((String) fontSize.getSelectedItem()));
		String temp_style = (String) fontStyle.getSelectedItem();
		if (temp_style.equals("����")) {
			
		} else if (temp_style.equals("����")) {
			textInfo.setStyle(FontAttrib.BOLD);
		} else if (temp_style.equals("б��")) {
			textInfo.setStyle(FontAttrib.ITALIC);
		} else if (temp_style.equals("��б��")) {
			textInfo.setStyle(FontAttrib.BOLD_ITALIC);
		}
		String temp_color = (String) fontColor.getSelectedItem();
		if (temp_color.equals("��ɫ")) {
			textInfo.setColor(new Color(0, 0, 0));
		} else if (temp_color.equals("��ɫ")) {
			textInfo.setColor(new Color(255, 0, 0));
		} else if (temp_color.equals("��ɫ")) {
			textInfo.setColor(new Color(0, 0, 255));
		} else if (temp_color.equals("��ɫ")) {
			textInfo.setColor(new Color(255, 255, 0));
		} else if (temp_color.equals("��ɫ")) {
			textInfo.setColor(new Color(0, 255, 0));
		}
		String temp_backColor = (String) fontBackColor.getSelectedItem();
		if (!temp_backColor.equals("��ɫ")) {
			if (temp_backColor.equals("��ɫ")) {
				textInfo.setBackColor(new Color(200, 200, 200));
			} else if (temp_backColor.equals("����")) {
				textInfo.setBackColor(new Color(255, 200, 200));
			} else if (temp_backColor.equals("����")) {
				textInfo.setBackColor(new Color(200, 200, 255));
			} else if (temp_backColor.equals("����")) {
				textInfo.setBackColor(new Color(255, 255, 200));
			} else if (temp_backColor.equals("����")) {
				textInfo.setBackColor(new Color(200, 255, 200));
			}
		}
		return textInfo;
	}
	
	/**
	 * �൱�ڷ�����Ϣ
	 * �������������writerThread������Ϣ
	 */
	private void dealMessage() {
		// ��ȡ�ı���Ϣ�������������
		FontAttrib textInfo = getTextInfo();
		clearInputText();
		// ������Ϣ���߳�
		writerThread.setTextInfo(textInfo);
		ResourceManagement.debug("���ڷ�����Ϣ");
		ResourceManagement.debug(textInfo);
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
