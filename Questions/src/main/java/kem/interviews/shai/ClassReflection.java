package kem.interviews.shai;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by Evgeny Kurtser on 07-Mar-22 at 7:14 PM.
 * <a href=mailto:lopotun@gmail.com>lopotun@gmail.com</a>
 */
public class ClassReflection {

	public StringBuilder parseClass(Class<?> cls) {
		StringBuilder res = new StringBuilder(1024);
		parseClass(cls, 0, res);
		return res;
	}

	private void parseClass(Class<?> cls, int level, StringBuilder sb) {
		sb.append(ident(level))
				.append("** ")
				.append(classModifiers(cls))
				.append(classesName(cls))
				.append(':')
				.append("** ")
				.append(System.lineSeparator());

		final Field[] fields = cls.getFields();
		for(Field field : fields) {
			sb.append(ident(level))
					.append(classesName(field.getDeclaringClass()))
					.append(' ')
					.append(field.getName())
					.append(System.lineSeparator());
		}

		final Method[] methods = cls.getDeclaredMethods();
		for(Method method : methods) {
			if(Modifier.isPublic(method.getModifiers())) {
				sb.append(ident(level))
						.append(classesName(method.getReturnType()))
						.append(' ')
						.append(method.getName())
						.append('(')
						.append(classesName(method.getParameterTypes()))
						.append(')')
						.append(System.lineSeparator());
			}
		}

		final Class<?>[] innerClasses = cls.getDeclaredClasses();
		for(Class<?> innerClass : innerClasses) {
			parseClass(innerClass, ++level, sb);
			level--;
		}
	}

	private String ident(int level) {
		char[] fill = new char[level];
		Arrays.fill(fill, '\t');
		return new String(fill);
	}

	private String classesName(Class<?>... cls) {
		return Arrays.stream(cls)
				.map(Class::getSimpleName)
				.collect(Collectors.joining());
	}

	private StringBuilder classModifiers(Class<?> cls) {
		StringBuilder res = new StringBuilder(64);
		final int modifiers = cls.getModifiers();
		if(Modifier.isPublic(modifiers)) {
			res.append("public").append(' ');
		}
		if(Modifier.isStatic(modifiers)) {
			res.append("static").append(' ');
		}
		if(Modifier.isFinal(modifiers)) {
			res.append("final").append(' ');
		}
		if(Modifier.isAbstract(modifiers)) {
			res.append("abstract").append(' ');
		}
		if(Modifier.isProtected(modifiers)) {
			res.append("protected").append(' ');
		}
		if(Modifier.isPrivate(modifiers)) {
			res.append("private").append(' ');
		}
		if(Modifier.isInterface(modifiers)) {
			res.append("interface").append(' ');
		}
		return res;
	}

	abstract static class InnerAbstOne {
		private boolean b;
		protected String protStr;
		public java.util.List<Boolean> booleanList;
		protected Boolean protMethod() {return true;}
		public Boolean pubMethod(String s, boolean b) {return true;}
	}

	protected static class InnerProtOne {
		private boolean b;
		protected String protStr;
		public java.util.List<Boolean> booleanList;
		protected Boolean protMethod() {return true;}
		public Boolean pubMethod(String s, boolean b) {return true;}
		protected static class InnerInnerProtTwo {
			private boolean twoB;
			protected String twoProtStr;
			public java.util.List<Boolean> twoBooleanList;
			protected Boolean twoProtMethod() {return true;}
			public Boolean twoPubMethod(String s, boolean b) {return true;}
		}
	}
}