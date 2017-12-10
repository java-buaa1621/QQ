package qq.ui.componentFunc;

import java.util.ArrayList;

import javax.swing.JButton;

import qq.util.Constant;

public class ButtonFunc {
	
	/**
	 * @param buttons
	 * @return ��һ����ѡ���button�±꣬û�б�ѡ��ķ���-1
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
