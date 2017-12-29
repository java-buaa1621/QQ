package qq.ui.component;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 * 字体的属性类
 * 包含文字内容与修饰的方法
 */
public class FontAttrib implements Serializable, Cloneable{
	
	private String text = null; // 要输入的文本
	
	public static final int GENERAL = 0; // 常规
	public static final int BOLD = 1; // 粗体
	public static final int ITALIC = 2; // 斜体
	public static final int BOLD_ITALIC = 3; // 粗斜体
	
	
	private String name = null; // 要输入的字体
	private int style = 0; // 要输入的样式
	private int	size = 0; // 要输入的字号
	private Color color = null; // 要输入的文字颜色
	private Color backColor = null; // 要输入的背景颜色

	/** 一个空的构造（可当做换行使用）*/
	public FontAttrib() {
		
	}
	
	/** 常量构造 */
	public FontAttrib(String text) {
		if(text == null)
			throw new IllegalArgumentException();
		
		this.text = text;
		this.name = "宋体";
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
     * 文本拆分成单个字的FontAttrib数组，字体为font
     * @param text 拆分文本
     * @param font 字体型号
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
	 * 返回包含相同字体，新的文字的FontAttrib
	 * @param text
	 * @return 创建相同字体新文本的对象
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
		// 字体
		StyleConstants.setFontFamily(set, "宋体");
		// 样式
		StyleConstants.setBold(set, false);
		StyleConstants.setItalic(set, false);
		// 大小
		StyleConstants.setFontSize(set, 14);
		// 颜色
		StyleConstants.setForeground(set, Color.BLACK);
		// 底色
		StyleConstants.setBackground(set, Color.WHITE);
		
		return set;
	}
	
	/**
	 * 返回属性集
	 * @return 属性集
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