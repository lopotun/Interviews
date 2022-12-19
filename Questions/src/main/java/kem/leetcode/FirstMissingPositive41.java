package kem.leetcode;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Given an unsorted integer array nums, return the smallest missing positive integer.
 * You must implement an algorithm that runs in O(n) time and uses constant extra space.
 * <p>
 * Q. <a href="https://leetcode.com/problems/first-missing-positive/">question</a>
 * A. <a href="https://www.geeksforgeeks.org/find-the-smallest-positive-number-missing-from-an-unsorted-array/">solution</a>
 */
public class FirstMissingPositive41 {

	// Time Complexity: O(n). Only two traversals are needed. So the time complexity is O(n).
	// Auxiliary Space: O(1). No extra space is needed, so the space complexity is constant.
	public static int firstMissingPositive(int[] arr) {
		final int n = arr.length;
		// Check if 1 is present in array or not
		for(int i = 0; i < n; i++) {

			// Loop to check boundary condition and for swapping
			while(arr[i] >= 1 && arr[i] <= n
					&& arr[i] != arr[arr[i] - 1]) {
				// Swap
				int temp = arr[arr[i] - 1];
				arr[arr[i] - 1] = arr[i];
				arr[i] = temp;
			}
		}
		//System.out.println(Arrays.toString(arr));
		// Finding which index has value less than n
		for(int i = 0; i < n; i++)
			if(arr[i] != i + 1)
				return (i + 1);

		// If array has values from 1 to n
		return (n + 1);
	}

	// Time Complexity: O(n*log(n))
	// Auxiliary Space: O(1)
	public static int firstMissingPositiveSort01(int[] nums) {
		if(nums == null || nums.length == 0) return 1;
		final int n = nums.length;
		int firstPositivePos = 0;
		for (int i=0; i<n; i++) {
			if(nums[i]<=0 || nums[i]>n) {
				//int tmp = nums[i];
				nums[i] = nums[firstPositivePos];
				nums[firstPositivePos] = 0;
				firstPositivePos++;
			}
		}

		for (int i=firstPositivePos; i < n; i++) {
			while(nums[i]>0 && nums[nums[i]-1] != nums[i]) {
				if(!swap(nums, i, nums[i]-1)) {
					break;
				}
			}
		}

		if(nums[0] != 1) {
			return 1;
		}
		if((nums.length > 1 && nums[1] == 1) || nums.length == 1) {
			return 2;
		}

		int res = 0;
		for (int i=0; i < n-1; i++) {
//			if(nums[i] == 0) {
//				res = i + 1;
//				break;
//			}
			if(nums[i+1] - nums[i] != 1) {
				res = nums[i] + 1;
				break;
			}
		}

		if(res == 0) {
			res = n - firstPositivePos + 1;
		}
		return res;
	}

	private static boolean swap(int[] arr, int a, int b) {
		if(a<0 || b<0 || arr.length ==0 || arr[a] == arr[b]) return false;
		int tmp = arr[a];
		arr[a] = arr[b];
		arr[b] = tmp;
		return true;
	}

	// Time Complexity: O(n*log(n))
	// Auxiliary Space: O(1)
	public static int firstMissingPositiveSort02(int[] nums) {
		final List<Integer> lst = IntStream.of(nums)
				.filter(x -> x > 0)
				.distinct()
				.sorted()
				.boxed()
				.collect(Collectors.toList());

		if(lst.isEmpty() || lst.get(0) > 1) {
			return 1;
		}
		final int n = lst.size();
		int ans = 0;
		for (int i = 0; i < n-1; i++) {
			if(lst.get(i+1) - lst.get(i) > 1) {
				ans = lst.get(i) + 1;
				break;
			}
		}
		System.out.println("ans=" + ans + ", n=" + n);
		if(ans == 0)
			ans = n+1;
		return ans;
	}

	// Driver Code
	public static void main(String[] args) {
		int[] arr = {2, 3, -7, 6, 8, 1, -10, 1000, 15};
		//int[] arr = {3,4,-1,1};
//		int[] arr = {1,1};
		int ans = firstMissingPositiveSort01(arr);

		System.out.println(ans);
	}
}

