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
	private final Dimension historySize = new Dimension(450, 400);
	private JScrollPane historyScrollPane = null;
	private JTextPane historyPane = null;
	private StyledDocument doc = null; // 用此工具插入文字样式和图片
	// 用户编辑区
	private Box editBox = null;
	private final Dimension editIconSize = new Dimension(18, 18);
	private JRadioButton emojiButton = null;
	private JRadioButton pictureButton = null;
	// 用户字体区
	private Box fontBox = null;
	Dimension fontComboBoxSize = new Dimension(50,20); // 好像是内部字大小
	private final int comboGap = 8;
	private JComboBox<String> fontName = null;
	private JComboBox<String> fontSize = null;
	private JComboBox<String> fontStyle = null;
	private JComboBox<String> fontColor = null;
	private JComboBox<String> fontBackColor = null;
	// 用户输入区
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
		return createWindow(new UserInfo(331079072, "zzx", "男", 20, "i'm brillient", 5));
	}
	
	public static ChatRoom createWindow(UserInfo info) {
		ChatRoom chatRoom = new ChatRoom(info);
		chatRoom.setVisible(true);
		return chatRoom;
	}
	
	/**
	 * 用工厂实现，网络Socket需要获得ChatRoom实例
	 * @param info
	 * @param writerThread
	 * @param readerThread
	 */
	private ChatRoom(UserInfo info) {
		thisRoom = this; // 预设监听器所需要的final变量
		
		try { // 使用Windows的界面风格
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 550);
		setResizable(false);
		setTitle("QQ电脑版");
		setIconImage(ResourceManagement.getImage("qq_icon.png"));
		
		this.initHeadPanel(info); 
		this.initQQShow();
		this.initEditPanel();
		this.initFontComboxes();
		this.initInputPanel();
		this.initHistoryPanel();
		
		this.getRootPane().setDefaultButton(sendButton); // 默认回车按钮
		
		getContentPane().add(headPane, BorderLayout.NORTH);
		getContentPane().add(qqShowPanel, BorderLayout.EAST);
		mainBox = Box.createVerticalBox();
		mainBox.add(historyPane);
		mainBox.add(editBox);
		mainBox.add(fontBox);
		mainBox.add(inputBox);
		getContentPane().add(mainBox, BorderLayout.CENTER);
		
//		FontAttrib textInfo = new FontAttrib();
//		textInfo.setText("你好");
//		textInfo.setName("宋体");
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
		// 初始化editBox
		editBox = Box.createHorizontalBox();
		
		// 初始化emoji, picture 按钮
		initEmojiButton();
		editBox.add(emojiButton);
		initPictureButton();
		editBox.add(pictureButton);
		
		editBox.add(Box.createHorizontalGlue()); // 填充占位
	}
	
	protected void initEmojiButton(){
		emojiButton = new JRadioButton(
				ResourceManagement.getScaledIcon("emoticon.png", editIconSize));
		emojiButton.setToolTipText("表情");
		
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
		// ------------防止表情一直显示 TODO:完善
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
		pictureButton.setToolTipText("图片");
		
		pictureButton.addMouseListener(
				AdapterFactory.createMouseEnterAndExitAdapter(pictureButton));
		pictureButton.addActionListener(new ActionListener() { // 插入图片事件
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser f = new JFileChooser(); // 查找文件
				f.showOpenDialog(null);
				File picture = f.getSelectedFile(); // TODO 检测后缀名
				if(picture != null) {
					insertPicture(picture); // 插入图片					
				}
			}
		});
	}
	
	protected void initFontComboxes(){
		fontBox = Box.createVerticalBox();
		Box innerBox = Box.createHorizontalBox();
		
		String[] str_name = { "宋体", "黑体", "Dialog", "Gulim" };
		String[] str_Size = { "12", "14", "18", "22", "30", "40" };
		String[] str_Style = { "常规", "斜体", "粗体", "粗斜体" };
		String[] str_Color = { "黑色", "红色", "蓝色", "黄色", "绿色" };
		String[] str_BackColor = { "无色", "灰色", "淡红", "淡蓝", "淡黄", "淡绿" };
		fontName = new JComboBox(str_name); // 字体名称
		fontSize = new JComboBox(str_Size); // 字号
		fontStyle = new JComboBox(str_Style); // 样式
		fontColor = new JComboBox(str_Color); // 颜色
		fontBackColor = new JComboBox(str_BackColor); // 背景颜色
		fontName.setPreferredSize(fontComboBoxSize);
		fontSize.setPreferredSize(fontComboBoxSize);
		fontStyle.setPreferredSize(fontComboBoxSize);
		fontColor.setPreferredSize(fontComboBoxSize);
		fontBackColor.setPreferredSize(fontComboBoxSize);
		/* 
		 * 初始化字体选择器
		 * 1.加入标签
		 * 2.加入组件
		 * 3.间距	 
		 */
		innerBox.add(new JLabel("字体 ")); // 加入标签
		innerBox.add(fontName); // 加入组件
		innerBox.add(Box.createHorizontalStrut(comboGap)); // 横向间距
		innerBox.add(new JLabel("样式 "));
		innerBox.add(fontStyle);
		innerBox.add(Box.createHorizontalStrut(comboGap));
		innerBox.add(new JLabel("字号 "));
		innerBox.add(fontSize);
		innerBox.add(Box.createHorizontalStrut(comboGap));
		innerBox.add(new JLabel("颜色 "));
		innerBox.add(fontColor);
		innerBox.add(Box.createHorizontalStrut(comboGap));
		innerBox.add(new JLabel("背景 "));
		innerBox.add(fontBackColor);
		innerBox.add(Box.createHorizontalStrut(comboGap));
		
		fontBox.add(innerBox);
		fontBox.add(Box.createVerticalStrut(comboGap)); // 底部间距 
	}
	
	protected void initInputPanel(){
		inputBox = Box.createVerticalBox();
		
		// 输入文本区域 TODO 使用textPane取代textArea
		inputArea = new JTextArea();
		inputArea.setLineWrap(true);
		inputArea.setWrapStyleWord(true);
		// 滚动面板
        JScrollPane textScrollPane = new JScrollPane(inputArea);
        textScrollPane.setPreferredSize(inputSize);
        textScrollPane.setHorizontalScrollBarPolicy(
        		JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // 永不出现横向滚动条
        textScrollPane.setVerticalScrollBarPolicy(
        		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // 视情况出现纵向滚动条
        inputBox.add(textScrollPane);
        // 添加sendButton
        Box sendBox = Box.createHorizontalBox();
		sendButton = new JButton("发送");
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dealMessage();
			}
		});
		sendBox.add(Box.createHorizontalGlue()); // 占位条
		sendBox.add(sendButton);
		inputBox.add(sendBox);
	}
	
	protected void initHistoryPanel(){
		historyPane = new JTextPane();
		historyPane.setBackground(Color.LIGHT_GRAY);
		historyPane.setEditable(false);
		
		doc = historyPane.getStyledDocument(); // 获得JTextPane的Document
		// 添加ScrollPane
		historyScrollPane = new JScrollPane(historyPane);
		historyScrollPane.setPreferredSize(historySize);
	}
	
	
	
	// ------------------------------------- 逻辑部分 ------------------------------- //
	
	public void insertText(String text) {
		inputArea.append(text);
	}
	
	public void sendPicture(ImageIcon picture) {
		// TODO
	}
	
	public void displayPicture(File file) {
		historyPane.setCaretPosition(doc.getLength()); // 设置插入位置
		historyPane.insertIcon(new ImageIcon(file.getPath())); // 插入图片
		displayText(new FontAttrib()); // 这样做可以换行
	}
	
	public void displayText(FontAttrib textInfo) {
		if(textInfo == null)
			throw new IllegalArgumentException();
		
		ResourceManagement.debug("窗口展示消息");
		ResourceManagement.debug(textInfo);
		try { // 插入文本
			doc.insertString(doc.getLength(),
					textInfo.getText() + "\n", textInfo.getAttrSet());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 从inputArea以及编辑面板获取文本信息
	 * @return 待发送的FontAttrib
	 */
	private FontAttrib getTextInfo() {
		FontAttrib textInfo = new FontAttrib();
		textInfo.setText(inputArea.getText());
		textInfo.setName((String) fontName.getSelectedItem());
		textInfo.setSize(Integer.parseInt((String) fontSize.getSelectedItem()));
		String temp_style = (String) fontStyle.getSelectedItem();
		if (temp_style.equals("常规")) {
			
		} else if (temp_style.equals("粗体")) {
			textInfo.setStyle(FontAttrib.BOLD);
		} else if (temp_style.equals("斜体")) {
			textInfo.setStyle(FontAttrib.ITALIC);
		} else if (temp_style.equals("粗斜体")) {
			textInfo.setStyle(FontAttrib.BOLD_ITALIC);
		}
		String temp_color = (String) fontColor.getSelectedItem();
		if (temp_color.equals("黑色")) {
			textInfo.setColor(new Color(0, 0, 0));
		} else if (temp_color.equals("红色")) {
			textInfo.setColor(new Color(255, 0, 0));
		} else if (temp_color.equals("蓝色")) {
			textInfo.setColor(new Color(0, 0, 255));
		} else if (temp_color.equals("黄色")) {
			textInfo.setColor(new Color(255, 255, 0));
		} else if (temp_color.equals("绿色")) {
			textInfo.setColor(new Color(0, 255, 0));
		}
		String temp_backColor = (String) fontBackColor.getSelectedItem();
		if (!temp_backColor.equals("无色")) {
			if (temp_backColor.equals("灰色")) {
				textInfo.setBackColor(new Color(200, 200, 200));
			} else if (temp_backColor.equals("淡红")) {
				textInfo.setBackColor(new Color(255, 200, 200));
			} else if (temp_backColor.equals("淡蓝")) {
				textInfo.setBackColor(new Color(200, 200, 255));
			} else if (temp_backColor.equals("淡黄")) {
				textInfo.setBackColor(new Color(255, 255, 200));
			} else if (temp_backColor.equals("淡绿")) {
				textInfo.setBackColor(new Color(200, 255, 200));
			}
		}
		return textInfo;
	}
	
	/**
	 * 相当于发送信息
	 * 包含清除区域，向writerThread发送消息
	 */
	private void dealMessage() {
		// 获取文本信息并清空输入区域
		FontAttrib textInfo = getTextInfo();
		clearInputText();
		// 发送消息到线程
		writerThread.setTextInfo(textInfo);
		ResourceManagement.debug("窗口发送消息");
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
