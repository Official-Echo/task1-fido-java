package fido.task1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static fido.task1.TwoThirdsOfLinkedList.getTwoThirdsNode;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class TwoThirdsOfLinkedListTest {

	@Test
	void emptyListReturnsNull() {
		assertNull(getTwoThirdsNode(null));
	}

	@Test
	void singleNodeReturnsNull() {
		assertNull(getTwoThirdsNode(new Node<>(0)));
	}

	@Test
	void twoNodesReturnIndexZero() {
		var head = new Node<>(0, new Node<>(1));

		assertEquals(0, getTwoThirdsNode(head).data);
	}

	@Test
	void taskExampleThreeNodesReturnsOne() {
		var head = new Node<>(0, new Node<>(1, new Node<>(2)));

		assertEquals(1, getTwoThirdsNode(head).data);
	}

	@Test
	void taskExampleFourNodesReturnsOne() {
		var head = new Node<>(0, new Node<>(1, new Node<>(2, new Node<>(3))));

		assertEquals(1, getTwoThirdsNode(head).data);
	}

	@Test
	void taskExampleFiveNodesReturnsTwo() {
		var head = new Node<>(0, new Node<>(1, new Node<>(2, new Node<>(3, new Node<>(4)))));

		assertEquals(2, getTwoThirdsNode(head).data);
	}

	@Test
	void returnsTheNodeItselfNotACopy() {
		var third = new Node<>(2, new Node<>(3, new Node<>(4)));
		var head = new Node<>(0, new Node<>(1, third));

		assertSame(third, getTwoThirdsNode(head));
	}

	@Test
	void neverReturnsTheHeadForThreeOrMoreNodes() {
		var head = new Node<>(0, new Node<>(1, new Node<>(2)));

		assertNotSame(head, getTwoThirdsNode(head));
	}

	@Test
	void neverReturnsTheLastNode() {
		var last = new Node<>(4);
		var head = new Node<>(0, new Node<>(1, new Node<>(2, new Node<>(3, last))));

		assertNotSame(last, getTwoThirdsNode(head));
	}

	@Test
	void doesNotModifyTheList() {
		var e = new Node<>(4);
		var d = new Node<>(3, e);
		var c = new Node<>(2, d);
		var b = new Node<>(1, c);
		var a = new Node<>(0, b);

		getTwoThirdsNode(a);

		assertSame(b, a.next);
		assertSame(c, b.next);
		assertSame(d, c.next);
		assertSame(e, d.next);
		assertNull(e.next);
		assertEquals(0, a.data);
		assertEquals(4, e.data);
	}

	@Test
	void returnsTheSameResultWhenCalledRepeatedly() {
		var head = new Node<>(0, new Node<>(1, new Node<>(2, new Node<>(3))));

		assertSame(getTwoThirdsNode(head), getTwoThirdsNode(head));
	}

	@Test
	void worksWithStringData() {
		var head = new Node<>("a", new Node<>("b", new Node<>("c")));

		assertEquals("b", getTwoThirdsNode(head).data);
	}

	@Test
	void worksWithRecordData() {
		record Point(int x, int y) {
		}

		var head = new Node<>(new Point(0, 0), new Node<>(new Point(1, 1), new Node<>(new Point(2, 2))));

		assertEquals(new Point(1, 1), getTwoThirdsNode(head).data);
	}

	@Test
	void worksWithNullData() {
		var head = new Node<Integer>(null, new Node<>(null, new Node<>(0)));

		assertNull(getTwoThirdsNode(head).data);
	}

	static Stream<Arguments> sizesAndExpectedIndexes() {
		return Stream.concat(
				IntStream.rangeClosed(2, 100).mapToObj(n -> arguments(n, 2 * n / 3 - 1)),
				Stream.of(arguments(1_000, 665), arguments(100_000, 66_665)));
	}

	@ParameterizedTest(name = "n = {0} returns index {1}")
	@MethodSource("sizesAndExpectedIndexes")
	void returnsNodeAtTwoThirdsPosition(int n, int expectedIndex) {
		Node<Integer> head = null;
		for (var i = n - 1; i >= 0; i--) {
			head = new Node<>(i, head);
		}

		assertEquals(expectedIndex, getTwoThirdsNode(head).data);
	}
}