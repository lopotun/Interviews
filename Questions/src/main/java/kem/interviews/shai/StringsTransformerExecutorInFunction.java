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

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class StringsTransformerExecutorInFunction {
	private final List<String> data;

	public StringsTransformerExecutorInFunction(List<String> startingData) {
		this.data = startingData;
	}

	public List<String> transform(List<StringFunction> functions) {
		// Guard.
		if(functions == null || functions.isEmpty()) {
			return data;
		}

		// TT emulates microservices chain.
		final TT tt = TT.of(functions);

		// Submit each string to be processed by series of StringFunctions.
		final List<Future<String>> futures = data.stream()
				.map(tt::apply)
				.collect(Collectors.toList());

		// Collect result of each above execution.
		// The result will be stored in the correct order since we create and iterate Futures in the same order.
		final List<String> result = futures.stream()
				.map(future -> {
					try {
						return future.get();
					} catch(ExecutionException | InterruptedException e) {
						return "ERROR";
					}
				}).collect(Collectors.toList());

		tt.shutdownNow();
		return result;
	}

	@FunctionalInterface
	public interface StringFunction {
		String transform(String str);
	}

	public static void main(String[] args) throws InterruptedException {
		final List<String> lst = Arrays.asList("Alternatively,", "we", "could", "use", "Java", "8", "Stream", "API", "and", "its", "Collectors.groupingBy()", "collector", "method.", "Using", "this", "method", "we", "produce", "a", "map", "from", "a", "stream,", "but", "we", "can", "invoke", "values()", "method", "on", "the", "final", "map", "to", "get", "a", "collection", "of", "all", "its", "values.", "Then", "we", "can", "decide", "if", "we", "want", "to", "stay", "with", "the", "Collection<List<T>>", "or", "if", "we", "want", "to", "cast", "it", "to", "List<List<T>>", "by", "passing", "the", "collection", "to", "the,", "e.g.", "ArrayList<T>", "constructor.", "Below", "you", "can", "find", "an", "exemplary", "code");
		final List<StringFunction> sf = Arrays.asList(
				str -> str.toUpperCase(Locale.ROOT),            // Java -> JAVA
				str -> str.replace('A', 'a'),    // JAVA -> JaVa
				str -> str.concat("[").concat(String.valueOf(str.length())).concat("]") // JaVa -> JaVa[4]
		);
		StringsTransformerExecutorInFunction x = new StringsTransformerExecutorInFunction(lst);
		final List<String> result = x.transform(sf);
		System.out.println(result);
	}
}

/**
 * Encapsulates {@linkplain StringsTransformerExecutorInFunction.StringFunction}, thread-pool that will run this function and reference to next TT instance (if any).
 */
class TT {
	private static final int MAX_NUM_OF_THREADS = 2;
	final ExecutorService threadPool = Executors.newFixedThreadPool(MAX_NUM_OF_THREADS);
	private final StringsTransformerExecutorInFunction.StringFunction function;
	private final TT next;

	private TT(StringsTransformerExecutorInFunction.StringFunction function, TT next) {
		this.function = function;
		this.next = next;
	}

	Future<String> apply(final String s) {
		Future<String> res;
		if(next == null) {
			// This is the last function in chain. Just submit your own transformation
			res = threadPool.submit(() -> function.transform(s));
		} else {
			// This is NOT the last function in chain.
			res = threadPool.submit(() -> {
				// The code below is executed in a separate thread.
				try {
					Future<String> fromNextF = next.apply(s);   // Get Future from next TT.
					String fromNextS = fromNextF.get();         // Wait for Future completion and get the result.
					return function.transform(fromNextS);       // Apply your own transformation on this result above and return the final result.
				} catch(InterruptedException | ExecutionException e) {
					return "ERROR";
				}
			});
		}
		return res;
	}

	void shutdownNow() {
		// Shut the thread-pool down immediately since we don't need it anymore.
		threadPool.shutdownNow();
		if(next != null) {
			next.shutdownNow();
		}
	}

	/**
	 * Creates TT instance with <strong>reverse</strong> chain of supplied functions.<p/>
	 * For example, if the given functions list is <em>[f1, f2, f3]</em> then the method returns <em>TT(f3, (TT(f2, TT(f1, null)))</em>
	 *
	 * @param functions functions list
	 * @return TT instance with <strong>reverse</strong> chain of supplied functions.
	 * @throws IllegalArgumentException if supplied list is <em>null</em> or empty.
	 */
	static TT of(List<StringsTransformerExecutorInFunction.StringFunction> functions) throws IllegalArgumentException {
		if(functions == null || functions.isEmpty())
			throw new IllegalArgumentException("StringFunction list should not be empty");
		return ofR(functions);
	}

	private static TT ofR(List<StringsTransformerExecutorInFunction.StringFunction> functions) {
		if(functions.size() == 1) {
			return new TT(functions.get(0), null);
		} else {
			final List<StringsTransformerExecutorInFunction.StringFunction> tail = functions.subList(0, functions.size() - 1);
			return new TT(functions.get(functions.size() - 1), ofR(tail));
		}
	}
}