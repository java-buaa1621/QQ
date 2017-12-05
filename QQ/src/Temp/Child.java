package Temp;

import java.awt.List;
import java.io.IOException;
import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.Map;

class Parent{
	private int x = 6;
	public Parent() {
		init();
	}
	void init(){System.out.println(1);};
}
public class Child extends Parent{ 
	public int x = 9;
    public Child() {
    	init();
    }
    void init() {System.out.println(2);}
    public static void main(String[] args) {
		Parent c = new Child();
	}
}