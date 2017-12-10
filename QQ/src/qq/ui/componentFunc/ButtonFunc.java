package qq.ui.componentFunc;

import java.util.ArrayList;

import javax.swing.JButton;

import qq.util.Constant;

public class ButtonFunc {
	
	/**
	 * @param buttons
	 * @return 第一个被选择的button下标，没有被选择的返回-1
	 */
	public static int getSelectedIndex(ArrayList<? extends JButton> buttons) {
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
}
