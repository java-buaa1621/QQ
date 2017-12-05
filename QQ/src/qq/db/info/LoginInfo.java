package qq.db.info;

import java.io.IOException;

import javax.print.attribute.standard.MediaSize.Other;

import qq.util.Func;

public class LoginInfo {
	
	public static final int MAX_LEN = 100;
	private int ID;
	private String password;
	
	/**
	 * @param ID ����������ÿ��id����ͬ
	 * @param password �ַ���������С�ڵ���100
	 */
	public LoginInfo(int ID, String password)  {
		try {
			if (!Func.isPositiveInt(ID))
				throw new IOException("��¼��Ϣ�������, ID < 0");
			if (password == null)
				throw new IOException("��¼��Ϣ�������, passwordΪ��");
			
			this.ID = ID;
			this.password = password;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** 
	 * @return sql�ַ������֣���ʽ����:
	 * 331079072,"123456"
	 */
	public String toSql() {
		String s = "";
		s += ID; s += ",";			// ���ID
		s += "\"" + password +"\"";	 	// �������
		return s;
	}
	
	public int getID() {
		return ID;
	}
	
	public String getPassword() {
		return password;
	}
	
}
