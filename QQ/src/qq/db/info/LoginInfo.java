package qq.db.info;

import java.io.IOException;

import javax.print.attribute.standard.MediaSize.Other;

import qq.util.Func;

public class LoginInfo {
	
	public static final int MAX_LEN = 100;
	private int ID;
	private String password;
	
	/**
	 * @param ID 正整形数，每个id均不同
	 * @param password 字符串，长度小于等于100
	 */
	public LoginInfo(int ID, String password)  {
		try {
			if (!Func.isPositiveInt(ID))
				throw new IOException("登录信息输入错误, ID < 0");
			if (password == null)
				throw new IOException("登录信息输入错误, password为空");
			
			this.ID = ID;
			this.password = password;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** 
	 * @return sql字符串部分，格式例如:
	 * 331079072,"123456"
	 */
	public String toSql() {
		String s = "";
		s += ID; s += ",";			// 添加ID
		s += "\"" + password +"\"";	 	// 添加密码
		return s;
	}
	
	public int getID() {
		return ID;
	}
	
	public String getPassword() {
		return password;
	}
	
}
