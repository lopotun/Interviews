package kem.leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Created by Evgeny Kurtser on 18-Aug-22 at 1:01 AM.
 * <a href=mailto:lopotun@gmail.com>Eugene Kurtzer</a>
 */
class FindTheDuplicateNumber287Test {

	@Test
	void findDuplicate01() {
		int res = new FindTheDuplicateNumber287().findDuplicate(new int[]{1, 3, 4, 2, 2});
		Assertions.assertEquals(2, res);
	}

	@Test
	void findDuplicate02() {
		int res = new FindTheDuplicateNumber287().findDuplicate(new int[]{3, 1, 3, 4, 2});
		Assertions.assertEquals(3, res);
	}
	@Test
	void findDuplicate11() {
		int res = new FindTheDuplicateNumber287().findDuplicate(new int[]{1, 1});
		Assertions.assertEquals(1, res);
	}
}