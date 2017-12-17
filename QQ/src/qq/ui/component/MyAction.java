package qq.ui.component;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * 包含一个抽象方法
 * 为了与类库兼容，继承AbstractAction
 */
public abstract class MyAction extends AbstractAction{
	
	public abstract void executeAction();
	
	public void actionPerformed(ActionEvent e) {
		this.executeAction();
	};
}
