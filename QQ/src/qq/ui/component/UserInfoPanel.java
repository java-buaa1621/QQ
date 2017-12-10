package qq.ui.component;

import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import qq.db.info.UserInfo;
import qq.util.Constant;
import qq.util.ResourceManagement;

public class UserInfoPanel extends JPanel{
	
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
	
}
