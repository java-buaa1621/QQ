package qq.ui.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Rectangle;

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

import qq.ui.component.FlowComponentScrollPanel;
import qq.ui.componentFactory.ButtonFactory;
import qq.ui.componentFactory.PanelFactory;
import qq.ui.componentFunc.ButtonFunc;
import qq.util.*;

public class HeadIconWindow extends JFrame {

	private RegisterWindow callerWindow;
	
	private JPanel contentPane;
	private JButton confirmButton;
	private FlowComponentScrollPanel<JRadioButton> headIconPane;
	private ButtonGroup headGroup;

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
		// 图片标号从1到ResourceManagement.MAX_HEAD_ICON
		ArrayList<JRadioButton> headButtons = new ArrayList<JRadioButton>();
		headGroup = new ButtonGroup(); // 初始化ButtonGroup
		
		for(int i = Constant.START_HEAD_ICON;i <= Constant.END_HEAD_ICON; i++) {
			JRadioButton rButton = initHeadButton(i);
			headButtons.add(rButton);
			headGroup.add(rButton); // 添加进ButtonGroup
		}
		JRadioButton first = headButtons.get(toIndex(1));
		first.setSelected(true); // 1号默认选中
		
		Dimension buttonSize = new Dimension(Constant.HEAD_ICON_LENX,Constant.HEAD_ICON_LENY);
		headIconPane = PanelFactory.createFlowComponentScrollPane(
				new Rectangle(10, 30, 410, 200), headButtons, 7, buttonSize);
		contentPane.add(headIconPane);
	}
	
	/**
	 * 创建一个带有头像图标的JRadioButton
	 * @param index 图片号
	 * @return 配置好的JRadioButton
	 */
	private JRadioButton initHeadButton(int index) {
		JRadioButton headButton = ButtonFactory.createSelectChangeColorRadioButton();
		headButton.setIcon(ResourceManagement.getHeadIcon(index));
		return headButton;
	}
	
	private void initConfirmButton() {
		confirmButton = new JButton("确认");
		final HeadIconWindow headIconWindow = this;
		final FlowComponentScrollPanel<JRadioButton> headPane = headIconPane;
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				headIconWindow.dispose();
				ArrayList<JRadioButton> rButtons = headIconPane.getComps();
				int index = ButtonFunc.getSelectedIndex(rButtons);
				callerWindow.setHeadIconIndex(toIconIndex(index));
			}
		});
		confirmButton.setBounds(327, 269, 93, 23);
		contentPane.add(confirmButton);
	}
	
	//------------------------------ 逻辑部分 --------------------------//
	
	/**
	 * 
	 * @param arrayIndex
	 * @return index -> iconIndex
	 */
	private int toIconIndex(int index){
		return ++index;
	}
	
	/**
	 * 
	 * @param iconIndex
	 * @return iconIndex -> index
	 */
	private int toIndex(int iconIndex) {
		return --iconIndex;
	}
	
}
