package qq.ui.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import qq.ui.component.FlowComponentScrollPanel;
import qq.ui.componentFactory.AdapterFactory;
import qq.ui.componentFactory.PanelFactory;
import qq.util.Constant;
import qq.util.ResourceManagement;

public class EmojiDialog extends JDialog {

	private FlowComponentScrollPanel<JButton> emojiPane = null;
	private static EmojiDialog dialog = null;
	private final ChatRoom outerRoom;
	public static final Dimension size = new Dimension(400, 250);

//	public static void main(String[] args) {
//		try {
//			getInstance();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public static EmojiDialog getInstance(ChatRoom outerRoom) {
		if(dialog == null) {
			dialog = new EmojiDialog(outerRoom);
			dialog.setVisible(true);
		}
		return dialog;
	}
	
	private EmojiDialog(final ChatRoom outerRoom) {
		this.outerRoom = outerRoom;
		
		setBounds(103,301,300,150);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setUndecorated(true);
		setAlwaysOnTop(true);
		setFocusable(true);
		
		// 文件读取创建图片
		ArrayList<JButton> buttons = new ArrayList<JButton>();
		for(int i = 0; i <= Constant.MAX_FACE_ICON; i++) {
			final JButton faceButton = new JButton(
					ResourceManagement.getFaceIcon(i));
			faceButton.setName("[" + i + "]");
			faceButton.setBackground(Color.WHITE);
			
			faceButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					outerRoom.insertText(faceButton.getName());
					close();
				}
			});
			faceButton.addMouseListener(
					AdapterFactory.createMouseEnterAndExitAdapter(faceButton));
			buttons.add(faceButton);
		}
		
		int gapX = 0; 
		int gapY = 0;
		int colNum = 8;
		Dimension emojiSize = new Dimension(32, 32);
		int compBorderWidth = 2;
		emojiPane = PanelFactory.createFlowComponentScrollPane(
				buttons, gapX, gapY, colNum, emojiSize, compBorderWidth);
		
		getContentPane().add(emojiPane);
	}
	
	public static void close() {
		if(dialog != null) {
			dialog.setVisible(false);
			dialog.dispose();
			dialog = null;
		}
	}
	
	public static boolean haveDialog() {
		return dialog != null;
	}
	
}
