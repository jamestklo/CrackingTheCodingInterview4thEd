package ch05q03_FinderOfNextSmallestPrevLargest;

import java.util.Stack;
/*
 * James TK Lo (C) 2013.  All rights reserved.  https://github.com/jamestklo
 * Personal implementation(s) in Java, for calculating the binary representation of a decimal number.
 *   
Cracking The Coding Interview 4th Edition, Question 5.3
Given an integer, print the next smallest and next largest number that have the same number of 1 bits in their binary representation.
 *
 * a solution cleaner than the book's, not yet support negative numbers
 */
public class FinderOfNextSmallestPrevLargest {
	public static int binaryToInteger(String bits) {
		int output = 0;	
		for (int i=0, j=bits.length()-1; i < bits.length(); ++i, --j) {
			if (bits.charAt(i) == '1') {
				output += 1<<j;	
			}
		}
		return output;
	}
	public static final String integerToBinary(int input) {
		int copy = input;
		Stack<String> stack = new Stack<String>();
		while (copy != 0) {
			int bit = copy & 1;
			if (bit == 1) {
				stack.add("1");
			}
			else {
				stack.add("0");
			}
			copy = copy >>> 1;
		}
		StringBuffer sb = new StringBuffer();
		while(! stack.empty()) {
			sb.append(stack.pop());
		}
		return sb.toString();
	}
	
	protected static final int NUM_BITS = 32;
	protected static final int POS_BITS = NUM_BITS-2;
	protected static final int MOST_POSITIVE = ((1<<POS_BITS) -1) + (1<<POS_BITS);
	protected static final int MOST_NEGATIVE = 0-MOST_POSITIVE;
	protected static int find(int input, boolean bit) {
		switch (input) {
			case -1:	return -1;
			case 0:		return 0;
		}
		if (bit) { 
			switch (input) {
				case MOST_NEGATIVE:	return MOST_NEGATIVE;	// previous largest of the most negative number
				case MOST_POSITIVE:	return MOST_POSITIVE;	// next smallest of the most positive number
			}
		}
		else {
			switch(input) {
				case MOST_NEGATIVE:	return 1;				// next smallest of the most negative number
				case -2:			return MOST_POSITIVE;	// next smallest of -2
				case 1:				return MOST_NEGATIVE;	// previous largest of 1
				case MOST_POSITIVE:	return -2;				// previous largest of the most positive number
			}
		}
		
		int copy  = input;
		int count = -1;
		int first = NUM_BITS;
		for (int i=0; i < NUM_BITS; ++i, copy>>=1) {
			if ((copy & 1) == 1) {
				++count;
			}
			else if (count >= 0) {
				first = i;			
				break;
			}
		}
		int mask = 1 << first;
		if (bit) {
			return ( input & (~0-mask+1) ) | mask | ((1<<count)-1);	
		}
		mask <<= 1;
		int right = 1 << (first-count);
		right = right -1;
		return ( input & (~0-mask+1) ) | (right<<count);					
	}
	public static int findNextSmallest(int input) {
		return find(input, input >= 0);
	}
	private static String findNextSmallest(String input) {
		return integerToBinary(findNextSmallest(binaryToInteger(input)));
	}
	public static int findPrevLargest(int input) {
		return find(input, input < 0);
	}
	private static String findPrevLargest(String input) {
		return integerToBinary(findPrevLargest(binaryToInteger(input)));
	}	
	public static void main(String[] args) {
		System.out.println(findNextSmallest("1011100"));
		System.out.println(findPrevLargest("1100011"));
	}
}
