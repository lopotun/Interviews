package kem.interviews.shai;

import groovy.lang.Tuple2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

class SumOfTwoEntriesInArrayTest {
	private SumOfTwoEntriesInArray theRabbit;
	@BeforeEach
	void setUp() {
		theRabbit = new SumOfTwoEntriesInArray();
	}

	@Test
	void getSumOfTwoEntriesInArray() {
		Assertions.assertTrue(() -> {
			final Set<Tuple2<Integer, Integer>> expected = new HashSet<>();
			int[] data = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
			int sumOfTwo = 10;
			expected.add(new Tuple2<>(1, 7));
			expected.add(new Tuple2<>(2, 6));
			expected.add(new Tuple2<>(3, 5));
			final Set<Tuple2<Integer, Integer>> res = theRabbit.getSumOfTwoEntriesInArray(data, sumOfTwo);
			return expected.containsAll(res) && res.containsAll(expected);
		});
	}

	@Test
	void duplicateValues() {
		Assertions.assertTrue(() -> {
			final Set<Tuple2<Integer, Integer>> expected = new HashSet<>();
			//                     0  1  2  3  4  5  6  7  8
			int[] data = new int[]{1, 2, 3, 4, 8, 7, 7, 8, 6};
			int sumOfTwo = 10;
			expected.add(new Tuple2<>(1, 4));
			expected.add(new Tuple2<>(1, 7));
			expected.add(new Tuple2<>(2, 5));
			expected.add(new Tuple2<>(2, 6));
			expected.add(new Tuple2<>(3, 8));
			final Set<Tuple2<Integer, Integer>> res = theRabbit.getSumOfTwoEntriesInArray(data, sumOfTwo);
			return expected.containsAll(res) && res.containsAll(expected);
		});
	}

	@Test
	void getNoResult() {
		Assertions.assertTrue(() -> {
			int[] data = new int[]{1, 2, 3, 4, 5};
			int sumOfTwo = 10;
			final Set<Tuple2<Integer, Integer>> res = theRabbit.getSumOfTwoEntriesInArray(data, sumOfTwo);
			return res.isEmpty();
		});
	}

	@Test
	void empty() {
		Assertions.assertTrue(() -> {
			final Set<Tuple2<Integer, Integer>> res = theRabbit.getSumOfTwoEntriesInArray(new int[]{}, 10);
			return res.isEmpty();
		});
	}

	@Test
	void one() {
		Assertions.assertTrue(() -> {
			final Set<Tuple2<Integer, Integer>> res = theRabbit.getSumOfTwoEntriesInArray(new int[]{10}, 10);
			return res.isEmpty();
		});
	}

	@Test
	void nullData() {
		Assertions.assertTrue(() -> {
			final Set<Tuple2<Integer, Integer>> res = theRabbit.getSumOfTwoEntriesInArray(null, 10);
			return res.isEmpty();
		});
	}
}