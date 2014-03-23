
/**
 * Definition for singly-linked list. 
 * class ListNode { 
 * 	int val; 
 * 	ListNode next;
 * 	ListNode(int x) { 
 * 		val = x; 
 * 		next = null; 
 * 	} 
 * }
 */
public class SortLinkedList {
	public ListNode sortList(ListNode head) {
		int length = 0;
		ListNode p = head;
		while (p != null) {
			length++;
			p = p.next;
		}
		if (length < 2)
			return head;

		ListNode h1, h2, tail;
		h1 = head;
		tail = head;
		for (int i = 0; i + 1 < length / 2; i++) {
			tail = tail.next;
		}

		h2 = tail.next;
		tail.next = null;

		h1 = sortList(h1);
		h2 = sortList(h2);

		tail = null;
		while (h1 != null && h2 != null) {
			if (tail == null) {
				if (h1.val < h2.val) {
					tail = head = h1;
					h1 = h1.next;
				} else {
					tail = head = h2;
					h2 = h2.next;
				}
			} else {
				if (h1.val < h2.val) {
					tail.next = h1;
					h1 = h1.next;
				} else {
					tail.next = h2;
					h2 = h2.next;
				}
				tail = tail.next;
			}
		}

		if (h1 == null) {
			tail.next = h2;
		}
		if (h2 == null) {
			tail.next = h1;
		}
		return head;
	}

	private void printList(ListNode head) {
		while (head != null) {
			System.out.println("*** " + head.val);
			head = head.next;
		}
	}

	public static void main(String[] args) {
		ListNode one = new ListNode(3);
		ListNode two = new ListNode(2);
		ListNode three = new ListNode(4);

		one.next = two;
		two.next = three;

		SortLinkedList sort = new SortLinkedList();
		sort.printList(sort.sortList(one));
	}
}
