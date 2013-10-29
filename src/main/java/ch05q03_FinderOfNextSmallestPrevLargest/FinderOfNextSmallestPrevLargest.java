package ch05q03_FinderOfNextSmallestPrevLargest;

import java.util.Stack;

public class FinderOfNextSmallestPrevLargest {
	private static final int NUM_BITS = 32;	
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
	public static int find(int input, int bit) {
		int copy  = input;
		int count = -1;
		int first = -1;
		for (int i=0; i < NUM_BITS; ++i, copy>>=1) {
			if ((copy & 1) == bit) {
				++count;
			}
			else if (count >= 0) {
				first = i;			
				break;
			}
		}
		int mask = 1 << first;
		if (bit == 1) {
			return ( input & (~0-mask+1) ) | mask | ((1<<count)-1);	
		}		
		int right = 1 << (first-count);
		right = right -1;
		return ( input & (~0-mask+1) ) | (right<<count);					
	}
	public static int findNextSmallest(int input) {
		return find(input, 1);
	}
	public static String findNextSmallest(String input) {
		return integerToBinary(findNextSmallest(binaryToInteger(input)));
	}
	public static int findPrevLargest(int input) {
		return find(input, 0);
	}
	public static String findPrevLargest(String input) {
		return integerToBinary(findPrevLargest(binaryToInteger(input)));
	}	
	public static void main(String[] args) {
		System.out.println(findNextSmallest("1011100"));
		System.out.println(findPrevLargest("100011"));
	}
}
