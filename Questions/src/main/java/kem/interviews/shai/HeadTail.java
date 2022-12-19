package kem.interviews.shai;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HeadTail<T> {
	private final T head;
	private final List<T> tail;

	HeadTail(T head, List<T> tail) {
		this.head = head;
		this.tail = tail;
	}

	/**
	 * Splits the given list to head and tail.
	 * @param lst the list to be split
	 * @param <T> type of the list element
	 * @return head and tail of the list.
	 */
	public static <T> HeadTail<T> split(List<T> lst) {
		if(lst == null || lst.isEmpty()) {
			return new HeadTail<>(null, Collections.emptyList());
		}
		return new HeadTail<>(lst.get(0), lst.stream().skip(1L).collect(Collectors.toList()));
	}

	public Optional<T> headOpt() {
		return Optional.ofNullable(head);
	}

	public T head() {
		return head;
	}

	public List<T> tail() {
		return tail;
	}
}