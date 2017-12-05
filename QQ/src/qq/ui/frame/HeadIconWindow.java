package qq.ui.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.text.rtf.RTFEditorKit;

import java.awt.FlowLayout;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.JScrollPane;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.mysql.jdbc.Constants;

import qq.util.ResourceManagement;

public class HeadIconWindow extends JFrame {

	private RegisterWindow callerWindow;
	
	private JPanel contentPane;
	private JButton confirmButton;
	private JScrollPane scrollPane;
	private JPanel headIconPane;
	private ButtonGroup headGroup;
	
	/**
	 * 有效角标[1,ResourceManagement.MAX_HEAD_ICON]
	 */
	private JRadioButton[] headButtons; 
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					startUp(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void startUp(RegisterWindow callerWindow) {
		HeadIconWindow frame = new HeadIconWindow(callerWindow);
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	private HeadIconWindow(RegisterWindow callerWindow) {
		this.callerWindow = callerWindow;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 446, 341);
		setTitle("选择头像");
		setIconImage(ResourceManagement.getImage("qq_icon.png"));
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		initHeadIconPanel();
		initConfirmButton();
	}
	
	/**
	 * 初始化头像面板，包含初始化headIconPane，headButton， scrollPane三部分
	 * @see HeadIconWindow#initHeadButton
	 */
	private void initHeadIconPanel() {
		final int gapX = 5;
		final int gapY = 5;
		headIconPane = new JPanel(); // 初始化HeadIconPane
		headIconPane.setLayout(new FlowLayout(FlowLayout.LEFT, gapX, gapY));
		headGroup = new ButtonGroup(); // 初始化ButtonGroup
		
		// 图片标号从1到ResourceManagement.MAX_HEAD_ICON
		headButtons = new JRadioButton[ResourceManagement.MAX_HEAD_ICON + 1];
		for(int i = 1;i <= ResourceManagement.MAX_HEAD_ICON; i++) {
			headButtons[i] = initHeadButton(i);
			headIconPane.add(headButtons[i]); // 添加进HeadIconPane
			headGroup.add(headButtons[i]); // 添加进ButtonGroup
		}
		headButtons[1].setSelected(true); // 1号默认选中
		
		// 设置在scrollPane里面的相对位置
		final int borderX = 10;
		final int borderY = 10;
		int col = 7;
		int colWidth = borderX + gapX + ResourceManagement.HEAD_ICON_LENX; 
		int row = (ResourceManagement.MAX_HEAD_ICON + (col-1)) / col; // 根据列数得到行数
		int rowHeight = borderY + gapY + ResourceManagement.HEAD_ICON_LENY;
		headIconPane.setPreferredSize(
				new Dimension(col * colWidth + 10, row * rowHeight + 10)); // 加10防止最后一行紧挨底
		
		scrollPane = new JScrollPane(headIconPane);
		scrollPane.setBounds(10, 30, 410, 200);
		scrollPane.setHorizontalScrollBarPolicy(
        		JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // 禁止出现横向滚动条
		contentPane.add(scrollPane);
	}
	
	/**
	 * 创建一个带有头像图标的JRadioButton，重载绘制方法
	 * @param index 图片号
	 * @return 配置好的JRadioButton
	 */
	private JRadioButton initHeadButton(int index) {
		final JRadioButton headButton = new JRadioButton(ResourceManagement.getHeadIcon(index)){
			// 重载绘制函数
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				if(isSelected()){
					setBackground(Color.YELLOW);
				} else {
					setBackground(Color.WHITE);
				}
			}
		};
		headButton.setBorder(new EmptyBorder(5, 5, 5, 5));
		return headButton;
	}
	
	private void initConfirmButton() {
		confirmButton = new JButton("确认");
		final HeadIconWindow headIconWindow = this;
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				headIconWindow.dispose();
				callerWindow.setHeadIconIndex(getHeadIconIndex());
			}
		});
		confirmButton.setBounds(327, 269, 93, 23);
		contentPane.add(confirmButton);
	}
	
	/**
	 * @return 存在被选择的返回被选择的index<br/>
	 * 不存在被选择的返回0
	 */
	private int getHeadIconIndex() {
		int ans = 0;
		for(int i = 1; i <= ResourceManagement.MAX_HEAD_ICON; i++){
			if(headButtons[i].isSelected()){
				ans = i;
			}
		}
		return ans;
	}
}
