package kem.leetcode;

/**
 * You are visiting a farm that has a single row of fruit trees arranged from left to right. The trees are represented by an integer array fruits where fruits[i] is the type of fruit the ith tree produces.
 * <p>
 * You want to collect as much fruit as possible. However, the owner has some strict rules that you must follow:
 * <p>
 * You only have two baskets, and each basket can only hold a single type of fruit. There is no limit on the amount of fruit each basket can hold.
 * Starting from any tree of your choice, you must pick exactly one fruit from every tree (including the start tree) while moving to the right. The picked fruits must fit in one of your baskets.
 * Once you reach a tree with fruit that cannot fit in your baskets, you must stop.
 * Given the integer array fruits, return the maximum number of fruits you can pick.
 * <p>
 * Example 1:
 * Input: fruits = [1,2,1]
 * Output: 3
 * Explanation: We can pick from all 3 trees.
 * <p>
 * Example 2:
 * Input: fruits = [0,1,2,2]
 * Output: 3
 * Explanation: We can pick from trees [1,2,2].
 * If we had started at the first tree, we would only pick from trees [0,1].
 * <p>
 * Example 3:
 * Input: fruits = [1,2,3,2,2]
 * Output: 4
 * Explanation: We can pick from trees [2,3,2,2].
 * If we had started at the first tree, we would only pick from trees [1,2].
 * <p>
 * Example 4:
 * Input: fruits = [3,3,3,1,2,1,1,2,3,3,4]
 * Output: 5
 * Explanation: We can pick from trees [1,2,1,1,2].
 * If we had started at the first tree, we would only pick from trees [3,3,3,1].
 * <p>
 * Q. <a href="https://leetcode.com/problems/fruit-into-baskets">...</a>
 */
public class FruitIntoBaskets904 {

	public int totalFruit(int[] fruits) {
		if(fruits == null || fruits.length == 0) return 0;
		if(fruits.length == 1) return 1;

		// I'll iterate starting from the 3rd tree. So, I initialize my baskets with fruits from the first two trees.
		int basket1 = fruits[0];    // basket #1. Initially contains a fruit from the 1st tree.
		int basket2 = fruits[1];    // basket #2. Initially contains a fruit from the 2nd tree.
		int prevFruit = fruits[1];  // last fruit that I put to one of the baskets. Initially contains a fruit form the 2nd tree.
		int tmpMax = 2;             // number of fruits in the current iteration. Initially -- 2.
		int max = 2;                // max num of fruits that I ever had. Initially -- 2.
		int bonus = fruits[0] == fruits[1]? 1: 0; // number of successive same trees. For example, trees [2,2,2,2] give bonus = 4.

		// Optimization. Iterate starting from the 3rd tree.
		for(int i=2; i<fruits.length; i++) {
			int fruit = fruits[i];
			if(fruit == basket1 || fruit == basket2) { // if the fruit suits either first or second basket
				tmpMax++;   // then increment number of fruits in the current iteration
			} else { // the fruit suits neither first nor second basket
				basket1 = prevFruit; // don't touch the content of the first basket
				basket2 = fruit; // put this fruit to the second basket
				tmpMax = 2 + bonus;
			}
			max = Math.max(tmpMax, max);
			bonus = prevFruit == fruit? ++bonus: 0;

			prevFruit = fruit;
		}
		return max;
	}
}