package qq.socket;

import java.awt.EventQueue;
import java.awt.Window;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;

import qq.ui.frame.ChatRoom;
import qq.util.ResourceManagement;

public class Client {
	
	public static ChatRoom chatWindow;
	
	public static void main(String args[]) {
		try {
			chatWindow = new ChatRoom();	
			chatWindow.setVisible(true);
			Socket s1 = new Socket("127.0.0.1", 9090);
			InputStream is = s1.getInputStream();
			DataInputStream dis = new DataInputStream(is);
			OutputStream os = s1.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			
			// 读写线程共用一个聊天室
			SocketReaderThread readerThread = new SocketReaderThread(dis, chatWindow);
			SocketWriterThread writerThread = new SocketWriterThread(dos, chatWindow);
			readerThread.start();
			writerThread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

