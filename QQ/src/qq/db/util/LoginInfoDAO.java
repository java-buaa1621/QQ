package qq.db.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sound.midi.MidiDevice.Info;

import qq.db.info.LoginInfo;
import qq.db.info.UserInfo;
import qq.exception.AlertException;
import qq.util.ResourceManagement;

/**
 * login_info 表单格式: <br/>
 * id int unsigned not null primary key, <br/>
 * password varchar(100) not null <br/>
 */
public class LoginInfoDAO extends BaseDAO{
	
	public static String getTableName(){
		return "login_info";
	}
	
	/**
	 * @param info 登录信息
	 * @return 添加登录信息addInfo()函数所需要的sql语句
	 */
	public static String addInfoToSql(LoginInfo info) {
		String sql = "INSERT INTO " + getTableName() + " (id, password) ";
		sql += "VALUES" + "(" + info.toSql() + ");";
		return sql;
	}
	
	public static void addInfo(LoginInfo info) throws SQLException {
		if(info == null)
			throw new IllegalArgumentException("注册信息丢失");
		update(addInfoToSql(info));
	}
	
	// 根据ID号删除
	public static void deleteByID(int ID) throws SQLException {
		String sql = "DELETE FROM " + getTableName() + " WHERE id = " + ID;
		update(sql);
	}
	
	public static String getInfoByIDToSql(int userID) {
		String sql = "SELECT ID,password FROM " + getTableName() + " WHERE id = " + userID + ";";
		return sql;
	}
	
	/**
	 * 根据ID号查找
	 * @param 查询的ID
	 * @return 返回查询到的UserInfo对象,查不到返回null
	 * @throws SQLException 在数据库查询登录信息失败，此异常由用户操作引起,需要被用户看到
	 */
	public static LoginInfo getInfoByID(int userID) throws SQLException {
		LoginInfo info = null;
		select(getInfoByIDToSql(userID));
		
		if(resultSet.next()) {
			int ID = resultSet.getInt("ID");
			String password = resultSet.getString("password");
			info = new LoginInfo(ID, password);
		}
		return info;
	}
	
	public static String getPasswordByIDToSql(int userID) {
		String sql = "SELECT password FROM " + getTableName() + " WHERE id = " + userID + ";";
		return sql;
	}
	
	/**
	 * 根据ID号查找
	 * @param 查询的password
	 * @return 返回查询到的password对象,查不到返回null
	 * @throws SQLException 数据库查询操作失败
	 * @throws AlertException 不存在账户
	 */	
	public static String getPasswordByID(int userID) throws AlertException, SQLException {
		String password = null;
		select(getPasswordByIDToSql(userID));
		
		if (resultSet.next()) {
			password = resultSet.getString("password");
		} else {
			throw new AlertException("此ID不存在");
		}
		return password;
	}
	
}
