package qq.socket;


import java.io.FileOutputStream;  
import java.io.InputStream;  
import java.io.OutputStream;  
import java.net.ServerSocket;  
import java.net.Socket;  

import javax.swing.ImageIcon;
  
public class CUploadPhotoServer {  
  
    public static void main(String[] args) throws Exception{  
        //1.��������ʼ����5612�˿�  
        ServerSocket ss = new ServerSocket(5612);  
        System.out.println("����������������ڼ���5612�˿�...");  
        //�ȴ��ͻ���  
        Socket s = ss.accept();  
        System.out.println("��⵽�ͻ��ˣ�׼�����ݽ���...");  
        //�ͻ��������ӣ���ȡ������  
        InputStream in = s.getInputStream();  
        //����ͼƬ�ֽ���  
        FileOutputStream fos = new FileOutputStream("C:/Users/asus/Documents/GitHub/QQ/QQ/resource/image/back8.jpg");  
        byte[] buf = new byte[1024];  
        int len = 0; 
        //���ֽ�����дͼƬ����  
        while ((len = in.read(buf)) != -1)  
        {  
            fos.write(buf,0,len);  
        }  
        //��ȡ�������׼�����ͻ��˷�����Ϣ  
        OutputStream out = s.getOutputStream();  
        out.write("�ϴ��ɹ�".getBytes());  
        //�ر���Դ  
        fos.close();  
        in.close();  
        out.close();  
        s.close();  
        ss.close();  
    }  
  
}  