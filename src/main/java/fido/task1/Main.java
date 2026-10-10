package fido.task1;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public final class Main {
	private static final String ARROW = "->";
	private static final String USAGE = """
			Usage: java -jar node-task-1.0.0.jar <n | "a -> b -> c">
			  n            list of n nodes with data 0..n-1
			  a -> b -> c  list of the given values. Use quotes to write since > redirects in shell
			  (no args)    input is read from the console
			""";

	private Main() {
	}

	public static void main(String[] args) {
		if (args.length == 1 && (args[0].equals("-h") || args[0].equals("--help"))) {
			IO.print(USAGE);
			return;
		}

		String input = args.length == 0
				? IO.readln("n or a -> b -> c: ")
				: String.join(" ", args);

		try {
			List<String> values = parse(input);

			Node<String> head = null;
			for (int i = values.size() - 1; i >= 0; i--) {
				head = new Node<>(values.get(i), head);
			}

			Node<String> result = TwoThirdsOfLinkedList.getTwoThirdsNode(head);
			IO.println(result == null ? "null" : result.data);
		} catch (IllegalArgumentException e) {
			System.err.println("Error: " + e.getMessage());
			System.err.print(USAGE);
			System.exit(1);
		}
	}

	private static List<String> parse(String input) {
		if (input == null || input.isBlank()) {
			throw new IllegalArgumentException("no input");
		}

		String text = input.strip();

		if (text.matches("\\d+")) {
			try {
				int n = Integer.parseInt(text);
				return IntStream.range(0, n).mapToObj(Integer::toString).toList();
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("number is too large: " + text);
			}
		}

		if (!text.contains(ARROW)) {
			throw new IllegalArgumentException("expected a number or values separated by '" + ARROW + "'");
		}

		List<String> values = Arrays.stream(text.split(ARROW, -1)).map(String::strip).toList();
		if (values.stream().anyMatch(String::isEmpty)) {
			throw new IllegalArgumentException("empty value between arrows");
		}

		IO.println("Parsed values: " + values);

		return values;
	}
}