package net.kem.interviews.taboola.calculator;

import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Evgeny Kurtser on 08-Nov-22 at 2:58 AM.
 * <a href=mailto:lopotun@gmail.com>lopotun@gmail.com</a>
 */
class Variable implements Evalable {
	private enum Type {NORMAL, PREFIX, POSTFIX}
	private static final Pattern P_VAR = Pattern.compile("^([a-zA-Z])$");    // a variable
	private static final Pattern P_VAR_PM = Pattern.compile("^([a-zA-Z])([+]{2}|-{2})$");   // i++ or i--
	private static final Pattern P_PM_VAR = Pattern.compile("^([+]{2}|-{2})([a-zA-Z])$");   // ++i or --i

	private final Matcher matcher;
	private final String raw;
	private final Type type;

	private Variable(Matcher matcher, String s, Type type) {
		this.matcher = matcher;
		this.raw = s;
		this.type = type;
	}

	public static Variable of(String s) {
		Matcher res = P_VAR.matcher(s);
		if(res.matches()) {
			return new Variable(res, s, Type.NORMAL);
		} else {
			res = P_VAR_PM.matcher(s);
			if(res.matches()) {
				return new Variable(res, s, Type.POSTFIX);
			} else {
				res = P_PM_VAR.matcher(s);
				if(res.matches()) {
					return new Variable(res, s, Type.PREFIX);
				} else {
					return null;
				}
			}
		}
	}

	static Double eval(String varName) {
		Double res = Assignment.getVars().get(varName);
		if(res == null) {
			throw new RuntimeException("Undefined variable " + varName);
		}
		return res;
	}

	@Override
	public Double eval() {
		Double res = null;
		switch(type) {
			case NORMAL, POSTFIX -> res = eval(matcher.group(1));
			case PREFIX -> res = Assignment.assign(matcher.group(2), remappingFunction(matcher.group(1)));
		}
		return res;
	}

	@Override
	public void postEval() {
		if(type == Type.POSTFIX) {
			Assignment.assign(matcher.group(1), remappingFunction(matcher.group(2)));
		}
	}

	private static BiFunction<? super String, ? super Double, Double> remappingFunction(String op) {
		return (varName, currValue) -> {
			if(currValue == null) throw new RuntimeException("Undefined variable " + varName);
			if(op.equals("++"))
				return currValue + 1d;
			else // "--"
				return currValue - 1d;
		};
	}


	@Override
	public String toString() {
		return raw;
	}
}
