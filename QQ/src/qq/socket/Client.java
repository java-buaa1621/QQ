package qq.socket;

import java.awt.EventQueue;
import java.awt.Window;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;

import qq.db.info.UserInfo;
import qq.ui.frame.ChatRoom;
import qq.util.ResourceManagement;

public class Client {
	
	public static ChatRoom chatWindow;
	
	public Client(UserInfo caller, UserInfo beCaller) {
		try {
			chatWindow = ChatRoom.createWindow(caller, beCaller);
			Socket socket = new Socket("127.0.0.1", 9091);
			
			// 读写线程共用一个聊天室
			SocketWriterThread writerThread = new SocketWriterThread(socket);
			chatWindow.setWriterThread(writerThread);
			SocketReaderThread readerThread = new SocketReaderThread(socket);
			readerThread.chatWindow = chatWindow;
			
			readerThread.start();
			writerThread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
/*	public static void main(String args[]) {
		try {
			chatWindow = ChatRoom.createWindow();
			Socket socket = new Socket("127.0.0.1", 9091);
			
			chatWindow = ChatRoom.createWindow();
			// 读写线程共用一个聊天室
			SocketWriterThread writerThread = new SocketWriterThread(socket);
			chatWindow.setWriterThread(writerThread);
			SocketReaderThread readerThread = new SocketReaderThread(socket);
			readerThread.chatWindow = chatWindow;
			
			readerThread.start();
			writerThread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
}

