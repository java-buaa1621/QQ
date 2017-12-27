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
	 * @param ID ����������ÿ��id����ͬ
	 * @param name �ַ���������С�ڵ���100
	 * @param sex �ַ�����ֻ���л�Ů
	 * @param age ��������
	 * @param motto �ַ���������С�ڵ���100
	 * @param headIconIndex ͷ����
	 */
	public UserInfo(int ID, String name, String sex, int age, String motto, int headIconIndex)  {
		if (!Func.isPositiveInt(ID) | age < 0 
				|| !Func.isValid(name, MAX_LEN) || !Func.isValid(motto, MAX_LEN)
				|| (!sex.equals("��")  && !sex.equals("Ů")) 
				|| !Func.isValid(headIconIndex, Constant.START_HEAD_ICON, Constant.END_HEAD_ICON)) 
			throw new IllegalArgumentException("�û���Ϣ�������");
		
		this.ID = ID;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.motto = motto;
		this.headIconIndex = headIconIndex;
	}
	
	/** 
	 * @return sql�ַ������֣���ʽ����:
	 * 331079072,"����ѫ","��",21,"i'm a good coder", 1
	 */
	public String toSql() {
		String s = "";
		s += ID; s += ",";					// ���ID
		s += "\"" + name +"\""; s += ","; 	// �������
		s += "\"" + sex +"\""; s += ","; 	// ����Ա�
		s += age; s += ","; 				// �������
		s += "\"" + motto +"\""; s += ","; 	// ��Ӹ���
		s += headIconIndex;					// ���ͷ������
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
