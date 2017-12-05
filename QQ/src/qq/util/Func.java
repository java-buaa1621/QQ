package qq.util;

/**
 * 全局多次使用的函数
 */
public abstract class Func {
	
	/**
	 * @param s 待检测字符串
	 * @param maxLen 字符串最大长度
	 * @return true:字符串存在且不超过最大长度 <br/>
	 * false:字符串不存在或超过最大长度
	 */
	public static boolean isValid(String s, int maxLen) {
		if (s == null || s.length() > maxLen){
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * @param num 待检测数字
	 * @param left 左边界
	 * @param right 右边界
	 * @return true:属于[left, right] <br/>
	 * false:不属于
	 */
	public static boolean isValid(int num, int left, int right) {
		if(left > right)
			throw new IllegalArgumentException();
		return num >= left && num <= right;
	}
	
	/**
	 * @param num 待检测数字
	 * @return true:是正整形 <br/>
	 * false:不是正整形
	 */
	public static boolean isPositiveInt(int num){
		return num > 0;
	}
	 
}
