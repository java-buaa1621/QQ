package qq.ui.component;

import javax.swing.JPasswordField;

import qq.db.info.LoginInfo;
import qq.exception.AlertException;

public class PasswordField extends JPasswordField{
	/**
	 * @return textת��������
	 * @throws AlertException ���볬��100�ֻ�����Ϊ��
	 */
	public String getPasswordString() throws AlertException {
		String password = new String(this.getPassword()); 
		if (password.length() > LoginInfo.MAX_LEN)
			throw new AlertException("���벻�ܳ���100��");
		else if (password.length() == 0) 
			throw new AlertException("���벻��Ϊ��");
		
		return password;
	}
	
}
