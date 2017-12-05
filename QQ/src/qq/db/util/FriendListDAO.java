package qq.db.util;

import java.sql.SQLException;
import java.util.ArrayList;

import qq.db.info.FriendInfo;

/**
 * friend_list* (*为正整形ID值)格式: <br/>
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
	 * 如果useID没有添加过info就执行添加，否则不执行添加
	 * @param userID
	 * @param info
	 * @return
	 * @throws SQLException
	 */
	public static int addInfoByID(int userID, FriendInfo info) throws SQLException {
		if(info == null)
			throw new IllegalArgumentException("好友信息丢失");
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
	 * @return resultSet中的friendInfo resultSet不为空 <br/>
	 * 空数组 resultSet为空集合
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
	 * @return userID对应好友列表中所有好友的ID值
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
	 * @return true userID的好友列表有friendID <br/> 
	 * false 没有
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
