package net.kem.interviews.taboola.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Evgeny Kurtser on 08-Nov-22 at 3:00 AM.
 * <a href=mailto:lopotun@gmail.com>lopotun@gmail.com</a>
 */
class CalculatorTest {

	@BeforeEach
	void reset() {
		Calculator.reset();
	}

	@Test
	void test01() {
		List<String> ll = List.of(
				"i = 0",
				"j = ++i",
				"x = i++ + 5",
				"y = 5 + 3 * 10",
				"i += y"
				);
		ll.forEach(Calculator::eval);

		assertEquals(37d, Calculator.getVars().get("i"));
		assertEquals(1d,  Calculator.getVars().get("j"));
		assertEquals(6d,  Calculator.getVars().get("x"));
		assertEquals(35d, Calculator.getVars().get("y"));
		assertEquals(4, Calculator.getVars().size());
	}

	@Test
	void test02() {
		Double expRes;
		String s;

		s = "i = 5 + 7 - 1 - 10";
		expRes = Calculator.eval(s);
		assertEquals(1d, expRes);
		assertEquals(1d, Calculator.getVars().get("i"));

		s = "i += 7";
		expRes = Calculator.eval(s);
		assertEquals(8d, expRes);
		assertEquals(8d, Calculator.getVars().get("i"));

		s = "i = 1";
		expRes = Calculator.eval(s);
		assertEquals(1d, expRes);
		assertEquals(1d, Calculator.getVars().get("i"));

		s = "f = i++ * 5 / ++i - ++i * 2 + 10";//f = i++ * 5 / ++i - ++i * 2 + 10
		expRes = Calculator.eval(s);
		assertEquals(3.666666666666667d, expRes);
		assertEquals(4d, Calculator.getVars().get("i"));

		s = "f = i++ * 5 / ++i - ++i * 2 + 10";
		expRes = Calculator.eval(s);
		assertEquals(-0.6666666666666661, expRes);
		assertEquals(-0.6666666666666661d, Calculator.getVars().get("f"));
		assertEquals(7d, Calculator.getVars().get("i"));
	}

	@Test
	void test03() {
		Double expRes;
		String s;

		s = "i = 10";
		expRes = Calculator.eval(s);
		assertEquals(10d, expRes);
		assertEquals(10d, Calculator.getVars().get("i"));

		assertThrowsExactly(RuntimeException.class, () -> Calculator.eval("y += i"), "Undefined variable y");
		assertThrowsExactly(RuntimeException.class, () -> Calculator.eval("i = 10 + y"), "Undefined variable y");
	}

	@Test
	void test04() {
		assertThrowsExactly(RuntimeException.class, () -> Calculator.eval("i = 10 +++ 7"), "Unrecognized operation 10 +++ 7");
		assertThrowsExactly(RuntimeException.class, () -> Calculator.eval("i = arr / 8"), "Unrecognized operation arr / 8");
		assertThrowsExactly(RuntimeException.class, () -> Calculator.eval("i = "), "Unrecognized operation ");
		assertThrowsExactly(RuntimeException.class, () -> Calculator.eval("i = i++"), "Undefined variable i");
		assertThrowsExactly(RuntimeException.class, () -> Calculator.eval("i = 7 - i"), "Undefined variable i");
	}



	private String getResult() {
		return Calculator.getVars().entrySet().stream()
				.map(kv -> String.format("%s=%.0f", kv.getKey(), kv.getValue()))
				.sorted()
				.collect(Collectors.joining(",", "(", ")"));
	}

	@Test
	public void testOriginalScenario(){
		Calculator.eval( "i = 0");
		Calculator.eval( "j = ++i");
		Calculator.eval( "x = i++ + 5");
		Calculator.eval( "y = 5 + 3 * 10");
		Calculator.eval( "i += y");
		String result = getResult();
		assertEquals("(i=37,j=1,x=6,y=35)", result);
	}

	@Test
	public void testLineFailedContinue(){
		Calculator.eval( "i = 0");
		Calculator.eval( "j = ++i");
		assertThrowsExactly(RuntimeException.class, () -> Calculator.eval("x = i++ + 5 + k"), "Undefined variable k");
	}

	@Test
	public void testLineFailedStopReset(){
		Calculator.eval( "i = 0");
		Calculator.eval( "j = ++i");
		assertThrowsExactly(RuntimeException.class, () -> Calculator.eval("x = i++ + 5 + k"), "Undefined variable k");

		Calculator.reset();
		Calculator.eval( "i = 0");
		Calculator.eval( "j = ++i");
		Calculator.eval( "x = i++ + 5");
		Calculator.eval( "y = 5 + 3 * 10");
		Calculator.eval( "i += y");
		String result = getResult();
		assertEquals("(i=37,j=1,x=6,y=35)", result);
	}

	@Test
	public void testManyUnaryPlusInLine(){
		Calculator.eval( "i = 2");
		Calculator.eval( "j = ++i + ++i * ++i - i++");
		String result = getResult();
		assertEquals("(i=6,j=18)", result);
	}

	@Test
	public void testManyUnaryMinusInLine(){
		Calculator.eval( "i = 10");
		Calculator.eval( "j = --i + --i * --i - i--");
		String result = getResult();
		assertEquals("(i=6,j=58)", result);
	}

	@Test
	public void testManyVariablesInLine(){
		Calculator.eval( "i = 2");
		Calculator.eval( "j = 10");
		Calculator.eval( "h = ++i + ++j * ++i - j++");
		String result = getResult();
		assertEquals("(h=36,i=4,j=12)", result);
	}

	@Test
	public void testVariableOnBothSidesInLine(){
		Calculator.eval( "i = 2");
		Calculator.eval( "i = ++i * ++i");
		String result = getResult();
		assertEquals("(i=12)", result);
	}

	@Test
	public void testDivisionInLine(){
		Calculator.eval( "i = 40");
		Calculator.eval( "j = 10");
		Calculator.eval( "h = i + i / j - j");
		String result = getResult();
		assertEquals("(h=34,i=40,j=10)", result);
	}

	@Test
	public void testAssignmentWithPlus(){
		Calculator.eval( "i = 40");
		Calculator.eval( "i += 10");
		String result = getResult();
		assertEquals("(i=50)", result);
	}

	@Test
	public void testAssignmentWithMinus(){
		Calculator.eval( "i = 40");
		Calculator.eval( "i -= 10");
		String result = getResult();
		assertEquals("(i=30)", result);
	}

	@Test
	public void testAssignmentWithMultiply(){
		Calculator.eval( "i = 40");
		Calculator.eval( "i *= 10");
		String result = getResult();
		assertEquals("(i=400)", result);
	}


	@Test
	public void testAssignmentWithDivision(){
		Calculator.eval( "i = 40");
		Calculator.eval( "i /= 10");
		String result = getResult();
		assertEquals("(i=4)", result);
	}
}