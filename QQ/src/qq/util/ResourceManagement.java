package qq.util;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import qq.db.info.UserInfo;
import qq.ui.component.FontAttrib;

public abstract class ResourceManagement {

	public static Image getImage(String path) {
		Image image = Toolkit.getDefaultToolkit().getImage(
				"resource/image/" + path);
		return image;
	}

	public static ImageIcon getImageIcon(String path) {
		ImageIcon icon = new ImageIcon("resource/image/" + path);
		return icon;
	}

	public static ImageIcon getHeadIcon(int headiconIndex) {
		ImageIcon icon = new ImageIcon("resource/image/head/" + headiconIndex
				+ ".png");
		return icon;
	}

	public static ImageIcon getEmojiIcon(int emojiIconIndex) {
		ImageIcon icon = new ImageIcon("resource/image/face/" + emojiIconIndex
				+ ".gif");
		return icon;
	}

	public static ImageIcon getScaledIcon(Image img, int width, int height) {
		img = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		return new ImageIcon(img);
	}

	/**
	 * 
	 * @param icon
	 *            大于0的double
	 * @param ratio
	 * @return
	 */
	public static ImageIcon getScaledIcon(ImageIcon icon, double ratio) {
		if (ratio < 0 || icon == null)
			throw new IllegalArgumentException();
		int newWidth = (int) (icon.getIconWidth() * ratio);
		int newHeight = (int) (icon.getIconHeight() * ratio);
		return getScaledIcon(icon, newWidth, newHeight);
	}

	public static ImageIcon getScaledIcon(ImageIcon icon, Dimension iconSize) {
		if (iconSize == null || icon == null)
			throw new IllegalArgumentException();
		return getScaledIcon(icon, iconSize.width, iconSize.height);
	}

	public static ImageIcon getScaledIcon(ImageIcon icon, int width, int height) {
		if (width <= 0 || height <= 0 || icon == null)
			throw new IllegalArgumentException();
		Image img = icon.getImage();
		return getScaledIcon(img, width, height);
	}

	public static ImageIcon getScaledIcon(String path, Dimension iconSize) {
		if (iconSize == null || path == null)
			throw new IllegalArgumentException();
		return getScaledIcon(path, iconSize.width, iconSize.height);
	}

	public static ImageIcon getScaledIcon(String path, int width, int height) {
		if (width <= 0 || height <= 0 || path == null)
			throw new IllegalArgumentException();
		Image img = getImage(path);
		return getScaledIcon(img, width, height);
	}

	/**
	 * 获得将icon缩放到长宽限制所需要的比例
	 * @param icon
	 * @param limitWidth
	 * @param limitHeight
	 * @return 将icon缩放到长宽限制所需要的比例, 小于1:缩小, 大于1：放大， 等于1：不变
	 */
	public static double getScaledRatio(ImageIcon icon, int limitWidth,
			int limitHeight) {
		if (limitWidth <= 0 || limitHeight <= 0 || icon == null)
			throw new IllegalArgumentException();
		int width = icon.getIconWidth();
		int height = icon.getIconHeight();
		double ratio1 = (double) limitWidth / width;
		double ratio2 = (double) limitHeight / height;
		return ratio1 < ratio2 ? ratio1 : ratio2;
	}

	/**
	 * 文件转换二进制
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static byte[] convertFileToByte(String filePath) throws IOException {
		File file = new File(filePath);
		byte[] bytes = new byte[(int) file.length()];
		FileInputStream fis;

		fis = new FileInputStream(file);
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		byte[] bb = new byte[2048];
		int ch;
		while ((ch = fis.read(bb)) != -1) {
			bytestream.write(bb, 0, ch);
		}
		bytes = bytestream.toByteArray();
		fis.close();

		return bytes;
	}

	
	
	
	
	
	
	
	public static void debug(String info) {
		System.out.println(info);
	}

	public static void debug(int info) {
		System.out.println(info);
	}

	public static void debug(double info) {
		System.out.println(info);
	}

	public static void debug(UserInfo info) {
		System.out.println(info.getID());
		System.out.println(info.getName());
		System.out.println(info.getMotto());
	}

	public static void debug(FontAttrib attrib) {
		System.out.println(attrib.getText());
		System.out.println("name:" + attrib.getName());
		System.out.println("size:" + attrib.getSize());
		System.out.println("style:" + attrib.getStyle());
	}
}
