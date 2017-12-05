package qq.db.util;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.security.auth.login.AccountLockedException;

import qq.db.info.*;
import qq.exception.AlertException;

/**
 * 本类用于定义与用户操作紧密相关的函数 <br/>
 * 或者涉及两个及以上表格的操作函数 <br/>
 * 与其他类DAO不同，本类不存在对应表格 <br/>
 */
public class DBManager {

	/**
	 * 判断登录操作是否成功
	 * @param info 判断数据库是否存在这条登录信息
	 * @return true:账户存在   false:账户不存在  
	 * @throws AlertException
	 * @throws SQLException 
	 */
	public static boolean checkLogin(final LoginInfo info) throws SQLException, AlertException  {
		if (info == null) 
			throw new IllegalArgumentException("登录信息不能为空");

		String dbPassword = LoginInfoDAO.getPasswordByID(info.getID());
		String password = info.getPassword();
		if(dbPassword == null || password == null) // 查找不到
			return false;		
		return dbPassword.equals(password);
	}
	
	/**
	 * 创建用户时使用 <br/>
	 * 添加用户信息表，登录信息表以及根据用户ID创建好友列表 <br/>
	 * @param userInfo 
	 * @param loginInfo
	 * @param userID
	 * @throws AlertException 包含一切构建用户信息出错(包括数据库错误)
	 */
	public static void updateUserRelatedDatabase(final UserInfo userInfo, 
		final LoginInfo loginInfo, int userID) throws AlertException {
		String sql1 = UserInfoDAO.addInfoToSql(userInfo);
		String sql2 = LoginInfoDAO.addInfoToSql(loginInfo);
		String sql3 = FriendListDAO.createTableByIdToSql(userID);
		String[] sqls = {sql1, sql2, sql3};
		try {
			BaseDAO.update(sqls);
		} catch (SQLException e) {
			throw new AlertException("注册用户失败！可能的原因是用户已经注册,或数据库出错");
		}
	}
	
	/**
	 * 获得所有好友的用户信息
	 * @param userID
	 * @return userID对应账号所有好友的userInfo
	 * @throws SQLException
	 */
	public static ArrayList<UserInfo> getFriUserInfos(int userID) throws SQLException {
		ArrayList<Integer> friendsID = FriendListDAO.getFriIDsByID(userID);
		ArrayList<UserInfo> friUserInfos = UserInfoDAO.getInfosByIDs(friendsID);
		return friUserInfos;
	}
	
	/**
	 * 两用户更新好友列表，相互添加对方
	 * @param userID
	 * @param friendID
	 * @throws SQLException 
	 */
	public static void addFriend(int userID, int friendID) throws SQLException {
		if (userID == friendID)
			return;
		// TODO: change
		FriendInfo userInfo = new FriendInfo(userID, true, "我的好友");
		FriendInfo friInfo = new FriendInfo(friendID, true, "我的好友");
		FriendListDAO.addInfoByID(userID, friInfo);
		FriendListDAO.addInfoByID(friendID, userInfo);
	}
	
}
