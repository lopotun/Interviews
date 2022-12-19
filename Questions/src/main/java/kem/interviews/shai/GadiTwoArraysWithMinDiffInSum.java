package kem.interviews.shai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * <a href=mailto:lopotun@gmail.com>Eugene Kurtzer</a>
 */
public class GadiTwoArraysWithMinDiffInSum {
	// 2, -1, 0, 4, -2, -9
	// -9, -2, -1, 0, 2, 4
	// -9, -1, 2
	// -2, 0, 4
	//
	// 2, 4, -9
	// -1, 0, -2

	private static List<int[]> twoArraysWithMinDiffInSum(int[] input) {
		if(input == null || input.length %2 != 0) {
			return Collections.emptyList();
		}
		int[] even = new int[input.length/2];
		int[] odd = new int[input.length/2];
		List<int[]> res = new ArrayList<>(2);
		res.add(even);
		res.add(odd);
		Arrays.sort(input);
		for(int i = 0; i < input.length; i++) {
			if(i%2 == 0) even[i/2] = input[i];
			else odd[i/2] = input[i];
		}
		return res;
	}

	public static void main(String[] args) {
		int[] input;
		input = null;
		System.out.println(twoArraysWithMinDiffInSum(input));
		input = new int[0];
		System.out.println(twoArraysWithMinDiffInSum(input));
		input = new int[]{42};
		System.out.println(twoArraysWithMinDiffInSum(input));
		input = new int[]{3, 1, 17, 42, 25, 10};
		System.out.println(twoArraysWithMinDiffInSum(input));
	}
}
