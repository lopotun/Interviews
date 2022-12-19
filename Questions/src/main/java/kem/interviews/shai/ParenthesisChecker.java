package kem.interviews.shai;

import java.util.List;
import java.util.Stack;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Evgeny Kurtser on 07-Mar-22 at 7:14 PM.
 * <a href=mailto:lopotun@gmail.com>lopotun@gmail.com</a>
 */
public class ParenthesisChecker {

	static class Zhenya1 {
		enum ParMarker {
			RoundC(')'),
			RoundO('(', RoundC),

			SquareC(']'),
			SquareO('[', SquareC),

			FiguredC('}'),
			FiguredO('{', FiguredC),

			NonPar('X');
			private final int ch;
			private final boolean isOpening;
			private final ParMarker matching;

			ParMarker(int ch) {
				this.ch = ch;
				this.isOpening = false;
				this.matching = null;
			}
			ParMarker(int ch, ParMarker matching) {
				this.ch = ch;
				this.isOpening = true;
				this.matching = matching;
			}

			public static ParMarker of(int ch) {
				return switch(ch) {
					case '(' -> RoundO;
					case ')' -> RoundC;
					case '[' -> SquareO;
					case ']' -> SquareC;
					case '{' -> FiguredO;
					case '}' -> FiguredC;
					default -> NonPar;
				};
			}

			public boolean handlePar(Stack<ParMarker> stack) {
				if(this == ParMarker.NonPar) {
					return false;
				}
				if(isOpening) {
					stack.push(this);
					return false;
				} else {
					if(stack.isEmpty()) {
						return true;
					} else {
						ParMarker top = stack.pop();
						return top.matching != this;
					}
				}
			}
		}

		static boolean isParenthesisCorrect(String s) {
			if(s == null) {
				return true;
			}
			s = s.trim();
			final Stack<ParMarker> stack = new Stack<>();
			return s.chars()
					.mapToObj(Zhenya1::toEnum)
					.filter(Zhenya1::parOnly)
					.noneMatch(pm -> handlePar(pm, stack))
			&& stack.isEmpty();
		}

		private static boolean handlePar(ParMarker pm, Stack<ParMarker> stack) {
			return pm.handlePar(stack);
		}

		private static boolean parOnly(ParMarker pm) {
			return pm != ParMarker.NonPar;
		}

		private static ParMarker toEnum(int ch) {
			return ParMarker.of(ch);
		}

		public static Function<String, Boolean> getF() {
			//return s -> isParenthesisCorrect(s);
			return Zhenya1::isParenthesisCorrect;
		}
	}

	static class Zhenya2 {
		public static boolean isParenthesisCorrect(String s) {
			if(s == null) {
				return true;
			}
			s = s.trim();

			final Stack<Character> stack = new Stack<>();

			for(Character ch : s.toCharArray()) {
				if(isOpeningPar(ch)) {
					stack.push(ch);
					continue;
				}
				if(isClosingPar(ch)) {
					if(stack.empty()) {
						return false;
					} else {
						char popped = stack.pop();
						if(!matches(popped, ch)) {
							return false;
						}
					}
				}
			}
			return stack.empty();
		}

		private static boolean isOpeningPar(char ch) {
			return ch == '(' || ch == '[' || ch == '{';
		}

		private static boolean isClosingPar(char ch) {
			return ch == ')' || ch == ']' || ch == '}';
		}

		private static boolean matches(char popped, char ch) {
			return (popped == '(' && ch == ')')
					|| (popped == '[' && ch == ']')
					|| (popped == '{' && ch == '}');
		}

//		public Function<String, Boolean> getF() {
//			//return s -> isParenthesisCorrect(s);
//			return Zhenya2::isParenthesisCorrect;
//		}
	}


	static class Gena {
		/*
		( [ { } ] )
		0 1 2 3 4 5
		 */
		public static boolean isParenthesisCorrect(String s) {
			if(s == null) {
				return true;
			}
			final List<Character> parChars = s.trim().chars()
					.mapToObj(ch -> (char) ch)
					.filter(ch -> ch == '(' || ch == ')' || ch == '[' || ch == ']' || ch == '{' || ch == '}')
					.collect(Collectors.toList());

			if(parChars.size() % 2 == 0) {
				int middlePoint = parChars.size()/2;
				for(int i=0; i< middlePoint; i++) {
					Character chO = parChars.get(middlePoint-1-i);
					Character chC = parChars.get(middlePoint+i);
					if(!((chO=='{'&&chC=='}') || (chO=='['&&chC==']') || (chO=='('&&chC==')'))) {
						return false;
					}
				}
				return true;
			}
			return false;
		}

		public static Function<String, Boolean> getF() {
			//return s -> isParenthesisCorrect(s);
			return Gena::isParenthesisCorrect;
		}
	}

	public static boolean isParenthesisCorrect(String s) {
		return Zhenya2.isParenthesisCorrect(s);
	}

	public static boolean isParenthesisCorrect(String s, Function<String, Boolean> f) {
		return f.apply(s);
	}

}