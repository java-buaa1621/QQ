package qq.db.info;

import java.io.IOException;

import qq.util.Func;

public class FriendInfo {
	public static final int MAX_LEN = 100;
	
	private int ID;
	private boolean online;
	private String groupName;
	
	/**
	 * @param ID ��������������ID
	 * @param online true:����  false:������
	 */
	public FriendInfo(int ID, boolean online, String groupName)  {
		if (!Func.isPositiveInt(ID))
			throw new IllegalArgumentException("����ID����");
		if (!Func.isValid(groupName, MAX_LEN));
		
		this.ID = ID;
		this.online = online;
		this.groupName = groupName;
	}
	
	/** 
	 * @return sql�ַ������֣���ʽ����:
	 * 331079072,true
	 */
	public String toSql() {
		String s = "";
		s += ID; s += ",";				// ���ID
		s += online; s += ",";	 		// �������״̬
		s += "\"" + groupName + "\"";	// �������
		return s;
	}
	
	public int getID() {
		return ID;
	}
	
	public boolean getOnline() {
		return online;
	}
	
	public String getGroupName() {
		return groupName;
	}
	
}
