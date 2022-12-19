package kem.leetcode;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * You are given an array of integers nums, there is a sliding window of size k which is moving from the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position.
 * Return the max sliding window.
 * Q. <a href="https://leetcode.com/problems/sliding-window-maximum/">...</a>
 */
public class SlidingWindowMaximum239 {

	// My implementation
	public int[] maxSlidingWindowMy(int[] nums, int k) {
		// Optimization for special case.
		if(nums.length == 1) {
			return nums;
		}
		// Optimization for special case.
		if(nums.length == k) {
			int a = Integer.MIN_VALUE;
			for(int b : nums) {
				a = Math.max(b, a);
			}
			return new int[]{a};
		}

		int[] res = new int[nums.length - k + 1];
		Slider slider = new Slider(nums, k);
		int maxToFill = Math.min(nums.length, k) - 1;

		res[0] = slider.max;
		for(int i = k; i < nums.length; i++) {
			slider.slide(nums[i]);
			res[i - maxToFill] = slider.max;
		}
		return res;
	}

	static class Slider {
		private final Deque<Integer> deque;
		private final int size;
		private int max = Integer.MIN_VALUE;
		private int numOfMaxDuplicates = 0;

		Slider(int[] nums, int size) {
			this.size = size;
			deque = new LinkedList<>();
			for(int i = 0; i < size; i++) {
				int entry = nums[i];
				deque.addLast(entry);
				findMaxAndItsCount(entry);
			}
		}

		void slide(int entry) {
			deque.addLast(entry);

			findMaxAndItsCount(entry);

			final Integer removed = deque.removeFirst();
			if(removed == max) {
				if(numOfMaxDuplicates == 0) { // Find new max and it's maxPos
					numOfMaxDuplicates = -1;
					max = deque.getFirst();
					for(int cur: deque) {
						findMaxAndItsCount(cur);
					}
				} else {
					numOfMaxDuplicates--;
				}
			}
		}

		private void findMaxAndItsCount(int entry) {
			if(entry > max) {
				max = entry;
				numOfMaxDuplicates = 0;
			} else {
				if(entry == max) {
					numOfMaxDuplicates++;
				}
			}
		}

		@Override
		public String toString() {
			return String.format("%d of %d in %s", max, size, deque);
		}
	}




	// Other's implementation
	public int[] maxSlidingWindow(int[] nums, int k) {
		// Optimization for special case.
		if(nums.length == 1) {
			return nums;
		}

		final int[] res = new int[nums.length - k + 1];
		final QueueOfBiggest q = new QueueOfBiggest();

		//fill first slide
		for(int i = 0; i < k; i++) {
			q.fill(nums[i]);
		}

		//move the slide
		for(int i = k; i < nums.length; i++) {
			res[i - k] = q.getMax();
			q.slide(nums[i - k], nums[i]);
		}

		//last slide res to be added
		res[nums.length - k] = q.getMax();
		return res;
	}

	private static class QueueOfBiggest {
		private final Deque<Integer> queue = new LinkedList<>();

		void slide(int toRemove, int toAdd) {
			// Remove
			if(queue.getFirst() == toRemove)
				queue.removeFirst();
			// Add
			fill(toAdd);
		}

		void fill(int e) {
			// Kill the weaker elements
			while(!queue.isEmpty() && queue.getLast() < e) {
				queue.removeLast();
			}
			// Append the new element to tail (if any)
			queue.addLast(e);
		}

		int getMax() {
			return queue.getFirst();
		}

		@Override
		public String toString() {
			return String.format("%s", queue);
		}
	}

	// Driver Code
	public static void main(String[] args) {
//		int[] nums = new int[] {1, 3, -1, -3, 5, 3, 6, 7}; int k = 3; // [3, 3, 5, 5, 6, 7]
//		int[] nums = new int[] {1}; int k = 1; // []
//		int[] nums = new int[]{9, 8, 7, 6, 5, 4, 3, 9};
//		int[] nums = new int[]{9, 8, 7, 6, 9, 5, 4, 3, 9}; // [9, 8, 9, 9, 9, 5, 9]
		int[] nums = new int[]{9, 8, 9, 9, 9, 5, 4, 3, 9}; // [9, 9, 9, 9, 9, 5, 9]
		int k = 3; // [9, 8, 7, 6, 5, 9]

		final int[] res = new SlidingWindowMaximum239().maxSlidingWindowMy(nums, k);

		System.out.println(Arrays.toString(res));
	}
}