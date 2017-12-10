package qq.ui.component;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import qq.util.ResourceManagement;

public abstract class ContentPanelFactory {
	
	/**
	 * @return ����õĴ��б���ͼƬ�����Բ��ֵ�JPanel
	 */
	public static JPanel createDafaultContentPanel() {
		final Image backgroundImage = ResourceManagement.getImage("back5.jpg");
		JPanel contentPane = new JPanel(){
			// ͨ�����ش˷���ʵ�����ñ���ͼƬ
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
