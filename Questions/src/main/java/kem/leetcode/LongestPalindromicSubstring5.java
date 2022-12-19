package kem.leetcode;

/**
 * Given a string s, return the longest palindromic substring in s.
 * Q. <a href="https://leetcode.com/problems/longest-palindromic-substring/">...</a>
 * A. <a href="https://youtu.be/XYQecbcd6_c">...</a>
 */
public class LongestPalindromicSubstring5 {
	private final int[] lr = new int[2];
	public String longestPalindrome(String s) {
		if(s.length() < 2) return s;
		if(s.length() == 2) {
			return s.charAt(0) == s.charAt(1)? s: s.substring(0, 1);
		}
		int resLen = 0;
		if(s.charAt(0) == s.charAt(1)) {
			lr[0] = 0; lr[1] = 2;
			resLen = 2;
		}

		for(int i=1; i< s.length()-1; i++) {
			// odd
			if(findPalindrome(i, i, resLen, s, lr)) {
				resLen = lr[1]-lr[0];
			}

			// even
			if(findPalindrome(i, i+1, resLen, s, lr)) {
				resLen = lr[1]-lr[0];
			}
		}

		String res;
		if(lr[1] - lr[0] == 1) {
			res = s.substring(0, 1);
		} else {
			res = s.substring(lr[0], lr[1]);
		}
		return res;
	}

	private boolean findPalindrome(int l, int r, int resLen, String s, int[] lr) {
		boolean res = false;
		while(l>=0 && r<s.length() && s.charAt(l) == s.charAt(r)) {
			if(r-l+1 > resLen) {
				lr[0] = l; lr[1] = r+1;
				resLen = r-l+1;
				res = true;
			}
			l--;
			r++;
		}
		return res;
	}

	// Driver Code
	public static void main(String[] args) throws InterruptedException {
//		final String s = "babadsabrbas"; // sabrbas
//		final String s = "babad"; // bab
//		final String s = "cbbd"; // bb
		final String s = "abba"; // abba
//		final String s = "cbbbbd"; // bbbb
//		final String s = "cbb12321d"; // 12321
//		final String s = "x"; // x
//		final String s = ""; //
//		final String s = "abc"; //
		final LongestPalindromicSubstring5 test = new LongestPalindromicSubstring5();
		final String res = test.longestPalindrome(s);
		System.out.println(res);
	}
}