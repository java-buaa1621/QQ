package qq.ui.componentFactory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import qq.ui.componentFunc.ButtonFunc;

public class ButtonFactory {
	public static JRadioButton createSelectChangeColorRadioButton(
			final Color selectColor, final Color notSelectColor) {
		
		return new JRadioButton() {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				if(isSelected()){
					setBackground(selectColor);
				} else {
					setBackground(notSelectColor);
				}
			}
		};
	}
	
	public static JRadioButton createSelectChangeColorRadioButton() {
		return createSelectChangeColorRadioButton(Color.YELLOW, Color.WHITE);
	}
	
	/**
	 * 创建JRadioButton数组并初始化
	 * @param num 
	 * @param contents 
	 * @param positions
	 * @param outerPane
	 * @param bGroup
	 * @return
	 */
	public static ArrayList<JRadioButton> createJRadioButtons(
			int num, String[] contents, Rectangle[] positions, JPanel outerPane, ButtonGroup bGroup,
			boolean isOpaque) {
		if(contents.length != num || positions.length != num)
			throw new IllegalArgumentException();
		
		ArrayList<JRadioButton> jRadioButtons = new ArrayList<JRadioButton>();
		for(int i = 0; i < num; i++) {
			JRadioButton rButton = new JRadioButton();
			ButtonFunc.initButton(rButton, outerPane, bGroup, positions[i], contents[i], isOpaque);
			jRadioButtons.add(rButton);
		}
		return jRadioButtons;
	}
	
	
}
