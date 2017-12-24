package qq.ui.componentFactory;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import qq.ui.component.FlowComponentScrollPanel;
import qq.util.Constant;
import qq.util.Func;
import qq.util.ResourceManagement;

public abstract class PanelFactory {
	
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
	
//  以下代码块用于debugs
//	editPane = new JPanel();
//	editPane.setBounds(0, 360, 456, 25);
//	editPane.setLayout(new FlowLayout(0, 5, FlowLayout.LEFT));
//	contentPane.add(editPane);
//	
//	// 设置emojiButton
//	Dimension iconSize = new Dimension(18, 18);
//	Dimension compSize = new Dimension(22, 22);
//	emojiButton = new JButton(
//			ResourceManagement.getScaledIcon("emoticon.png", iconSize));
//	emojiButton.setBorder(new EmptyBorder(4, 0, 0, 0));
//	emojiButton.setPreferredSize(compSize);
//	editPane.add(emojiButton);
	
	/**
	 * 简化版createFlowComponentScrollPane
	 * 创建内部内容采取流动布局jScrollPanel
	 * @param panelPos
	 * @param components
	 * @param colNum
	 * @param compSize
	 * @return
	 */
	public static <T extends JComponent> FlowComponentScrollPanel<T> createFlowComponentScrollPane(
			Rectangle panelPos, ArrayList<T> components, int colNum, Dimension compSize) {
		
		return new FlowComponentScrollPanel<T>(panelPos, components, colNum, compSize);
	}
	
	/**
	 * 
	 * @param panelPos
	 * @param comp
	 * @param colNum
	 * @param compSize
	 * @return
	 */
	public static <T extends JComponent> FlowComponentScrollPanel<T> createFlowComponentScrollPane(
			Rectangle panelPos, T comp, int colNum, Dimension compSize) {
		
		return new FlowComponentScrollPanel<T>(panelPos, Func.toArrayList(comp), colNum, compSize);
	}
	
	/**
	 * 完全版createFlowComponentScrollPane
	 * @param panelPos
	 * @param components
	 * @param gapX
	 * @param gapY
	 * @param colNum
	 * @param compSize
	 * @param compBorderWidth
	 * @return
	 */
	public static <T extends JComponent> FlowComponentScrollPanel<T> createFlowComponentScrollPane(
			Rectangle panelPos, ArrayList<T> components, 
			int gapX, int gapY, int colNum, Dimension compSize, int compBorderWidth) {
		
		return new FlowComponentScrollPanel<T>(
				panelPos, components, gapX, gapY, colNum, compSize, compBorderWidth);
	}
	
	/**
	 * 
	 * @param panelPos
	 * @param comp
	 * @param gapX
	 * @param gapY
	 * @param colNum
	 * @param compSize
	 * @param compBorderWidth
	 * @return
	 */
	public static <T extends JComponent> FlowComponentScrollPanel<T> createFlowComponentScrollPane(
			Rectangle panelPos, T comp, 
			int gapX, int gapY, int colNum, Dimension compSize, int compBorderWidth) {
		
		return new FlowComponentScrollPanel<T>(
				panelPos, Func.toArrayList(comp), gapX, gapY, colNum, compSize, compBorderWidth);
	}
	
}
