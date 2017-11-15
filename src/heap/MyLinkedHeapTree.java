package heap;

import net.datastructures.CompleteBinaryTree;
import net.datastructures.EmptyTreeException;
import net.datastructures.LinkedBinaryTree;
import net.datastructures.Position;
import net.datastructures.NodeDeque;

/**
 * An implementation of a complete binary tree by means
 * of a linked structure (LinkedBinaryTree). The LinkedBinaryTree class
 * takes care of most of the "mechanics" of modifying
 * the tree.
 */

public class MyLinkedHeapTree<E> extends LinkedBinaryTree<E>
		implements CompleteBinaryTree<E> {

	/**
	 * Default constructor. The tree begins empty.
	 */

	// Deque to keep track of the nodes that do not have two children
	private NodeDeque<Position<E>> _nodes;

	public MyLinkedHeapTree( ) {
		_nodes = new NodeDeque<Position<E>>();
	}

	/**
	 * Adds an element to the tree just after the last node. Returns the newly
	 * created position for the element.
	 *
	 * This method must run in constant O(1) worst-case time.
	 *
	 * @param element to be added to the tree as the new last node
	 * @return the Position of the newly inserted element
	 */

	/* This method adds the given element to be inserted at the left most
	 * location in bottom level of the left-complete binary tree (unless
	 * that level is full, in which case the element is inserted at the
	 * left most location in a new level)- this maintains left-complete
	 * status. We accomplish this using a deque of all the nodes that
	 * don't have both a left and right child. The first element in this
	 * deque will always -conveniently- be the parent of the node-to-be.
	 */

	public Position<E> add(E element) {

		// Variable to keep track of the new position to be returned
		Position<E> position = null;

		// If the binary tree is empty, the element is simply inserted
		// at the root spot, and the root position is returned (and of
		// course the root position is added to the deque)

		if (super.isEmpty()){

			super.addRoot(element);
			_nodes.addFirst(super.root());
			position = super.root();

		} else {

			/* Otherwise, if the tree is not empty, we get the first element
			 * in the deque, which is the "left most" element without both a
			 * left and right child ("left most" if the tree were represented
			 * as a line). We either insert the element as a left child or
			 * right child depending on whether the node already has a left
			 * child or not. The first element in the deck will not have both
			 * a left and a right child because if we add a right child here,
			 * we then remove it from the deque.
			 */

			Position<E> firstNode = _nodes.getFirst();

			if (super.hasLeft(firstNode)){

				position = super.insertRight(firstNode, element);
				_nodes.addLast(position); // Newly created position added to the deque
				_nodes.removeFirst();

			} else {

				position = super.insertLeft(firstNode, element);
				_nodes.addLast(position); // Newly created position added to the deque

			}

		}

		return position;

	}

	/**
	 * Removes and returns the element stored in the last node of the tree.
	 *
	 * This method must run in constant O(1) worst-case time.
	 *
	 * @return the element formerly stored in the last node (prior to its removal)
	 * @throws EmptyTreeException if the tree is empty and no last node exists
	 */

	/* This method removes the right-most element on the bottom-most level
	 * of the tree, thus maintaining the tree's left-complete status. This
	 * is accomplished by accessing the deque's last element which is the
	 * position of the element we want to remove and return. Since we remove
	 * this node both from the deque and from the tree, we must add its
	 * parent back to the front of the deque if it previously had two children
	 * - the parent now does not have both children.
	 */

	public E remove() throws EmptyTreeException {

		// Variable to be filled by the element in the "last" position
		E element = null;

		// Throws EmptyTreeException if the tree is empty
		if (super.isEmpty()){
			throw new EmptyTreeException("ERROR: Empty Tree");
		}

		// If the size of the tree is 1, then it consists of only the root.
		// Thus we simply remove the root from the tree and the deque.

		if (super.size() == 1){
			element = super.remove(_nodes.removeLast());
		} else {

			// Otherwise, we remove the last element in the deque, which is
			// always the last added position and the position containing the
			// element to be removed/returned. Then we find the parent node.

			Position<E> parent = super.parent(_nodes.removeLast());

			/* We know that if the parent node has a right child, the last
			 * node is the right child since the left child precedes it. But
			 * if there is no right child, the last node must be the left
			 * child since we know the parent has at least one child. In
			 * either case, we remove the last node from the binary tree. In
			 * the first case, we also have to add the parent node back to
			 * the deque.
			 */

			if (super.hasRight(parent)){
				element = super.remove(super.right(parent));
				_nodes.addFirst(parent);
			} else {
				element = super.remove(super.left(parent));
			}


		}

		return element;
	}

	/**
	 * This method returns the element located at the given position
	 * in the linked binary tree. We do this by replacing the element
	 * located there with null as a placeholder. And then returning the
	 * element to the position after accessing and returning it to the
	 * position. Worst case runtime is still O(1).
	 *
	 * @param a position in the tree
	 */

	public E getElement(Position<E> position){
		E element = super.replace(position, null);
		super.replace(position, element);
		return element;
	}

	// This is an accessor method for the node that was last added
	public Position<E> getLast(){

		// Throws EmptyTreeException if the tree is empty
		if (super.isEmpty()){
			throw new EmptyTreeException("ERROR: Empty Tree");
		}

		return _nodes.getLast();
	}
}
