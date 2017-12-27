package qq.ui.frame;

import java.awt.Dimension;  
import java.awt.Image;  
import java.awt.TrayIcon;  
import java.awt.event.MouseAdapter;  
import java.awt.event.MouseEvent;  
  
import javax.swing.JDialog;  
import javax.swing.JPopupMenu;  
import javax.swing.event.PopupMenuEvent;  
import javax.swing.event.PopupMenuListener;  
  
/** 
 * �̳�TrayIcon����JDialog��Ϊ�����˵�����ʾý�� 
 */  
public class MyTrayIcon extends TrayIcon {  
          
    private JDialog dialog;  
      
    /** 
     * ���췽����������ָ��ͼ�񡢹�����ʾ�͵����˵��� MyTrayIcon 
     * @param image ��ʾ��ϵͳ���̵�ͼ�� 
     * @param ps    ����ƶ���ϵͳ����ͼ���ϵ���ʾ��Ϣ 
     * @param Jmenu �����˵� 
     */  
    public MyTrayIcon(Image image,String ps,final JPopupMenu Jmenu) {  
        super(image,ps);  
              
        //��ʼ��JDialog  
        dialog = new JDialog();  
        dialog.setUndecorated(true);//ȡ������װ��  
        dialog.setAlwaysOnTop(true);//���ô���ʼ��λ���Ϸ�  
              
        //����ϵͳͼ���СΪ�Զ�����  
        this.setImageAutoSize(true);  
              
        //ΪTrayIcon������������  
        this.addMouseListener(new MouseAdapter() {  
              
            @Override  
            public void mouseReleased(MouseEvent e) {  
                      
                //����Ҽ���������ͷ�ʱ���ã���ʾ�����˵�  
                if (e.getButton()==MouseEvent.BUTTON3 && Jmenu != null) {  
                      
                    //����dialog����ʾλ��  
                    Dimension size = Jmenu.getPreferredSize();  
                    dialog.setLocation(e.getX()-size.width-3, e.getY() - size.height-3);  
                    dialog.setVisible(true);  
                      
                    //��ʾ�����˵�Jmenu  
                    Jmenu.show(dialog.getContentPane(), 0, 0);  
                }  
            }  
        });  
          
        //Ϊ�����˵���Ӽ�����  
        Jmenu.addPopupMenuListener(new PopupMenuListener() {  
              
            @Override  
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {}  
              
            @Override  
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {  
                dialog.setVisible(false);  
            }  
              
            @Override  
            public void popupMenuCanceled(PopupMenuEvent e) {  
                dialog.setVisible(false);  
            }  
        });  
      
    }  
      
}  