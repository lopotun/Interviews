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

public class StringsTransformer {
	private List<String> data = new ArrayList<String>();

	public StringsTransformer(List<String> startingData) {
		this.data = startingData;
	}

	private void forEach(StringFunction function) {
		List<String> newData = new ArrayList<>();
		for(String str : data) {
			newData.add(function.transform(str));
		}
		data = newData;
	}

	public List<String> transform(List<StringFunction> functions) throws InterruptedException {
		List<Thread> threads = new ArrayList<>();
		for(final StringFunction f : functions) {
			threads.add(new Thread(new Runnable() {
				@Override
				public void run() {
					forEach(f);
				}
			}));
		}
		for(Thread t : threads) {
			t.join();
		}
		return data;
	}

	public static interface StringFunction {
		String transform(String str);
	}
}