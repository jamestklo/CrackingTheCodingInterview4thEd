package ch09q03_BinarySearchOnSortedArrayRotated;
/*
 * James TK Lo (C) 2013.  All rights reserved.  https://github.com/jamestklo
 * Personal implementation(s) in Java, for calculating the binary representation of a decimal number.
 *   
Cracking The Coding Interview 4th Edition, Question 9.3
Given a sorted array of n integers that has been rotated an unknown number of times, give an O(log n) algorithm that finds an element in the array.
	You may assume that the array was originally sorted in increasing order 
	EXAMPLE:
		Input: find 5 in array (15 16 19 20 25 1 3 4 5 7 10 14)
		Output: 8 (the index of 5 in the array)
 *
 * a solution cleaner than the book's
 */
public class BinarySearchOnSortedArrayRotated {
	public static int search(int a[], int target) {
		int l=0, u=a.length-1;
		while (l <= u) {
			int m = l + (u-l)/2; // different from book's "(l+u)/2": to prevent overflow
			if (target == a[m]) {
				return m;
			}
			else if (target < a[m]) {
				// normal
				if (target >= a[l] || a[l] >= a[m]) {
					u = m-1;
				}
				else {
					l = m+1;
				}
			}
			else if (target > a[m]) { 
				// normal
				if (target <= a[u] || a[u] <= a[m]) {
					l = m + 1;
				}
				else {
					u = m - 1;
				}
			}
		}
		return -1;
	}
	
	public static void main(String[] args) {
		// ideas for test cases
		// array:
		// 	empty array, null, 1 element array
		//	normal array, array with many elements
		//  array with a lot of duplicates, array has only 1 type of element, duplicates happen at left most, right most, and midpoint, and a combination of
		// 	rotated array in which minimum is at midpoint
		// 	rotated array in which minimum is on left side of midpoint
		// 	rotated array in which minimum is on right side of mipoint
		// 	rotated array in which minimum is at the end of array
		// target:
		//  target is exactly at midpoint
		// 	target is on left of midpoint
		// 	target is on right of midpoint
		// 	target is not in array (-1 is the return)
		// 	target is a duplicate in array
	}

}
