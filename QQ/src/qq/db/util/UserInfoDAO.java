package qq.db.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import qq.db.info.UserInfo;
import qq.exception.AlertException;
import qq.util.ResourceManagement;

/**
 * user_info ����ʽ: <br/>
 * id int unsigned not null primary key, <br/>
 * name varchar(100) not null, <br/>
 * name varchar(100) not null, <br/>
 * sex char(1) not null default "��", <br/>
 * age tinyint unsigned not null default "0", <br/>
 * motto varchar(100) not null, <br/>
 * head_icon int unsigned not null default 1 <br/>
 */
public class UserInfoDAO extends BaseDAO{

	public static String getTableName(){
		return "user_info";
	}
	
	/**
	 * @param info �û���Ϣ
	 * @return ����û���ϢaddInfo()��������Ҫ��sql���
	 */
	public static String addInfoToSql(UserInfo info) {
		String sql = "INSERT INTO " + getTableName() + " (id, name, sex, age, motto, head_icon) ";
		sql += "VALUES" + "(" + info.toSql() + ");";
		return sql;
	}
	
	public static void addInfo(UserInfo info) throws SQLException {
		if(info == null)
			throw new IllegalArgumentException("����û���Ϣ��ʧ");
		update(addInfoToSql(info));
	}
	
	// ����ID��ɾ��
	public static void deleteByID(int ID) throws SQLException {
		String sql = "DELETE FROM " + getTableName() + " WHERE id = " + ID;
		update(sql);
	}
	
	public static String getInfoByIDToSql(int userID) {
		String sql = "SELECT ID,name,sex,age,motto,head_icon FROM " + getTableName() 
				+ " WHERE id = " + userID + ";";
		return sql;
	}
	
	/**
	 * ����ID�Ų���
	 * @param ��ѯ��ID
	 * @return ���ز�ѯ����UserInfo����, �����ڷ���null
	 * @throws SQLException
	 */
	// TODO: �ı��׳��쳣
	public static UserInfo getInfoByID(int userID) throws SQLException {
		select(getInfoByIDToSql(userID));
		UserInfo info = null;
		if(resultSet.next()) {
			int ID = resultSet.getInt("ID");
			String name = resultSet.getString("name");
			String sex = resultSet.getString("sex");
			int age = resultSet.getInt("age");
			String motto = resultSet.getString("motto");
			int headIconIndex = resultSet.getInt("head_icon");
			info = new UserInfo(ID, name, sex, age, motto, headIconIndex);
		}
		return info;
	}
	
	/**
	 * ����һ���û�ID����һ�����Ӧ��UserInfo
	 * ����м���Ϊnull��UserInfo,�׳�NullPointerException
	 * @param userIDs
	 * @return UserInfos
	 * @throws SQLException 
	 */
	public static ArrayList<UserInfo> getInfosByIDs(ArrayList<Integer> userIDs) throws SQLException {
		if(userIDs == null)
			throw new IllegalArgumentException();
		
		ArrayList<UserInfo> userInfos = new ArrayList<UserInfo>();
		for (Integer userID : userIDs) {
			UserInfo info = getInfoByID(userID);
			if(info == null)
				throw new NullPointerException();
			userInfos.add(info);
		}
		return userInfos;
	}
	
}
