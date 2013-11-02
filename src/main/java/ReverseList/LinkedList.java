package ReverseList;

public class LinkedList {
	ListNode head;
	ListNode tail;
	public void add(Object o) {
		if (head == null) {
			head = new ListNode(o);
			tail = head;
		}
		else {
			tail.next = new ListNode(o);
			//tail.next.prev = tail;
			tail = tail.next;
		}
	}
	public Object remove() {
		if (head == null) {
			return null;
		}
		Object r = head.value;
		head = head.next;
		/*if (head != tail) {
			head.prev = tail;
		}*/
		if (head == null) {
			tail = null;
		}
		return r;
	}
	public ListNode reverse() {
		ListNode prev = null;
		ListNode curr = head;
		ListNode next = curr.next; 
		while (next != null) {
			curr.next = prev;
			// curr.prev = next;
			prev = curr;
			curr = next;
			next = next.next;			
		}
		this.head = curr;
		return head;
	}
	private static class ListNode {
		ListNode next;
		Object value;
		public ListNode(Object v) {
			value = v;
		}
	}	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}
