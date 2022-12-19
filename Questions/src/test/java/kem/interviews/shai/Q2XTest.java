package kem.interviews.shai;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Evgeny Kurtser on 08-Jan-22 at 11:43 PM.
 * <a href=mailto:lopotun@gmail.com>lopotun@gmail.com</a>
 */
class Q2XTest {

	@Test
		// Shai 1.Q2
	void jobsDependencies() {
		/*
		1 -> ()
		2 -> (1)
		3 -> (2, 1, 6, 5)
		4 -> (3, 2, 1, 7)
		5 -> ()
		6 -> (5)
		7 -> (2, 1, 3, 6, 5)
		8 -> (7)
		9 -> ()
		 */
		List<Integer> l1 = Arrays.asList(1, 2, 3, 4);
		List<Integer> l2 = Arrays.asList(1, 2, 7, 4);
		List<Integer> l3 = Arrays.asList(5, 6, 3, 7);
		List<Integer> l4 = Arrays.asList(7, 8);
		List<Integer> l5 = Arrays.asList(9);
		List<Integer> l6 = Arrays.asList(8, 7);

		Map<Integer, Set<Integer>> globalDependencies = calculateGlobalDependencies(l1, l2, l3, l4, l5, l6);
		boolean hasCircularDependency = hasCircularDependency(globalDependencies);
		System.out.println(globalDependencies);
		System.out.println(hasCircularDependency);
	}


	/**
	 * Builds rules dependencies map.
	 *
	 * @param lst list of rules
	 * @return rules dependencies map
	 */
	@SafeVarargs
	private static <T> Map<T, Set<T>> calculateGlobalDependencies(List<T>... lst) {
		Map<T, Set<T>> globalDependencies = new HashMap<>();
		Arrays.stream(lst).forEach(l -> calcGlobDepsRecur(l, new HashSet<>(), globalDependencies));
		return globalDependencies;
	}

	/**
	 * Builds rules dependencies map.
	 *
	 * @param lst                list of rules
	 * @param ruleDeps           set of dependencies of the current rule
	 * @param globalDependencies resulting rules dependencies map
	 */
	private static <T> void calcGlobDepsRecur(List<T> lst, Set<T> ruleDeps, Map<T, Set<T>> globalDependencies) {
		// Split the list to head and tail
		HeadTail<T> ht = HeadTail.split(lst);
		if(!ht.tail().isEmpty()) {
			// Prepare set of dependencies for the next rule
			Set<T> nextRuleDeps = new HashSet<>(ruleDeps);
			nextRuleDeps.add(ht.head());
			// Traverse to the next rule
			calcGlobDepsRecur(ht.tail(), nextRuleDeps, globalDependencies);
		}
		// Assign dependencies set to the current rule
		globalDependencies.merge(lst.get(0), ruleDeps, (curSet, newSet) -> {
			Set<T> res = new HashSet<>(curSet);
			res.addAll(newSet);
			return res;
		});
	}

	private static <T> boolean hasCircularDependency(Map<T, Set<T>> globalDependencies) {
		return globalDependencies.entrySet().stream()
				.anyMatch(me -> me.getValue().stream()
						.anyMatch(v -> globalDependencies.get(v).contains(me.getKey())));
	}


	// Shai 1.Q1
	@Test
	void testQ11() {
		Assertions.assertTrue(Palindrome.isPalindrome(null));
		Assertions.assertTrue(Palindrome.isPalindrome(""));
		Assertions.assertTrue(Palindrome.isPalindrome("x"));
		Assertions.assertTrue(Palindrome.isPalindrome("boB"));
		Assertions.assertTrue(Palindrome.isPalindrome("Marma"));
		Assertions.assertFalse(Palindrome.isPalindrome("Marrom"));
		Assertions.assertFalse(Palindrome.isPalindrome("HeX"));
		Assertions.assertFalse(Palindrome.isPalindrome("zara"));
	}

	// Shai 2.Q1
	@Test
	void testParenthesis() {
		Assertions.assertTrue(ParenthesisChecker.isParenthesisCorrect(null));
		Assertions.assertTrue(ParenthesisChecker.isParenthesisCorrect(""));
		Assertions.assertTrue(ParenthesisChecker.isParenthesisCorrect("x"));
		Assertions.assertTrue(ParenthesisChecker.isParenthesisCorrect("la-la-la"));
		Assertions.assertTrue(ParenthesisChecker.isParenthesisCorrect("(bo)B"));
		Assertions.assertTrue(ParenthesisChecker.isParenthesisCorrect("M[a{(r)m}]a"));
		Assertions.assertTrue(ParenthesisChecker.isParenthesisCorrect("()[()]"));
		Assertions.assertTrue(ParenthesisChecker.isParenthesisCorrect("({[]})[]{()}"));
		Assertions.assertFalse(ParenthesisChecker.isParenthesisCorrect("({[]}))]})"));
		Assertions.assertFalse(ParenthesisChecker.isParenthesisCorrect("H(eX]"));
		Assertions.assertFalse(ParenthesisChecker.isParenthesisCorrect("z((ar)a"));

		Assertions.assertTrue(ParenthesisChecker.isParenthesisCorrect(null, ParenthesisChecker.Zhenya1.getF()));
		Assertions.assertTrue(ParenthesisChecker.isParenthesisCorrect("", ParenthesisChecker.Zhenya1.getF()));
		Assertions.assertTrue(ParenthesisChecker.isParenthesisCorrect("x", ParenthesisChecker.Zhenya1.getF()));
		Assertions.assertTrue(ParenthesisChecker.isParenthesisCorrect("la-la-la", ParenthesisChecker.Zhenya1.getF()));
		Assertions.assertTrue(ParenthesisChecker.isParenthesisCorrect("(bo)B", ParenthesisChecker.Zhenya1.getF()));
		Assertions.assertTrue(ParenthesisChecker.isParenthesisCorrect("M[a{(r)m}]a", ParenthesisChecker.Zhenya1.getF()));
		Assertions.assertTrue(ParenthesisChecker.isParenthesisCorrect("()[()]", ParenthesisChecker.Zhenya1.getF()));
		Assertions.assertTrue(ParenthesisChecker.isParenthesisCorrect("({[]})[]{()}", ParenthesisChecker.Zhenya1.getF()));
		Assertions.assertFalse(ParenthesisChecker.isParenthesisCorrect("({[]}))]})", ParenthesisChecker.Zhenya1.getF()));
		Assertions.assertFalse(ParenthesisChecker.isParenthesisCorrect("H(eX]", ParenthesisChecker.Zhenya1.getF()));
		Assertions.assertFalse(ParenthesisChecker.isParenthesisCorrect("z((ar)a", ParenthesisChecker.Zhenya1.getF()));
	}

	// Shai 2.Q2
	@Test
	void testQ22() {
		Q22 q22 = new Q22();
		q22.addRawLine("Moshe moshe@futora.com");
		q22.addRawLine("Avi avi@futora.com");
		q22.addRawLine("Team1 Moshe, Avi");
		q22.addRawLine("Team2 John, Mary, TEAM2@futora.com");
		q22.addRawLine("Development: Team1, Team2");
		q22.addRawLine("Company: Development, QA, Assaf, Meital");
		final Collection<String> emails = q22.getEmails(Arrays.asList("Team1", "Development"));
		System.out.println(emails);
	}

	// Shai ClassReflection
	@Test
	void testClassReflection() {
		ClassReflection cr = new ClassReflection();
		System.out.println(cr.parseClass(ClassReflection.class));
//		System.out.println(cr.parseClass(String.class));
//		System.out.println(cr.parseClass(HashMap.class));
	}
}