package qq.socket;

import java.awt.EventQueue;
import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.SocketException;

import qq.ui.frame.ChatRoom;
import qq.util.ResourceManagement;

public class Server {
	
	public static ChatRoom chatWindow;
	
	public static void main(String args[]){
		try {
			chatWindow = ChatRoom.createWindow();
			chatWindow.setVisible(true);
			ServerSocket s = new ServerSocket(9091);
			Socket s1 = s.accept();
			OutputStream os = s1.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			InputStream is = s1.getInputStream();
			DataInputStream dis = new DataInputStream(is);
			
			// 读写线程共用一个聊天室
			SocketReaderThread readerThread = new SocketReaderThread(dis, chatWindow);
			SocketWriterThread writerThread = new SocketWriterThread(dos, chatWindow);
			readerThread.start();
			writerThread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return 可使用的端口号，没有返回0
	 */
	public static int getUsablePort() {
		int port = 0;
		for(int i=1025; i< 65535; i++){
            try{
                 ServerSocket s = new ServerSocket(i);
                 s.close();
                 port = i;
                 break;
            }catch(IOException e){
                continue;
            }
		}
		return port;
	}
	
}
