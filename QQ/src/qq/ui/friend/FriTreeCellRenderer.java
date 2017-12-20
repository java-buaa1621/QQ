package qq.ui.friend;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

import qq.util.ResourceManagement;

public class FriTreeCellRenderer implements TreeCellRenderer {
	
	ImageIcon arrow_left;
	ImageIcon arrow_down;
	
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
		// 节点需要不为根节点，和根节点的孩子节点
		if (leaf && fri.getParent() != tree.getModel().getRoot()) { 
			// 设置JLable的图片
            Image image = fri.getImageIcon().getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
            setIcon(new ImageIcon(image));
            setIconTextGap(0); // 设置JLable的图片与文字之间的距离	
			// 设置JLable的文字
			String text = "<html>" + fri.getUserName() + "<br/>" + fri.getUserMotto() + "</html>";
			setText(text);
		// 非叶子节点的文字为节点的nodeName
		} else { 
			JLabel midNode = new JLabel();
			midNode.setText(fri.getNodeName()); // 设置JLable的文字
			// 根据节点状态设置JLable的图片
			if (expanded) {
				midNode.setIcon(arrow_down);
			} else {
            	midNode.setIcon(arrow_left);				
			}
			return midNode;
		}
	}

}
