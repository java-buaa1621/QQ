package qq.ui.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;

import qq.ui.component.AdapterFactory;
import qq.ui.component.ContentPanelFactory;
import qq.ui.component.IDTextField;
import qq.util.ResourceManagement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.ContentHandlerFactory;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class AddFriendWindow extends JFrame {

	private JPanel contentPane;
	private IDTextField IDField;
	private JPanel showPane;
	private JButton searchButton;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					startUp();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void startUp() {
		AddFriendWindow frame = new AddFriendWindow();
		frame.setVisible(true);
	}
	
	/**
	 * Create the frame.
	 */
	public AddFriendWindow() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("查找");
		setBounds(100, 100, 450, 300);
		setIconImage(ResourceManagement.getImage("qq_icon.png"));
		
		// 默认contentPane
		contentPane = ContentPanelFactory.createDafaultContentPanel();
		setContentPane(contentPane);
		
		initIDField();
		initShowPane();
		initSearchButton();
	}
	
	public void initIDField() {
		IDField = new IDTextField();
		IDField.setBounds(31, 39, 376, 21);
		IDField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		// 重载聚焦与失去聚焦
		IDField.addFocusListener(AdapterFactory.createFocusAdapter(IDField, "请输入查找ID"));
		contentPane.add(IDField);
	}
	
	public void initShowPane() {
		showPane = new JPanel();
		showPane.setBounds(31, 85, 376, 122);
		showPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		contentPane.add(showPane);
	}
	
	public void initSearchButton() {
		searchButton = new JButton("查找");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		searchButton.setBounds(314, 228, 93, 23);
		contentPane.add(searchButton);
	}
	
}
