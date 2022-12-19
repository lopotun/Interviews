package kem.leetcode;

import kem.interviews.shai.HeadTail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.Math.min;

/**
 * Alice is texting Bob using her phone. The mapping of digits to letters is usual in a phone set.
 * <p>
 * In order to add a letter, Alice has to press the key of the corresponding digit i times, where i is the position of the letter in the key.
 * <p>
 * For example, to add the letter 's', Alice has to press '7' four times. Similarly, to add the letter 'k', Alice has to press '5' twice.
 * Note that the digits '0' and '1' do not map to any letters, so Alice does not use them.
 * However, due to an error in transmission, Bob did not receive Alice's text message but received a string of pressed keys instead.
 * For example, when Alice sent the message "bob", Bob received the string "2266622".
 * Given a string pressedKeys representing the string received by Bob, return the total number of possible text messages Alice could have sent.
 * Since the answer may be very large, return it modulo 109 + 7.
 * Q. <a href="https://leetcode.com/problems/count-number-of-texts/">...</a>
 */
public class CountNumberOfTexts2266 {


	public int countTextsScala(String pressedKeys) {
		List<Integer> coins = new ArrayList<>();
		coins.add(1);
		coins.add(2);
		coins.add(3);
		return countChange(3, coins);
	}
	private int countChange(int money, List<Integer> coins) {
		if(money == 0) {
			return 1;
		} else if(money < 0) {
			return 0;
		} else if(coins.isEmpty()) {
			return 0;
		} else {
			final HeadTail<Integer> split = HeadTail.split(coins);
			int head = split.head();
			return countChange(money, split.tail())
					+ countChange(money - head, coins);
		}
	}


	int countTextsPython(String pressedKeys) {
		final char[] s = pressedKeys.toCharArray();
		final int n = s.length;
		final int md = (int) (1e9 + 7);

		long[] dp = new long[n];
		dp[0] = 1;
		int cnt = 1;
		long ans = 1;

		for(int i = 1; i < n; i++) {
			if(s[i] == s[i - 1]) { // check if this and previous digit are the same
				cnt++;
				if(s[i] == '7' || s[i] == '9') {
					if(cnt <= 4) {
						dp[i] = 1;
					}

					int k = min(cnt - 1, 4);

					while(k != 0) {
						dp[i] += dp[i - k];
						dp[i] %= md;
						k--;
					}
				} else {

					if(cnt <= 3) {
						dp[i] = 1;
					}

					int k = min(cnt - 1, 3);

					while(k != 0) {
						dp[i] += dp[i - k];
						dp[i] %= md;

						k--;
					}
				}
			} else {
				//resetting the count for new substring with different values.
				cnt = 1;
				dp[i] = 1;

				// adding answer to previous substring.
				ans = (ans * dp[i - 1]) % md;

			}
		}

		ans = (ans * dp[n - 1]) % md;

		return (int) ans;
	}

	public int countTexts(String s) {
		final int n = s.length();
		final long[] dp = new long[n + 1];
		dp[0] = 1;
		final int mod = 1_000_000_000 + 7;

		for(int i = 1; i <= n; i++) {
			char prev = s.charAt(i-1);
			dp[i] = dp[i-1];
			if(i >= 2 && s.charAt(i-2) == prev) {
				dp[i] = (dp[i] + dp[i-2]) % mod;
			} else continue;
			if(i >= 3 && s.charAt(i-3) == prev) {
				dp[i] = (dp[i] + dp[i-3]) % mod;
			} else continue;
			if(i >= 4 && s.charAt(i-4) == prev && (prev == '7' || prev == '9')) {
				dp[i] = (dp[i] + dp[i-4]) % mod;
			}
		}
		return (int) (dp[n] % mod);
	}


	int ballR(int n) {
		System.out.printf("%,d%n", n);
		return switch(n) {
			case 1 -> 1;
			case 2 -> 2;
			case 3 -> 4;
			default -> ballR(n - 1) + ballR(n - 2) + ballR(n - 3);
		};
	}

	// https://tproger.ru/articles/dynprog-starters/
	int ball(int n) {
		int[] dp = new int[3];
		dp[0] = 1; dp[1] = 2; dp[2] = 4;
		for(int i=3; i<n; i++) {
			dp[i%3] = dp[0] + dp[1] + dp[2];
		}
		return dp[(n-1)%3];
	}
	// Driver Code
	public static void main(String[] args) {
//		String pressedKeys = "22233"; // 8
//		String pressedKeys = "22"; // 2
//		String pressedKeys = "222"; // 4
//		String pressedKeys = "2222"; // 7
//		String pressedKeys = "22222"; // 13
//		String pressedKeys = "222222"; // 24
//		String pressedKeys = "222222222222222222222222222222222222"; // 82876089
//		final int res = new CountNumberOfTexts2266().countTexts(pressedKeys);

		final int res = new CountNumberOfTexts2266().ball(5);//76
//		final int res = new CountNumberOfTexts2266().ballR(75);

		System.out.printf("%,d", res);
	}
}