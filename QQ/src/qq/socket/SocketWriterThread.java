package qq.socket;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;

import qq.ui.component.FontAttrib;
import qq.ui.frame.ChatRoom;
import qq.util.ResourceManagement;

/**
 * 发送消息线程
 * 聊天室拥有发送线程
 */
public class SocketWriterThread extends Thread{
	private ObjectOutputStream oos = null;
	// textInfo用于临时保存点击发送按钮后发送的信息
	private FontAttrib textInfo = null;
	private ImageIcon picture = null;
	
	/**
	 * 
	 * 函数设置参数，完成writerThread与chatWindow相互指向的设置
	 * @param dos 输出流
	 * @param chatWindow 与此线程绑定的JFrame对象<br/>
	 * @throws IOException 
	 */
	public SocketWriterThread(Socket socket) throws IOException{
		oos = new ObjectOutputStream(socket.getOutputStream());  
	}
	
	public void run() {
		try {
			while (true) {
				/**
				 * 此句不能去掉！此句不能去掉！此句不能去掉！
				 * 去掉会导致无法发送消息，我也不知道为什么
				 * TODO:查原因！
				 */
				System.out.print("");
				
				FontAttrib textInfo = obtainTextInfo();
				if(textInfo != null) {
					sendTextInfo(textInfo);
				}
				ImageIcon picture = obtainPicture();
				if(picture != null) {
					sendPicture(picture);
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * 循环等待直到点击发送且信息不为空
	 * @return chatRoom图形界面点击发送时保存的信息
	 * @throws IOException 
	 */
	protected FontAttrib obtainTextInfo() throws IOException {
		FontAttrib sendInfo = this.textInfo;
		this.textInfo = null;
		return sendInfo;	
	}
	
	protected ImageIcon obtainPicture() throws IOException {
		ImageIcon sendPicture = this.picture;
		this.picture = null;
		return sendPicture;	
	}
	
	protected void sendTextInfo(FontAttrib textInfo) throws IOException {
		oos.writeObject(textInfo);
		oos.flush();
	}
	
	protected void sendPicture(ImageIcon picture) throws IOException {
		oos.writeObject(picture);
		oos.flush();
	}
	
	public void setTextInfo(FontAttrib textInfo) {
		this.textInfo = textInfo;
	}
	
	public void setPicture(ImageIcon picture) {
		this.picture = picture;
	}
	
}