package kem.interviews.shai;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * <a href=mailto:lopotun@gmail.com>Eugene Kurtzer</a>
 */
public class ArkadyStringWithRules {
	// a -> [a, b, c]   valid
	// b -> [a, c]      invalid
	// c -> [a]         invalid
	interface Rule<T> {
		T name();
		boolean isEndsWithValid();
		boolean isFollowsWithValid(T ch);
	}

	static class RuleImpl<T> implements Rule<T> {
		T name;
		Set<T> followers;
		boolean endsWithValid;

		RuleImpl(T name, Set<T> followers, boolean endsWithValid) {
			this.name = name;
			this.followers = new HashSet<>(followers);
			this.endsWithValid = endsWithValid;
		}

		@Override
		public T name() {
			return name;
		}
		@Override
		public boolean isEndsWithValid() {
			return endsWithValid;
		}

		@Override
		public boolean isFollowsWithValid(T ch) {
			return followers.contains(ch);
		}

		@Override
		public String toString() {
			return String.format("%s[%b] -> %s", name(), isEndsWithValid(), followers);
		}
	}

	static class CharRules {
		private static final Map<Character, Rule<Character>> RULES = new HashMap<>();
		private static final Rule<Character> DEFAULT_RULE = new RuleImpl<>('-', Collections.emptySet(), false);
		public static void loadRules() {
			Rule<Character> rule = new RuleImpl<>('a', toCharSet("abc"), true);
			RULES.put(rule.name(), rule);

			rule = new RuleImpl<>('b', toCharSet("ac"), false);
			RULES.put(rule.name(), rule);

			rule = new RuleImpl<>('c', toCharSet("a"), true);
			RULES.put(rule.name(), rule);
		}

		public static Rule<Character> of(Character ch) {
			if(RULES.isEmpty()) {
				loadRules();
			}
			return RULES.getOrDefault(ch, DEFAULT_RULE);
		}

		private static Set<Character> toCharSet(String input) {
			Set<Character> set = new HashSet<>();
			for(char ch: input.toCharArray()) {
				set.add(ch);
			}
			return set;
		}
	}


	private static boolean isValid(String input) {
		if(input == null || input.length() == 0) {
			return false;
		}
		Rule<Character> rule = CharRules.of(input.charAt(input.length()-1));
		if(!rule.isEndsWithValid()) {
			return false;
		}
//		if(input.length() == 1) {
//			return true;
//		}
		boolean res = true;
		for(int i=0; res && i<input.length()-1; i++) {
			rule = CharRules.of(input.charAt(i));
			if(!rule.isFollowsWithValid(input.charAt(i+1))) {
				res = false;
			}
		}
		return res;
	}

	public static void main(String[] args) {
		System.out.println(!isValid("ac"));
		System.out.println(!isValid(null));
		System.out.println(!isValid(""));
		System.out.println(isValid("a"));
		System.out.println(!isValid("b"));
		System.out.println(isValid("c"));
		System.out.println(isValid("abc"));
		System.out.println(!isValid("wa"));
		System.out.println(!isValid("aw"));
		System.out.println(!isValid("awa"));
	}
}
