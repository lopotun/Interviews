package kem.interviews.shai;

import groovy.lang.Tuple2;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Given array of numbers and sum of some two items of this array.
 * The method returns tuple(s) of position of these two items that give ths supplied sum.
 * <a href=mailto:lopotun@gmail.com>EvgenyK</a>
 */
public class SumOfTwoEntriesInArray {
	/**
	 * Given array of numbers and sum of some two items of this array.
	 * The method returns tuple(s) of position of these two items that give ths supplied sum.
	 * @param data     array of numbers
	 * @param sumOfTwo sum of some two items of this array
	 * @return set of tuples of position of two items that give ths supplied sum. If no items found, the method returns an empty set.
	 */
	public Set<Tuple2<Integer, Integer>> getSumOfTwoEntriesInArray(int[] data, int sumOfTwo) {
		if(data == null) {
			return Collections.emptySet();
		}
		// key: input data item; value: set of positions of this item. I.e., if input is [1, 3, 3, 3, 2], then the map is [1->{0}, 3->{1,2,3}, 2->{4}]
		final Map<Integer, Set<Integer>> dataMap = new HashMap<>(data.length);
		for(int i=0; i<data.length; i++) {
			final int counterAsFinal = i;
			dataMap.compute(data[i], (key, valueSet) -> {
				if(valueSet == null) {
					valueSet = new HashSet<>();
				}
				valueSet.add(counterAsFinal);
				return valueSet;
			});
		}

		final Set<Tuple2<Integer, Integer>> res = new HashSet<>();
		dataMap.forEach((key, value) -> {
			final int whatToFind = sumOfTwo - key;
			// Get (first) position of first part of sum
			final int i = value.stream()
					.findFirst()
					.orElseThrow(() -> new RuntimeException("This shit should not have been happened"));

			// find set of positions of second part of the sum
			dataMap.getOrDefault(whatToFind, Collections.emptySet()).stream()
					.filter(pos -> pos != i) // ignore self item
					.forEach(pos -> // for rest of positions...
							res.add(new Tuple2<>(Math.min(i, pos), Math.max(i, pos)))); //... store positions of both parts of sum.
		});
		return res;
	}
}