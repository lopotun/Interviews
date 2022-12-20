package net.kem.interviews.taboola.calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Evgeny Kurtser on 08-Nov-22 at 2:57 AM.
 * <a href=mailto:lopotun@gmail.com>lopotun@gmail.com</a>
 */
class Assignment implements Evalable {
	private static final Pattern P_ASSIGNMENT = Pattern.compile("^([a-zA-Z]) +(=|\\+=|-=|\\*=|/=) +(.+)$");   // i OP 35 + 8 (OP is = or += or -=  or *= or /=)

	private static final Map<String, Double> VARS = new HashMap<>();
	private static final Map<String, Double> VARS_ROLLBACK = new HashMap<>();

	private final Matcher matcher;
	private final String raw;

	private Assignment(Matcher matcher, String s) {
		this.matcher = matcher;
		this.raw = s;
	}

	static Assignment of(String s) {
		final Matcher res = P_ASSIGNMENT.matcher(s);
		if(res.matches()) {
			return new Assignment(res, s);
		} else {
			throw new RuntimeException("Invalid assignment format in " + s);
		}
	}

	@Override
	public Double eval() {
		final String varName = matcher.group(1);
		final Evalable evalable = Expression.of(matcher.group(3));
		Double varValue = evalable.eval();
		final BiFunction<? super String, ? super Double, Double> remappingFunction = remappingFunction(matcher.group(2), varValue);
		return assign(varName, remappingFunction);
	}

	private static BiFunction<? super String, ? super Double, Double> remappingFunction(String s, double varValue) {
		return switch(s) {
			case "=" -> (k, v) -> varValue;
			case "+=" -> (k, v) -> {
				if(v == null) throw new RuntimeException("Undefined variable " + k);
				return v + varValue;
			};
			case "-=" -> (k, v) -> {
				if(v == null) throw new RuntimeException("Undefined variable " + k);
				return v - varValue;
			};
			case "*=" -> (k, v) -> {
				if(v == null) throw new RuntimeException("Undefined variable " + k);
				return v * varValue;
			};
			case "/=" -> (k, v) -> {
				if(v == null) throw new RuntimeException("Undefined variable " + k);
				return v / varValue;
			};
			default -> throw new RuntimeException("Unrecognized operation " + s);
		};
	}

//	static Double assign(String varName, double varValue) {
//		VARS_ROLLBACK.put(varName, VARS.get(varName));
//		return VARS.put(varName, varValue);
//	}

	static Double assign(String varName, BiFunction<? super String, ? super Double, Double> remappingFunction) {
		final Double currValue = VARS.get(varName);
		if(currValue != null) {
			VARS_ROLLBACK.put(varName, currValue);
		}
		return VARS.compute(varName, remappingFunction);
	}

	static Map<String, Double> getVars() {
		return Map.copyOf(VARS);
	}

	static void rollback() {
		VARS.putAll(VARS_ROLLBACK);
	}

	static void commit() {
		VARS_ROLLBACK.clear();
	}

	static void reset() {
		VARS.clear();
		VARS_ROLLBACK.clear();
	}

	@Override
	public String toString() {
		return raw;
	}
}
