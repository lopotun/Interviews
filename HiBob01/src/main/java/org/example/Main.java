package org.example;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <a href="https://gist.github.com/MichaelKreimer/572f0509a8c2def07b029ed458a0aa25">HiBob question</a>
 * You need to implement method
 * <pre>
 *      private static Map<LocalDate, Role> rolesByDates(List<Role> history, List<LocalDate> dates) {
 *         return null;
 *     }
 * </pre>
 */
class Main {

	private static final Role DUMMY_ROLE = new Role("DUMMY ONE", LocalDate.now());

	/**
	 * Calculates what role an employee had per given set of dates
	 *
	 * @param history Employee role history sorted by the effectiveFrom property in descending order
	 * @param dates   list of dates
	 */
	private static Map<LocalDate, Role> rolesByDates01(List<Role> history, List<LocalDate> dates) {
		return dates.stream()
				.map(date -> {
					final Role role = history.stream()
							.filter(h -> date.isAfter(h.effectiveFrom))
							.findFirst().orElse(DUMMY_ROLE);
					return new DateRole(date, role);
				})
				.collect(Collectors.toMap(t -> t.date, t -> t.role));
	}

	private static Map<LocalDate, Role> rolesByDates02(List<Role> history, List<LocalDate> dates) {
		return dates.stream()
				.flatMap(date -> {
					final Optional<DateRole> res = history.stream()
							.filter(h -> date.isAfter(h.effectiveFrom))
							.findFirst()
							.map(role -> new DateRole(date, role));
					return Stream.of(res);
				})
				.flatMap(Optional::stream)
				.collect(Collectors.toMap(t -> t.date, t -> t.role));
	}

	private static Map<LocalDate, Role> rolesByDates(List<Role> history, List<LocalDate> dates) {
		return dates.stream()
				.flatMap(date -> history.stream()
						.filter(h -> date.isAfter(h.effectiveFrom))
						.findFirst()
						.map(role -> new DateRole(date, role))
						.stream())
				.collect(Collectors.toMap(t -> t.date, t -> t.role));
	}

	static class DateRole {
		public DateRole(java.time.LocalDate date, Role role) {
			this.date = date;
			this.role = role;
		}

		private final java.time.LocalDate date;
		private final Role role;
	}

	public static void main(String[] args) {
		List<Role> history = Arrays.asList(
				new Role("Team Leader", LocalDate.of(2020, 8, 1)),
				new Role("Fullstack developer", LocalDate.of(2016, 5, 15)),
				new Role("Frontend developer", LocalDate.of(2015, 11, 17))
		);

		List<LocalDate> dates = Arrays.asList(
				LocalDate.of(2021, 1, 1),
				LocalDate.of(2014, 1, 1),
				LocalDate.of(2016, 6, 1)
		);

		Map<LocalDate, Role> rolesByDates = rolesByDates(history, dates);

		rolesByDates.forEach((key, value) -> System.out.println("date = " + key + ", role = " + value.getTitle()));

	}

	public static class Role {
		private String title;
		private LocalDate effectiveFrom;

		public Role(String title, LocalDate effectiveFrom) {
			this.title = title;
			this.effectiveFrom = effectiveFrom;
		}

		public String getTitle() {
			return title;
		}

		public LocalDate getEffectiveFrom() {
			return effectiveFrom;
		}

		@Override
		public boolean equals(Object o) {
			if(this == o) return true;
			if(o == null || getClass() != o.getClass()) return false;
			Role role = (Role) o;
			return Objects.equals(title, role.title) &&
					Objects.equals(effectiveFrom, role.effectiveFrom);
		}

		@Override
		public int hashCode() {
			return Objects.hash(title, effectiveFrom);
		}
	}
}