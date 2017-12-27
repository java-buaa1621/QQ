package qq.socket;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

import qq.ui.component.FontAttrib;
import qq.ui.frame.ChatRoom;
import qq.util.ResourceManagement;

/**
 * ������Ϣ�߳�
 */
public class SocketWriterThread extends Thread{
	private ObjectOutputStream oos = null;
	public ChatRoom chatWindow = null;
	// textInfo������ʱ���������Ͱ�ť���͵���Ϣ
	private FontAttrib textInfo = null;
	
	/**
	 * 
	 * �������ò��������writerThread��chatWindow�໥ָ�������
	 * @param dos �����
	 * @param chatWindow ����̰߳󶨵�JFrame����<br/>
	 * @throws IOException 
	 */
	public SocketWriterThread(Socket socket) throws IOException{
		oos = new ObjectOutputStream(socket.getOutputStream());  
	}
	
	public void run() {
		try {
			while (true) {
				/**
				 * �˾䲻��ȥ�����˾䲻��ȥ�����˾䲻��ȥ����
				 * ȥ���ᵼ���޷�������Ϣ����Ҳ��֪��Ϊʲô
				 * TODO:��ԭ��
				 */
				ResourceManagement.debug("");
				FontAttrib textInfo = obtainMessage();
				if(textInfo != null) {
					ResourceManagement.debug("�̷߳�����Ϣ");
					ResourceManagement.debug(textInfo);
					sendMessage(textInfo);
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setTextInfo(FontAttrib textInfo) {
		this.textInfo = textInfo;
		ResourceManagement.debug("�߳�������Ϣ");
		ResourceManagement.debug(textInfo);
	}
	
	/**
	 * 
	 * ѭ���ȴ�ֱ�������������Ϣ��Ϊ��
	 * @return chatRoomͼ�ν���������ʱ�������Ϣ
	 * @throws IOException 
	 */
	protected FontAttrib obtainMessage() throws IOException {
		FontAttrib sendInfo = this.textInfo;
		this.textInfo = null;
		if(sendInfo != null){
			ResourceManagement.debug("�߳����÷�����Ϣ");
			ResourceManagement.debug(sendInfo);
		}
		return sendInfo;	
	}
	
	protected void sendMessage(FontAttrib textInfo) throws IOException {
		oos.writeObject(textInfo);
		oos.flush();
	}
	
}