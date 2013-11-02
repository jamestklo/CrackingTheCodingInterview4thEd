package ch05q02_PrintDecimalInBinaryString;

import java.util.Stack;
/*
 * James TK Lo (C) 2013.  All rights reserved.  https://github.com/jamestklo
 * Personal implementation(s) in Java, for calculating the binary representation of a decimal number.
 *   
Cracking The Coding Interview 4th Edition, Question 5.2
Given a (decimal - e.g. 3.72) number that is passed in as a string, print the binary representation. 
If the number can not be represented accurately in binary, print “ERROR”ee.
 *
 * the book's solution (page 134) has a bug in line 14: the block if(decPart == 1) shouldn't be there 
 * 	for example, input=3.1 would lead to an incorrect output
 * 
 * here we support negative numbers, while the book doesn't, assuming positive/negative decimals are presented in the same way
 */
public class NumbersInString {

	final static int NUM_BITS = countbits();
	private static final int countbits() {
		int input = -1;
		int count = 0;
		while (input != 0) {
			input >>>= 1;
			++count;
		}
		return count;
	}
	public static String printBinary(String n) {
		if (n == null || n.equals("")) {
			return n;
		}
		StringBuffer output = new StringBuffer();		
		int point = n.indexOf(".");
		
		// added consideration of the case when input doesn't have a "."
		int intPart = Integer.parseInt( (point > 0)?n.substring(0, point):n );
		Stack<Integer> intStck = new Stack<Integer>();
		while(intPart != 0) {
			intStck.add(intPart & 1);
			intPart >>>= 1;		// using unsigned operator to shift bits
		}
		while (! intStck.empty()) {
			output.append(intStck.pop());			
		}
		
		StringBuffer decStrb = new StringBuffer();
		double decPart = (point > 0)?Double.parseDouble(n.substring(point)):0;
		while (decPart > 0) {
			/*if (decStrb.length() >= 32) {
				return "ERROR";
			}*/
			decPart *= 2;
			if (decPart >= 1) {
				decStrb.append("1");
				decPart -= 1;
			}
			else {
				decStrb.append("0");
			}
		}
		if (decStrb.length() > 0) {
			output.append(".");
			output.append(decStrb);	
		}
		return output.toString();
	}
	public static void main(String[] args) {
		System.out.println(printBinary("-1.5"));
		System.out.println(printBinary("3000000000.1"));
	}
}
