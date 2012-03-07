package org.bear.main;

import java.util.Scanner;

public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);        
		System.out.print("Please input a number: ");        
		int input = scanner.nextInt();
		//System.out.printf("Oh! I get %d!!", scanner.nextInt());
		System.out.printf("Oh! I get " + input);

	}

}
