package Test2;
import java.awt.EventQueue;
import java.awt.Window;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;

import qq.util.ResourceManagement;

public class TestClient {
	
	public static TestFrame window;
	
	public static void main(String args[]) {
		try {
			window = new TestFrame();	
			window.setVisible(true);
			Socket s1 = new Socket("127.0.0.1", 9090);
			InputStream is = s1.getInputStream();
			DataInputStream dis = new DataInputStream(is);
			OutputStream os = s1.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			
			MyClientReader mcr = new MyClientReader(dis, window);
			MyClientWriter mcw = new MyClientWriter(dos, window);
			window.outerThread = mcw;
			mcr.start();
			mcw.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class MyClientReader extends Thread{
	private DataInputStream dis;
	public final TestFrame window;
	
	public MyClientReader(DataInputStream dis, TestFrame window){
		this.dis = dis;
		this.window = window;
	}
	
	public void run(){
		String info;
		try{
			while (true) {
				info = dis.readUTF();
				ResourceManagement.debug("客户端收到： " + info);
				window.displayMessage(info);

			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class MyClientWriter extends Thread{
	private DataOutputStream dos;
	public final TestFrame window;
	public String message;
	
	public MyClientWriter(DataOutputStream dos, TestFrame window){
		this.dos = dos;
		this.window = window;
	}
	
	public void run() {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);

		try {
			while (true) {
				String info;
				// info = br.readLine();
				do{
					info = message;
					System.out.flush();
				}while(info == null);
				
				dos.writeUTF("客户端发出");
				dos.writeUTF(info);
				message = null;

			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}