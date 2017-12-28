package qq.ui.frame;

//
//abstract class TextBuffer {
//	
//	protected final static int MAX_SIZE = 10; // 容量
//	protected Object[] textInfo;
//	protected int size; // 相当于当前总数
//	
//	public TextBuffer() {
//		textInfo = new Object[MAX_SIZE];
//		clear();
//	}
//	
//	protected void clear() {
//		for(int i = 0; i < MAX_SIZE; i++) 
//			textInfo[i] = null;
//		size = 0;
//	}
//	
//	public void insert(Object obj) {
//		if(obj == null)
//			throw new IllegalArgumentException();
//		
//		textInfo[size++] = obj;
//		if(size == MAX_SIZE)
//			flush();
//	}
//	
//	public void flush() {
//		dealTextInfo(textInfo, size);
//		clear();
//	}
//	
//	protected abstract void dealTextInfo(Object[] textInfos, int size);
//			
//}


public class Temp {
	public static void main(String args[]) {
		TextBuffer buf = new TextBuffer() {
			@Override
			protected void dealTextInfo(Object[] textInfos, int size) {
				for(int i = 0; i < size; i++){
					Object text = textInfos[i];
					System.out.print((String) text);
				}
				System.out.print('\n');
			}
		};
		String s = "dfiuobgauisbfgoiaubgduiobugiobroasdiabgurie";
		for(int i=0;i<s.length();i++){
			buf.insert(s.substring(i, i+1));
		}
		buf.flush();
	}
}
