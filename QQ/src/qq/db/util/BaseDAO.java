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
	 
	/** 操作（查询），更改resultSet 
	 * @throws SQLException 执行 executeQuery方法出现异常
	 */
	public static ResultSet select(String sql) throws SQLException {
		ResourceManagement.debug("sql是:" + sql);
		Statement statement = DBConnector.getStatement();
		resultSet = statement.executeQuery(sql);
		return resultSet;
    }
	
    /** 操作（新增、修改、删除） 
     * 捆绑式操作，要么sql语句全都执行，要么全都不执行
     * @param 单个sql语句
     * @throws SQLException 执行 executeUpdate方法出现异常
     * @return 改变的行数
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
    		ResourceManagement.debug("sql是:" + sql);
    	
		int num = 0;
		Connection connection = DBConnector.getConnection();
		connection.setAutoCommit(false); // 设置不自动提交
		Statement statement = DBConnector.getStatement();
		try {
			for (String sql : sqls) 
				num += statement.executeUpdate(sql);
			connection.commit(); // 如果执行update没有错，提交
		} catch (SQLException e) {
			connection.rollback(); // 如果出错，回滚取回已经提交的数据
			throw e;
		} finally {
			connection.setAutoCommit(true);
		}
		return num;
    }
    
    /**
     * 将resultSet初始化到可获得数据的位置(resultSet.next())
     * @return true 数据集不为空; false 数据集为空
     * @throws SQLException 
     */
    
    
    /* 不知道什么时候关闭statement
    protected static void closeStatement(Statement statement) {
		try {
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
    
}
