package qq.ui.component;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import qq.db.info.UserInfo;
import qq.ui.componentFactory.AdapterFactory;
import qq.util.Constant;
import qq.util.ResourceManagement;

public class UserInfoPanel extends JPanel{
	
	public static final Dimension minPanelSize = new Dimension(170, 70);
	
	/**
	 * 大小由内部组件大小决定
	 * @param info
	 */
	public UserInfoPanel(UserInfo info) {
		if (info == null)
			throw new IllegalArgumentException();
		
		ImageIcon headIcon = ResourceManagement.getHeadIcon(info.getHeadIconIndex());
		String userName = info.getName();
		String userMotto = info.getMotto();
		
		setLayout(null); // 绝对布局
		
		JLabel imageLabel = new JLabel(headIcon);
		imageLabel.setBounds(10, 10, 
				Constant.HEAD_ICON_LENX, Constant.HEAD_ICON_LENY);
		add(imageLabel);
		
		JLabel userNameLabel = new JLabel(userName);
		userNameLabel.setBounds(96, 10, 48, 15);
		add(userNameLabel);
		
		JLabel userMottoLabel = new JLabel(userMotto);
		userMottoLabel.setBounds(96, 55, 180, 15);
		add(userMottoLabel);
		
		
		addMouseListener(AdapterFactory.createMouseEnterAndExitAdapter(this));
	}
	
	/**
	 * 大小有设置决定
	 * @param info
	 * @param pos
	 */
	public UserInfoPanel(UserInfo info, Rectangle pos) {
		this(info);
		setBounds(pos);
	}
	
	public void addMouseListener(final AbstractAction action) {
		addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		    	action.actionPerformed(null);
		    }
		});
	}
	
}
