package kem.leetcode;

/**
 * Given an array of integers nums containing n + 1 integers where each integer is in the range [1, n] inclusive.
 * There is only one repeated number in nums, return this repeated number.
 * You must solve the problem without modifying the array nums and uses only constant extra space.
 * Q. <a href="https://leetcode.com/problems/find-the-duplicate-number/">...</a>
 */
public class FindTheDuplicateNumber287 {
	private static final int[] PREPARED_SUMS = {0, 1, 3, 6, 10, 15, 21, 28, 36, 45, 55, 66, 78};

	// Since all the elements are positive by definition I can reverse element at position 'nums[i]'.
	// Encountering of negative 'nums[i]' means that element 'i' is duplicate.
	public int findDuplicate(int[] nums) {
		int res = nums[0];
		for(int x: nums) {
			int i = Math.abs(x);
			if(nums[i] < 0) {
				res = i;
				break;
			}
			nums[i] = -nums[i];
		}
		// Restore the original array
		for(int x: nums) {
			int i = Math.abs(x);
			nums[i] = Math.abs(nums[i]);
		}
		return res;
	}

	// If the duplicate element were appeared one time only, it would much easier to find it:
	// Just calculate sum of first n members of an arithmetical progression (d=1, a1 = 1, an = n-1) Sum = n*(a1 + an)/2
	// then calculate sum of elements of 'nums'. Difference between these two sums gives the duplicate element.
	public int findDuplicateOnce(int[] nums) {
		int theoretical = sum(nums.length - 1);
		int realSum = sum(nums);
		return realSum - theoretical;
	}

	private int sum(int[] nums) {
		int res = 0;
		for(int x : nums) {
			res += x;
		}
		return res;
	}

	private int sum(int n) {
		if(n < PREPARED_SUMS.length) {
			return PREPARED_SUMS[n];
		}
		return (n*(1 + n))/2;
	}

	// Driver Code
	public static void main(String[] args) {
//		int[] arr = {1, 1, 2, 3}; // 3 (1)
//		int[] arr = {1, 2, 2, 3}; // OK
//		int[] arr = {1, 2, 3, 3}; // 6 (3)
		int[] arr = {1, 2, 3, 4, 4}; // 24 (4)
		int ans = new FindTheDuplicateNumber287().findDuplicate(arr);

		System.out.println(ans);
	}
}

