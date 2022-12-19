package kem.leetcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Evgeny Kurtser on 09-Nov-22 at 10:40 PM.
 * <a href=mailto:lopotun@gmail.com>Eugene Kurtzer</a>
 */
class FruitIntoBaskets904Test {

	@Test
	void test01() {
		int[] fruits = new int[]{1,2,1};
		assertEquals(3, new FruitIntoBaskets904().totalFruit(fruits));
	}

	@Test
	void test02() {
		int[] fruits = new int[]{0,1,2,2};
		assertEquals(3, new FruitIntoBaskets904().totalFruit(fruits));
	}
	@Test
	void test03() {
		int[] fruits = new int[]{1,2,3,2,2};
		assertEquals(4, new FruitIntoBaskets904().totalFruit(fruits));
	}
	@Test
	void test04() {
		int[] fruits = new int[]{3,3,3,1,2,1,1,2,3,3,4};
		assertEquals(5, new FruitIntoBaskets904().totalFruit(fruits));
	}
	@Test
	void test05() {
		int[] fruits = null;
		assertEquals(0, new FruitIntoBaskets904().totalFruit(fruits));
	}
	@Test
	void test06() {
		int[] fruits = new int[]{};
		assertEquals(0, new FruitIntoBaskets904().totalFruit(fruits));
	}
	@Test
	void test07() {
		int[] fruits = new int[]{5};
		assertEquals(1, new FruitIntoBaskets904().totalFruit(fruits));
	}
	@Test
	void test08() {
		int[] fruits = new int[]{5,5};
		assertEquals(2, new FruitIntoBaskets904().totalFruit(fruits));
	}
	@Test
	void test09() {
		int[] fruits = new int[]{5,5,5};
		assertEquals(3, new FruitIntoBaskets904().totalFruit(fruits));
	}
	@Test
	void test10() {
		int[] fruits = new int[]{0,1,6,6,4,4,6};
		assertEquals(5, new FruitIntoBaskets904().totalFruit(fruits));
	}
	@Test
	void test11() {
		int[] fruits = new int[]{4,1,1,1,3,1,7,5};
		assertEquals(5, new FruitIntoBaskets904().totalFruit(fruits));
	}
	@Test
	void test12() {
		int[] fruits = new int[]{4,2,2,3,1,1,4,5};
		assertEquals(3, new FruitIntoBaskets904().totalFruit(fruits));
	}
}