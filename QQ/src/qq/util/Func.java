package qq.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.UIManager;

/**
 * ȫ�ֶ��ʹ�õĺ���
 */
public abstract class Func {
	
	/** ʹ��Windows�Ľ����� */
	public static void useWindowsStyle() {
		try { 
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param s ������ַ���
	 * @param maxLen �ַ�����󳤶�
	 * @return true:�ַ��������Ҳ�������󳤶� <br/>
	 * false:�ַ��������ڻ򳬹���󳤶�
	 */
	public static boolean isValid(String s, int maxLen) {
		if (s == null || s.length() > maxLen){
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * @param num ���������
	 * @param left ��߽�
	 * @param right �ұ߽�
	 * @return true:����[left, right] <br/>
	 * false:������
	 */
	public static boolean isValid(int num, int left, int right) {
		if(left > right)
			throw new IllegalArgumentException();
		return num >= left && num <= right;
	}
	
	/**
	 * @param num ���������
	 * @return true:�������� <br/>
	 * false:����������
	 */
	public static boolean isPositiveInt(int num){
		return num > 0;
	}
	
	/**
	 * ͨ����Ʒ��������Ʒ�����õ���Ʒ����
	 * @param total ���ڵ���0����Ʒ����
	 * @param colNum ����0����Ʒ����
	 * @return ��õ���Ʒ��������totalС��0��colNumС�ڵ���0�򷵻�0
	 */
	public static int getRowNum(int total, int colNum) {
		if(total < 0 || colNum <= 0)
			return 0;
		return (total + colNum - 1) / colNum;
	}
	
	/**
	 * ��ĳһ����ײת���ɶ�������
	 * @param comp ����Ϊnull
	 * @return
	 */
	public static  <T> ArrayList<T> toArrayList(final T object) {
		if(object == null)
			throw new IllegalArgumentException();
		
		ArrayList<T> objects = new ArrayList<T>();
		objects.add(object);
		return objects;
	}
	
	public static boolean isValidEmoji(int index) {
		return isValid(index, Constant.START_FACE_ICON, Constant.END_FACE_ICON);
	}
	
	public static String getDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//�������ڸ�ʽ
		return df.format(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
	}
	
	public static String getClock() {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//�������ڸ�ʽ
		return df.format(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
	}
	
	public static String getTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
		return df.format(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
	}
	
}
