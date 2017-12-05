package qq.ui.component;

import javax.swing.JTextField;

import qq.exception.AlertException;

public class IDTextField extends JTextField{
	
	/**
	 * @return 将文本中的内容转化成ID正整形数
	 * @throws AlertException 如果无法转换或者转换的结果非正数
	 */
	public int getID() throws AlertException{
		String IDtext = this.getText();
		if (IDtext == "") 
			throw new AlertException("输入ID不能为空");
		
		// 处理输入不能转化为整数, 加工parseInt()抛出的异常
		int ID;
		try {
			ID = Integer.parseInt(IDtext);
		} catch (NumberFormatException e) {
			throw new AlertException("输入ID范围  1~2147483647");
		}
		
		if (!(ID > 0))
			throw new AlertException("输入ID不能为负数");
		
		return ID;
	}
	
}
