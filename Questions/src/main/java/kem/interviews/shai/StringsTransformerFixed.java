package kem.interviews.shai;

/*
R&D Engineer Questions for Next Interview
The interview will include the following question(s) and discussion around your answers. You are not required
to prepare any written solution (although you can if you want)
Question 1
Jimmy was tasked with writing a class that takes a base list of strings and a series of transformations and
applies them, returning the end result.
To better utilize all available resources, the solution was done in a multi-threaded fashion. Explain the
problems with this solution, and offer 2 alternatives. Discuss the advantages of each approach.
*/

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class StringsTransformerFixed {
	private static final int MAX_NUM_OF_THREADS = 40;
	private final List<String> data;

	public StringsTransformerFixed(List<String> startingData) {
		this.data = startingData;
	}

	private List<String> forEach(List<StringFunction> functions, Collection<String> part) {
		List<String> newData = part.stream()
				.map(str -> {
					String tmp = str;
					for(StringFunction function : functions) {
						tmp = function.transform(tmp);
					}
					return tmp;
				}).collect(Collectors.toList());
		return newData;
	}

	public List<String> transform(List<StringFunction> functions) throws InterruptedException {
		class TT extends Thread {
			private final Collection<String> part;
			private List<String> res;
			public TT(Collection<String> part) {
				this.part = part;
			}
			@Override
			public void run() {
				res = forEach(functions, part);
			}
		}

		List<TT> threads = new ArrayList<>();
		final List<Collection<String>> parts = partition(data, MAX_NUM_OF_THREADS);
		parts.forEach(part -> {
			TT tt = new TT(part);
			threads.add(tt);
			tt.start();
		});

		for(Thread t : threads) {
			t.join();
		}

		return threads.stream()
				.flatMap(tt -> tt.res.stream())
				.collect(Collectors.toList());
	}

	public interface StringFunction {
		String transform(String str);
	}

	private static <T> List<Collection<T>> partition(Collection<T> collection, int num) {
		if(collection == null || collection.isEmpty()) {
			return Collections.emptyList();
		}
		int chunkSize = (int) Math.ceil((double) collection.size() / (double) num);
		final AtomicInteger counter = new AtomicInteger();
		final Collection<List<T>> values = collection.stream()
				//.collect(Collectors.groupingBy(it -> counter.getAndIncrement() / num))
				.collect(Collectors.groupingBy(it -> counter.getAndIncrement() / chunkSize))
				.values();
		return new ArrayList<>(values);
	}

	public static void main(String[] args) throws InterruptedException {
		final List<String> lst = Arrays.asList("Alternatively,", "we", "could", "use", "Java", "8", "Stream", "API", "and", "its", "Collectors.groupingBy()", "collector", "method.", "Using", "this", "method", "we", "produce", "a", "map", "from", "a", "stream,", "but", "we", "can", "invoke", "values()", "method", "on", "the", "final", "map", "to", "get", "a", "collection", "of", "all", "its", "values.", "Then", "we", "can", "decide", "if", "we", "want", "to", "stay", "with", "the", "Collection<List<T>>", "or", "if", "we", "want", "to", "cast", "it", "to", "List<List<T>>", "by", "passing", "the", "collection", "to", "the,", "e.g.", "ArrayList<T>", "constructor.", "Below", "you", "can", "find", "an", "exemplary", "code");
		final List<StringFunction> fs = Arrays.asList(str -> str.toUpperCase(Locale.ROOT), str -> str.replace('A', 'a'));
		StringsTransformerFixed x = new StringsTransformerFixed(lst);
		final List<String> result = x.transform(fs);
		System.out.println(result);
	}
}