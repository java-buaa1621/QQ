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
	 * ��Ч�Ǳ�[1,ResourceManagement.MAX_HEAD_ICON]
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
		final int gapX = 5;
		final int gapY = 5;
		headIconPane = new JPanel(); // ��ʼ��HeadIconPane
		headIconPane.setLayout(new FlowLayout(FlowLayout.LEFT, gapX, gapY));
		headGroup = new ButtonGroup(); // ��ʼ��ButtonGroup
		
		// ͼƬ��Ŵ�1��ResourceManagement.MAX_HEAD_ICON
		headButtons = new JRadioButton[ResourceManagement.MAX_HEAD_ICON + 1];
		for(int i = 1;i <= ResourceManagement.MAX_HEAD_ICON; i++) {
			headButtons[i] = initHeadButton(i);
			headIconPane.add(headButtons[i]); // ��ӽ�HeadIconPane
			headGroup.add(headButtons[i]); // ��ӽ�ButtonGroup
		}
		headButtons[1].setSelected(true); // 1��Ĭ��ѡ��
		
		// ������scrollPane��������λ��
		final int borderX = 10;
		final int borderY = 10;
		int col = 7;
		int colWidth = borderX + gapX + ResourceManagement.HEAD_ICON_LENX; 
		int row = (ResourceManagement.MAX_HEAD_ICON + (col-1)) / col; // ���������õ�����
		int rowHeight = borderY + gapY + ResourceManagement.HEAD_ICON_LENY;
		headIconPane.setPreferredSize(
				new Dimension(col * colWidth + 10, row * rowHeight + 10)); // ��10��ֹ���һ�н�����
		
		scrollPane = new JScrollPane(headIconPane);
		scrollPane.setBounds(10, 30, 410, 200);
		scrollPane.setHorizontalScrollBarPolicy(
        		JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // ��ֹ���ֺ��������
		contentPane.add(scrollPane);
	}
	
	/**
	 * ����һ������ͷ��ͼ���JRadioButton�����ػ��Ʒ���
	 * @param index ͼƬ��
	 * @return ���úõ�JRadioButton
	 */
	private JRadioButton initHeadButton(int index) {
		final JRadioButton headButton = new JRadioButton(ResourceManagement.getHeadIcon(index)){
			// ���ػ��ƺ���
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
		confirmButton = new JButton("ȷ��");
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
	 * @return ���ڱ�ѡ��ķ��ر�ѡ���index<br/>
	 * �����ڱ�ѡ��ķ���0
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
