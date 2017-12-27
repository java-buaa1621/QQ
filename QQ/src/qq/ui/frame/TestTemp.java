package qq.ui.frame;

import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

import qq.util.ResourceManagement;

public class TestTemp {
	
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
	
	public static ImageIcon convertFileToIcon(String filePath) throws IOException {
		byte[] bytes = convertFileToByte(filePath);
		return new ImageIcon(bytes);
	}
	
	public static void main(String args[]) throws IOException {
		new ImageIcon(convertFileToByte("C:/Users/asus/Desktop/resource/image/back1.png"));
	}
}
