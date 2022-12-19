package kem.interviews.shai;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Evgeny Kurtser on 03-May-22 at 8:19 PM.
 * <a href=<a href="lopotun@gmail.com">Eugene Kurtzer</a>
 */
public class TreeTraverses {

	public <T extends Comparable<T>> BiTree<T> addAll(List<T> newItems) {
		if(newItems == null) {
			return null;
		}
		final HeadTail<T> headTail = HeadTail.split(newItems);
		BiTree<T> tree = new BiTree<>(headTail.head());
		headTail.tail().forEach(newItem -> add(tree, newItem));
		return tree;
	}


	public <T extends Comparable<T>> boolean add(BiTree<T> tree, T newItem) {
		if(tree == null) {
			return false;
		}
		int cmp = tree.getValue().compareTo(newItem);
		if(cmp > 0) {
			if(tree.getLeft() != null)
				return add(tree.getLeft(), newItem);
			else {
				tree.setLeft(newItem);
				return true;
			}
		}
		if(cmp < 0) {
			if(tree.getRight() != null)
				return add(tree.getRight(), newItem);
			else {
				tree.setRight(newItem);
				return true;
			}
		}
		return true;
	}


	// function to find the level
	// having maximum number of Nodes
	public <T> int maxNodeLevel(Tree<T> root) {
		if(root == null)
			return -1;

		Queue<Tree<T>> q = new LinkedList<>();
		q.add(root);

		// Current level
		int level = 0;

		// Maximum Nodes at same level
		int max = Integer.MIN_VALUE;

		// Level having maximum Nodes
		int levelWithMaxNodes = 0;

		while(true) {
			// Count Nodes in a level
			int nodeCount = q.size();

			if(nodeCount == 0)
				break;

			// If it is maximum till now
			// Update levelWithMaxNodes to current level
			if(nodeCount > max) {
				max = nodeCount;
				levelWithMaxNodes = level;
			}

			// Pop complete current level
			while(nodeCount > 0) {
				Tree<T> node = q.remove();
				/*if (node.left != null)
					q.add(node.left);
				if (node.right != null)
					q.add(node.right);*/
				node.getChildren().forEach(child -> {
					if(child != null) {
						q.add(child);
					}
				});
				nodeCount--;
			}

			// Increment for next level
			level++;
		}
		return levelWithMaxNodes;
	}

	public <T> boolean bfs(BiTree<T> tree, T item) {
		if(tree == null) return false;
		Queue<Tree<T>> queue = new ArrayDeque<>();
		queue.add(tree);

		while(!queue.isEmpty()) {
			Tree<T> currentNode = queue.remove();
			if(currentNode.getValue().equals(item)) {
				return true;
			} else {
				currentNode.getChildren().forEach(child -> {
					if(child != null) {
						queue.add(child);
					}
				});
			}

		}
		return false;
	}

	public <T> boolean dfs(BiTree<T> tree, T item) {
		if(tree == null) return false;
		if(tree.getValue().equals(item)) return true;
		if(tree.getLeft() != null) return dfs(tree.getLeft(), item);
		if(tree.getRight() != null) return dfs(tree.getRight(), item);
		return false;
	}
}

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
class Tree<T> {
	@Getter
	protected T value;
	@Getter//(value = AccessLevel.PUBLIC)
	protected List<Tree<T>> children;

	protected Tree(T value) {
		this.value = value;
		this.children = new ArrayList<>();
	}

	protected Tree(T value, List<Tree<T>> children) {
		this.value = value;
		this.children = children;
	}

	public static <T> Tree<T> of(T value) {
		return new Tree<>(value);
	}

	public Tree<T> addChild(T value) {
		Tree<T> newChild = new Tree<>(value);
		children.add(newChild);
		return newChild;
	}
}

@ToString
@AllArgsConstructor(staticName = "of")
class BiTree<T> extends Tree<T> {

	public BiTree(T value) {
		super(value, new ArrayList<>(2));
		children.add(null);
		children.add(null);
	}

	public BiTree<T> getLeft() {
		return (BiTree<T>) children.get(0);
	}

	public BiTree<T> getRight() {
		return (BiTree<T>) children.get(1);
	}

	public BiTree<T> setLeft(T value) {
		return set(value, 0);
	}

	public BiTree<T> setRight(T value) {
		return set(value, 1);
	}

	private BiTree<T> set(T value, int pos) {
		BiTree<T> newChild = new BiTree<>(value);
		children.set(pos, newChild);
		return newChild;
	}
}
