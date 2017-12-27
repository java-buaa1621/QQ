package qq.socket;


import java.io.FileOutputStream;  
import java.io.InputStream;  
import java.io.OutputStream;  
import java.net.ServerSocket;  
import java.net.Socket;  

import javax.swing.ImageIcon;
  
public class CUploadPhotoServer {  
  
    public static void main(String[] args) throws Exception{  
        //1.服务器开始监听5612端口  
        ServerSocket ss = new ServerSocket(5612);  
        System.out.println("服务端已启动，正在监听5612端口...");  
        //等待客户端  
        Socket s = ss.accept();  
        System.out.println("检测到客户端，准备数据接收...");  
        //客户端已连接，获取输入流  
        InputStream in = s.getInputStream();  
        //创建图片字节流  
        FileOutputStream fos = new FileOutputStream("C:/Users/asus/Documents/GitHub/QQ/QQ/resource/image/back8.jpg");  
        byte[] buf = new byte[1024];  
        int len = 0; 
        //往字节流里写图片数据  
        while ((len = in.read(buf)) != -1)  
        {  
            fos.write(buf,0,len);  
        }  
        //获取输出流，准备给客户端发送消息  
        OutputStream out = s.getOutputStream();  
        out.write("上传成功".getBytes());  
        //关闭资源  
        fos.close();  
        in.close();  
        out.close();  
        s.close();  
        ss.close();  
    }  
  
}  