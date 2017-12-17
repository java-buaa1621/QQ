package Temp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;



public abstract class Child {
	public abstract void fun();
	
	public void work() {
		fun();
	}
	
    public static void main(String[] args) throws FileNotFoundException{
    	
    	System.out.println((char) -1);
    }
}