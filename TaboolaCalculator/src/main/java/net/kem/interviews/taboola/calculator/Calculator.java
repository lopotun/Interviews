package net.kem.interviews.taboola.calculator;

import java.util.Map;

/**
 * Objective: Implement a text based calculator application. Usage of Rhino, Nashorn, antlr and other similar solutions is not allowed.
 * Input: The input is a series of assignment expressions. The syntax is a subset of Java numeric expressions and operators.
 * Output: At the end of evaluating the series, the value of each variable is printed out.
 * Example:
 * Input: Following is a series of valid inputs for the program:
 * i = 0
 * j = ++i
 * x = i++ + 5
 * y = 5 + 3 * 10
 * i += y
 * Output:
 * (i=37,j=1,x=6,y=35)
 * Created by Evgeny Kurtser on 07-Nov-22 at 9:14 AM.
 * <a href=mailto:lopotun@gmail.com>lopotun@gmail.com</a>
 */
public class Calculator {

	public static Double eval(String s) throws RuntimeException {
		try {
			return Assignment.of(s).eval();
		} catch(Throwable t) {
			Assignment.rollback();
			if(t instanceof RuntimeException) {
				throw (RuntimeException) t;
			} else {
				throw new RuntimeException("Something went wrong", t);
			}
		} finally {
			Assignment.commit();
		}
	}

	public static Map<String, Double> getVars() {
		return Assignment.getVars();
	}

	public static void reset() {
		Assignment.reset();
	}
}
