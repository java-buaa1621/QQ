package qq.ui.component;

import javax.naming.InitialContext;
import javax.swing.JComponent;

import java.util.List;
import java.awt.Color;
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

import qq.util.Constant;
import qq.util.Func;
import qq.util.ResourceManagement;

/**
 *
 * @param <T> 面板里面所有组件必须为T的子类
 */
public class FlowComponentScrollPanel<T extends JComponent> extends JScrollPane {
	
	private JPanel innerPane;
	private int gapX;
	private int gapY;
	private int colNum;
	private Dimension compSize;
	private int compBorderWidth;
	
	/**
	 * 创建内部内容采取流动布局jPanel
	 * @param scrollPanelPos 整体的大小(不变)
	 * @param components 组件(需要保持一致性宽高)
	 * @param gapX 横向间距(防止紧贴外层panel)
	 * @param gapY 纵向间距(防止紧贴外层panel)
	 * @param colNum 内部组件列数
	 * @param compSize 组件本体大小，不含边界,例如headIcon = (40,40)
	 * @param compBorderWidth 组件设置的边框宽度(增大点击面积)
	 */
	public FlowComponentScrollPanel(
			Rectangle scrollPanelPos, ArrayList<T> components, int gapX, int gapY, 
			int colNum, Dimension compSize, int compBorderWidth) {
		if(scrollPanelPos == null || components == null || compSize == null)
			throw new IllegalArgumentException();
		if(gapX < 0 || gapY < 0 || colNum <= 0 || compBorderWidth < 0)
			throw new IllegalArgumentException();
		// 设置属性
		this.gapX = gapX;
		this.gapY = gapY;
		this.colNum = colNum;
		this.compSize = compSize;
		this.compBorderWidth = compBorderWidth;
		// 初始化HeadIconPane
		innerPane = new JPanel(); 
		innerPane.setLayout(new FlowLayout(FlowLayout.LEFT, gapX, gapY));
		// 初始化JScrollPane
		this.setViewportView(innerPane);
		this.setBounds(scrollPanelPos);
		this.setHorizontalScrollBarPolicy(
        		JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // 禁止出现横向滚动条
		
		this.addComps(components);
	}
	
	/**
	 * 
	 * @param scrollPanelPos
	 * @param components
	 * @param colNum
	 * @param compSize
	 */
	public FlowComponentScrollPanel(Rectangle scrollPanelPos, ArrayList<T> components, 
			int colNum, Dimension compSize) {
		this(scrollPanelPos, components, 5, 5, colNum, compSize, 5);
	}
	
	/**
	 * 
	 * @return 所有innerPane里的组件
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<T> getComps() {
		List<Component> list= (List<Component>)Arrays.asList(innerPane.getComponents());
		ArrayList<Component> arrayList = new ArrayList<Component>(list);
		ArrayList<? super T> comps = arrayList;
		return (ArrayList<T>)comps;
	}
	
	/**
	 * 向innerPane里添加组件
	 * @param comp
	 */
	public void addComp(T comp) {
		addComps(Func.toArrayList(comp));
	}
	
	/**
	 * 完成所有comp的初始化，并添加入innerPane
	 * 同时扩展跟随comps数量扩展innerPane大小
	 * @param comps
	 */
	public void addComps(ArrayList<T> comps) {
		if(comps == null)
			throw new IllegalArgumentException();
		
		for(T comp : comps){
			initComp(comp);
			innerPane.add(comp);
		}
		resizeInnerPanel();
	}
	
	/**
	 * 移除innerPane里的组件
	 */
	public void removeAll() {
		innerPane.removeAll();
	}
	
	public void resetComp(T comp) {
		resetComps(Func.toArrayList(comp));
	}
	
	public void resetComps(ArrayList<T> comps) {
		removeAll();
		addComps(comps);
	}
	
	private void initComp(T comp) {
		if(comp == null)
			throw new IllegalArgumentException();
		
		comp.setBorder(
				new EmptyBorder(compBorderWidth, compBorderWidth, compBorderWidth, compBorderWidth));
		// 本体大小加上2倍边距
		int x = (int)compSize.getWidth() + 2*compBorderWidth; 
		int y = (int)compSize.getHeight() + 2*compBorderWidth;
		comp.setPreferredSize(new Dimension(x, y));
	}
	
	/**
	 * 根据当前innerPane中的组件动态改变大小
	 */
	private void resizeInnerPanel() {
		ArrayList<T> components = getComps();
		// 设置在scrollPane里面的相对位置
		if(components.isEmpty()) {
			innerPane.setPreferredSize(new Dimension(0, 0));
		} else {
			int compWidth = compSize.width;
			int compHeight = compSize.height;
			int colWidth = 2*compBorderWidth + gapX + compWidth; 
			int rowNum = Func.getRowNum(components.size(), colNum);
			int rowHeight = 2*compBorderWidth + gapY + compHeight;
			int x = colNum * colWidth + 10;
			int y = rowNum * rowHeight + 10;
//			ResourceManagement.debug(gapY);
//			ResourceManagement.debug(rowNum);
//			ResourceManagement.debug(x);
//			ResourceManagement.debug(y);
			innerPane.setPreferredSize(new Dimension(x, y)); // 加10防止最后一行紧挨底
		}
	}
	
}
