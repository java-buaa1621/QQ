package qq.util;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.UIManager;

/**
 * 全局多次使用的函数
 */
public abstract class Func {
	
	/** 使用Windows的界面风格 */
	public static void useWindowsStyle() {
		try { 
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 使用Java的界面风格 */
	public static void useJavaStyle() {
		try { 
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
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
	
	/**
	 * 将某一个对撞转换成对象数组
	 * @param comp 不能为null
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
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		return df.format(new Date());// new Date()为获取当前系统时间
	}
	
	public static String getClock() {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
		return df.format(new Date());// new Date()为获取当前系统时间
	}
	
	public static String getTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		return df.format(new Date());// new Date()为获取当前系统时间
	}
	
	/** 将srcFile拷贝到本机目录 */
	public static void copyFile(String desPath, File srcFile) {
		try {
			File desFile = new File(desPath);
			int size = 100;
			byte[] b = new byte[size];
		
			FileOutputStream fos = new FileOutputStream(desFile);
			FileInputStream fis = new FileInputStream(srcFile);
			int readLen;
			while ((readLen = fis.read(b, 0, size)) != -1) {
				fos.write(b, 0, readLen);
			}
			fis.close(); 
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** 本机拷贝 */
	public static void copyFile(String desPath, String srcPath) {
		copyFile(desPath, new File(srcPath));
	}
	
	/** 返回文件类型， 格式例如:  .jpg */ 
	public static String getFormat(File file) {
		if(file == null)
			throw new IllegalArgumentException();
		
		String path = file.getPath();
		int len = path.length();
		String format = null; // 找不到
		for(int i = len-1; i>=0; i--) {
			if(path.charAt(i) == '.') {
				format = path.substring(i, len);
				break;
			}
		}
		return format;
	}
	
	/**
	 * 
	 * @param title
	 * @param parent 外部组件
	 * @return
	 */
	public static JFileChooser invokeOpenFileChoose(String title, Component parent) {
		JFileChooser jfc = new JFileChooser(); // 查找文件
		jfc.setDialogTitle(title); 
		jfc.showOpenDialog(parent);
		return jfc;
	}
	
}
