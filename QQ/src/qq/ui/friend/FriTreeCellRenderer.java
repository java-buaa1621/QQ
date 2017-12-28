package qq.ui.friend;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Panel;
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
	private static Color onClickColor = Color.LIGHT_GRAY; 
	
	public FriTreeCellRenderer(){
		arrow_left = ResourceManagement.getImageIcon("arrow_left.png"); //节点折叠时的图片
		arrow_down = ResourceManagement.getImageIcon("arrow_down.png"); //节点展开式的图片
	}
	
	
	/**
	 * @Override 为每个FriTreeNode(节点和叶子)提供各自的绘制的方式
	 */
	public Component getTreeCellRendererComponent(JTree tree, Object value,
		boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		 // 把value转换为节点
		FriTreeNode fri = (FriTreeNode) value;
		// 绘制叶子节点，节点需要不为根节点，和根节点的孩子节点
		if (leaf && fri.getParent() != tree.getModel().getRoot()) {
			UserInfoPanel panel = new UserInfoPanel(fri.getUserInfo());
			panel.setPreferredSize(UserInfoPanel.minPanelSize); 
			if(fri.onClickPaint)
				panel.setBackground(onClickColor);
			
			fri.setPanel(panel);
			return panel;
		// 绘制非叶子节点，非叶子节点的文字为节点的nodeName
		} else { 
			JLabel midLabel = new JLabel();
			midLabel.setText(fri.getNodeName()); // 设置JLable的文字
			midLabel.setPreferredSize(new Dimension(200,50));
			// 根据节点状态设置JLable的图片
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
