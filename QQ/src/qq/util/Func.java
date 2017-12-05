package qq.util;

/**
 * ȫ�ֶ��ʹ�õĺ���
 */
public abstract class Func {
	
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
	 
}
