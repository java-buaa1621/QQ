package qq.ui.componentFunc;

import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import qq.ui.component.MyAction;

public class ComponentFunc {
	
	/**
	 * 设置组件所在窗口的快捷键，只要焦点在窗口，按下key就会产生action
	 * @param comp
	 * @param key 键值
	 * @param action
	 */
	public static void setShortCutKey(
			final JComponent comp, final char keyValue, final MyAction action) {
		KeyStroke key = KeyStroke.getKeyStroke(keyValue);
		InputMap imap = comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW); // 所在窗口被键入就行动 
		imap.put(key, key.getKeyChar());
		ActionMap amap = comp.getActionMap();
		amap.put(key.getKeyChar(), action);
	}
	
	public static void setShortCutKey(
			final JComponent comp, final int keyValue, final MyAction action) {
		
		setShortCutKey(comp, (char)keyValue, action);
	}
}
