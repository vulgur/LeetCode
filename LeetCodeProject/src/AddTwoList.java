public class AddTwoList {
	/**
	 * Definition for singly-linked list. public class ListNode { int val;
	 * ListNode next; ListNode(int x) { val = x; next = null; } }
	 */
	private class NumList {
		ListNode headNode = null;
		ListNode tailNode = null;

		public NumList() {
			headNode = tailNode;
		}

		public void appendNode(ListNode node) {
			if (headNode == null) {
				headNode = node;
				tailNode = node;
			} else {
				tailNode.next = node;
				tailNode = node;
			}
		}
	}

	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		// Start typing your Java solution below
		// DO NOT write main() function
		NumList numList = new NumList();
		ListNode sumNode = null;
		boolean carry = false;
		while (l1 != null || l2 != null) {
			int sum = 0;
			if (l1 != null) {
				sum += l1.val;
			}
			if (l2 != null) {
				sum += l2.val;
			}

			if (carry) {
				sum++;
			}
			if (sum >= 10) {
				sum %= 10;
				System.out.println("sum=" + sum);
				carry = true;
			} else {
				carry = false;
			}
			sumNode = new ListNode(sum);
			numList.appendNode(sumNode);
			if (l1 != null)
				l1 = l1.next;
			if (l2 != null)
				l2 = l2.next;
		}
		if (carry) {
			sumNode = new ListNode(1);
			numList.appendNode(sumNode);
		}
		return numList.headNode;
	}

	public String nodeToString(ListNode node) {
		StringBuilder sb = new StringBuilder();
		while (node != null) {
			sb.append(node.val);
			node = node.next;
		}
		return sb.reverse().toString();
	}
}
