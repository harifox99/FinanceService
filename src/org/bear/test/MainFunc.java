package org.bear.test;

public class MainFunc {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Animal animal = new Dog();
		((Dog) animal).setEar("ear2");
		System.out.println(((Dog) animal).getEar());
	}

}
