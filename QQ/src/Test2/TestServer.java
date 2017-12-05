package Test2;
import java.awt.EventQueue;
import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.SocketException;

import qq.util.ResourceManagement;

public class TestServer {
	
	public static TestFrame window;
	
	public static void main(String args[]){
		try {
			window = new TestFrame();
			window.setVisible(true);
			ServerSocket s = new ServerSocket(9090);
			Socket s1 = s.accept();
			OutputStream os = s1.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			InputStream is = s1.getInputStream();
			DataInputStream dis = new DataInputStream(is);
			
			MyServerReader msr = new MyServerReader(dis, window);
			MyServerWriter msw = new MyServerWriter(dos, window);
			window.outerThread = msw;
			msr.start();
			msw.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class MyServerReader extends Thread{
	private DataInputStream dis;
	public final TestFrame window;
	
	public MyServerReader(DataInputStream dis, TestFrame window){
		this.dis = dis;
		this.window = window;
	}
	
	public void run(){
		String info;
		try{
			while (true) {
				info = dis.readUTF();
				ResourceManagement.debug("服务端收到： " + info);
				window.displayMessage(info);

			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class MyServerWriter extends Thread{
	private DataOutputStream dos;
	public final TestFrame window;
	public String message;
	
	public MyServerWriter(DataOutputStream dos, TestFrame window){
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
				
				dos.writeUTF("服务端发送");
				dos.writeUTF(info);
				message = null;
				
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}