package kem.leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Created by Evgeny Kurtser on 17-Aug-22 at 6:03 PM.
 * <a href=mailto:lopotun@gmail.com>Eugene Kurtzer</a>
 */
class FirstMissingPositive41Test {

	@Test
	void testEmpty() {
		int res = FirstMissingPositive41.firstMissingPositiveSort01(new int[0]);
		Assertions.assertEquals(1, res);
	}

	@Test
	void testZero() {
		int res = FirstMissingPositive41.firstMissingPositiveSort01(new int[]{0});
		Assertions.assertEquals(1, res);
	}

	@Test
	void testOne() {
		int res = FirstMissingPositive41.firstMissingPositiveSort01(new int[]{1});
		Assertions.assertEquals(2, res);
	}

	@Test
	void testNegativeOne() {
		int res = FirstMissingPositive41.firstMissingPositiveSort01(new int[]{-5});
		Assertions.assertEquals(1, res);
	}

	@Test
	void testNegative() {
		int res = FirstMissingPositive41.firstMissingPositiveSort01(new int[]{-1, 0, -5});
		Assertions.assertEquals(1, res);
	}

	@Test
	void test01() {
		int res = FirstMissingPositive41.firstMissingPositiveSort01(new int[]{1, 2, 0});
		Assertions.assertEquals(3, res);
	}

	@Test
	void test02() {
		int res = FirstMissingPositive41.firstMissingPositiveSort01(new int[]{3, 4, -1, 1});
		Assertions.assertEquals(2, res);
	}

	@Test
	void test03() {
		int res = FirstMissingPositive41.firstMissingPositiveSort01(new int[]{7, 8, 9, 11, 12});
		Assertions.assertEquals(1, res);
	}

	@Test
	void test04() {
		int res = FirstMissingPositive41.firstMissingPositiveSort01(new int[]{2, 3, -7, 6, 8, 1, -10, 1000, 15});
		Assertions.assertEquals(4, res);
	}

	@Test
	void test05() {
		int res = FirstMissingPositive41.firstMissingPositiveSort01(new int[]{3, 1, 1, -1, 1});
		Assertions.assertEquals(2, res);
	}

	@Test
	void test11() {
		int res = FirstMissingPositive41.firstMissingPositiveSort01(new int[]{1, 1});
		Assertions.assertEquals(2, res);
	}

	@Test
	void test12() {
		int res = FirstMissingPositive41.firstMissingPositiveSort01(new int[]{1, 2});
		Assertions.assertEquals(3, res);
	}

	@Test
	void test22() {
		int res = FirstMissingPositive41.firstMissingPositiveSort01(new int[]{2, 2});
		Assertions.assertEquals(1, res);
	}

	@Test
	void test232() {
		int res = FirstMissingPositive41.firstMissingPositiveSort01(new int[]{2, 3, 2});
		Assertions.assertEquals(1, res);
	}

	@Test
	void test133() {
		int res = FirstMissingPositive41.firstMissingPositiveSort01(new int[]{1, 3, 3});
		Assertions.assertEquals(2, res);
	}
}