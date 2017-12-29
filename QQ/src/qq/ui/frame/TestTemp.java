package qq.ui.frame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import qq.util.Func;
import qq.util.ResourceManagement;

public class TestTemp {
	public static void main(String args[]) throws IOException {
//		File file = new File("C:/Users/asus/Desktop/temp.txt");
//		File file2 = new File("C:/Users/asus/Desktop/save.txt");
//		
//		byte[] b = new byte[100];
//		FileInputStream fis = new FileInputStream(file);
//		FileOutputStream fos = new FileOutputStream(file2);
//		int num;
//		while ((num = fis.read(b, 0, 100)) != -1) {
//			fos.write(b, 0, num);
//		}
//		fis.close(); fos.close();
		Func.copyFile("C:/Users/asus/Desktop/save.doc", "C:/Users/asus/Desktop/temp.txt");
	}
}
