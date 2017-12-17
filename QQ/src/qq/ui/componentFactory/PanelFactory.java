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
	
	/*
	public static JScrollPane createFlowComponentPane(
			Rectangle panelPos, ArrayList<JComponent> components, int gapX, int gapY, 
			int colNum, int compBorderWidth) {
		
		JPanel innerPane = new JPanel(); // 初始化HeadIconPane
		innerPane.setLayout(new FlowLayout(FlowLayout.LEFT, gapX, gapY));
		for(JComponent component : components) { // 设置component并添加入innerPane
			component.setBorder(new EmptyBorder(compBorderWidth, compBorderWidth, compBorderWidth, compBorderWidth));
			innerPane.add(component);
		}
		
		// 设置在scrollPane里面的相对位置
		int colWidth = compBorderWidth + gapX + compWidth; 
		int rowNum = Func.getRowNum(components.size(), colNum);
		int rowHeight = compBorderWidth + gapY + compHeight;
		innerPane.setPreferredSize(
				new Dimension(colNum * colWidth + 10, rowNum * rowHeight + 10)); // 加10防止最后一行紧挨底
		
		JScrollPane outerPane = new JScrollPane(innerPane);
		outerPane.setBounds(panelPos);
		return outerPane;
	}*/
	
	/**
	 * 创建内部内容采取流动布局jScrollPane
	 * @param T
	 */
	public static <T extends JComponent> FlowComponentScrollPanel<T> createFlowComponentScrollPane(
			Rectangle panelPos, ArrayList<T> components, int colNum, Dimension compSize) {
		return new FlowComponentScrollPanel<T>(panelPos, components, colNum, compSize);
	}
	
	public static <T extends JComponent> FlowComponentScrollPanel<T> createFlowComponentScrollPane(
			Rectangle panelPos, ArrayList<T> components, 
			int gapX, int gapY, int colNum, Dimension compSize, int compBorderWidth) {
		
		return new FlowComponentScrollPanel<T>(
				panelPos, components, gapX, gapY, colNum, compSize, compBorderWidth);
	}
	
}
