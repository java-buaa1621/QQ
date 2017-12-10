package Temp;

import java.util.ArrayList;
import java.util.List;
class GrandFather{
	
}
class Father extends GrandFather{
	public void earn(){
		
	}
}
class Son extends Father{}

public class Child { 
	public static void main(String args[]){
		ArrayList<Son> sa = new ArrayList<Son>();
		GrandFather gf = new GrandFather();
		sa.add((Son)gf);
		System.out.println(123);
		ArrayList<? extends Father> a= sa;
		//@SuppressWarnings("unchecked")
		ArrayList<Father> b = (ArrayList<Father>)a;
		for(Father f:b){
			f.earn();
		}
	}
}