package qq.socket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import qq.ui.frame.ChatRoom;

public class SocketWriterThread extends Thread{
	private DataOutputStream dos;
	public final ChatRoom chatWindow;
	// message������ʱ���������Ͱ�ť���͵���Ϣ
	private String message;
	
	/**
	 * 
	 * �������ò��������writerThread��chatWindow�໥ָ�������
	 * @param dos �����
	 * @param chatWindow ����̰߳󶨵�JFrame����<br/>
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
	 * ѭ���ȴ�ֱ�������������Ϣ��Ϊ��
	 * @return chatRoomͼ�ν���������ʱ�������Ϣ
	 */
	protected String obtainMessage() {
		String info;
		// ѭ���ȴ�ֱ��info��Ϊ��
		do{
			info = this.message;
			System.out.flush(); // TODO debug(ɾȥ��һ�У��޷�������Ϣ)
		}while(info == null);
		// ���message
		this.message = null;
		
		return info;	
	}

	protected void sendMessage(String info) throws IOException {
		dos.writeUTF(info);
	}
	
}