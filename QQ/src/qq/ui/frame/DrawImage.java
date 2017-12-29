package qq.ui.frame;
import java.applet.*;  
import java.awt.*; 
import java.net.*;
import javax.swing.*;
 
class MyPanel extends JPanel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyPanel(){
        setPreferredSize(new Dimension(100, 100));
        setBackground(Color.BLUE);
        setOpaque(false);
    }
}
 
public class DrawImage extends Applet{ 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image image; 
    private JPanel panel;
 
    public void init(){ 
        panel = new JPanel(new BorderLayout());
        panel.add(new JButton("Test"), BorderLayout.CENTER);
        panel.add(new MyPanel(), BorderLayout.NORTH);
        panel.add(new MyPanel(), BorderLayout.SOUTH);
        panel.add(new MyPanel(), BorderLayout.WEST);
        panel.add(new MyPanel(), BorderLayout.EAST);
            
        try{
            image = getImage(new URL("http://avatar.profile.csdn.net/5/2/8/2_ufofind.jpg")); 
             
        }catch (Exception e){
            e.printStackTrace();
        }
         
        add(panel);
        setSize(500, 250);
        setVisible(true);
    } 
 
    public void paint(Graphics g){ 
        g.drawImage(image, 0, 0, this); 
    }
}