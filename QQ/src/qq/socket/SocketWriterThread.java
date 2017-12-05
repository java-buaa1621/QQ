package qq.socket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import qq.ui.frame.ChatRoom;

public class SocketWriterThread extends Thread{
	private DataOutputStream dos;
	public final ChatRoom chatWindow;
	// message用于临时保存点击发送按钮后发送的信息
	private String message;
	
	/**
	 * 
	 * 函数设置参数，完成writerThread与chatWindow相互指向的设置
	 * @param dos 输出流
	 * @param chatWindow 与此线程绑定的JFrame对象<br/>
	 */
	public SocketWriterThread(DataOutputStream dos, ChatRoom chatWindow){
		this.dos = dos;
		chatWindow.setWriterThread(this);
		this.chatWindow = chatWindow;
	}
	
	public void run() {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		try {
			while (true) {
				String info = obtainMessage();
				sendMessage(info);
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * 
	 * 循环等待直到点击发送且信息不为空
	 * @return chatRoom图形界面点击发送时保存的信息
	 */
	protected String obtainMessage() {
		String info;
		// 循环等待直到info不为空
		do{
			info = this.message;
			System.out.flush(); // TODO debug(删去这一行，无法发送消息)
		}while(info == null);
		// 清除message
		this.message = null;
		
		return info;	
	}

	protected void sendMessage(String info) throws IOException {
		dos.writeUTF(info);
	}
	
}