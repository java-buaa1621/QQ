package qq.ui.component;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 * �����������
 * �����������������εķ���
 */
public class FontAttrib implements Serializable, Cloneable{
	
	private String text = null; // Ҫ������ı�
	
	public static final int GENERAL = 0; // ����
	public static final int BOLD = 1; // ����
	public static final int ITALIC = 2; // б��
	public static final int BOLD_ITALIC = 3; // ��б��
	
	
	private String name = null; // Ҫ���������
	private int style = 0; // Ҫ�������ʽ
	private int	size = 0; // Ҫ������ֺ�
	private Color color = null; // Ҫ�����������ɫ
	private Color backColor = null; // Ҫ����ı�����ɫ

	/** һ���յĹ��죨�ɵ�������ʹ�ã�*/
	public FontAttrib() {
		
	}
	
	/** �������� */
	public FontAttrib(String text) {
		if(text == null)
			throw new IllegalArgumentException();
		
		this.text = text;
		this.name = "����";
		this.style = 0;
		this.size = 14;
		this.color = Color.BLACK;
		this.backColor = Color.WHITE;
	}

    @Override  
    protected FontAttrib clone() throws CloneNotSupportedException {  
        return (FontAttrib)super.clone(); 
    }  
    
    /**
     * �ı���ֳɵ����ֵ�FontAttrib���飬����Ϊfont
     * @param text ����ı�
     * @param font �����ͺ�
     * @return
     */
    public static ArrayList<FontAttrib> splitText(String text, FontAttrib font) {
    	if(text == null)
    		throw new IllegalArgumentException();
    	
    	ArrayList<FontAttrib> words = new ArrayList<FontAttrib>();
		int len = text.length();
		for(int i = 0; i < len; i++) {
			String word = text.substring(i, i+1);
			FontAttrib wordFont = font.toNewText(word);
			words.add(wordFont);
		}
		return words;
	}
	/**
	 * ���ذ�����ͬ���壬�µ����ֵ�FontAttrib
	 * @param text
	 * @return ������ͬ�������ı��Ķ���
	 */
	public FontAttrib toNewText(String newText) {
		FontAttrib copy = null;
		try {
			copy = (FontAttrib) this.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		copy.setText(newText);
		return copy;
	}
	
	public static SimpleAttributeSet getDafaultSet() {
		SimpleAttributeSet set = new SimpleAttributeSet();
		// ����
		StyleConstants.setFontFamily(set, "����");
		// ��ʽ
		StyleConstants.setBold(set, false);
		StyleConstants.setItalic(set, false);
		// ��С
		StyleConstants.setFontSize(set, 14);
		// ��ɫ
		StyleConstants.setForeground(set, Color.BLACK);
		// ��ɫ
		StyleConstants.setBackground(set, Color.WHITE);
		
		return set;
	}
	
	/**
	 * �������Լ�
	 * @return ���Լ�
	 */
	public SimpleAttributeSet getAttrSet() {
		SimpleAttributeSet attrSet = new SimpleAttributeSet();
		if (name != null)
			StyleConstants.setFontFamily(attrSet, name);
		if (style == FontAttrib.GENERAL) {
			StyleConstants.setBold(attrSet, false);
			StyleConstants.setItalic(attrSet, false);
		} else if (style == FontAttrib.BOLD) {
			StyleConstants.setBold(attrSet, true);
			StyleConstants.setItalic(attrSet, false);
		} else if (style == FontAttrib.ITALIC) {
			StyleConstants.setBold(attrSet, false);
			StyleConstants.setItalic(attrSet, true);
		} else if (style == FontAttrib.BOLD_ITALIC) {
			StyleConstants.setBold(attrSet, true);
			StyleConstants.setItalic(attrSet, true);
		}
		StyleConstants.setFontSize(attrSet, size);
		if (color != null)
			StyleConstants.setForeground(attrSet, color);
		if (backColor != null)
			StyleConstants.setBackground(attrSet, backColor);
		return attrSet;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getBackColor() {
		return backColor;
	}

	public void setBackColor(Color backColor) {
		this.backColor = backColor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}
}