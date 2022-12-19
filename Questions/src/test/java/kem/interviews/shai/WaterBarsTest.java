package kem.interviews.shai;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Created by Evgeny Kurtser on 16-Nov-22 at 4:44 PM.
 * <a href=mailto:lopotun@gmail.com>Eugene Kurtzer</a>
 */
class WaterBarsTest {

	private static WaterBars waterBars;
	@BeforeAll
	static void setUpAll() {
		waterBars = new WaterBars();
	}

	@AfterAll
	static void tearDownAll() {
		waterBars = null;
	}

	@Test
	void tNull() {
		int[] bars = null;
		Assertions.assertEquals(0, waterBars.totalWater(bars));
	}

	@Test
	void tOne() {
		int[] bars = new int[]{5};
		Assertions.assertEquals(0, waterBars.totalWater(bars));
	}

	@Test
	void tTwoUp() {
		int[] bars = new int[]{5, 6};
		Assertions.assertEquals(5, waterBars.totalWater(bars));
	}

	@Test
	void tTwoDown() {
		int[] bars = new int[]{6, 5};
		Assertions.assertEquals(5, waterBars.totalWater(bars));
	}

	@Test
	void tDUD() {
		int[] bars = new int[]{5, 15, 7};
		Assertions.assertEquals(5+7, waterBars.totalWater(bars));
	}

	@Test
	void tUDU() {
		int[] bars = new int[]{15, 7, 10};
		Assertions.assertEquals(10*2, waterBars.totalWater(bars));
	}

	@Test
	void tAllU() {
		int[] bars = new int[]{1, 2, 3, 4, 5, 6, 7};
		Assertions.assertEquals(1+2+3+4+5+6, waterBars.totalWater(bars));
	}

	@Test
	void tAllD() {
		int[] bars = new int[]{7, 6, 5, 4, 3, 2, 1};
		Assertions.assertEquals(1+2+3+4+5+6, waterBars.totalWater(bars));
	}

	@Test
	void tAllEq() {
		int[] bars = new int[]{5, 5, 5, 5, 5, 5, 5};
		Assertions.assertEquals(5*6, waterBars.totalWater(bars));
	}

	@Test
	void t01() {
		int[] bars = new int[]{40, 30, 27, 15, 10, 20, 33, 10};
		Assertions.assertEquals(33*6+10, waterBars.totalWater(bars));
	}

	@Test
	void t02() {
		int[] bars = new int[]{40, 30, 27, 15, 10, 20, 5};
		Assertions.assertEquals(30+27+20*3+5, waterBars.totalWater(bars));
	}
}