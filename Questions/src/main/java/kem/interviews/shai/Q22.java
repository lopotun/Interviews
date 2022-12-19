package kem.interviews.shai;

import lombok.AllArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Evgeny Kurtser on 07-Mar-22 at 7:14 PM.
 * <a href=mailto:lopotun@gmail.com>lopotun@gmail.com</a>
 */
public class Q22 {
	private final Set<String> raw = new HashSet<>();
	private Map<String, Set<ItemValue>> org = null;

	public void addRawLine(String rawLine) {
		raw.add(rawLine);
	}

	public Collection<String> getEmails(Collection<String> names) {
		if(org == null) {
			org = raw.stream()
					.map(String::trim)
					.filter(line -> !line.isEmpty())
					.map(line -> line.split(":?,?\\s"))
					.filter(lines -> lines.length > 1)
					.collect(Collectors.toMap(
							lines -> lines[0],
							lines -> {
								final List<String> strings = new LinkedList<>(Arrays.asList(lines));
								strings.remove(0);
								return strings.stream()
										.map(item -> item.contains("@") ?
												new Email(item) :
												new Reference(item))
										.collect(Collectors.toSet());
							}));
		}

		//Set<ItemValue> iv = org.get("Avi");
		final Set<ItemValue> iv = names.stream().flatMap(name -> org.getOrDefault(name, Collections.emptySet()).stream()).collect(Collectors.toSet());
		return iv.stream().flatMap(x -> x.getMail(org).stream()).collect(Collectors.toSet());
	}


	interface ItemValue {
		Set<String> getMail(Map<String, Set<ItemValue>> org);
	}

	@lombok.AllArgsConstructor
	@lombok.ToString
	static class Email implements ItemValue {
		private final String mail;

		@Override
		public Set<String> getMail(Map<String, Set<ItemValue>> org) {
			return Collections.singleton(mail);
		}
	}

	@AllArgsConstructor
	@lombok.ToString
	static class Reference implements ItemValue {
		private final String depName;

		@Override
		public Set<String> getMail(Map<String, Set<ItemValue>> org) {
			return org.getOrDefault(depName, Collections.emptySet()).stream()
					.flatMap(mail -> mail.getMail(org).stream())
					.collect(Collectors.toSet());
		}
	}
}
