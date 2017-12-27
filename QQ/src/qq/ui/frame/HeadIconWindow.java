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
		setTitle("ѡ��ͷ��");
		setIconImage(ResourceManagement.getImage("qq_icon.png"));
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		initHeadIconPanel();
		initConfirmButton();
	}
	
	/**
	 * ��ʼ��ͷ����壬������ʼ��headIconPane��headButton�� scrollPane������
	 * @see HeadIconWindow#initHeadButton
	 */
	private void initHeadIconPanel() {
		// ͼƬ��Ŵ�1��ResourceManagement.MAX_HEAD_ICON
		ArrayList<JRadioButton> headButtons = new ArrayList<JRadioButton>();
		headGroup = new ButtonGroup(); // ��ʼ��ButtonGroup
		
		for(int i = Constant.START_HEAD_ICON;i <= Constant.END_HEAD_ICON; i++) {
			JRadioButton rButton = initHeadButton(i);
			headButtons.add(rButton);
			headGroup.add(rButton); // ��ӽ�ButtonGroup
		}
		JRadioButton first = headButtons.get(toIndex(1));
		first.setSelected(true); // 1��Ĭ��ѡ��
		
		Dimension buttonSize = new Dimension(Constant.HEAD_ICON_LENX,Constant.HEAD_ICON_LENY);
		headIconPane = PanelFactory.createFlowComponentScrollPane(
				new Rectangle(10, 30, 410, 200), headButtons, 7, buttonSize);
		contentPane.add(headIconPane);
	}
	
	/**
	 * ����һ������ͷ��ͼ���JRadioButton
	 * @param index ͼƬ��
	 * @return ���úõ�JRadioButton
	 */
	private JRadioButton initHeadButton(int index) {
		JRadioButton headButton = ButtonFactory.createSelectChangeColorRadioButton();
		headButton.setIcon(ResourceManagement.getHeadIcon(index));
		return headButton;
	}
	
	private void initConfirmButton() {
		confirmButton = new JButton("ȷ��");
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
	
	//------------------------------ �߼����� --------------------------//
	
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
