package qq.ui.component;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextField;

public final class AdapterFactory {
	
	/**
	 * 鼠标点击时显示用户输入,离开时若用户输入为空显示text
	 * @param textField 要实现的控件
	 * @param text 默认显示文本
	 * @return 重载实现的FocusAdapter子类
	 */
	public static FocusAdapter createFocusAdapter(final JTextField textField, final String text) {
		return new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(text.equals(textField.getText())){
					textField.setText("");
			    }
			}
			
			@Override
			public void focusLost(FocusEvent e) {
				if("".equals(textField.getText().trim())){
					textField.setText(text);
				}
			}
		};
	}
}
