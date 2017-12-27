package qq.ui.frame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;

import qq.db.info.*;
import qq.db.util.*;
import qq.exception.*;
import qq.ui.component.*;
import qq.ui.componentFactory.AdapterFactory;
import qq.ui.componentFactory.PanelFactory;
import qq.util.*;

public class RegisterWindow extends JFrame {
	
	// ��ͼ����
	private JPanel contentPane;
	private IDTextField IDField; // ������������
	private JTextField nameField;
	private JComboBox<Integer> ageComboBox; // ����0-199;
	private ButtonGroup sexGroup; 
	private JRadioButton manButton;
	private JRadioButton womanButton;
	private JTextArea mottoArea;
	private PasswordField passwordField;
	private JButton selectHeadButton;
	private JButton registerButton;
	// �߼�����
	private int headIconIndex; // ��ʼ��Ĭ������1��ͷ��

	// ------------------------- ��ͼ���� ------------------------- //
	
	public static void startUp() {
		RegisterWindow registerWindow = new RegisterWindow();
		registerWindow.setVisible(true);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					startUp();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private RegisterWindow() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("ע���û�");
		setBounds(100, 100, 409, 380);
		setIconImage(ResourceManagement.getImage("qq_icon.png"));
		
		// Ĭ��contentPane
		contentPane = PanelFactory.createDafaultContentPanel();
		setContentPane(contentPane);
		
		initLabels();
		initIDField();
		initNameField();
		initSexButtons();
		initAgeComboBox();
		initMottoArea();
		initPasswordField();
		initSelectHeadButton();
		initRegisterButton();
		this.setHeadIconIndex(1); // ����Ĭ��1��ͷ��
	}
	
	private void initLabels() {
		JLabel label0 = new JLabel("�û�ID");
		label0.setBounds(21, 11, 75, 29);
		contentPane.add(label0);
		
		JLabel label1 = new JLabel("�û��ǳ�");
		label1.setBounds(21, 50, 75, 29);
		contentPane.add(label1);
		
		JLabel label2 = new JLabel("�Ա�");
		label2.setBounds(21, 89, 75, 29);
		contentPane.add(label2);
		
		JLabel label3 = new JLabel("����");
		label3.setBounds(21, 128, 75, 29);
		contentPane.add(label3);
		
		JLabel label4 = new JLabel("����ǩ��");
		label4.setBounds(21, 167, 75, 29);
		contentPane.add(label4);
		
		JLabel label5 = new JLabel("����");
		label5.setBounds(21, 283, 75, 29);
		contentPane.add(label5);
	}
	
	private void initIDField() {
		IDField = new IDTextField();
		IDField.setColumns(10);
		IDField.setBounds(106, 11, 263, 29);
		IDField.addFocusListener(AdapterFactory.createTextFieldFocusAdapter(IDField, " �������˺�"));
		contentPane.add(IDField);
	}
	
	private void initNameField (){
		nameField = new JTextField();
		nameField.setBounds(106, 50, 263, 29);
		nameField.setColumns(10);
		nameField.addFocusListener(
				AdapterFactory.createTextFieldFocusAdapter(nameField, " �������ǳ�"));
		contentPane.add(nameField);
	}
	
	private void initAgeComboBox() {
		ageComboBox = new JComboBox<Integer>();
		ageComboBox.setBounds(106, 132, 64, 21);
		for(int i = 0; i < 200; i++) 
			ageComboBox.addItem(i);
		contentPane.add(ageComboBox);
	}
	
	private void initSexButtons() {
		manButton = new JRadioButton("��", true); // ��Ĭ��ѡ��
		manButton.setBounds(102, 92, 121, 23);
		manButton.setContentAreaFilled(false); // ����͸��
		contentPane.add(manButton);
		
		womanButton = new JRadioButton("Ů");
		womanButton.setBounds(238, 92, 121, 23);
		womanButton.setContentAreaFilled(false); // ����͸��
		contentPane.add(womanButton);
		
		// ��ӽ�ButtonGroup, ButtonGroupֻ�����߼�����������ʾ��������ӵ�contentPane
		sexGroup = new ButtonGroup();
		sexGroup.add(manButton);
		sexGroup.add(womanButton);
	}
	
	private void initMottoArea() {
		// �����ı�����
		mottoArea = new JTextArea();
		mottoArea.setLineWrap(true);
		mottoArea.setWrapStyleWord(true);
		// �������
        JScrollPane textScrollPane = new JScrollPane(mottoArea);
        textScrollPane.setBounds(106, 170, 263, 91);
        textScrollPane.setHorizontalScrollBarPolicy(
        		JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // �������ֺ��������
		contentPane.add(textScrollPane);
	}
	
	private void initPasswordField() {
		passwordField = new PasswordField();
		passwordField.setBounds(106, 283, 263, 29);
		passwordField.addFocusListener(
				AdapterFactory.createTextFieldFocusAdapter(passwordField, " ����������"));
		contentPane.add(passwordField);
	}
	
	/**
	 * �л���ѡ��ͷ����� 
	 */
	private void initSelectHeadButton() {
		selectHeadButton = new JButton("ѡ��ͷ��");
		final RegisterWindow registerWindow = this;
		selectHeadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HeadIconWindow.startUp(registerWindow);
			}
		});
		selectHeadButton.setBounds(130, 318, 93, 23);
		contentPane.add(selectHeadButton);
	}
	
	private void initRegisterButton() {
		registerButton = new JButton("ע��");
		registerButton.setBounds(258, 318, 93, 23);
		final RegisterWindow registerWindow = this; // Ϊ��������˻��ɹ���رմ��ڲ����ľ��
		registerButton.addActionListener(new ActionListener() {
			// ����ע��ʱ��������һ�����û���ɵ�AlertException��ת��Ϊ������ʾ
			public void actionPerformed(ActionEvent e) {
				UserInfo userInfo = null;
				LoginInfo loginInfo = null;
				try {
					userInfo = buildUserInfo();
					loginInfo = buildLoginInfo();
					DBManager.updateUserRelatedDatabase(userInfo, loginInfo, userInfo.getID());
				} catch (AlertException e1) {
					AlertWindow.startUp(e1.getMessage());
					return; // ��ֹ�رմ���
				}
				// �رմ���
				registerWindow.dispose();
			}
		});
		contentPane.add(registerButton);
		
	}

	
	// ------------------------- �߼����� ------------------------- //
	
	private UserInfo buildUserInfo() throws AlertException {
			int ID = IDField.getID();
		
			String name = nameField.getText();
			if (name.length() > UserInfo.MAX_LEN)
				throw new AlertException("�ǳƳ���100��");
			else if (name.length() == 0) 
				throw new AlertException("�ǳƲ���Ϊ��");
			
			int age = (int) ageComboBox.getSelectedItem();
			
			String sex = getSelectedSex();
			
			String motto = mottoArea.getText();
			if (motto.length() > UserInfo.MAX_LEN)
				throw new AlertException("����ǩ������100��");
			else if (motto.length() == 0) 
				throw new AlertException("����ǩ������Ϊ��");
			
			return new UserInfo(ID, name, sex, age, motto, headIconIndex);
	}
	
	private LoginInfo buildLoginInfo() throws AlertException {
		int ID = IDField.getID();
		String password = passwordField.getPasswordString();
		return new LoginInfo(ID, password);
	}
	
	/**
	 * @return "��"��"Ů"��
	 */
	private String getSelectedSex() {
		if (manButton.isSelected())
			return "��";
		else 
			return "Ů";
	}
	
	/**
	 * ����headIconIndex��ֵ
	 * @param headIconIndex ͷ����
	 */
	protected void setHeadIconIndex(int headIconIndex) {
		if(!Func.isValid(headIconIndex, Constant.START_HEAD_ICON, Constant.END_HEAD_ICON))
			throw new IllegalArgumentException("headIconIndex����Χ");
		this.headIconIndex = headIconIndex;
	}
	
}
