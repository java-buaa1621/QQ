package qq.socket;

import java.io.FileInputStream;  
import java.io.InputStream;  
import java.io.OutputStream;  
import java.net.Socket;  
//�ͻ��ˣ��ϴ�ͼƬ  
public class CUploadPhotoClient {  
 
   public static void main(String[] args) throws Exception{  
       //1.������������  
       Socket s = new Socket("127.0.0.1",5612);  
       System.out.println("�����ӵ�������5612�˿ڣ�׼������ͼƬ...");  
       //��ȡͼƬ�ֽ���  
       FileInputStream fis = new FileInputStream("C:/Users/asus/Documents/GitHub/QQ/QQ/resource/image/back80.jpg");  
       //��ȡ�����  
       OutputStream out = s.getOutputStream();  
       byte[] buf = new byte[1024];  
       int len = 0;  
       //2.�����������Ͷ������  
       while ((len = fis.read(buf)) != -1)  
       {  
           out.write(buf,0,len);  
       }  
       //֪ͨ����ˣ����ݷ������  
       s.shutdownOutput();  
       //3.��ȡ����������ܷ��������͹�������Ϣ���ϴ��ɹ���  
       InputStream in = s.getInputStream();  
       byte[] bufIn = new byte[1024];  
       int num = in.read(bufIn);  
       System.out.println(new String(bufIn,0,num));  
       //�ر���Դ  
       fis.close();  
       out.close();  
       in.close();  
       s.close();  
   }  
}  