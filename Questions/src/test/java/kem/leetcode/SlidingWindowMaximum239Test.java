package kem.leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

class SlidingWindowMaximum239Test {

	private SlidingWindowMaximum239 test;
	@BeforeEach
	void setUp() {
		test = new SlidingWindowMaximum239();
	}


	@Test
	void maxSlidingWindow10K() {
		maxSlidingWindowRandom(10_000, 10);
		maxSlidingWindowRandom(10_000, 10);
		maxSlidingWindowRandom(10_000, 100);
		maxSlidingWindowRandom(10_000, 1_000);
	}
	@Test
	void maxSlidingWindow100K() {
		maxSlidingWindowRandom(100_000, 10);
		maxSlidingWindowRandom(100_000, 10);
		maxSlidingWindowRandom(100_000, 100);
		maxSlidingWindowRandom(100_000, 1_000);
	}
	@Test
	void maxSlidingWindow1M() {
		maxSlidingWindowRandom(1_000_000, 10);
		maxSlidingWindowRandom(1_000_000, 10);
		maxSlidingWindowRandom(1_000_000, 100);
		maxSlidingWindowRandom(1_000_000, 1_000);
	}
	@Test
	void maxSlidingWindow10M() {
		maxSlidingWindowRandom(10_000_000, 10);
		maxSlidingWindowRandom(10_000_000, 10);
		maxSlidingWindowRandom(10_000_000, 100);
		maxSlidingWindowRandom(10_000_000, 1_000);
	}

	@Test
	void maxSlidingWindow05() {
		maxSlidingWindow(new int[]{9, 8, 9, 9, 9, 5, 4, 3, 9}, 3, new int[]{9, 9, 9, 9, 9, 5, 9});
	}

	@Test
	void maxSlidingWindow04() {
		maxSlidingWindow(new int[]{9, 8, 7, 6, 9, 5, 4, 3, 9}, 3, new int[]{9, 8, 9, 9, 9, 5, 9});
	}

	@Test
	void maxSlidingWindow03() {
		maxSlidingWindow(new int[]{9, 8, 7, 6, 5, 4, 3, 9}, 3, new int[]{9, 8, 7, 6, 5, 9});
	}

	@Test
	void maxSlidingWindow02() {
		maxSlidingWindow(new int[]{1}, 1, new int[]{1});
	}

	@Test
	void maxSlidingWindow01() {
		maxSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3, new int[]{3, 3, 5, 5, 6, 7});
	}


	private void maxSlidingWindowRandom(int n, int k) {
		final int[] nums = ThreadLocalRandom.current().ints(n).toArray();
		maxSlidingWindow(nums, k, null);
	}


	private void maxSlidingWindow(int[] nums, int k, int[] expected) {
		int[] res1, res2;
		long delta1, delta2;
		long start1 = System.currentTimeMillis();
		res1 = test.maxSlidingWindowMy(nums, k);
		delta1 = System.currentTimeMillis() - start1;

		long start2 = System.currentTimeMillis();
		res2 = test.maxSlidingWindow(nums, k);
		delta2 = System.currentTimeMillis() - start2;
		Assertions.assertArrayEquals(res1, res2);
		System.out.printf("%d vs %d\t\tin %,d nums\t\twith %d frame%n", delta1, delta2, nums.length, k);
		if(expected != null) {
			Assertions.assertArrayEquals(expected, res2);
		}
	}
}