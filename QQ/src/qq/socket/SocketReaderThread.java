package qq.socket;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.swing.ImageIcon;

import org.omg.PortableInterceptor.DISCARDING;

import qq.ui.component.FontAttrib;
import qq.ui.frame.ChatRoom;
import qq.util.ResourceManagement;

/**
 * 接收消息线程
 * 接收线程拥有聊天室
 */
public class SocketReaderThread extends Thread{
	private ObjectInputStream ois = null;
	public ChatRoom chatWindow = null;
	
	public SocketReaderThread(Socket socket) throws IOException{
		ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream())); 
	}
	
	public void run(){
		try{
			while (true) {
				Object obj = ois.readObject();
				if(obj instanceof FontAttrib) {
					FontAttrib textInfo = (FontAttrib) obj;
					if(textInfo != null) 
						chatWindow.displayReceivedText(textInfo);
				} else if(obj instanceof ImageIcon) {
					ImageIcon picture = (ImageIcon) obj;
					if(picture != null) 
						chatWindow.displayReceivedPicture(picture);
				} else if(obj instanceof Shake) {
					Shake shake = (Shake) obj;
					if(shake != null) 
						chatWindow.performShake();
				} else if(obj instanceof File) {
					File file = (File) obj;
					if(file != null) 
						chatWindow.receivedFile(file);
				}
				
			}
		}catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}