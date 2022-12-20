package net.kem.interviews.taboola.calculator;

import java.util.function.BinaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Expression implements Evalable {
	private static final Pattern P_PLUS_MINUS = Pattern.compile("^(.+) +([-+]) +(.+)$");  // [something] - [something]  or  [something] + [something]
	private static final Pattern P_MULT_DIV = Pattern.compile("(.+) +([*/]) +(.+)");    // [something] * [something]  or   [something] / [something]

	private BinaryOperator<Double> operation;
	private Evalable left, right;

	private final String raw;

	private Expression(String s) {
		this.raw = s;
	}

	@Override
	public Double eval() throws RuntimeException {
		Double res = operation.apply(left.eval(), right.eval());
		left.postEval();
		right.postEval();
		return res;
	}

	public static Evalable of(String s) {
		Evalable res;
		Matcher matcher = P_PLUS_MINUS.matcher(s);
		if(matcher.matches()) { // this is [something] - [something]  or  [something] + [something]
			res = new Expression(s);
			((Expression) res).operation = operation(matcher.group(2));
			((Expression) res).left = of(matcher.group(1));
			((Expression) res).right = of(matcher.group(3));
		} else {
			matcher = P_MULT_DIV.matcher(s);
			if(matcher.matches()) { // this is [something] * [something]  or  [something] / [something]
				res = new Expression(s);
				((Expression) res).operation = operation(matcher.group(2));
				((Expression) res).left = of(matcher.group(1));
				((Expression) res).right = of(matcher.group(3));
			} else {
				res = Variable.of(s);
				if(res == null) { // this is not a variable
					res = Num.of(s);
					if(res == null) { // this is not a number
						throw new RuntimeException("Unrecognized operation " + s);
					}
				}
			}
		}
		return res;
	}

	private static BinaryOperator<Double> operation(String s) {
		return switch(s) {
			case "+" -> Double::sum;
			case "-" -> (a, b) -> a - b;
			case "*" -> (a, b) -> a * b;
			case "/" -> (a, b) -> a / b;
			default -> throw new RuntimeException("Unrecognized operation " + s);
		};
	}

	@Override
	public String toString() {
		return raw;
	}
}
