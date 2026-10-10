package fido.task1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

	@Test
	void createsLeafNode() {
		var node = new Node<>(42);

		assertEquals(42, node.data);
		assertNull(node.next);
	}

	@Test
	void linksTwoNodes() {
		var head = new Node<>(10, new Node<>(20));

		assertEquals(10, head.data);
		assertEquals(20, head.next.data);
		assertNull(head.next.next);
	}

	@Test
	void chainsMultipleNodes() {
		var head = new Node<>(1, new Node<>(2, new Node<>(3)));

		assertEquals(1, head.data);
		assertEquals(2, head.next.data);
		assertEquals(3, head.next.next.data);
		assertNull(head.next.next.next);
	}

	@Test
	void worksWithNonPrimitiveTypes() {
		var head = new Node<>("alpha", new Node<>("beta"));

		assertEquals("alpha", head.data);
		assertEquals("beta", head.next.data);
	}

	@Test
	void allowsInPlaceMutation() {
		var node = new Node<>(10);
		node.data = 99;
		node.next = new Node<>(100);

		assertEquals(99, node.data);
		assertEquals(100, node.next.data);
	}

	@Test
	void canBeTraversedLinkByLink() {
		var head = new Node<>(1, new Node<>(2, new Node<>(3)));

		var first = head.next;
		assertEquals(2, first.data);

		var second = first.next;
		assertEquals(3, second.data);

		assertNull(second.next);
	}
}