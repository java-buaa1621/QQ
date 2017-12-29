package qq.socket;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.text.StyledEditorKit.BoldAction;

import qq.ui.component.FontAttrib;
import qq.ui.frame.ChatRoom;
import qq.util.ResourceManagement;

/**
 * ������Ϣ�߳�
 * ������ӵ�з����߳�
 */
public class SocketWriterThread extends Thread{
	private ObjectOutputStream oos = null;
	// textInfo������ʱ���������Ͱ�ť���͵���Ϣ
	private FontAttrib textInfo = null;
	private ImageIcon picture = null;
	private Shake shake = null;
	private File file = null;
	
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
				System.out.print("");
				
				FontAttrib textInfo = obtainTextInfo();
				if(textInfo != null) {
					sendTextInfo(textInfo);
				}
				ImageIcon picture = obtainPicture();
				if(picture != null) {
					sendPicture(picture);
				}
				Shake shake = obtainShake();
				if(shake != null) {
					sendShake(shake);
				}
				File file = obtainFile();
				if(file != null) {
					sendFile(file);
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	// ------------------------- obtain --------------------------------- //
	
	/**
	 * 
	 * ѭ���ȴ�ֱ�������������Ϣ��Ϊ��
	 * @return chatRoomͼ�ν���������ʱ�������Ϣ
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
	
	protected Shake obtainShake() throws IOException {
		Shake shake = this.shake;
		this.shake = null;
		return shake;	
	}
	
	protected File obtainFile() throws IOException {
		File file = this.file;
		this.file = null;
		return file;	
	}
	
	
	// ------------------------- send --------------------------------- //
	
	protected void sendTextInfo(FontAttrib textInfo) throws IOException {
		oos.writeObject(textInfo);
		oos.flush();
	}
	
	protected void sendPicture(ImageIcon picture) throws IOException {
		oos.writeObject(picture);
		oos.flush();
	}
	
	protected void sendShake(Shake shake) throws IOException {
		oos.writeObject(shake);
		oos.flush();
	}
	
	protected void sendFile(File file) throws IOException {
		oos.writeObject(file);
		oos.flush();
	}
	
	
	// ------------------------- set --------------------------------- //
	
	public void setTextInfo(FontAttrib textInfo) {
		this.textInfo = textInfo;
	}
	
	public void setPicture(ImageIcon picture) {
		this.picture = picture;
	}
	
	public void setShake(Shake shake) {
		this.shake = shake;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
}