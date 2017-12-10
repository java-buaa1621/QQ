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
	
}
