package fido.task1;

public final class TwoThirdsOfLinkedList {
	private TwoThirdsOfLinkedList() {
	}

	public static <T> Node<T> getTwoThirdsNode(Node<T> head) {
		if (head == null || head.next == null) {
			return null;
		}

		Node<T> res = head;
		int len = 2;
		Node<T> curr = head.next.next;

		while (curr != null) {
			len = (len + 1) % 3;

			if (len != 1) {
				res = res.next;
			}

			curr = curr.next;
		}

		return res;
	}
}