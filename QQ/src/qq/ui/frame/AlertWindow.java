package qq.ui.frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Color;

import javax.swing.JTextArea;

public class AlertWindow extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					startUp("nothing");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void startUp(String warning) {
		AlertWindow frame = new AlertWindow(warning);
		frame.setVisible(true);
	}
	
	/**
	 * Create the frame.
	 */
	private AlertWindow(String warning) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea textArea = new JTextArea(warning);
		textArea.setEditable(false);
		textArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		// 滚动面板
        JScrollPane textScrollPane = new JScrollPane(textArea);
        textScrollPane.setBounds(21, 10, 392, 241);
        textScrollPane.setHorizontalScrollBarPolicy(
        		JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // 永不出现横向滚动条
		contentPane.add(textScrollPane);
	}
	
}
