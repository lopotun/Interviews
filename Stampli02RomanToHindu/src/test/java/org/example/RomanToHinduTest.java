package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Evgeny Kurtser on 24-Nov-22 at 4:46 PM.
 * <a href=mailto:lopotun@gmail.com>Eugene Kurtzer</a>
 */
class RomanToHinduTest {
	private RomanToHindu test;

	@BeforeEach
	void setUp() {
		test = new RomanToHindu();
	}

	@Test
	void toNull() {
		assertEquals(0, test.toHindu(null));
	}

	@Test
	void toEmpty() {
		assertEquals(0, test.toHindu(""));
	}

	@Test
	void toX() {
		assertEquals(10, test.toHindu("X"));
	}

	@Test
	void toIV() {
		assertEquals(4, test.toHindu("IV"));
	}

	@Test
	void toVI() {
		assertEquals(6, test.toHindu("VI"));
	}

	@Test
	void toDCXLVIII() {
		assertEquals(648, test.toHindu("DCXLVIII"));
	}

	@Test
	void toMMDXLIX() {
		assertEquals(2549, test.toHindu("MMDXLIX"));
	}

	@Test
	void toMCMXCIX() {
		assertEquals(1999, test.toHindu("MCMXCIX"));
	}

	@Test
	void toMCMXLIV() {
		assertEquals(1944, test.toHindu("MCMXLIV"));
	}
}