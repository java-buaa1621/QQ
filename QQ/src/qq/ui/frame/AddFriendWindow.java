package qq.ui.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;

import qq.db.info.UserInfo;
import qq.ui.component.*;
import qq.ui.componentFactory.AdapterFactory;
import qq.ui.componentFactory.PanelFactory;
import qq.util.Constant;
import qq.util.ResourceManagement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.ContentHandlerFactory;
import java.util.ArrayList;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class AddFriendWindow extends JFrame {

	private JPanel contentPane;
	private IDTextField IDField;
	private FlowComponentScrollPanel showPane;
	private JButton searchButton;
	private JTextField textField;
	
	private static Dimension infoPanelSize = new Dimension(170, 70);

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
		setTitle("≤È’“");
		setBounds(100, 100, 450, 300);
		setIconImage(ResourceManagement.getImage("qq_icon.png"));
		
		// ƒ¨»œcontentPane
		contentPane = PanelFactory.createDafaultContentPanel();
		setContentPane(contentPane);
		
		initIDField();
		initShowPane();
		initSearchButton();
		UserInfoPanel uip = 
				new UserInfoPanel(new UserInfo(331079072, "zzx", "ƒ–", 20, "i'm brillient", 5));
		uip.setPreferredSize(infoPanelSize);
		uip.setBackground(Color.BLUE);
		showPane.add(uip);
		UserInfoPanel uip2 = 
				new UserInfoPanel(new UserInfo(331079072, "zzx", "ƒ–", 20, "i'm brillient", 5));
		uip2.setPreferredSize(infoPanelSize);
		uip2.setBackground(Color.BLUE);
		showPane.add(uip2);

	}
	
	public void initIDField() {
		IDField = new IDTextField();
		IDField.setBounds(31, 39, 376, 21);
		IDField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		IDField.addFocusListener(
				AdapterFactory.createTextFieldFocusAdapter(IDField, " «Î ‰»Î≤È’“ID"));
		contentPane.add(IDField);
	}
	
	public void initShowPane() {
		Rectangle panelPos = new Rectangle(31, 85, 376, 122);
		ArrayList<JComponent> components = new ArrayList<JComponent>();
		Dimension compSize = Constant.userInfoPanelSize;
		
		showPane = PanelFactory.createFlowComponentScrollPane(
				panelPos, components, 3, compSize);
		showPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		contentPane.add(showPane);
	}
	
	public void initSearchButton() {
		searchButton = new JButton("≤È’“");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		searchButton.setBounds(314, 228, 93, 23);
		contentPane.add(searchButton);
	}
	
	public void updateShowPane(ArrayList<UserInfo> userInfos) {
		showPane.removeAll();
		
		showPane.add(
				new UserInfoPanel(new UserInfo(331079072, "zzx", "ƒ–", 20, "i'm brillient", 5)));
	}
	
}
