package kem.leetcode;

/**
 * Given an integer n, your task is to count how many strings of length n can be formed under the following rules:
 * <p><p>
 * Each character is a lower case vowel ('a', 'e', 'i', 'o', 'u')<p>
 * Each vowel 'a' may only be followed by an 'e' => ae<p>
 * Each vowel 'e' may only be followed by an 'a' or an 'i'    => ea, ei<p>
 * Each vowel 'i' may not be followed by another 'i'  => ia, ie, io, iu<p>
 * Each vowel 'o' may only be followed by an 'i' or a 'u'    => oi, ou<p>
 * Each vowel 'u' may only be followed by an 'a' => ua
 * <p><p>
 * Since the answer may be too large, return it modulo 10^9 + 7.
 * Q. <a href="https://leetcode.com/problems/count-vowels-permutation/">...</a>
 * A. <a href="https://www.youtube.com/watch?v=VUVpTZVa7Ls">...</a>
 */
public class CountVowelsPermutation1220 {
	private static final int LIMIT = 1_000_000_000 + 7;//Math.pow(10, 9) + 7;
	private static final int A = 0, E = 1, I = 2, O = 3, U = 4;

	private final long[][] freqs = new long[5][];

	public int countVowelPermutationSpeedBoost(int n) {
		if(n == 1) {
			return 5;
		}
		freqs[A] = new long[n]; freqs[A][0] = 1;
		freqs[E] = new long[n]; freqs[E][0] = 1;
		freqs[I] = new long[n]; freqs[I][0] = 1;
		freqs[O] = new long[n]; freqs[O][0] = 1;
		freqs[U] = new long[n]; freqs[U][0] = 1;

		for(int j = 1; j < n; j++) {
			freqs[A][j] = (freqs[E][j - 1] + freqs[I][j - 1] + freqs[U][j - 1]) % LIMIT; //e i u
			freqs[E][j] = (freqs[A][j - 1] + freqs[I][j - 1]) % LIMIT; //a i
			freqs[I][j] = (freqs[E][j - 1] + freqs[O][j - 1]) % LIMIT; //e o
			freqs[O][j] = freqs[I][j - 1]; //i
			freqs[U][j] = (freqs[I][j - 1] + freqs[O][j - 1]) % LIMIT; //i o
		}

		long sum = 0;
		for(long[] freq : freqs) {
			sum += freq[n-1];
		}
		return (int) (sum % LIMIT);
	}

	private final long[][] FR = new long[5][2];
	{
		FR[A][0] = 1L;
		FR[E][0] = 1L;
		FR[I][0] = 1L;
		FR[O][0] = 1L;
		FR[U][0] = 1L;
	}
	public int countVowelPermutationMemoryBoost(int n) {
		if(n == 1) {
			return 5;
		}
		
		for(int j = 1; j < n; j++) {
			FR[A][1] = (FR[E][0] + FR[I][0] + FR[U][0]) % LIMIT; //e i u
			FR[E][1] = (FR[A][0] + FR[I][0]) % LIMIT; //a i
			FR[I][1] = (FR[E][0] + FR[O][0]) % LIMIT; //e o
			FR[O][1] = FR[I][0]; //i
			FR[U][1] = (FR[I][0] + FR[O][0]) % LIMIT; //i o

			for(long[] fr : FR) {
				fr[0] = fr[1];
			}
		}

		long sum = 0;
		for(long[] fr : FR) {
			sum += fr[1];
		}
		return (int) (sum % LIMIT);
	}

	public int countVowelPermutation(int n, boolean speedBoost) {
		if(speedBoost) return countVowelPermutationSpeedBoost(n);
		else return countVowelPermutationMemoryBoost(n);
	}

	// Driver Code
	public static void main(String[] args) throws InterruptedException {
		final int n = 144;
		final CountVowelsPermutation1220 test = new CountVowelsPermutation1220();
		final int res = test.countVowelPermutation(n, true);
		System.out.println(res);
	}
}