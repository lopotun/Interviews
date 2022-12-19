package kem.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]] such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.
 * Notice that the solution set must not contain duplicate triplets.
 * Q. <a href="https://leetcode.com/problems/3sum/">...</a>
 */
public class ThreeSum15 {

	/*private final Map<Integer, Integer> NEGATIVES = new HashMap<>();
	private final Map<Integer, Integer> POSITIVES = new HashMap<>();
	private int zeros = 0;

	public List<List<Integer>> threeSum(int[] nums) {
		for(int x: nums) {
			if(x < 0) NEGATIVES.merge(x, 1, (oldVal, newVal) -> oldVal+1);
			else if(x > 0) POSITIVES.merge(x, 1, (oldVal, newVal) -> oldVal+1);
			else zeros++;
		}

		Set<List<Integer>> res = new HashSet<>();
		if(zeros > 2) res.add(Arrays.asList(0, 0, 0));
		if(NEGATIVES.isEmpty() || POSITIVES.isEmpty()) {
			return new ArrayList<>(res);
		}

		for(Map.Entry<Integer, Integer> meNeg: NEGATIVES.entrySet()) {
			Integer a = meNeg.getKey();
			for(Map.Entry<Integer, Integer> mePos: POSITIVES.entrySet()) {
				Integer b = mePos.getKey();
				int c = a + b;
				if(c < 0) { // keep looking in positives
					if(POSITIVES.containsKey(-c)) {
						if(!b.equals(-c) || mePos.getValue() > 1) {
							res.add(toTriplet(a, b, -c));
						}
					}
				} else
				if(c > 0) { // look in negatives
					if(NEGATIVES.containsKey(-c)) {
						if(!a.equals(-c) || meNeg.getValue() > 1) {
							res.add(toTriplet(a, b, -c));
						}
					}
				} else
				if(zeros > 0) {
					res.add(toTriplet(a, b, 0));
				}
			}
		}
		return new ArrayList<>(res);
	}

	private List<Integer> toTriplet(Integer a, Integer b, Integer c) {
		Integer temp;
		if (b < a) {
			temp = a;
			a = b;
			b = temp;
		}
		if (c < b) {
			temp = b;
			b = c;
			c = temp;
		}
		if (b < a) {
			temp = a;
			a = b;
			b = temp;
		}
		return Arrays.asList(a, b, c);
	}*/
	private final Map<Integer, Boolean> NEGATIVES = new HashMap<>();
	private final Map<Integer, Boolean> POSITIVES = new HashMap<>();
	private int zeros = 0;

	public List<List<Integer>> threeSum(int[] nums) {
		for(int x: nums) {
			if(x < 0) NEGATIVES.merge(x, false, (oldVal, newVal) -> true);
			else if(x > 0) POSITIVES.merge(x, false, (oldVal, newVal) -> true);
			else zeros++;
		}

		Set<List<Integer>> res = new HashSet<>();
		if(zeros > 2) res.add(Arrays.asList(0, 0, 0));
		if(NEGATIVES.isEmpty() || POSITIVES.isEmpty()) {
			return new ArrayList<>(res);
		}

		for(Map.Entry<Integer, Boolean> meNeg: NEGATIVES.entrySet()) {
			Integer a = meNeg.getKey();
			for(Map.Entry<Integer, Boolean> mePos: POSITIVES.entrySet()) {
				int b = mePos.getKey();
				int c = a + b;
				if(c < 0) { // keep looking in positives
					if(POSITIVES.containsKey(-c)) {
						if(b != -c || mePos.getValue()) {
							res.add(toTriplet(a, b, -c));
						}
					}
				} else
				if(c > 0) { // look in negatives
					if(NEGATIVES.containsKey(-c)) {
						if(a != -c || meNeg.getValue()) {
							res.add(toTriplet(a, b, -c));
						}
					}
				} else
				if(zeros > 0) {
					res.add(toTriplet(a, b, 0));
				}
			}
		}
		return new ArrayList<>(res);
	}

	private List<Integer> toTriplet(int a, int b, int c) {
		int temp;
		if (b < a) {
			temp = a;
			a = b;
			b = temp;
		}
		if (c < b) {
			temp = b;
			b = c;
			c = temp;
		}
		if (b < a) {
			temp = a;
			a = b;
			b = temp;
		}
		return Arrays.asList(a, b, c);
	}



	// Driver Code
	public static void main(String[] args) {
//		int[] arr = {-1,0,1,2,-1,-4}; // [[-1,-1,2],[-1,0,1]]
//		int[] arr = {0, 1, 1}; // []
//		int[] arr = {0, 0, 0}; // [0, 0, 0]
		int[] arr = {3, 0, -2, -1, 1, 2}; // [[-2,-1,3], [-2,0,2], [-1,0,1]]
		final List<List<Integer>> res = new ThreeSum15().threeSum(arr);

		System.out.println(res);
	}
}