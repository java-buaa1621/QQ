package qq.ui.componentFactory;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JRadioButton;

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
	
}
