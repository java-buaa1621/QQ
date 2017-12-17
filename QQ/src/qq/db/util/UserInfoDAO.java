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
 * user_info 表单格式: <br/>
 * id int unsigned not null primary key, <br/>
 * name varchar(100) not null, <br/>
 * name varchar(100) not null, <br/>
 * sex char(1) not null default "男", <br/>
 * age tinyint unsigned not null default "0", <br/>
 * motto varchar(100) not null, <br/>
 * head_icon int unsigned not null default 1 <br/>
 */
public class UserInfoDAO extends BaseDAO{

	public static String getTableName(){
		return "user_info";
	}
	
	/**
	 * @param info 用户信息
	 * @return 添加用户信息addInfo()函数所需要的sql语句
	 */
	public static String addInfoToSql(UserInfo info) {
		String sql = "INSERT INTO " + getTableName() + " (id, name, sex, age, motto, head_icon) ";
		sql += "VALUES" + "(" + info.toSql() + ");";
		return sql;
	}
	
	public static void addInfo(UserInfo info) throws SQLException {
		if(info == null)
			throw new IllegalArgumentException("添加用户信息丢失");
		update(addInfoToSql(info));
	}
	
	// 根据ID号删除
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
	 * 根据ID号查找
	 * @param 查询的ID
	 * @return 返回查询到的UserInfo对象, 不存在返回null
	 * @throws SQLException
	 */
	// TODO: 改变抛出异常
	public static UserInfo getInfoByID(int userID) throws SQLException {
		select(getInfoByIDToSql(userID));
		UserInfo info = nextInfo();
		return info;
	}
	
	/**
	 * 根据一组用户ID返回一组其对应的UserInfo
	 * 如果中间有为null的UserInfo,抛出NullPointerException
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
	
	public static String getInfoByNameToSql(String name) {
		String sql = "SELECT ID,name,sex,age,motto,head_icon FROM " + getTableName() 
				+ " WHERE name = " + '"' + name + '"' + ";";
		return sql;
	}
	
	public static ArrayList<UserInfo> getInfosByName(String name) throws SQLException {
		select(getInfoByNameToSql(name));
		ArrayList<UserInfo> infos = nextInfos();
		return infos;
	}
	
	/**
	 * @return 从resultSet中提取出的第一个info， 没有返回null
	 * @throws SQLException
	 */
	private static UserInfo nextInfo() throws SQLException {
		ArrayList<UserInfo> infos = nextInfos();
		if(infos.size() == 0){
			return null;
		} else {
			return infos.get(0);
		}
	}
	
	/**
	 * 从resultSet中提取出所有info，并设置resultSet为null
	 * @return
	 * @throws SQLException
	 */
	private static ArrayList<UserInfo> nextInfos() throws SQLException {
		ArrayList<UserInfo> infos = new ArrayList<UserInfo>();
		while (resultSet.next()) {
			int ID = resultSet.getInt("ID");
			String name = resultSet.getString("name");
			String sex = resultSet.getString("sex");
			int age = resultSet.getInt("age");
			String motto = resultSet.getString("motto");
			int headIconIndex = resultSet.getInt("head_icon");
			UserInfo info = new UserInfo(ID, name, sex, age, motto, headIconIndex);
			infos.add(info);
		}
		resultSet = null; // 防止其他地方提取
		return infos;
	}
	
}
