package qq.ui.component;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.management.InstanceAlreadyExistsException;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import qq.util.Constant;
import qq.util.ResourceManagement;

public final class AdapterFactory {
	
	/**
	 * 鼠标点击时显示用户输入,离开时若用户输入为空显示text
	 * @param textField 要实现的控件
	 * @param text 提示文本
	 * @return 重载实现的FocusAdapter子类
	 */
	public static FocusAdapter createFocusAdapter(final JTextField textField, final String text) {
		// 初始化时显示信息
		textField.setText(text);
		if(textField instanceof JPasswordField)
			((JPasswordField)textField).setEchoChar(Constant.pwFieldPlainTextChar);
		
		return new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				// 点击时,若为密码区域,则显示暗文
				if(textField instanceof JPasswordField)
					((JPasswordField)textField).setEchoChar(Constant.pwFieldDefaultChar);
				// 如果textField里面只有提示文本
				if(text.equals(textField.getText())){
					textField.setText("");
			    }
			}
			
			@Override
			public void focusLost(FocusEvent e) {
				// 如果内容为空或只有空格等内容
				if("".equals(textField.getText().trim())){
					// 离开时，若为密码区域，用户输入密码为空，则显示明文
					if(textField instanceof JPasswordField)
						((JPasswordField)textField).setEchoChar(Constant.pwFieldPlainTextChar);
					// 添加默认显示文本
					textField.setText(text);
				}
			}
		};
	}
	
	/**
	 * @param component 要实现的控件
	 * @param enterColor 鼠标进入颜色
	 * @param exitColor 鼠标离开颜色
	 * @return 实现进入变GRAY,出去变BLACK的MouseAdapter对象
	 */
	public static MouseAdapter createMouseEnterAndExitAdapter(
			final JComponent component) {
		return createMouseEnterAndExitAdapter(component, Color.GRAY, Color.BLACK);
	}
	
	/**
	 * @param component 要实现的控件
	 * @param enterColor 鼠标进入颜色
	 * @param exitColor 鼠标离开颜色
	 * @return 实现进入变enterColor,出去变exitColor的MouseAdapter对象
	 */
	public static MouseAdapter createMouseEnterAndExitAdapter(
			final JComponent component, final Color enterColor, final Color exitColor) {

		return new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				component.setForeground(enterColor);
			}
			public void mouseExited(MouseEvent e) {
				component.setForeground(exitColor);
			}
		};
	}
}
