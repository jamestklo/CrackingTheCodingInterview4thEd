package ch05q03_FinderOfNextSmallestPrevLargest;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

import com.google.common.base.Joiner;
/*
 * James TK Lo (C) 2013.  All rights reserved.  https://github.com/jamestklo
 * Personal implementation(s) in Java, for calculating the binary representation of a decimal number.
 *   
Cracking The Coding Interview 4th Edition, Question 5.3
Given an integer, print the next smallest and next largest number that have the same number of 1 bits in their binary representation.
 *
 * a solution cleaner than the book's
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
		if (input == 0) {
			return "0";
		}
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
	protected static final int MOST_NEGATIVE = -1-MOST_POSITIVE;
	protected static int find(int input, boolean bit) {		
		switch (input) {
			case -1:	return -1;
			case 0:		return 0;
		}
		if (bit && input==MOST_POSITIVE) {
			// next smallest of the most positive number
			return MOST_POSITIVE;	
		}
		else if (input == MOST_NEGATIVE){
			// previous largest of the most negative number
			return MOST_NEGATIVE;	
		}
		
		int copy  = input;
		int count = -1;
		int first = NUM_BITS;
		int biti = bit?1:0;
		for (int i=0; i < NUM_BITS; ++i, copy>>>=1) {		
			if ((copy & 1) == biti) {
				++count;
			}
			else if (count >= 0) {
				first = i;			
				break;
			}
		}
		int mask = 1 << first;
		if (first == NUM_BITS) {			
			mask -= 1;
			++count;
		}

		if (bit) {
			return ( input & (0-mask) ) | mask | ((1<<count)-1);
		}
		mask <<= 1;
		int right = 1 << (first-count);
		right = right -1;		
		return ( input & (0-mask) ) | (right<<count);					
	}
	public static int findNextSmallest(int input) {
		return find(input, true);
	}
	private static String findNextSmallest(String input) {
		return integerToBinary(findNextSmallest(binaryToInteger(input)));
	}
	public static int findPrevLargest(int input) {
		return find(input, false);
	}
	private static String findPrevLargest(String input) {
		return integerToBinary(findPrevLargest(binaryToInteger(input)));
	}	
	public static void main(String[] args) {
		List<String> strlist = new ArrayList<String>();
		strlist.add("1011100");
		strlist.add(integerToBinary(0));		
		strlist.add(integerToBinary(-1));
		strlist.add(integerToBinary(MOST_NEGATIVE));
		strlist.add(integerToBinary(1));
		strlist.add(integerToBinary(MOST_POSITIVE));
		strlist.add(integerToBinary(-2));
		strlist.add(integerToBinary(-4));
		strlist.add(integerToBinary(3));
		strlist.add(integerToBinary(7));
		strlist.add(integerToBinary(-3));
		
		ListIterator<String> str_itr = strlist.listIterator();
		while (str_itr.hasNext()) {
			String next = str_itr.next();
			int nextInteger = binaryToInteger(next);
			String smallest = findNextSmallest(next);
			String largest  = findPrevLargest(next);
			String[] parts = new String[3];			
			parts[0] = next						+"\t"+	nextInteger;
			parts[1] = findPrevLargest(smallest)+"\t"+	smallest;
			parts[2] = findNextSmallest(largest)+"\t"+	largest;
			System.out.println(Joiner.on("\n").join(parts));
			System.out.println("");
		}
	}
}
