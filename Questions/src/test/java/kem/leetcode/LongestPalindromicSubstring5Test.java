package kem.leetcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LongestPalindromicSubstring5Test {
	private LongestPalindromicSubstring5 test;

	@BeforeEach
	void setUp() {
		test = new LongestPalindromicSubstring5();
	}

	@Test
	void longestPalindrome01() {
		final String s = "babadsabrbas";
		assertEquals("sabrbas", test.longestPalindrome(s));
	}

	@Test
	void longestPalindrome02() {
		final String s = "babad";
		assertEquals("bab", test.longestPalindrome(s));
	}

	@Test
	void longestPalindrome03() {
		final String s = "cbbd";
		assertEquals("bb", test.longestPalindrome(s));
	}

	@Test
	void longestPalindrome04() {
		final String s = "abba";
		assertEquals("abba", test.longestPalindrome(s));
	}

	@Test
	void longestPalindrome05() {
		final String s = "cbbbbd";
		assertEquals("bbbb", test.longestPalindrome(s));
	}

	@Test
	void longestPalindrome06() {
		final String s = "cbb12321d";
		assertEquals("12321", test.longestPalindrome(s));
	}

	@Test
	void longestPalindrome07() {
		final String s = "x";
		assertEquals("x", test.longestPalindrome(s));
	}

	@Test
	void longestPalindrome08() {
		final String s = "";
		assertEquals("", test.longestPalindrome(s));
	}

	@Test
	void longestPalindrome09() {
		final String s = "abc";
		assertEquals("a", test.longestPalindrome(s));
	}

	@Test
	void longestPalindrome10() {
		final String s = "bb";
		assertEquals("bb", test.longestPalindrome(s));
	}

	@Test
	void longestPalindrome11() {
		final String s = "ccd";
		assertEquals("cc", test.longestPalindrome(s));
	}

	@Test
	void longestPalindrome12() {
		final String s = "cccd";
		assertEquals("ccc", test.longestPalindrome(s));
	}

	@Test
	void longestPalindrome13() {
		final String s = "dccc";
		assertEquals("ccc", test.longestPalindrome(s));
	}

	@Test
	void longestPalindrome15() {
		final String s = "abcda";
		assertEquals("a", test.longestPalindrome(s));
	}
}