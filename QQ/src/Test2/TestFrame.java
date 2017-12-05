package Test2;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.Color;

import javax.swing.JButton;

import qq.util.ResourceManagement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TestFrame extends JFrame {
	
	private JPanel contentPane;
	private JTextArea textArea;
	private JTextArea outputArea;
	public String name;
	
	public Thread outerThread;
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestFrame frame = new TestFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
	
//	@Override
//	public void paint(Graphics g) {
//		System.out.println("painting");
//	}
	
	public TestFrame() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 604, 454);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.textArea = new JTextArea();
		textArea.setBackground(Color.LIGHT_GRAY);
		textArea.setForeground(Color.BLACK);
		textArea.setBounds(43, 214, 391, 168);
		contentPane.add(textArea);
		
		JButton button = new JButton("\u53D1\u9001");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dealMessage();
			}
		});
		button.setForeground(Color.BLACK);
		button.setBounds(445, 359, 93, 23);
		contentPane.add(button);
		
		this.outputArea = new JTextArea();
		outputArea.setBackground(Color.LIGHT_GRAY);
		outputArea.setBounds(43, 10, 391, 168);
		contentPane.add(outputArea);
		outputArea.setEditable(false);
	}
	
	public TestFrame(Thread thread, String name) {
		this();
		this.outerThread = thread;
		this.name = name;
		setTitle(name);
	}
	
	protected void dealMessage() {
		String text = textArea.getText();
		textArea.setText("");
		ResourceManagement.debug(111111111);
		if (outerThread instanceof MyClientWriter){
			((MyClientWriter) outerThread).message = text;
			ResourceManagement.debug(222222222);
		}
			
		else if (outerThread instanceof MyServerWriter){
			((MyServerWriter) outerThread).message = text;
			ResourceManagement.debug(333333333);
		}
	}
	
	public void displayMessage(String text) {
		outputArea.append("¶Ô·½Ëµ£º " + text + "\n");
	}
	
}
