package kem.interviews.shai;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;


class LRUCacheTest {
	private LRUCache<Character, Integer> lruCache;
	@BeforeEach
	void setUp() {
		lruCache = new LRUCache<>(5);
		lruCache.put('A', 1);
		lruCache.put('B', 2);
		lruCache.put('C', 3);
		lruCache.put('D', 4);
		lruCache.put('E', 5);
		lruCache.put('F', 6);
	}

	@Test
	void tNull() {
		assertThrowsExactly(IllegalArgumentException.class, () -> new LRUCache<>(0));
	}

	@Test
	void tClear() {
		lruCache.clear();
		Assertions.assertTrue(lruCache.isEmpty());
	}

	@Test
	void tGetOldestToYoungest() {
		Assertions.assertEquals(Optional.of(2), lruCache.get('B'));
		Assertions.assertEquals(Optional.of(2), lruCache.get('B'));
	}

	@Test
	void tGetMiddleToYoungest() {
		Assertions.assertEquals(Optional.of(4), lruCache.get('D'));
		Assertions.assertEquals(Optional.of(4), lruCache.get('D'));
	}

	@Test
	void tGetYoungest() {
		Assertions.assertEquals(Optional.of(6), lruCache.get('F'));
		Assertions.assertEquals(Optional.of(6), lruCache.get('F'));
	}

	@Test
	void tRemoveOldest() {
		Assertions.assertEquals(Optional.of(2), lruCache.remove('B'));
		Assertions.assertSame(4, lruCache.size());
	}

	@Test
	void tRemovePreOldest() {
		Assertions.assertEquals(Optional.of(3), lruCache.remove('C'));
		Assertions.assertSame(4, lruCache.size());
	}

	@Test
	void tRemoveMiddle() {
		Assertions.assertEquals(Optional.of(4), lruCache.remove('D'));
		Assertions.assertSame(4, lruCache.size());
	}

	@Test
	void tRemovePreYoungest() {
		Assertions.assertEquals(Optional.of(5), lruCache.remove('E'));
		Assertions.assertSame(4, lruCache.size());
	}

	@Test
	void tRemoveYoungest() {
		Assertions.assertEquals(Optional.of(6), lruCache.remove('F'));
		Assertions.assertSame(4, lruCache.size());
	}

	@Test
	void tRemoveAll() {
		Assertions.assertEquals(Optional.of(2), lruCache.remove('B'));
		Assertions.assertEquals(Optional.of(3), lruCache.remove('C'));
		Assertions.assertEquals(Optional.of(4), lruCache.remove('D'));
		Assertions.assertEquals(Optional.of(5), lruCache.remove('E'));
		Assertions.assertEquals(Optional.of(6), lruCache.remove('F'));
		Assertions.assertTrue(lruCache.isEmpty());
	}
}