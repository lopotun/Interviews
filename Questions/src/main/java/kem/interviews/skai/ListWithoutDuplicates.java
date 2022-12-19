package kem.interviews.skai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Write a function in Java that receives a list of integers and returns the same list without duplicates.
 * Don't use list collections.
 * You may change the ordering of the list.
 * <a href="https://www.glassdoor.com/Interview/Skai-Interview-RVW27925785.htm">Skai</a>
 */
public class ListWithoutDuplicates {
	public static List<Integer> noDuplicates(List<Integer> lst) {
		if(lst == null || lst.isEmpty()) {
			return Collections.emptyList();
		}

		List<Integer> res = new ArrayList<>(lst.size());
		lst.sort(Integer::compareTo);
		res.add(lst.get(0));
		for(int i=1; i<lst.size(); i++) {
			Integer cur = lst.get(i);
			if(!res.get(res.size()-1).equals(cur)) {
				res.add(cur);
			}
		}
		return res;
	}

	public static void main(String[] args) {
		List<Integer> lst = new ArrayList<>(List.of(5, 3, 8, 5, 7, 2, -1, -1, 8));
		final List<Integer> noDuplicates = noDuplicates(lst);
		System.out.println(noDuplicates);
	}
}
