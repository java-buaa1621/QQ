package qq.ui.componentFunc;

import java.util.ArrayList;

import javax.swing.JComponent;

public class JComponentFunc {
	
	/**
	 * 
	 * @param comp һ��JComponent
	 * @return ���ɵ�ArrayList<JComponent> 
	 */
	public static ArrayList<JComponent> toArrayList(final JComponent comp) {
		ArrayList<JComponent> comps = new ArrayList<JComponent>();
		comps.add(comp);
		return comps;
	}
	
}
