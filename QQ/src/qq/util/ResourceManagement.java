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
