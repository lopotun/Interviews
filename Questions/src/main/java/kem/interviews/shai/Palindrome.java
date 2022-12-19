package kem.interviews.shai;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Evgeny Kurtser on 07-Mar-22 at 7:14 PM.
 * <a href=mailto:lopotun@gmail.com>lopotun@gmail.com</a>
 */
public class Palindrome {
	public static boolean isPalindrome(String s) {
		if(s == null) {
			return true;
		}
		s = s.trim();
		if(s.length() == 1) {
			return true;
		}

		// Map character->boolean
		Map<Integer, Boolean> map = new HashMap<>(s.length());
		// Flip boolean value every time we encounter the given character.
		// So, 'true' gives us odd number of this character occurrences; 'false' even number of this character occurrences;
		s.toLowerCase().chars().forEach(ch -> map.merge(ch, true, (oldVal, newVal) -> !oldVal));

		// The string is palindrome if it has no more than one character that occurs once.
		return map.values().stream()
				.filter(v -> v)
				.count() < 2;
	}
}
