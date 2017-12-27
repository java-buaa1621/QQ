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
			ServerSocket serverSocket = new ServerSocket(9091);
			Socket socket = serverSocket.accept();
			ResourceManagement.debug("��������");
			
			// ��д�̹߳���һ��������
			SocketWriterThread writerThread = new SocketWriterThread(socket);
			chatWindow.setWriterThread(writerThread);
			SocketReaderThread readerThread = new SocketReaderThread(socket);
			chatWindow.setReaderThread(readerThread);
			writerThread.chatWindow = chatWindow;
			readerThread.chatWindow = chatWindow;
			
			readerThread.start();
			writerThread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return ��ʹ�õĶ˿ںţ�û�з���0
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
