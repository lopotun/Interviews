package kem.interviews.shai;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * Created by Evgeny Kurtser on 03-May-22 at 8:19 PM.
 * <a href=<a href="lopotun@gmail.com">Eugene Kurtzer</a>
 */
class TreeTraversesTest {

	@Test
	void addAll() {
		TreeTraverses x = new TreeTraverses();
		BiTree<Integer> t = x.addAll(Arrays.asList(1,3,5,6,8,4,5,3,9,7));
	}

	@Test
	void add() {
	}

	@Test
	void dfs() {
		TreeTraverses x = new TreeTraverses();
		BiTree<Integer> tree = x.addAll(Arrays.asList(1,3,5,6,8,4,5,3,9,7));
		Assertions.assertFalse(x.dfs(tree, 2));
		Assertions.assertTrue(x.dfs(tree, 4));
	}

	@Test
	void bfs() {
		TreeTraverses x = new TreeTraverses();
		BiTree<Integer> tree = x.addAll(Arrays.asList(1,3,5,6,8,4,5,3,9,7));
		Assertions.assertFalse(x.bfs(tree, 2));
		Assertions.assertTrue(x.bfs(tree, 4));
	}

	@Test
	void maxInLevel() {
		TreeTraverses x = new TreeTraverses();
		Tree<Integer> tree = Tree.of(1);
		Tree<Integer> level_11 = tree.addChild(3);
		Tree<Integer> level_12 = tree.addChild(7);
		Tree<Integer> level_13 = tree.addChild(6);

		Tree<Integer> level_21 = level_11.addChild(2);
		Tree<Integer> level_31 = level_21.addChild(4);
		Tree<Integer> level_32 = level_21.addChild(6);
		Tree<Integer> level_33 = level_21.addChild(7);

		Tree<Integer> level_22 = level_12.addChild(1);

		Tree<Integer> level_23 = level_13.addChild(2);
		Tree<Integer> level_24 = level_13.addChild(8);

		Tree<Integer> level_34 = level_23.addChild(8);
		Tree<Integer> level_35 = level_23.addChild(9);

		Assertions.assertEquals(3, x.maxNodeLevel(tree));
	}
}