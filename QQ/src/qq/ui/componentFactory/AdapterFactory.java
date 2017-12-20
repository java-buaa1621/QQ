package qq.ui.componentFactory;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.management.InstanceAlreadyExistsException;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import qq.ui.component.MyAction;
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
	 * �����뱳����GRAY,��ȥ��BLACK
	 * @param component Ҫʵ�ֵĿؼ�
	 * @return ouseAdapter����
	 */
	public static MouseAdapter createMouseEnterAndExitAdapter(
			final JComponent component) {
		if(component instanceof JPanel) { // panel����
			return createMouseEnterAndExitAdapter(component, Color.LIGHT_GRAY, Color.WHITE);
		} else { // ���ֲ���
			return createMouseEnterAndExitAdapter(component, Color.GRAY, Color.BLACK);
		}
	}
	
	/**
	 * ������ʱ�ı�component������ɫ
	 * @param component Ҫʵ�ֵĿؼ�
	 * @param enterColor ��������ɫ
	 * @param exitColor ����뿪��ɫ
	 * @return ʵ�ֽ����enterColor,��ȥ��exitColor��MouseAdapter����
	 */
	public static MouseAdapter createMouseEnterAndExitAdapter(
			final JComponent component, final Color enterColor, final Color exitColor) {
		if(component instanceof JPanel) {
			return new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					component.setBackground(enterColor);
				}
				public void mouseExited(MouseEvent e) {
					component.setBackground(exitColor);
				}
			};
		} else {
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
	
	/**
	 * ������keyValue��������ʱ��ִ��action�еķ���
	 * @param keyValue
	 * @param action
	 * @return
	 */
	public static KeyAdapter createKeyTypedAdapter(final char keyValue, final MyAction action) {
		if(action == null)
			throw new IllegalArgumentException();
		
		return new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == keyValue) {
					action.executeAction();
				}
			}
		};
	}
	
	public static KeyAdapter createKeyTypedAdapter(final int keyValue, final MyAction action) {
		if(action == null)
			throw new IllegalArgumentException();
		
		return createKeyTypedAdapter((char) keyValue, action);
	}
	
	
	
}
