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
	
	/**
	 * 通过物品总数和物品列数得到物品行数
	 * @param total 大于等于0的物品总数
	 * @param colNum 大于0的物品列数
	 * @return 求得的物品行数，若total小于0或colNum小于等于0则返回0
	 */
	public static int getRowNum(int total, int colNum) {
		if(total < 0 || colNum <= 0)
			return 0;
		return (total + colNum - 1) / colNum;
	}
	
}
