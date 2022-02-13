package com.utilities.stringutils;

public class ReversedInteger {

	public static void main(String[] args) {
		Integer in = 1234;
		
		Integer reversedIn = 0;
		while(in>0) {
			int rem = in%10;
			reversedIn = reversedIn * 10 + rem;
			in = in/10;
		}
		
		System.out.println(reversedIn);
	}
}
