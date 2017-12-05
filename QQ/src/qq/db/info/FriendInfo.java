package qq.db.info;

import java.io.IOException;

import qq.util.Func;

public class FriendInfo {
	public static final int MAX_LEN = 100;
	
	private int ID;
	private boolean online;
	private String groupName;
	
	/**
	 * @param ID 正整形数，好友ID
	 * @param online true:在线  false:不在线
	 */
	public FriendInfo(int ID, boolean online, String groupName)  {
		if (!Func.isPositiveInt(ID))
			throw new IllegalArgumentException("好友ID错误");
		if (!Func.isValid(groupName, MAX_LEN));
		
		this.ID = ID;
		this.online = online;
		this.groupName = groupName;
	}
	
	/** 
	 * @return sql字符串部分，格式例如:
	 * 331079072,true
	 */
	public String toSql() {
		String s = "";
		s += ID; s += ",";				// 添加ID
		s += online; s += ",";	 		// 添加在线状态
		s += "\"" + groupName + "\"";	// 添加组名
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
