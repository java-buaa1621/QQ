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
	 * ����������ڴ��ڵĿ�ݼ���ֻҪ�����ڴ��ڣ�����key�ͻ����action
	 * @param comp
	 * @param key ��ֵ
	 * @param action
	 */
	public static void setShortCutKey(
			final JComponent comp, final char keyValue, final MyAction action) {
		KeyStroke key = KeyStroke.getKeyStroke(keyValue);
		InputMap imap = comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW); // ���ڴ��ڱ�������ж� 
		imap.put(key, key.getKeyChar());
		ActionMap amap = comp.getActionMap();
		amap.put(key.getKeyChar(), action);
	}
	
	public static void setShortCutKey(
			final JComponent comp, final int keyValue, final MyAction action) {
		
		setShortCutKey(comp, (char)keyValue, action);
	}
}
