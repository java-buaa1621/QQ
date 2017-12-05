package qq.ui.component;

import javax.swing.JTextField;

import qq.exception.AlertException;

public class IDTextField extends JTextField{
	
	/**
	 * @return ���ı��е�����ת����ID��������
	 * @throws AlertException ����޷�ת������ת���Ľ��������
	 */
	public int getID() throws AlertException{
		String IDtext = this.getText();
		if (IDtext == "") 
			throw new AlertException("����ID����Ϊ��");
		
		// �������벻��ת��Ϊ����, �ӹ�parseInt()�׳����쳣
		int ID;
		try {
			ID = Integer.parseInt(IDtext);
		} catch (NumberFormatException e) {
			throw new AlertException("����ID��Χ  1~2147483647");
		}
		
		if (!(ID > 0))
			throw new AlertException("����ID����Ϊ����");
		
		return ID;
	}
	
}
