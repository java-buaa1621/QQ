package qq.ui.componentFunc;

import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import qq.util.Constant;

public class ButtonFunc {
	
	/**
	 * @param buttons
	 * @return ��һ����ѡ���button�±꣬û�б�ѡ��ķ���-1
	 */
	public static int getSelectedIndex(ArrayList<? extends AbstractButton> buttons) {
		int ans = -1;
		int total = buttons.size();
		for(int i = 0; i < total; i++){
			if(buttons.get(i).isSelected()){
				ans = i;
				break;
			}
		}
		return ans;
	}
	
	/**
	 * ��ӽ�outerPane�����þ���λ��
	 * @param button
	 * @param outerPane
	 * @param pos
	 */
	public static void initButton(
			AbstractButton button, JPanel outerPane, Rectangle pos) {
		if(button == null || outerPane == null || pos == null)
			throw new IllegalArgumentException();
		
		outerPane.add(button);
		button.setBounds(pos);
	}
	
	/**
	 * ��ӽ�outerPane����ӽ�bGroup�����þ���λ��,
	 * @param button
	 * @param outerPane
	 * @param bGroup
	 * @param pos
	 */
	public static void initButton(
			AbstractButton button, JPanel outerPane, ButtonGroup bGroup, Rectangle pos) {
		if(bGroup == null)
			throw new IllegalArgumentException();
		
		initButton(button, outerPane, pos);
		bGroup.add(button);
	}
	
	/**
	 * ��ӽ�outerPane����ӽ�bGroup�����þ���λ��,�����ı�
	 * @param button
	 * @param outerPane
	 * @param bGroup
	 * @param pos
	 */
	public static void initButton(
			AbstractButton button, JPanel outerPane, ButtonGroup bGroup, Rectangle pos, String text) {
		if(text == null)
			throw new IllegalArgumentException();
		
		initButton(button, outerPane, bGroup, pos);
		button.setText(text);
	}
	
	/**
	 * 
	 * @param button
	 * @param outerPane
	 * @param bGroup
	 * @param pos
	 * @param text
	 * @param isOpaque true:��͸�� false:͸��
	 */
	public static void initButton(
			AbstractButton button, JPanel outerPane, ButtonGroup bGroup, Rectangle pos, String text, 
			boolean isOpaque) {

		initButton(button, outerPane, bGroup, pos, text);
		button.setContentAreaFilled(isOpaque);
	}
	
	public static void buildGroup(ArrayList<? extends AbstractButton> buttons, ButtonGroup bGroup) {
		if(buttons == null || bGroup == null)
			throw new IllegalArgumentException();
		
		for(AbstractButton button : buttons) {
			bGroup.add(button);
		}
	}
	
	public static void setIsSelected(ArrayList<JRadioButton> rButtons, int index) {
		if(rButtons.size() <= index || index < 0)
			throw new IllegalArgumentException();
		JRadioButton rButton = rButtons.get(index);
		rButton.setSelected(true);
	}
	
	public static void setIsSelected(ArrayList<JRadioButton> rButtons, ArrayList<Integer> indexes) {
		for (Integer i : indexes) {
			setIsSelected(rButtons, i);
		}
	}
	
}
