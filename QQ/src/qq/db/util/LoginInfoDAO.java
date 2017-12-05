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
 * login_info ����ʽ: <br/>
 * id int unsigned not null primary key, <br/>
 * password varchar(100) not null <br/>
 */
public class LoginInfoDAO extends BaseDAO{
	
	public static String getTableName(){
		return "login_info";
	}
	
	/**
	 * @param info ��¼��Ϣ
	 * @return ��ӵ�¼��ϢaddInfo()��������Ҫ��sql���
	 */
	public static String addInfoToSql(LoginInfo info) {
		String sql = "INSERT INTO " + getTableName() + " (id, password) ";
		sql += "VALUES" + "(" + info.toSql() + ");";
		return sql;
	}
	
	public static void addInfo(LoginInfo info) throws SQLException {
		if(info == null)
			throw new IllegalArgumentException("ע����Ϣ��ʧ");
		update(addInfoToSql(info));
	}
	
	// ����ID��ɾ��
	public static void deleteByID(int ID) throws SQLException {
		String sql = "DELETE FROM " + getTableName() + " WHERE id = " + ID;
		update(sql);
	}
	
	public static String getInfoByIDToSql(int userID) {
		String sql = "SELECT ID,password FROM " + getTableName() + " WHERE id = " + userID + ";";
		return sql;
	}
	
	/**
	 * ����ID�Ų���
	 * @param ��ѯ��ID
	 * @return ���ز�ѯ����UserInfo����,�鲻������null
	 * @throws SQLException �����ݿ��ѯ��¼��Ϣʧ�ܣ����쳣���û���������,��Ҫ���û�����
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
	 * ����ID�Ų���
	 * @param ��ѯ��password
	 * @return ���ز�ѯ����password����,�鲻������null
	 * @throws SQLException ���ݿ��ѯ����ʧ��
	 * @throws AlertException �������˻�
	 */	
	public static String getPasswordByID(int userID) throws AlertException, SQLException {
		String password = null;
		select(getPasswordByIDToSql(userID));
		
		if (resultSet.next()) {
			password = resultSet.getString("password");
		} else {
			throw new AlertException("��ID������");
		}
		return password;
	}
	
}
