package kem.interviews.shai;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * <a href="https://www.interviewcake.com/question/java/stock-price?course=fc1&section=greedy">Apple stock</a>
 *  * <a href=mailto:lopotun@gmail.com>EvgenyK</a>
 */
class AppleStockTest {

	@Test
	void oneMinOneMax() {
		AppleStock appleStock = new AppleStock();
		int[] stockPrices = new int[] {10, 7, 5, 8, 11, 9}; // 11-5=6
		Assertions.assertEquals(6, appleStock.getStockProfit(stockPrices));
	}

	@Test
	void twoMinTwoMax() {
		AppleStock appleStock = new AppleStock();
		int[] stockPrices = new int[] {10, 12, 5, 8, 4, 6}; // 8-5=3
		Assertions.assertEquals(3, appleStock.getStockProfit(stockPrices));
	}

	@Test
	void alwaysDown() {
		AppleStock appleStock = new AppleStock();
		int[] stockPrices = new int[] {10, 8, 7, 5, 3, 1}; // 7-8=-1
		Assertions.assertEquals(-1, appleStock.getStockProfit(stockPrices));
	}

	@Test
	void twoValuesDown() {
		AppleStock appleStock = new AppleStock();
		int[] stockPrices = new int[] {10, 8}; // 8-10=-2
		Assertions.assertEquals(-2, appleStock.getStockProfit(stockPrices));
	}

	@Test
	void twoValuesUp() {
		AppleStock appleStock = new AppleStock();
		int[] stockPrices = new int[] {8, 10}; // 10-8=2
		Assertions.assertEquals(2, appleStock.getStockProfit(stockPrices));
	}

	@Test
	void twoValuesSame() {
		AppleStock appleStock = new AppleStock();
		int[] stockPrices = new int[] {5, 5}; // 5-5=0
		Assertions.assertEquals(0, appleStock.getStockProfit(stockPrices));
	}

}