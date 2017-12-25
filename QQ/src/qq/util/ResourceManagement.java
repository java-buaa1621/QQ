package qq.util;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import qq.db.info.UserInfo;


public abstract class ResourceManagement {
	
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
	
	public static ImageIcon getFaceIcon(int faceiconIndex) {
		ImageIcon icon = new ImageIcon("resource/image/face/" + faceiconIndex + ".gif");
		return icon;
	}
	
	public static ImageIcon getScaledIcon(String path, Dimension iconSize) {
		return getScaledIcon(path, iconSize.width, iconSize.height);
	}
	
	public static ImageIcon getScaledIcon(String path, int width, int height) {
		Image img = getImage(path);
		img = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		return new ImageIcon(img);
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
}
