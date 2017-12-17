package qq.ui.friend;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

import qq.ui.component.UserInfoPanel;
import qq.util.ResourceManagement;

public class FriTreeCellRenderer extends JPanel implements TreeCellRenderer {
	
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
//          Image image = fri.getImageIcon().getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
//		    setIcon(new ImageIcon(image));
//		    setIconTextGap(0); // ����JLable��ͼƬ������֮��ľ���	
			// ����JLable������
//			String text = "<html>" + fri.getUserName() + "<br/>" + fri.getUserMotto() + "</html>";
//			setText(text);
			UserInfoPanel pane = new UserInfoPanel(fri.getUserInfo());
			this.add(pane);
		// ��Ҷ�ӽڵ������Ϊ�ڵ��nodeName
		} else { 
			JLabel midLabel = new JLabel();
			midLabel.setText(fri.getNodeName()); // ����JLable������
			// ���ݽڵ�״̬����JLable��ͼƬ
			if (expanded) {
				midLabel.setIcon(arrow_down);
			} else {
				midLabel.setIcon(arrow_left);
			}
            this.add(midLabel);
		}
		return this;
	}

}
