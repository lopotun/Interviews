package org.example;

import java.util.HashMap;
import java.util.Map;

/**
 * 1. Roman letters<p>
 *     <pre>
 * Roman    Hindu-Arabic Equivalent
 * I        1
 * V        5
 * X        10
 * L        50
 * C        100
 * D        500
 * M        1000
 * </pre>
 * <p>
 * There are a few rules for writing numbers with Roman numerals.<p>
 * ● Repeating a numeral up to three times represents addition of the number.
 * For example, III represents 1 + 1 + 1 = 3. Only I, X, C, and M can be repeated; V, L, and D cannot be, and there is no need to do so.<p>
 * ● Writing numerals that decrease from left to right represents addition of the numbers.
 * For example, LX represents 50 + 10 = 60 and XVI represents 10 + 5 + 1 =
 * 16.<p>
 * ● To write a number that otherwise would take repeating of a numeral four or more
 * times, there is a subtraction rule. Writing a smaller numeral to the left of a larger
 * numeral represents subtraction. For example, IV represents 5 - 1 = 4 and IX
 * represents 10 - 1 = 9. To avoid ambiguity, the only pairs of numerals that use this
 * subtraction rule are
 * <pre>
 * Roman    Hindu-Arabic Equivalent
 * IV       4 = 5 - 1
 * IX       9 = 10 - 1
 * XL       40 = 50 - 10
 * XC       90 = 100 - 10
 * CD       400 = 500 - 100
 * CM       900 = 1000 - 100
 * </pre>
 * ● Examples:
 * <ol>
 * <li>DCXLVIII</li>
 * <li>MMDXLIX</li>
 * <li>MCMXLIV</li>
 * <li>MCMXCIX</li>
 * </ol>
 * Created by Evgeny Kurtser on ${DATE} at ${TIME}.
 * <a href=mailto:lopotun@gmail.com>Eugene Kurtzer</a>
 */
public class RomanToHindu {
	private static final Map<Character, Integer> ROMAN2HINDU = new HashMap<>();
	
	public RomanToHindu() {
		ROMAN2HINDU.put('I', 1);
		ROMAN2HINDU.put('V', 5);
		ROMAN2HINDU.put('X', 10);
		ROMAN2HINDU.put('L', 50);
		ROMAN2HINDU.put('C', 100);
		ROMAN2HINDU.put('D', 500);
		ROMAN2HINDU.put('M', 1000);
	}

	public int toHindu(String roman) {
		if(roman == null || roman.length() == 0) {
			return 0;
		}

		int res = 0;
		for(int i=0; i<roman.length(); i++) {
			int cur = ROMAN2HINDU.get(roman.charAt(i));
			int next = i < roman.length()-1?
					ROMAN2HINDU.get(roman.charAt(i+1)):
					0;
			if(cur < next) {
				cur = next - cur;
				i++;
			}
			res += cur;
		}
		return res;
	}


	/**
	 *
	 * @param args .
	 */
	public static void main(String[] args) {
		System.out.println("Hello world!");
		RomanToHindu test = new RomanToHindu();
		String roman;
		int hindu;

		roman = "DCXLVIII";
		hindu = 648;
		System.out.printf("%b\t%s = %d%n", hindu == test.toHindu(roman), roman, hindu);

		roman = "MMDXLIX";
		hindu = 2549;
		System.out.printf("%b\t%s = %d%n", hindu == test.toHindu(roman), roman, hindu);

		roman = "MCMXLIV";
		hindu = 1944;
		System.out.printf("%b\t%s = %d%n", hindu == test.toHindu(roman), roman, hindu);

		roman = "MCMXCIX";
		hindu = 1999;
		System.out.printf("%b\t%s = %d%n", hindu == test.toHindu(roman), roman, hindu);

		roman = "X";
		hindu = 10;
		System.out.printf("%b\t%s = %d%n", hindu == test.toHindu(roman), roman, hindu);

		roman = "IV";
		hindu = 4;
		System.out.printf("%b\t%s = %d%n", hindu == test.toHindu(roman), roman, hindu);

		roman = "VI";
		hindu = 6;
		System.out.printf("%b\t%s = %d%n", hindu == test.toHindu(roman), roman, hindu);
	}
}