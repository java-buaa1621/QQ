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
import javax.swing.plaf.metal.MetalBorders.Flush3DBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Position;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import qq.ui.component.FlowComponentScrollPanel;
import qq.ui.component.FontAttrib;
import qq.ui.component.MyAction;
import qq.ui.component.UserInfoPanel;
import qq.ui.componentFactory.AdapterFactory;
import qq.ui.componentFactory.ButtonFactory;
import qq.ui.componentFactory.PanelFactory;
import qq.ui.componentFunc.ButtonFunc;
import qq.util.Constant;
import qq.util.Func;
import qq.util.ResourceManagement;

import java.awt.Color;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextArea;

import com.mysql.jdbc.Buffer;

import qq.db.info.UserInfo;
import qq.socket.*;

public class ChatRoom extends JFrame {

	private final ChatRoom thisRoom;
	// ��JFrame����������߳�
	private SocketWriterThread writerThread = null;
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
	private final Dimension historySize = new Dimension(450, 300);
	private final Dimension pictureLimitSize = new Dimension(300, 150);
	private JScrollPane historyScrollPane = null;
	private JTextPane historyPane = null;
	private StyledDocument doc = null; // �ô˹��߲���������ʽ��ͼƬ
	private TextBuffer textReceivedBuffer = null;
	private TextBuffer textSendBuffer = null;
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
	// ������
	private UserInfo caller = null;
	private UserInfo beCaller = null;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//createWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	// ��Ԫ����
/*	public static ChatRoom createWindow() {
		return createWindow(new UserInfo(331079072, "zzx", "��", 20, "i'm brillient", 5),
				new UserInfo(331079072, "zzx", "��", 20, "i'm brillient", 8));
	}*/
	
	public static ChatRoom createWindow(UserInfo caller, UserInfo beCaller) {
		ChatRoom chatRoom = new ChatRoom(caller, beCaller);
		chatRoom.setVisible(true);
		return chatRoom;
	}
	
	/**
	 * �ù���ʵ�֣�����Socket��Ҫ���ChatRoomʵ��
	 * @param info
	 * @param writerThread
	 * @param readerThread
	 */
	private ChatRoom(UserInfo caller, UserInfo beCaller) {
		if(caller == null || beCaller == null)
			throw new IllegalArgumentException();
		
		thisRoom = this; // Ԥ�����������Ҫ��final����
		this.caller = caller;
		this.beCaller = beCaller;
		
		Func.useWindowsStyle();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 550);
		setResizable(false);
		setTitle("QQ���԰�");
		setIconImage(ResourceManagement.getImage("qq_icon.png"));
		
		this.initHeadPanel(); 
		this.initQQShow();
		this.initEditPanel();
		this.initFontComboxes();
		this.initInputPanel();
		this.initHistoryPanel();
		
		this.getRootPane().setDefaultButton(sendButton); // Ĭ�ϻس���ť
		
		getContentPane().add(headPane, BorderLayout.NORTH);
		getContentPane().add(qqShowPanel, BorderLayout.EAST);
		mainBox = Box.createVerticalBox();
		mainBox.add(historyScrollPane);
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

	protected void initHeadPanel() {
		headPane = new UserInfoPanel(beCaller);
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
				// ResourceManagement.debug(e.gesizepositeComponent().getClass().getName());
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
				File file = f.getSelectedFile(); // TODO ����Ƿ�ΪͼƬ
				if(file != null) {
					ImageIcon icon = new ImageIcon(file.getPath());
					sendPicture(icon); // ����ͼƬ					
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
		
		// �����ı����� 
		// TODO ʹ��textPaneȡ��textArea
		inputArea = new JTextArea();
		inputArea.setLineWrap(true);
		inputArea.setWrapStyleWord(true);
		// ���ð��س�������Ϣ
		MyAction sendTextAction = new MyAction() {
			public void executeAction() {
				sendTextInfo();
			}
		};
		inputArea.addKeyListener(AdapterFactory.createKeyTypedAdapter(KeyEvent.VK_ENTER, sendTextAction));
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
				sendTextInfo();
			}
		});
		sendBox.add(Box.createHorizontalGlue()); // ռλ��
		sendBox.add(sendButton);
		inputBox.add(sendBox);
	}
	
	protected void initHistoryPanel(){
		historyPane = new JTextPane();
		historyPane.setBackground(Color.WHITE);
		historyPane.setEditable(false);
		doc = historyPane.getStyledDocument(); // ���JTextPane��Document
		
		// ���ScrollPane
		historyScrollPane = new JScrollPane(historyPane);
		historyScrollPane.setPreferredSize(historySize);
		if(!Constant.DEBUG)
			historyScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		initTextBuffer();
		initHistoryText();
		displayTime();
	}
	
	
	// ------------------------------------- ��ʷ��Ϣ�߼����� ------------------------------- //
	
	protected void initTextBuffer() {
		textReceivedBuffer = new TextBuffer() {
			@Override
			protected void dealTextInfo(Object[] textInfos, int size) {
				for(int i = 0; i < size; i++) {
					Object textInfo =  textInfos[i];
					if(textInfo instanceof ImageIcon) {
						displayIcon((ImageIcon) textInfo);
					} else if(textInfo instanceof FontAttrib) {
						displayWords((FontAttrib) textInfo);
					}
				}
			}
		};
		textSendBuffer = new TextBuffer() {
			@Override
			protected void dealTextInfo(Object[] textInfos, int size) {
				takeLeftLine();
				for(int i = 0; i < size; i++) {
					Object textInfo =  textInfos[i];
					if(textInfo instanceof ImageIcon) {
						displayIcon((ImageIcon) textInfo);
					} else if(textInfo instanceof FontAttrib) {
						displayWords((FontAttrib) textInfo);
					}
				}
			}
		};
	}
	
	protected void initHistoryText() {
		// TODO:
	}
	
	protected void displayTime() {
		try {
			displayWords(new FontAttrib(Func.getTime()));
			changeLine(2);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �൱�ڷ�����Ϣ
	 * �������������writerThread������Ϣ
	 */
	private void sendTextInfo() {
		// ��ȡ�ı���Ϣ�������������
		FontAttrib textInfo = getTextInfo();
		clearInputText();
		// ������Ϣ���߳�
		writerThread.setTextInfo(textInfo);
		// ����
		displaySendText(textInfo);
	}
	
	/** �ȶ�ͼƬ����ѹ����⴦���ٷ��� */
	public void sendPicture(ImageIcon picture) {
		double ratio = ResourceManagement.getScaledRatio(
				picture, pictureLimitSize.width, pictureLimitSize.height);
		if(ratio < 1) {
			picture = ResourceManagement.getScaledIcon(picture, ratio);
		}
		writerThread.setPicture(picture);
		// ����
		displaySendPicture(picture);
	}
	
	/** doc�в���ͼƬ */
	private void displayIcon(ImageIcon icon) {
		if(icon == null)
			throw new IllegalArgumentException();

		historyPane.setCaretPosition(doc.getLength()); // ���ò���λ��
		historyPane.insertIcon(icon); // ����ͼƬ
	}
	
	/**  
	 * doc�в������� 
	 * @param FontAttrib�����ı�
	 */
	private void displayWords(FontAttrib words) {
		if(words == null)
			throw new IllegalArgumentException();
		
		try {
			doc.insertString(doc.getLength(), words.getText(), words.getAttrSet());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	/** ���� */
	private void changeLine() {
		try {
			doc.insertString(doc.getLength(), "\n", new SimpleAttributeSet());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	/** ���� */
	private void changeLine(int time) {
		if(time <= 0)
			throw new IllegalArgumentException();
		while(time-- > 0)
			changeLine();
	}
	
	/** ʹ��glueռ����˿հ� */
	private void takeLeftLine() {
		historyPane.setCaretPosition(doc.getLength()); // �ҵ�����λ��
		historyPane.insertComponent(Box.createHorizontalGlue()); // ʹ�ò���ͷ������ƫ��
		// historyPane.insertComponent(new JButton("button"));
	}
	
	/** չʾ�û�ͷ�� */
	private void displayUser(UserInfo info) {
		ImageIcon icon = ResourceManagement.getHeadIcon(info.getHeadIconIndex());
		displayIcon(icon);
		FontAttrib prefix;
		if(info.getID() == caller.getID()) { // ʹ����
			prefix = new FontAttrib("��˵:  ");
		} else { 							// �Է�
			prefix = new FontAttrib(info.getMotto() + "˵:  ");
		}
		displayWords(prefix);
		changeLine(2);
	}
	
	/** չʾ����ͼƬ */
	private void displayPicture(ImageIcon picture) {
		if(picture == null)
			throw new IllegalArgumentException();
		
		displayIcon(picture);
		changeLine(2);
	}
	
	/** չʾ���뺬emoji�ı� */
	private void displayText(FontAttrib textInfo) {
		if(textInfo == null)
			throw new IllegalArgumentException();

		String text = textInfo.getText();
		Pattern pattern = Pattern.compile("(\\[\\d+\\])"); // ƥ������: [some ints...]
		Matcher matcher = pattern.matcher(text);
		
		int restStart = 0;
		while (matcher.find()) {
			String matchStr = matcher.group(1);
			String indexStr = matchStr.substring(1, matchStr.length() - 1);
			int index = Integer.parseInt(indexStr); // emoji���
			String displayText = null;
			if(Func.isValidEmoji(index)) { // �ҵ��Ϸ�emoji
				displayText = text.substring(restStart, matcher.start());
				displayWords(textInfo.toNewText(displayText));
				displayIcon(ResourceManagement.getEmojiIcon(index));
			} else {
				displayText = text.substring(restStart, matcher.end());
				displayWords(textInfo.toNewText(displayText));
			}
			restStart = matcher.end();
		}
		// ʣ���ı�������Ѱ�ҵ��ֶΣ����ʣ�������
		String restText = text.substring(restStart, text.length());
		displayWords(textInfo.toNewText(restText));
		changeLine(2);
	}
	
	public void displayReceivedPicture(ImageIcon picture) {
		displayUser(beCaller);
		displayPicture(picture);
	}
	
	public void displayReceivedText(FontAttrib textInfo) {
		displayUser(beCaller);
		displayText(textInfo);
	}
	
	private void displaySendPicture(ImageIcon picture) {
		takeLeftLine();
		displayUser(caller);
		takeLeftLine();
		displayPicture(picture);
	}
	
	private void displaySendText(FontAttrib textInfo) {
		takeLeftLine();
		displayUser(caller);
		takeLeftLine();
		displayText(textInfo);
	}
	
	
	// ------------------------------------- �߼����� ------------------------------- //
	
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
	
	public void insertText(String text) {
		inputArea.append(text);
	}
	
	private void clearInputText() {
		inputArea.setText("");
	}
	
	public void setWriterThread(SocketWriterThread writerThread) {
		this.writerThread = writerThread;
	}

}

abstract class TextBuffer {
	
	protected final static int MAX_SIZE = 70; // ����
	protected Object[] textInfo;
	protected int size; // �൱�ڵ�ǰ����
	
	public TextBuffer() {
		textInfo = new Object[MAX_SIZE];
		clear();
	}
	
	protected void clear() {
		for(int i = 0; i < MAX_SIZE; i++) 
			textInfo[i] = null;
		size = 0;
	}
	
	public void insert(Object obj) {
		if(obj == null)
			throw new IllegalArgumentException();
		
		textInfo[size++] = obj;
		if(size == MAX_SIZE)
			flush();
	}
	
	public void flush() {
		dealTextInfo(textInfo, size);
		clear();
	}
	
	protected abstract void dealTextInfo(Object[] textInfos, int size);
			
}
