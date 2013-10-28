package ch04q06_CommonAncestorOf2BinaryTreeNodes;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/*
 * James TK Lo (C) 2013.  All rights reserved.  https://github.com/jamestklo
 * Personal implementation(s) in Java, for finding the first common ancestor between 2 nodes in a binary tree
 *
Cracking The Coding Interview 4th Edition, Question 4.6
Design an algorithm and write code to find the first common ancestor of two nodes in a binary tree.
Avoid storing additional nodes in a data structure NOTE: This is not necessarily a binary search tree.
 *
 * the book's best solution (attempt #3 in page 129) will likely run in O(n^2) time.
 * 
 * this.firstCommonAncestor1(BinaryTreeNode p, BinaryTreeNode q) -> uses list of BinaryTreeNode's
 *   time  O(2n+log(n))
 *   space O(2*log(n))
 * this.firstCommonAncestor2(BinaryTreeNode p, BinaryTreeNode q) -> uses only 1 static int in BinaryTreeNode class
 *   time  O(n), recursive post-order traversal
 *   space O(1)
 * this.firstCommonAncestor2a(BinaryTreeNode p, BinaryTreeNode q) -> uses an object-wrapper to replace static int
 *   time  O(n), recursive post-order traversal
 *   space O(1)
 */

public class BinaryTreeNode {
	BinaryTreeNode left;
	BinaryTreeNode right;
	// path from this node to destination, in reverse order
	//   return null if no such path is found
	public List<BinaryTreeNode> path(BinaryTreeNode destination) {
		List<BinaryTreeNode> p = null;
		if (this.equals(destination)) {
			p = new LinkedList<BinaryTreeNode>();
			p.add(this);
			return p;
		}
		if (this.left != null) {
			p = this.left.path(destination);
			if (p != null) {
				p.add(this);
				return p;
			}
		}
		if (this.right != null) {
			p = this.right.path(destination);
			if (p != null) {
				p.add(this);
				return p;
			}
		}
		return null;
	}
	  
	private static BinaryTreeNode firstCommon(List<BinaryTreeNode> path1, List<BinaryTreeNode> path2, int diff) {
		ListIterator<BinaryTreeNode> itr1 = path1.listIterator();
		// number of nodes to skip in longer path: diff
		for (int i=0; i < diff; ++i) {
			itr1.next();
	    }	  	  
		ListIterator<BinaryTreeNode> itr2 = path2.listIterator();
		while (itr1.hasNext() && itr2.hasNext()) {
			BinaryTreeNode next1 = itr1.next();
			BinaryTreeNode next2 = itr2.next();
			if (next1.equals(next2)) {
				return next1;
			}
		}	
		return null;
	}
	public BinaryTreeNode firstCommonAncestor1(BinaryTreeNode p, BinaryTreeNode q) {
		List<BinaryTreeNode> path1 = this.path(p); // O(n);
		List<BinaryTreeNode> path2 = this.path(q); // O(n);
		if (path1 == null || path2 == null) {
			return null;
		} 
		// at most O(log n)	
		int diff = path2.size() - path1.size();
		if (diff < 0) { // path1 is longer than path1
			return firstCommon(path1, path2, 0-diff);
		}
		return firstCommon(path2, path1, diff);	
	};
	  
	private static int nodesFound = 0;
	private BinaryTreeNode firstCommonAncestorRecursive(BinaryTreeNode p, BinaryTreeNode q, int startFound) {    
		if (this.equals(p) || this.equals(q)) {
			if (++nodesFound == 2) {
				return null;
			}
		}
		if (this.left != null) {
			BinaryTreeNode first = this.left.firstCommonAncestorRecursive(p, q);
			if (first != null) {
				return first;
			}
		}
		if (nodesFound < 2 && this.right != null) {
			BinaryTreeNode first = this.right.firstCommonAncestorRecursive(p, q);
			if (first != null) {
				return first;
			}
		}
		if (nodesFound == 2 && startFound == 0) {
			nodesFound = 0;
			return this;	 
		}
		return null;
	}   
	public BinaryTreeNode firstCommonAncestor2(BinaryTreeNode p, BinaryTreeNode q) {
		nodesFound = 0;
		return this.firstCommonAncestorRecursive(p, q, 0);
	}
	  
	private static class FinderOfFirstCommonAncestor {
		private int nodesFound = 0;
		public BinaryTreeNode find(BinaryTreeNode root, BinaryTreeNode p, BinaryTreeNode q, int startFound) {
			if (root.equals(p) || root.equals(q)) {
				if (++nodesFound == 2) {
					return null;
				}
			}
			BinaryTreeNode first = null;
			if (root.left != null) {
				first = this.find(root.left, p, q, nodesFound);
				if (first != null) {
					return first;
				}
			}
			if (nodesFound < 2 && root.right != null) {
				first = this.find(root.right, p, q, nodesFound);
				if (first != null) {
					return first;
				}
			}
			if (nodesFound == 2 && startFound == 0) {
				nodesFound = 0;
				return root;
			}
			return null;	  
		}
	}
	public BinaryTreeNode firstCommonAncestor2a(BinaryTreeNode p, BinaryTreeNode q) {
		FinderOfFirstCommonAncestor finder = new FinderOfFirstCommonAncestor();
		return finder.find(this, p, q, 0);
	}
	  
	public static void main(String[] args) {
		// ideas for test cases; p & q are interchangable
		// solution assumes binary tree is not corrupted -> no cycles, no node has > 2 children, etc.
		//  normal: p & q on different sides of a binary tree
		//  p & q are siblings of each other
		//  p & q are at different levels/height/depths 
		//  p is parent of q
		//  p is ancestor of q
		//  either or both p & q are leaf nodes
		//  p is not in the tree -> null
		//  both p & q are not in the tree -> null
		//  binary tree has only root node
		//  ... etc.
	}
}
