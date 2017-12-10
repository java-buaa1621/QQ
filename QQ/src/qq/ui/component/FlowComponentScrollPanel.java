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
	
	// Components[] ������붼��JComponent
	private JPanel innerPane;
	
	/**
	 * �����ڲ����ݲ�ȡ��������jPanel
	 * @param components ���(��Ҫ����һ���Կ��)
	 * @param gapX ������(��ֹ�������panel)
	 * @param gapY ������(��ֹ�������panel)
	 * @param colNum �ڲ��������
	 * @param compBorderWidth ������õı߿���(���������)
	 */
	public FlowComponentScrollPanel(
			Rectangle panelPos, ArrayList<? extends JComponent> components, int gapX, int gapY, 
			int colNum, Dimension compSize, int compBorderWidth) {
		if(components == null)
			throw new IllegalArgumentException();
		
		innerPane = new JPanel(); // ��ʼ��HeadIconPane
		innerPane.setLayout(new FlowLayout(FlowLayout.LEFT, gapX, gapY));
		for(JComponent component : components) { // ����component�������innerPane
			component.setBorder(new EmptyBorder(compBorderWidth, compBorderWidth, compBorderWidth, compBorderWidth));
			innerPane.add(component);
		}
		
		// ������scrollPane��������λ��
		if(components.isEmpty()) {
			innerPane.setPreferredSize(new Dimension(0, 0));
		} else {
			int compWidth = compSize.width;
			int compHeight = compSize.height;
			int colWidth = compBorderWidth + gapX + compWidth; 
			int rowNum = Func.getRowNum(components.size(), colNum);
			int rowHeight = compBorderWidth + gapY + compHeight;
			innerPane.setPreferredSize(
					new Dimension(colNum * colWidth + 10, rowNum * rowHeight + 10)); // ��10��ֹ���һ�н�����
		}
		
		this.setViewportView(innerPane);
		this.setBounds(panelPos);
		this.setHorizontalScrollBarPolicy(
        		JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // ��ֹ���ֺ��������
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
