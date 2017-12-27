package qq.db.info;

import java.io.IOException;
import java.lang.reflect.Method;

import qq.util.Constant;
import qq.util.Func;
import qq.util.ResourceManagement;

public class UserInfo {
	
	public static final int MAX_LEN = 100;
	private int ID;
	private String name;
	private String sex;
	private int age;
	private String motto;
	private int headIconIndex;
	
	/**
	 * 
	 * @param ID 正整形数，每个id均不同
	 * @param name 字符串，长度小于等于100
	 * @param sex 字符串，只能男或女
	 * @param age 正整形数
	 * @param motto 字符串，长度小于等于100
	 * @param headIconIndex 头像编号
	 */
	public UserInfo(int ID, String name, String sex, int age, String motto, int headIconIndex)  {
		if (!Func.isPositiveInt(ID) | age < 0 
				|| !Func.isValid(name, MAX_LEN) || !Func.isValid(motto, MAX_LEN)
				|| (!sex.equals("男")  && !sex.equals("女")) 
				|| !Func.isValid(headIconIndex, Constant.START_HEAD_ICON, Constant.END_HEAD_ICON)) 
			throw new IllegalArgumentException("用户信息输入错误");
		
		this.ID = ID;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.motto = motto;
		this.headIconIndex = headIconIndex;
	}
	
	/** 
	 * @return sql字符串部分，格式例如:
	 * 331079072,"张政勋","男",21,"i'm a good coder", 1
	 */
	public String toSql() {
		String s = "";
		s += ID; s += ",";					// 添加ID
		s += "\"" + name +"\""; s += ","; 	// 添加姓名
		s += "\"" + sex +"\""; s += ","; 	// 添加性别
		s += age; s += ","; 				// 添加年龄
		s += "\"" + motto +"\""; s += ","; 	// 添加格言
		s += headIconIndex;					// 添加头像索引
		return s;
	}
	
	public int getID() {
		return ID;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSex() {
		return sex;
	}
	
	public int getAge() {
		return age;
	}
	
	public String getMotto() {
		return motto;
	}
	
	public int getHeadIconIndex() {
		return headIconIndex;
	}
	
}
