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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class StringsTransformerWithExecutor {
	private static final int MAX_NUM_OF_THREADS = 2;
	private final List<String> data;

	public StringsTransformerWithExecutor(List<String> startingData) {
		this.data = startingData;
	}

	public List<String> transform(List<StringFunction> functions) {
		class TT implements Callable<String> {
			private final List<StringFunction> functions;
			private final String s;
			public TT(List<StringFunction> functions, String s) {
				this.functions = functions;
				this.s = s;
			}
			@Override
			public String call() {
				String res = s;
				for(StringFunction f: functions) {
					res = f.transform(res);
				}
				return res;
			}
		}

		final int len = data.size();
		final List<String> result = new ArrayList<>(len);
		final List<Future<String>> futures = new ArrayList<>(len);
		final ExecutorService threadPool = Executors.newFixedThreadPool(MAX_NUM_OF_THREADS);

		// Submit each string to be processed by series of StringFunctions.
		data.forEach(datum -> futures.add(threadPool.submit(new TT(functions, datum))));

		// Collect result of each above execution.
		// The result will be stored in the correct order since we create and iterate Futures in the same order.
		futures.forEach(future -> {
			try {
				result.add(future.get());
			} catch(ExecutionException | InterruptedException e) {
				result.add("ERROR");
			}
		});
		// Shut the thread-pool down immediately since we don't need it anymore.
		threadPool.shutdownNow();
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
		StringsTransformerWithExecutor x = new StringsTransformerWithExecutor(lst);
		final List<String> result = x.transform(sf);
		System.out.println(result);
	}
}