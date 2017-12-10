package qq.ui.component;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import qq.util.ResourceManagement;

public abstract class ContentPanelFactory {
	
	/**
	 * @return 创造好的带有背景图片，绝对布局的JPanel
	 */
	public static JPanel createDafaultContentPanel() {
		final Image backgroundImage = ResourceManagement.getImage("back5.jpg");
		JPanel contentPane = new JPanel(){
			// 通过重载此方法实现设置背景图片
			@Override
			protected void paintComponent(Graphics g) {
				g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
			}
		};
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(null);
		return contentPane;
	}
	
}
