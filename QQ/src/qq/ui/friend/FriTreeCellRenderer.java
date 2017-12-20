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
			// ����JLable��ͼƬ
            Image image = fri.getImageIcon().getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
            setIcon(new ImageIcon(image));
            setIconTextGap(0); // ����JLable��ͼƬ������֮��ľ���	
			// ����JLable������
			String text = "<html>" + fri.getUserName() + "<br/>" + fri.getUserMotto() + "</html>";
			setText(text);
		// ��Ҷ�ӽڵ������Ϊ�ڵ��nodeName
		} else { 
			JLabel midNode = new JLabel();
			midNode.setText(fri.getNodeName()); // ����JLable������
			// ���ݽڵ�״̬����JLable��ͼƬ
			if (expanded) {
				midNode.setIcon(arrow_down);
			} else {
            	midNode.setIcon(arrow_left);				
			}
			return midNode;
		}
	}

}
