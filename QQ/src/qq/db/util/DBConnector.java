package qq.db.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sound.midi.MidiDevice.Info;

import qq.db.info.LoginInfo;
import qq.db.info.UserInfo;
import qq.util.ResourceManagement;

public class DBConnector {
	// host name & database name,��ʱ����� �ʺ�֮����������
    private static final String URL="jdbc:mysql://127.0.0.1:3306/QQ?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    private static final String NAME="root";
    private static final String PASSWORD="15241100";
    // ����DAO���õ�����
    private static Connection connection = null;
  
    public static Connection getConnection() {
		if (connection == null) {
	    	try {
				Class.forName("com.mysql.jdbc.Driver"); // ����MySQL��������
				connection = DriverManager.getConnection(URL, NAME, PASSWORD); // ������ݿ������
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}
    
    /**
     * @return ��ȡ�ɹ�����statement, ʧ�ܷ���null
     */
    public static Statement getStatement() {
    	Statement statement = null;
		try {
			statement = getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return statement;
	}
    
	 public static void closeConnection() {
    	if(connection != null){
    		try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
	 }
    
}

