package qq.db.util;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.security.auth.login.AccountLockedException;

import qq.db.info.*;
import qq.exception.AlertException;

/**
 * �������ڶ������û�����������صĺ��� <br/>
 * �����漰���������ϱ��Ĳ������� <br/>
 * ��������DAO��ͬ�����಻���ڶ�Ӧ��� <br/>
 */
public class DBManager {

	/**
	 * �жϵ�¼�����Ƿ�ɹ�
	 * @param info �ж����ݿ��Ƿ����������¼��Ϣ
	 * @return true:�˻�����   false:�˻�������  
	 * @throws AlertException
	 * @throws SQLException 
	 */
	public static boolean checkLogin(final LoginInfo info) throws SQLException, AlertException  {
		if (info == null) 
			throw new IllegalArgumentException("��¼��Ϣ����Ϊ��");

		String dbPassword = LoginInfoDAO.getPasswordByID(info.getID());
		String password = info.getPassword();
		if(dbPassword == null || password == null) // ���Ҳ���
			return false;		
		return dbPassword.equals(password);
	}
	
	/**
	 * �����û�ʱʹ�� <br/>
	 * ����û���Ϣ����¼��Ϣ���Լ������û�ID���������б� <br/>
	 * @param userInfo 
	 * @param loginInfo
	 * @param userID
	 * @throws AlertException ����һ�й����û���Ϣ����(�������ݿ����)
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
			throw new AlertException("ע���û�ʧ�ܣ����ܵ�ԭ�����û��Ѿ�ע��,�����ݿ����");
		}
	}
	
	/**
	 * ������к��ѵ��û���Ϣ
	 * @param userID
	 * @return userID��Ӧ�˺����к��ѵ�userInfo
	 * @throws SQLException
	 */
	public static ArrayList<UserInfo> getFriUserInfos(int userID) throws SQLException {
		ArrayList<Integer> friendsID = FriendListDAO.getFriIDsByID(userID);
		ArrayList<UserInfo> friUserInfos = UserInfoDAO.getInfosByIDs(friendsID);
		return friUserInfos;
	}
	
	/**
	 * ���û����º����б��໥��ӶԷ�
	 * @param userID
	 * @param friendID
	 * @throws SQLException 
	 */
	public static void addFriend(int userID, int friendID) throws SQLException {
		if (userID == friendID)
			return;
		// TODO: change
		FriendInfo userInfo = new FriendInfo(userID, true, "�ҵĺ���");
		FriendInfo friInfo = new FriendInfo(friendID, true, "�ҵĺ���");
		FriendListDAO.addInfoByID(userID, friInfo);
		FriendListDAO.addInfoByID(friendID, userInfo);
	}
	
}
