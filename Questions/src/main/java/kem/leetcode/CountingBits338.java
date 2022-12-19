package kem.leetcode;

import java.util.Arrays;
import java.util.Collections;

/**
 * Given an integer n, return an array ans of length n + 1 such that for each i (0 <= i <= n), ans[i] is the number of 1's in the binary representation of i.
 * Q. <a href="https://leetcode.com/problems/counting-bits/">...</a>
 */
public class CountingBits338 {
	public int[] countBits(int n) {
		if(n == 0) return new int[]{0};

		int[] res = new int[n+1];
		res[0] = 0; res[1] = 1;
		for(int i=2; i<=n; i++) {
			int div = i / 2; // === i >> 1
			int mod = i % 2; // === i & 1
			res[i] = res[div] + mod;
			// Same as res[i] = res[i >> 1] + (i & 1);
		}
		return res;
	}

	// Driver Code
	public static void main(String[] args) {
//		int n = 2; // [0,1,1]
		int n = 9; // [0,1,1,2,1,2,2,3,1,2]
//		int n = 5; // [0,1,1,2,1,2]
//		int n = 1; // [0,1,1,2,1,2]
		final int[] res = new CountingBits338().countBits(n);

		System.out.println(Arrays.toString(res));
	}
}