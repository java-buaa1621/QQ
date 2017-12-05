package qq.db.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import qq.db.info.UserInfo;
import qq.util.ResourceManagement;

public class BaseDAO {
	protected static ResultSet resultSet;
	 
	/** ��������ѯ��������resultSet 
	 * @throws SQLException ִ�� executeQuery���������쳣
	 */
	public static ResultSet select(String sql) throws SQLException {
		ResourceManagement.debug("sql��:" + sql);
		Statement statement = DBConnector.getStatement();
		resultSet = statement.executeQuery(sql);
		return resultSet;
    }
	
    /** �������������޸ġ�ɾ���� 
     * ����ʽ������Ҫôsql���ȫ��ִ�У�Ҫôȫ����ִ��
     * @param ����sql���
     * @throws SQLException ִ�� executeUpdate���������쳣
     * @return �ı������
     */
	protected static int update(String sql) throws SQLException {
    	if (sql == null)
    		throw new IllegalArgumentException();
    	
    	String[] sqls = {sql};
		return update(sqls);
    }
    
    protected static int update(String[] sqls) throws SQLException {
    	if (sqls == null)
    		throw new IllegalArgumentException();
    	
    	for (String sql : sqls)
    		ResourceManagement.debug("sql��:" + sql);
    	
		int num = 0;
		Connection connection = DBConnector.getConnection();
		connection.setAutoCommit(false); // ���ò��Զ��ύ
		Statement statement = DBConnector.getStatement();
		try {
			for (String sql : sqls) 
				num += statement.executeUpdate(sql);
			connection.commit(); // ���ִ��updateû�д��ύ
		} catch (SQLException e) {
			connection.rollback(); // ��������ع�ȡ���Ѿ��ύ������
			throw e;
		} finally {
			connection.setAutoCommit(true);
		}
		return num;
    }
    
    /**
     * ��resultSet��ʼ�����ɻ�����ݵ�λ��(resultSet.next())
     * @return true ���ݼ���Ϊ��; false ���ݼ�Ϊ��
     * @throws SQLException 
     */
    
    
    /* ��֪��ʲôʱ��ر�statement
    protected static void closeStatement(Statement statement) {
		try {
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
    
}
