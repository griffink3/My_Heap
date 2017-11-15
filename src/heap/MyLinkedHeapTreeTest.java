package heap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import net.datastructures.EmptyTreeException;


/**
 * This class is used to test the functionality of my MyLinkedHeapTree implementation.
 */

/* In testing the implementation of MyLinkedHeapTree, we cover the four implemented methods-
 * add, remove, getElement, and getLast. For add and remove, in addition to testing for basic
 * functionality like actually adding and removing elements, we test to make sure that these
 * methods interact with the deque properly, removing/adding the appropriate elements where
 * needed. Testing getElement and getLast is comparably simple but necessary. Beyond testing
 * these methods, we also test for the generic functionality of the MyLinkedHeapTree- meaning
 * we test initializing the tree to take in elements other than integers. Finally, since we
 * only throw an exception at two locations in the implementation, we test these exceptions
 * (although this test was included in the stencil code).
 */

public class MyLinkedHeapTreeTest {

	/**
	 * A simple example of checking that the add() method adds the first element to the tree.
	 *
	 * NOTE: In this test, we also check the edge case that when the tree is initialized
	 * empty and then we add one element, the element becomes the root.
	 */
	@Test
	public void testAddOneElement() {
		MyLinkedHeapTree<Integer> tree = new MyLinkedHeapTree<Integer>();
		tree.add(1);

		/* These are two ways of asserting the same thing
		 * Use whichever you find more convenient out of
		 * assertThat(actual, is(expected)) and
		 * assertTrue(boolean)
		 * Take a look at the JUnit docs for more assertions you might want to use.
		 */
		assertThat(tree.size(), is(1));
		assertTrue(tree.size() == 1);

		assertTrue(tree.getElement(tree.root()) == 1);
	}

	/**
	 * To check that the add() method works after adding the first element to the tree.
	 * We add several more elements to the tree and then check that they were added with
	 * the size method and that the root/last nodes are in fact the elements they should
	 * be (i.e. the first and last elements added).
	 */
	@Test
	public void testAddSeveralElements(){
		MyLinkedHeapTree<Integer> tree = new MyLinkedHeapTree<Integer>();
		tree.add(1);
		tree.add(7);
		tree.add(89);
		tree.add(777);

		assertTrue(tree.size() == 4);

		/* We also want to check that the root remains the same and that
		 * we've only added the new elements at the "last" node position.
		 * To do this, we assume the getElement() and getLast() methods
		 * works- although the functionality of these methods are tested
		 * for later. This same goes for many of the test methods below.
		 */
		assertTrue(tree.getElement(tree.root()) == 1);
		assertTrue(tree.getElement(tree.getLast()) == 777);
	}

	/**
	 * To check that the add() method returns a position after adding an element
	 * to the tree. We check that it returns the root position and last node
	 * position if we've only added one element, and the last node position for
	 * every element after that.
	 */
	@Test
	public void testAddReturnsPosition(){
		MyLinkedHeapTree<Integer> tree = new MyLinkedHeapTree<Integer>();

		assertTrue(tree.add(8) == tree.root()); // Should return root position

		assertTrue(tree.add(0) == tree.getLast()); // Should return last position
		assertTrue(tree.add(-4) == tree.getLast());
		assertTrue(tree.add(60) == tree.getLast());
	}

	/**
	 * To check that after adding a second child to a node- the node that was the
	 * first element in the deque- that node is removed. To do this we add two elements
	 * and verify the parent of the second one is the first one, or the root. This means
	 * that at this point, the parent is still in the deque. Then we add one more at
	 * which point the root is removed from the deque. We then verify this fact by adding
	 * one more and checking that its parent is not the root but in fact the second node
	 * added.
	 */
	@Test
	public void testAddRemovesFirstFromDeque(){
		MyLinkedHeapTree<Integer> tree = new MyLinkedHeapTree<Integer>();
		tree.add(90);
		tree.add(91);

		// Confirming that the parent of this second node is the first, which means
		// the first node is still in the deque
		assertTrue(tree.getElement(tree.parent(tree.getLast())) == 90);

		tree.add(92); // Now the root node should be taken out of the deque

		// And we check this by adding one more and making sure its parent, the
		// node at the front of the deque, is not the root node but the second one
		tree.add(93);
		assertTrue(tree.getElement(tree.parent(tree.getLast())) == 91);
	}

	/**
	 * Standard test for the remove method. Checking that it works for several
	 * elements to decrement the size of the tree. We also check that the remove
	 * method doesn't interfere with the root node.
	 */
	@Test
	public void testRemoveSeveralElements(){
		MyLinkedHeapTree<Integer> tree = new MyLinkedHeapTree<Integer>();
		tree.add(0000);
		tree.add(1111);
		tree.add(2222);
		tree.remove();

		assertTrue(tree.size() == 2);
		assertTrue(tree.getElement(tree.root()) == 0000);
		tree.remove();
		tree.remove();
		assertTrue(tree.size() == 0);
	}

	/**
	 * To check that the remove() method removes the "last" node in the tree.
	 * This node should be the most recently added node as well as the right-most
	 * node on the bottom-most level of the tree.
	 */
	@Test
	public void testRemoveLastElement(){
		MyLinkedHeapTree<Integer> tree = new MyLinkedHeapTree<Integer>();
		tree.add(777);
		tree.add(190);
		tree.add(7);

		// Checking that the last element is 7 before we remove
		assertTrue(tree.getElement(tree.getLast()) == 7);
		tree.remove();

		// And then checking that the last element is now 190
		assertTrue(tree.getElement(tree.getLast()) == 190);
	}

	/**
	 * To check that the remove() method returns the element after removing it
	 * and that this returned element is the last element added that has yet to
	 * be removed.
	 */
	@Test
	public void testRemoveReturnsElement(){
		MyLinkedHeapTree<Integer> tree = new MyLinkedHeapTree<Integer>();
		tree.add(7);
		tree.add(0);
		tree.add(678);
		tree.add(86793);

		assertTrue(tree.remove() == 86793); // 86793 was the most recently added
		assertTrue(tree.remove() == 678);
		assertTrue(tree.remove() == 0);
		assertTrue(tree.remove() == 7);
	}

	/**
	 * To check that we can remove the only element when there is only one
	 * element in the tree (i.e. the root)- since this constitutes a separate
	 * code in the method.
	 */
	@Test
	public void testRemoveWhenOnlyOneElement(){
		MyLinkedHeapTree<Integer> tree = new MyLinkedHeapTree<Integer>();
		tree.add(98);

		// Checking that the size is one element before we remove
		assertTrue(tree.size() == 1);
		tree.remove();

		// And then checking that the tree is now empty
		assertTrue(tree.size() == 0);
	}

	/**
	 * To check that after removing one of the children of a previously child-full
	 * parent, the parent gets added back to the deck so that the next time we add
	 * an element, we add it as a child of this parent. To do this, we add 3 elements
	 * at which point the root node is no longer in the deque. We verify this by
	 * adding one more and checking that it's parent is not the root. Then we remove
	 * this node and then one more- whereupon the root node should be added back.
	 * Again, we confirm this by adding one more node and checking that it's parent
	 * is the root.
	 */
	@Test
	public void testRemoveAddsBackToDeque(){
		MyLinkedHeapTree<Integer> tree = new MyLinkedHeapTree<Integer>();
		tree.add(90);
		tree.add(91);
		tree.add(92); // Now root node is taken out of the deque
		tree.add(93);

		// The parent of this most recently added node should not be the root
		assertTrue(tree.getElement(tree.parent(tree.getLast())) == 91);

		tree.remove();
		tree.remove(); // At this point the root should be added back to the deque

		// And we verify this by adding an element because the parent of this new
		// element will be the first element in the deque
		tree.add(94);
		assertTrue(tree.getElement(tree.parent(tree.getLast())) == 90);
	}

	/**
	 * To check that the tree's functionality is generic meaning we can insert
	 * elements that are not just integers. We test doubles and strings here-
	 * with both the add and remove methods of the tree.
	 */
	@Test
	public void testGenerics(){
		// Here we instantiate the tree to take in string elements
		MyLinkedHeapTree<String> tree = new MyLinkedHeapTree<String>();
		tree.add("I");
		tree.add("Love");
		tree.add("Heap");
		tree.add("LOL");
		tree.remove();

		assertTrue(tree.size() == 3);
		assertTrue(tree.getElement(tree.root()) == "I");
		assertTrue(tree.getElement(tree.getLast()) == "Heap");

		// Here we instantiate the tree to take in double elements
		MyLinkedHeapTree<Double> tree1 = new MyLinkedHeapTree<Double>();
		tree1.add(7.7);
		tree1.add(0.75);
		tree1.remove();
		tree1.add(9.90909);

		assertTrue(tree1.size() == 2);
		assertTrue(tree1.getElement(tree1.root()) == 7.7);
		assertTrue(tree1.getElement(tree1.getLast()) == 9.90909);
	}

	/**
	 * To check that the getElement() method returns the element at the
	 * position given. This is arguably the most important helper method.
	 * We test this method with the root and the last element added.
	 */
	@Test
	public void testGetElement(){
		MyLinkedHeapTree<Integer> tree = new MyLinkedHeapTree<Integer>();
		tree.add(7); // This element is the root

		assertTrue(tree.getElement(tree.root()) == 7);

		tree.add(77);
		assertTrue(tree.getElement(tree.getLast()) == 77); // Testing it on last element
	}

	/**
	 * To check the getLast() method returns the position of the last element
	 * added. We check this method with the root and a further element added.
	 */
	@Test
	public void testGetLast(){
		MyLinkedHeapTree<Integer> tree = new MyLinkedHeapTree<Integer>();
		tree.add(7); // This element is the root

		// The position of this last element added should be the root position
		assertTrue(tree.getLast() == tree.root());

		tree.add(77);
		assertTrue(tree.getElement(tree.getLast()) == 77);
		// Testing that getLast() works on other elements added
	}

	/**
	 * To test that an exception is thrown if we try to call getLast() on an
	 * empty MyLinkedHeapTree.
	 */
	@Test(expected = EmptyTreeException.class)
	public void testGetLastThrowsEmptyTreeException() {
		MyLinkedHeapTree<Integer> tree = new MyLinkedHeapTree<Integer>();
		tree.getLast();
	}


	/**
	 * This is an example of how to test whether an exception you expect to be thrown on a certain line of code
	 * is actually thrown. As shown, you'd simply add the expected exception right after the @Test annotation.
	 * This test will pass if the exception expected is thrown by the test and fail otherwise.
	 */
	@Test(expected = EmptyTreeException.class)
	public void testRemoveThrowsEmptyTreeException() {
		MyLinkedHeapTree<Integer> tree = new MyLinkedHeapTree<Integer>();
		tree.remove();
	}


}
