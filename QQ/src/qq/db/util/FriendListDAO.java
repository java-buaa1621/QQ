package qq.db.util;

import java.sql.SQLException;
import java.util.ArrayList;

import qq.db.info.FriendInfo;

/**
 * friend_list* (*Ϊ������IDֵ)��ʽ: <br/>
 * id INT UNSIGNED NOT NULL PRIMARY KEY, <br/>
 * online BOOLEAN NOT NULL DEFAULT FALSE, <br/>
 * group_name varchar(100) not null <br/>
 */
public class FriendListDAO extends BaseDAO{

	public static String getTableName(int userID){
		return "friend_list" + userID;
	}
	
	public static String createTableByIdToSql(int userID) {
		String tableSql = "CREATE TABLE " + getTableName(userID) + "("
				+ "id INT UNSIGNED NOT NULL PRIMARY KEY,"
				+ "online BOOLEAN NOT NULL DEFAULT FALSE,"
				+ "group_name varchar(100) not null"
				+ ");";
		return tableSql;
	}
	
	public static void createTableByID(int userID) {
		try {
			update(createTableByIdToSql(userID));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static String addInfoByIDToSql(int userID, FriendInfo info) {
		String sql = "INSERT INTO " + getTableName(userID) + " (id, online, group_name) ";
		sql += "VALUES" + "(" + info.toSql() + ");";
		return sql;
	}
	
	/**
	 * ���useIDû����ӹ�info��ִ����ӣ�����ִ�����
	 * @param userID
	 * @param info
	 * @return
	 * @throws SQLException
	 */
	public static int addInfoByID(int userID, FriendInfo info) throws SQLException {
		if(info == null)
			throw new IllegalArgumentException("������Ϣ��ʧ");
		int num = 0;
		if (!FriendListDAO.haveFriend(userID, info.getID()))
			num = update(addInfoByIDToSql(userID, info));
		return num;
	}	

	public static String deleteByIDToSql(int userID, int friendID) {
		String sql = "DELETE FROM " + getTableName(userID) + " WHERE id = " + friendID;
		return sql;
	}
	
	public static void deleteByID(int userID, int friendID) throws SQLException {
		update(deleteByIDToSql(userID, friendID));
	}
	
	public static String getFriInfosByIDToSql(int userID) {
		String sql = "SELECT * FROM " + getTableName(userID) + ";";
		return sql;
	}
	
	/**
	 * @param userID 
	 * @return resultSet�е�friendInfo resultSet��Ϊ�� <br/>
	 * ������ resultSetΪ�ռ���
	 * @throws SQLException
	 */
	public static ArrayList<FriendInfo> getFriInfosByID(int userID) throws SQLException {
		resultSet = select(getFriInfosByIDToSql(userID));
		ArrayList<FriendInfo> friendInfos = new ArrayList<FriendInfo>();
		
		while (resultSet.next()) {
			int ID = resultSet.getInt("id");
			boolean online = resultSet.getBoolean("online");
			String groupName = resultSet.getString("group_name");
			FriendInfo info = new FriendInfo(ID, online, groupName);
			friendInfos.add(info);
		}
		return friendInfos;
	}
	
	/**
	 * @param userID
	 * @return userID��Ӧ�����б������к��ѵ�IDֵ
	 * @throws SQLException
	 */
	public static ArrayList<Integer> getFriIDsByID(int userID) throws SQLException {
		ArrayList<FriendInfo> friendInfos = getFriInfosByID(userID);
		ArrayList<Integer> friendIDs = new ArrayList<Integer>();
		for(FriendInfo friendInfo : friendInfos)
			friendIDs.add(friendInfo.getID());
		return friendIDs;
	}
	
	/**
	 * @param userID
	 * @param friendID
	 * @return true userID�ĺ����б���friendID <br/> 
	 * false û��
	 * @throws SQLException 
	 */
	public static boolean haveFriend(int userID, int friendID) throws SQLException {
		ArrayList<Integer> friendIDs = getFriIDsByID(userID);
		for (Integer ID : friendIDs) {
			if (ID == friendID)
				return true;
		}
		return false;
	}
	
}
