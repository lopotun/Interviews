package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Evgeny Kurtser on 20-Nov-22 at 12:11 PM.
 * <a href=mailto:lopotun@gmail.com>Eugene Kurtzer</a>
 */
class LanguageRulesTest {

	@Test
	public void tNull() {
		assertFalse(LanguageRules.isValid(null));
	}

	@Test
	public void tEmpty() {
		assertFalse(LanguageRules.isValid(""));
	}

	@Test
	public void t03() {
		assertFalse(LanguageRules.isValid("ac"));
	}

	@Test
	public void t04() {
		assertFalse(LanguageRules.isValid("ab"));
	}

	@Test
	public void t05() {
		assertTrue(LanguageRules.isValid("aba"));
	}

	@Test
	public void t06() {
		assertFalse(LanguageRules.isValid("aZba"));
	}
}