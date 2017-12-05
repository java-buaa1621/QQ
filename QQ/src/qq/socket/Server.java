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
			chatWindow = new ChatRoom();
			chatWindow.setVisible(true);
			ServerSocket s = new ServerSocket(9090);
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
}
