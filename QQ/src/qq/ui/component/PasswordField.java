package qq.ui.component;

import javax.swing.JPasswordField;

import qq.db.info.LoginInfo;
import qq.exception.AlertException;

public class PasswordField extends JPasswordField{
	/**
	 * @return text转化的密码
	 * @throws AlertException 密码超过100字或密码为空
	 */
	public String getPasswordString() throws AlertException {
		String password = new String(this.getPassword()); 
		if (password.length() > LoginInfo.MAX_LEN)
			throw new AlertException("密码不能超过100字");
		else if (password.length() == 0) 
			throw new AlertException("密码不能为空");
		
		return password;
	}
	
}
