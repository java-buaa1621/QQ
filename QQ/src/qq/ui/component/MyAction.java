package qq.ui.component;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * ����һ�����󷽷�
 * Ϊ���������ݣ��̳�AbstractAction
 */
public abstract class MyAction extends AbstractAction{
	
	public abstract void executeAction();
	
	public void actionPerformed(ActionEvent e) {
		this.executeAction();
	};
}
