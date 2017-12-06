package Temp;

import java.awt.List;
import java.io.IOException;
import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Child { 
    public static void main(String[] args) {
    	System.out.println(4180/60 * Math.log10((13.41-18.294)/(13.3-18.294)));
    	double A = 3.90802 /1000; 
    	double B = -5.80195 / 10000000;
    	double r0 = 1000;
    	Scanner sc = new Scanner(System.in);
    	double rt;
    	double T;
		while (true) {
			rt = sc.nextDouble();
			double de = 2*B;
			double delta = A*A - 4*B*(1- rt/r0);
			double nu = -A + Math.sqrt(delta);
			System.out.println(nu/de);
		}
	}
}