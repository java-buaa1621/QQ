package qq.socket;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import org.omg.PortableInterceptor.DISCARDING;

import qq.ui.component.FontAttrib;
import qq.ui.frame.ChatRoom;
import qq.util.ResourceManagement;

/**
 * ������Ϣ�߳�
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
				FontAttrib textInfo = (FontAttrib) obj;
				if(textInfo != null) {
					ResourceManagement.debug("�߳��յ���Ϣ");
					ResourceManagement.debug(textInfo);
					chatWindow.displayText(textInfo);
				}
			}
		}catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}