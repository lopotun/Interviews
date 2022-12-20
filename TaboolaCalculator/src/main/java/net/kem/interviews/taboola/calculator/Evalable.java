package net.kem.interviews.taboola.calculator;

/**
 * Created by Evgeny Kurtser on 08-Nov-22 at 2:58 AM.
 * <a href=mailto:lopotun@gmail.com>lopotun@gmail.com</a>
 */
interface Evalable {
	Double eval();

	default void postEval() {
	}
}
