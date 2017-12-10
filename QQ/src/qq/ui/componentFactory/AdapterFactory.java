package qq.ui.componentFactory;

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
	 * �����ʱ��ʾ�û�����,�뿪ʱ���û�����Ϊ����ʾtext
	 * @param textField Ҫʵ�ֵĿؼ�
	 * @param text ��ʾ�ı�
	 * @return ����ʵ�ֵ�FocusAdapter����
	 */
	public static FocusAdapter createTextFieldFocusAdapter(final JTextField textField, final String text) {
		// ��ʼ��ʱ��ʾ��Ϣ
		textField.setText(text);
		if(textField instanceof JPasswordField)
			((JPasswordField)textField).setEchoChar(Constant.pwFieldPlainTextChar);
		
		return new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				// ���ʱ,��Ϊ��������,����ʾ����
				if(textField instanceof JPasswordField)
					((JPasswordField)textField).setEchoChar(Constant.pwFieldDefaultChar);
				// ���textField����ֻ����ʾ�ı�
				if(text.equals(textField.getText())){
					textField.setText("");
			    }
			}
			
			@Override
			public void focusLost(FocusEvent e) {
				// �������Ϊ�ջ�ֻ�пո������
				if("".equals(textField.getText().trim())){
					// �뿪ʱ����Ϊ���������û���������Ϊ�գ�����ʾ����
					if(textField instanceof JPasswordField)
						((JPasswordField)textField).setEchoChar(Constant.pwFieldPlainTextChar);
					// ���Ĭ����ʾ�ı�
					textField.setText(text);
				}
			}
		};
	}
	
	/**
	 * @param component Ҫʵ�ֵĿؼ�
	 * @param enterColor ��������ɫ
	 * @param exitColor ����뿪��ɫ
	 * @return ʵ�ֽ����GRAY,��ȥ��BLACK��MouseAdapter����
	 */
	public static MouseAdapter createMouseEnterAndExitAdapter(
			final JComponent component) {
		return createMouseEnterAndExitAdapter(component, Color.GRAY, Color.BLACK);
	}
	
	/**
	 * @param component Ҫʵ�ֵĿؼ�
	 * @param enterColor ��������ɫ
	 * @param exitColor ����뿪��ɫ
	 * @return ʵ�ֽ����enterColor,��ȥ��exitColor��MouseAdapter����
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
