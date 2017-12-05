package qq.ui.component;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextField;

public final class AdapterFactory {
	
	/**
	 * �����ʱ��ʾ�û�����,�뿪ʱ���û�����Ϊ����ʾtext
	 * @param textField Ҫʵ�ֵĿؼ�
	 * @param text Ĭ����ʾ�ı�
	 * @return ����ʵ�ֵ�FocusAdapter����
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
