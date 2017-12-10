package qq.ui.component;

import javax.swing.JComponent;

import java.util.List;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.xml.namespace.QName;

import org.omg.CORBA.PUBLIC_MEMBER;

import qq.ui.componentFunc.JComponentFunc;
import qq.util.Constant;
import qq.util.Func;

public class FlowComponentScrollPanel extends JScrollPane {
	
	// Components[] 里面必须都是JComponent
	private JPanel innerPane;
	
	/**
	 * 创建内部内容采取流动布局jPanel
	 * @param components 组件(需要保持一致性宽高)
	 * @param gapX 横向间距(防止紧贴外层panel)
	 * @param gapY 纵向间距(防止紧贴外层panel)
	 * @param colNum 内部组件列数
	 * @param compBorderWidth 组件设置的边框宽度(增大点击面积)
	 */
	public FlowComponentScrollPanel(
			Rectangle panelPos, ArrayList<? extends JComponent> components, int gapX, int gapY, 
			int colNum, Dimension compSize, int compBorderWidth) {
		if(components == null)
			throw new IllegalArgumentException();
		
		innerPane = new JPanel(); // 初始化HeadIconPane
		innerPane.setLayout(new FlowLayout(FlowLayout.LEFT, gapX, gapY));
		for(JComponent component : components) { // 设置component并添加入innerPane
			component.setBorder(new EmptyBorder(compBorderWidth, compBorderWidth, compBorderWidth, compBorderWidth));
			innerPane.add(component);
		}
		
		// 设置在scrollPane里面的相对位置
		if(components.isEmpty()) {
			innerPane.setPreferredSize(new Dimension(0, 0));
		} else {
			int compWidth = compSize.width;
			int compHeight = compSize.height;
			int colWidth = compBorderWidth + gapX + compWidth; 
			int rowNum = Func.getRowNum(components.size(), colNum);
			int rowHeight = compBorderWidth + gapY + compHeight;
			innerPane.setPreferredSize(
					new Dimension(colNum * colWidth + 10, rowNum * rowHeight + 10)); // 加10防止最后一行紧挨底
		}
		
		this.setViewportView(innerPane);
		this.setBounds(panelPos);
		this.setHorizontalScrollBarPolicy(
        		JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // 禁止出现横向滚动条
	}
	
	/**
	 * 
	 * @param panelPos
	 * @param components
	 * @param colNum
	 * @param compSize
	 */
	public FlowComponentScrollPanel(Rectangle panelPos, ArrayList<? extends JComponent> components, 
			int colNum, Dimension compSize) {
		this(panelPos, components, 5, 5, colNum, compSize, 5);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<JComponent> getComps() {
		List<Component> list= (List<Component>)Arrays.asList(innerPane.getComponents());
		ArrayList<? super JComponent> comps = new ArrayList<Component>(list);
		return (ArrayList<JComponent>)comps;
	}
	
	public void addComp(JComponent comp) {
		addComps(JComponentFunc.toArrayList(comp));
	}
	
	public void addComps(ArrayList<JComponent> comps) {
		for(JComponent comp : comps) 
			innerPane.add(comp);
	}
	
	public void removeAll() {
		innerPane.removeAll();
	}
	
	public void resetComp(JComponent comp) {
		resetComps(JComponentFunc.toArrayList(comp));
	}
	
	public void resetComps(ArrayList<JComponent> comps) {
		removeAll();
		addComps(comps);
	}
	
}
