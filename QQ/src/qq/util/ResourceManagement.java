package qq.util;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.ImageIcon;


public abstract class ResourceManagement {
	public static final boolean DEBUG = true;
	public static final int MAX_HEAD_ICON = 100;
	public static final int HEAD_ICON_LENX = 40;
	public static final int HEAD_ICON_LENY = 40;
	
	public static Image getImage(String path) {
		Image image = Toolkit.getDefaultToolkit().getImage("resource/image/" + path);
		return image;
	}
	
	public static ImageIcon getImageIcon(String path) {
		ImageIcon icon = new ImageIcon("resource/image/" + path);
		return icon;
	}
	
	public static ImageIcon getHeadIcon(int headiconIndex) {
		ImageIcon icon = new ImageIcon("resource/image/head/" + headiconIndex + ".png");
		return icon;
	}
	
	public static ImageIcon getScaledIcon(String path, int width, int height) {
		Image img = getImage(path);
		img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(img);
	}
	
	public static void debug(String info) {
		System.out.println(info);
	}
	public static void debug(int info) {
		System.out.println(info);
	}
}
