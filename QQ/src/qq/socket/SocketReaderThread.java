package qq.socket;

import java.io.DataInputStream;
import java.io.IOException;

import qq.ui.frame.ChatRoom;
import qq.util.ResourceManagement;

public class SocketReaderThread extends Thread{
	private DataInputStream dis;
	public final ChatRoom chatWindow;
	
	public SocketReaderThread(DataInputStream dis, ChatRoom chatWindow){
		this.dis = dis;
		chatWindow.setReaderThread(this);
		this.chatWindow = chatWindow;
	}
	
	public void run(){
		String info;
		try{
			while (true) {
				info = dis.readUTF();
				chatWindow.displayText(info);
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}