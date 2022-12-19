package kem.interviews.shai;

import groovy.lang.Tuple2;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Evgeny Kurtser on 03-May-22 at 8:19 PM.
 * <a href=<a href="lopotun@gmail.com">Eugene Kurtzer</a>
 */
public class Permutations<T> {
	List<T> excludeIth(@NotNull List<T> set, int pos) {
		return excludeAndExtractIth(set, pos).getV2();
	}

	Tuple2<Optional<T>, List<T>> excludeAndExtractHead(@NotNull List<T> set) {
		return excludeAndExtractIth(set, 0);
	}
	Tuple2<Optional<T>, List<T>> excludeAndExtractIth(@NotNull List<T> set, int pos) {
		if(pos < 0 || pos > set.size() - 1) {
			return new Tuple2<>(Optional.empty(), set);
		}
		Tuple2<Optional<T>, List<T>> res = new Tuple2<>(Optional.empty(), new ArrayList<>(set.size()));

		for(int i = 0; i < set.size(); i++) {
			T item = set.get(i);
			if(i != pos) {
				res.getV2().add(item);
			} else {
				res = new Tuple2<>(Optional.of(item), res.getV2());
			}
		}
		return res;
	}

	List<T> shiftRotateRight(@NotNull List<T> set) {
		if(set.size() < 2) {
			return new ArrayList<>(set);
		}
		List<T> lst = new ArrayList<>(set);
		T tmp = lst.get(0);
		lst.set(0, lst.get(lst.size() - 1));
		lst.set(lst.size() - 1, tmp);
		return new ArrayList<>(lst);
	}

	public static <T> void printAll(@NotNull List<Character> lst, char delimiter) {
		printAllRecursive(lst.size(), lst.toArray(new Character[]{}), delimiter);
	}
	public static <T> void printAllRecursive(int n, T[] elements, char delimiter) {
		if(n == 1) {
			printArray(elements, delimiter);
		} else {
			for(int i = 0; i < n-1; i++) {
				printAllRecursive(n - 1, elements, delimiter);
				if(n % 2 == 0) {
					swap(elements, i, n-1);
				} else {
					swap(elements, 0, n-1);
				}
			}
			printAllRecursive(n - 1, elements, delimiter);
		}
	}

	private static <T> void swap(T[] input, int a, int b) {
		T tmp = input[a];
		input[a] = input[b];
		input[b] = tmp;
	}
	private static <T> void printArray(T[] input, char delimiter) {
		System.out.print('\n');
		for(int i = 0; i < input.length; i++) {
			System.out.print(input[i]);
		}
	}


	/*
	abcd -> a(bcd)
	bcd -> b(cd)
	cd -> c(d)
	d -> d
	
	abcd abdc
	a b c d
	a, ab, ac, ad, abc, abd, abcd, abdc
	 */
	public static void main(String[] args) {
		List<Character> s = new ArrayList<>();
		s.add('A');
		s.add('B');
		s.add('C');
		s.add('D');
//		s.add('E');
//		s.add('F');
		System.out.println(s);
		final List<Character> characters = new Permutations<Character>().excludeIth(s, 3);
		System.out.println(characters);
		Tuple2<Optional<Character>, List<Character>> tuple2 = new Permutations<Character>().excludeAndExtractIth(s, 3);
		System.out.println(tuple2);
		printAll(s, ',');
	}
}
