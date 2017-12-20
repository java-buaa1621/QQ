package qq.ui.friend;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

import qq.ui.component.UserInfoPanel;
import qq.util.ResourceManagement;

public class FriTreeCellRenderer implements TreeCellRenderer {
	
	ImageIcon arrow_left;
	ImageIcon arrow_down;
	
	public FriTreeCellRenderer(){
		arrow_left = ResourceManagement.getImageIcon("arrow_left.png"); //�ڵ��۵�ʱ��ͼƬ
		arrow_down = ResourceManagement.getImageIcon("arrow_down.png"); //�ڵ�չ��ʽ��ͼƬ
	}
	
	
	/**
	 * @Override Ϊÿ��FriTreeNode(�ڵ��Ҷ��)�ṩ���ԵĻ��Ƶķ�ʽ
	 */
	public Component getTreeCellRendererComponent(JTree tree, Object value,
		boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		 // ��valueת��Ϊ�ڵ�
		FriTreeNode fri = (FriTreeNode) value;
		// �ڵ���Ҫ��Ϊ���ڵ㣬�͸��ڵ�ĺ��ӽڵ�
		if (leaf && fri.getParent() != tree.getModel().getRoot()) { 
			UserInfoPanel pane = new UserInfoPanel(fri.getUserInfo());
			pane.setPreferredSize(UserInfoPanel.minPanelSize);
			pane.setFocusColor();
			return pane;
		// ��Ҷ�ӽڵ������Ϊ�ڵ��nodeName
		} else { 
			JLabel midLabel = new JLabel();
			midLabel.setText(fri.getNodeName()); // ����JLable������
			midLabel.setPreferredSize(new Dimension(200,50));
			// ���ݽڵ�״̬����JLable��ͼƬ
			if (expanded) {
				midLabel.setIcon(arrow_down);
			} else {
				midLabel.setIcon(arrow_left);
			}
			midLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					ResourceManagement.debug(123);
				}
			});
			return midLabel;
		}
	}

}
