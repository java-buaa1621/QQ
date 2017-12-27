package qq.ui.frame;

import java.awt.AWTException;  
import java.awt.Color;  
import java.awt.Graphics;  
import java.awt.SystemTray;  
import java.awt.Window;  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;  
import java.awt.event.MouseAdapter;  
import java.awt.event.MouseEvent;  
  
import javax.swing.ImageIcon;  
import javax.swing.JComponent;  
import javax.swing.JFrame;  
import javax.swing.JMenuItem;  
import javax.swing.JPopupMenu;  
import javax.swing.UIManager;  
import javax.swing.plaf.basic.BasicPopupMenuUI;  
  
  
public class Demo {  
  
    public static void main(String[] args) throws Exception {  
          
        //������ϵͳ�������Ϊ���嵱ǰ���  
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  
          
        //��ʼ������  
        JFrame frame=new JFrame("My QQ");  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.setSize(500, 500);  
        frame.setLocationRelativeTo(null);  
        frame.setType(Window.Type.UTILITY);  
          
        ImageIcon img=new ImageIcon(Demo.class.getClassLoader().getResource("res/images/logo.gif"));  
        frame.setIconImage(img.getImage());  
          
        //���嵯���˵�  
        JPopupMenu Jmenu=new JPopupMenu();  
          
        //ΪJPopupMenu����UI  
        Jmenu.setUI(new BasicPopupMenuUI(){  
            @Override  
            public void paint(Graphics g, JComponent c){  
                super.paint(g, c);  
                  
                //�������˵����Ļ�ɫ����  
                g.setColor(new Color(236,237,238));  
                g.fillRect(0, 0, 25, c.getHeight());  
                  
                //�������˵��Ҳ�İ�ɫ����  
                g.setColor(new Color(255,255,255));  
                g.fillRect(25, 0, c.getWidth()-25, c.getHeight());  
            }  
        });  
          
        //���嵯���˵���  
        JMenuItem online = new JMenuItem("��������",new ImageIcon(  
                Demo.class.getClassLoader().getResource("res/images/online.png")));  
        JMenuItem busy = new JMenuItem("æµ",new ImageIcon(  
                Demo.class.getClassLoader().getResource("res/images/busy.png")));  
        JMenuItem invisible= new JMenuItem("����");  
        JMenuItem openmenu = new JMenuItem("�������");  
        JMenuItem closemenu = new JMenuItem("�˳�MyQQ");  
          
        //��ӵ����˵�������˵�  
        Jmenu.add(online);  
        Jmenu.add(busy);  
        Jmenu.add(invisible);  
        Jmenu.addSeparator();//��ӷָ���  
        Jmenu.add(openmenu);  
        Jmenu.add(closemenu);  
          
        //�õ���ǰϵͳ����  
        SystemTray systemtray = SystemTray.getSystemTray();  
          
        //������ָ��ͼ�񡢹�����ʾ�͵����˵��� MyTrayIcon  
        MyTrayIcon trayicon=new MyTrayIcon(img.getImage(),"MyQQ",Jmenu);  
          
        //��TrayIcon��ӵ�ϵͳ����  
        try {  
            systemtray.add(trayicon);   
        } catch (AWTException e1) {  
            e1.printStackTrace();  
        }  
          
        //���õ�����ϵͳ����ͼ����ʾ������  
        trayicon.addMouseListener(new MouseAdapter(){   
            @Override  
            public void mouseClicked(MouseEvent e) {  
                  
                //���������,���ô���״̬��������ʾ  
                if(e.getButton()==MouseEvent.BUTTON1){  
                    frame.setExtendedState(JFrame.NORMAL);  
                    frame.setVisible(true);  
                }  
            }  
        });  
          
        //����ActionListener������  
        ActionListener MenuListen = new ActionListener() {  
              
            @Override  
            public void actionPerformed(ActionEvent e) {  
                                 
                if (e.getActionCommand().equals("�˳�MyQQ")){  
                       
                    systemtray.remove(trayicon);   
                    System.exit(0);  
                }  
                else if(e.getActionCommand().equals("�������")){  
                    frame.setExtendedState(JFrame.NORMAL);  
                    frame.setVisible(true);  
                }  
                         
        }};  
          
        //Ϊ�����˵�����Ӽ�����  
        openmenu.addActionListener(MenuListen);  
        closemenu.addActionListener(MenuListen);  
          
        frame.setVisible(true);  
          
    }  
      
}  
