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
 * @param <T> ������������������ΪT������
 */
public class FlowComponentScrollPanel<T extends JComponent> extends JScrollPane {
	
	private JPanel innerPane;
	private int gapX;
	private int gapY;
	private int colNum;
	private Dimension compSize;
	private int compBorderWidth;
	
	/**
	 * �����ڲ����ݲ�ȡ��������jPanel
	 * @param scrollPanelPos ����Ĵ�С(����)
	 * @param components ���(��Ҫ����һ���Կ��)
	 * @param gapX ������(��ֹ�������panel)
	 * @param gapY ������(��ֹ�������panel)
	 * @param colNum �ڲ��������
	 * @param compSize ��������С�������߽�,����headIcon = (40,40)
	 * @param compBorderWidth ������õı߿���(���������)
	 */
	public FlowComponentScrollPanel(
			Rectangle scrollPanelPos, ArrayList<T> components, int gapX, int gapY, 
			int colNum, Dimension compSize, int compBorderWidth) {
		if(scrollPanelPos == null || components == null || compSize == null)
			throw new IllegalArgumentException();
		if(gapX < 0 || gapY < 0 || colNum <= 0 || compBorderWidth < 0)
			throw new IllegalArgumentException();
		// ��������
		this.gapX = gapX;
		this.gapY = gapY;
		this.colNum = colNum;
		this.compSize = compSize;
		this.compBorderWidth = compBorderWidth;
		// ��ʼ��HeadIconPane
		innerPane = new JPanel(); 
		innerPane.setLayout(new FlowLayout(FlowLayout.LEFT, gapX, gapY));
		// ��ʼ��JScrollPane
		this.setViewportView(innerPane);
		this.setBounds(scrollPanelPos);
		this.setHorizontalScrollBarPolicy(
        		JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // ��ֹ���ֺ��������
		
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
	 * @return ����innerPane������
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<T> getComps() {
		List<Component> list= (List<Component>)Arrays.asList(innerPane.getComponents());
		ArrayList<Component> arrayList = new ArrayList<Component>(list);
		ArrayList<? super T> comps = arrayList;
		return (ArrayList<T>)comps;
	}
	
	/**
	 * ��innerPane��������
	 * @param comp
	 */
	public void addComp(T comp) {
		addComps(Func.toArrayList(comp));
	}
	
	/**
	 * �������comp�ĳ�ʼ�����������innerPane
	 * ͬʱ��չ����comps������չinnerPane��С
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
	 * �Ƴ�innerPane������
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
		// �����С����2���߾�
		int x = (int)compSize.getWidth() + 2*compBorderWidth; 
		int y = (int)compSize.getHeight() + 2*compBorderWidth;
		comp.setPreferredSize(new Dimension(x, y));
	}
	
	/**
	 * ���ݵ�ǰinnerPane�е������̬�ı��С
	 */
	private void resizeInnerPanel() {
		ArrayList<T> components = getComps();
		// ������scrollPane��������λ��
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
			innerPane.setPreferredSize(new Dimension(x, y)); // ��10��ֹ���һ�н�����
		}
	}
	
}
