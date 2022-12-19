package org.example;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Language
 * API:
 * <p>
 * bool IsValid(string word)
 * <p>
 * Examples:</br>
 * Letter | followers | Final Can be on end of word<p>
 * --------------------------------------<p>
 * a | [a,b,d] | true<p>
 * b | [a,f] | false<p>
 * c | [a] | true<p>
 * .<p>
 * .<p>
 * .<p>
 * O(k)<p>
 *
 * ac => not valid - c cannot be after a (Followed)<p>
 * ab => not valid - b cannot be final<p>
 * aba => valid<p>
 * <p>
 * (*) Split into as many classes / interfaces as needed<p>
 * (*) Create clean code<p>
 * <p>
 * Created by Evgeny Kurtser on ${DATE} at ${TIME}.
 * <a href=mailto:lopotun@gmail.com>Eugene Kurtzer</a>
 */
public class LanguageRules {
	public static boolean isValid(String s) {
		if(s == null || s.length() == 0)
			return false;

		Rule rule = CharRules.of(s.charAt(s.length() - 1));
		if(!rule.isEndsWithValid()) {
			return false;
		}

		boolean res = true;
		for(int i=0; res && i<s.length()-1; i++) {
			rule = CharRules.of(s.charAt(i));
			if(!rule.isFollowerValid(s.charAt(i+1))) {
				res = false;
			}
		}
		return res;
	}

	public static void main(String[] args) {
		System.out.println("Hello world!");
	}
}

interface Rule {
	Character getName();
	boolean isFollowerValid(Character ch);
	boolean isEndsWithValid();
}


class RuleImpl implements Rule {
	private final Character name;
	private final Set<Character> followers;
	private final boolean endsWithValid;

	RuleImpl(Character name, Set<Character> followers, boolean endsWithValid) {
		this.name = name;
		this.followers = followers;
		this.endsWithValid = endsWithValid;
	}
	RuleImpl(Character name, String followers, boolean endsWithValid) {
		this.name = name;
		this.followers = toCharSet(followers);
		this.endsWithValid = endsWithValid;
	}

	private static Set<Character> toCharSet(String s) {
		Set<Character> res = new HashSet<>();
		for(Character ch: s.toCharArray())
			res.add(ch);
		return res;
	}


	@Override
	public Character getName() {
		return name;
	}

	@Override
	public boolean isFollowerValid(Character ch) {
		return followers.contains(ch);
	}

	@Override
	public boolean isEndsWithValid() {
		return endsWithValid;
	}
}

class CharRules {
	private static final Map<Character, Rule> RULES = new HashMap<>();
	private static final Rule DEFAULT_RULE = new RuleImpl('-', Collections.emptySet(), false);
	public static Rule of(Character ch) {
		if(RULES.isEmpty()) {
			loadRules();
		}
		return RULES.getOrDefault(ch, DEFAULT_RULE);//todo
	}

	private static void loadRules() {
		Rule rule;
		rule = new RuleImpl('a', "abd", true);
		RULES.put(rule.getName(), rule);

		rule = new RuleImpl('b', "af", false);
		RULES.put(rule.getName(), rule);

		rule = new RuleImpl('c', "a", true);
		RULES.put(rule.getName(), rule);
	}
}