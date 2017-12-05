package qq.db.info;

import java.io.IOException;
import java.lang.reflect.Method;

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
	 */
	public UserInfo(int ID, String name, String sex, int age, String motto, int headIconIndex)  {
		if (!Func.isPositiveInt(ID) | age < 0 
				|| !Func.isValid(name, MAX_LEN) || !Func.isValid(motto, MAX_LEN)
				|| (!sex.equals("男")  && !sex.equals("女")) 
				|| !Func.isValid(headIconIndex, 1, ResourceManagement.MAX_HEAD_ICON)) 
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
	
	public void setID(int ID) {
		this.ID = ID;
	}
	
	public int getID() {
		return ID;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getSex() {
		return sex;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setMotto(String motto) {
		this.motto = motto;
	}
	
	public String getMotto() {
		return motto;
	}
		
	public void setHeadIconIndex(int headIconIndex){
		this.headIconIndex = headIconIndex;
	}
	
	public int getHeadIconIndex() {
		return headIconIndex;
	}
	
}
