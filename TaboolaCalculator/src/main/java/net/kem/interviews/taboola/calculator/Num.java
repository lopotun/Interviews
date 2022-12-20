package net.kem.interviews.taboola.calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Evgeny Kurtser on 08-Nov-22 at 2:58 AM.
 * <a href=mailto:lopotun@gmail.com>lopotun@gmail.com</a>
 */
class Num implements Evalable {
	private static final Pattern P_NUMBER = Pattern.compile("^(\\d+)$");    // a number
	private final Matcher matcher;
	private final String raw;

	private Num(Matcher matcher, String s) {
		this.matcher = matcher;
		this.raw = s;
	}

	static Num of(String s) {
		final Matcher res = P_NUMBER.matcher(s);
		if(res.matches()) {
			return new Num(res, s);
		} else {
			return null;
		}
	}

	@Override
	public Double eval() {
		return Double.parseDouble(matcher.group(1));
	}

	@Override
	public String toString() {
		return raw;
	}
}
