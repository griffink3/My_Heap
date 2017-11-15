package heap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import net.datastructures.EmptyPriorityQueueException;
import net.datastructures.Entry;
import net.datastructures.InvalidEntryException;
import net.datastructures.InvalidKeyException;

/**
 * This class can be used to test the functionality of my MyHeap implementation.
 * You will find a few examples to guide you through the syntax of writing test cases.
 */

/* In testing the implementation of MyHeap, we test the implementation of the min(),
 * insert(), removeMin(), remove(), replaceKey(), and replaceValue() methods, as well
 * as the implementation of the helper methods upHeap(), downHeap(), and checkKey(),
 * and the simple methods, size() and isEmpty(). We don't test setComparator() however
 * beyond testing the exception that it throws, because the comparator is inaccessible.
 * In addition to testing method functionality, we test that the appropriate exceptions
 * are thrown where needed.
 */

public class MyHeapTest {


	/**
	 * To check that the size() method returns the number of elements in the heap.
	 */
	@Test
	public void testSizeMethod() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		assertThat(heap.size(), is(0)); // Size should be 0

		heap.insert(1, "A");
		assertThat(heap.size(), is(1)); // Size should be 1

		heap.insert(2, "B");
		heap.insert(909, "C");
		heap.insert(16, "D");
		assertThat(heap.size(), is(4)); // Size should be 4
	}

	/**
	 * To check that the isEmpty() method returns whether the heap is empty.
	 */
	@Test
	public void testIsEmptyMethod() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		assertThat(heap.isEmpty(), is(true)); // Heap should be empty

		heap.insert(1, "CS tas");
		assertThat(heap.isEmpty(), is(false)); // Heap should not be empty

		heap.insert(2, "are");
		heap.insert(909, "really");
		heap.insert(16, "awesome");
		assertThat(heap.isEmpty(), is(false)); // Heap should not be empty
	}

	/**
	 * To check that the min() method returns the element with the minimum key.
	 * We also check that the minimum is what it should be through different
	 * iterations.
	 */
	@Test
	public void testMinMethod() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.insert(77, "A");
		assertThat(heap.min().getKey(), is(77)); // Minimum key should be 77 since only element

		heap.insert(2, "B");
		assertThat(heap.min().getKey(), is(2)); // Minimum key should be 2 now

		heap.insert(909, "C");
		heap.insert(3, "D");
		assertThat(heap.min().getKey(), is(2)); // Minimum key should still be 2
	}

	/**
	 * A simple test to ensure that insert() works.
	 */
	@Test
	public void testInsertOneElement() {
		// set-up
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.insert(1, "A");

		// Assert that your data structure is consistent using
		// assertThat(actual, is(expected))
		assertThat(heap.size(), is(1));
		assertThat(heap.min().getKey(), is(1));
	}

	/**
	 * To test that the insert() method properly upheaps after we insert an element
	 * less than its parent or an element that should now be the root. In the process,
	 * we also test that the insert() method works for multiple elements.
	 */
	@Test
	public void testInsertUpHeaps() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.insert(9, "MIKEY C");
		heap.insert(797, "IS");
		heap.insert(64, "A CUTIE");

		assertThat(heap.size(), is(3));
		assertThat(heap.min().getKey(), is(9));
		assertThat(heap.isEmpty(), is(false));

		heap.insert(1, ":)");
		// This entry should now be at the minimum even though we just inserted it
		// due to the upheaping that should occur
		assertThat(heap.min().getKey(), is(1));
		assertThat(heap.min().getValue(), is(":)"));
	}

	/**
	 * To test that the insert() method returns the entry to be inserted.
	 */
	@Test
	public void testInsertReturnsEntry() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		assertThat(heap.insert(7, "M").getKey(), is(7));
		assertThat(heap.insert(9, "U").getValue(), is("U"));
	}

	/**
	 * To test that the removeMin() method returns the root when only one element.
	 * Also checks that the removeMin() method does in fact return the entry.
	 */
	@Test
	public void testRemoveMinJustRoot() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.insert(44, "E");
		assertThat(heap.removeMin().getKey(), is(44));
		heap.insert(44, "E");
		assertThat(heap.removeMin().getValue(), is("E"));
	}

	/**
	 * This is an example to check that  the order of the heap is sorted as per the keys
	 * by comparing a list of the actual and expected keys.
	 */
	@Test
	public void testRemoveMinHeapOrderUsingList() {
	MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.insert(11, "A");
		heap.insert(13, "B");
		heap.insert(64, "C");
		heap.insert(16, "D");
		heap.insert(44, "E");

		// the expected ordering that keys come in
		List<Integer> expectedKeys = Arrays.asList(11, 13, 16, 44, 64);

		// the actual ordering of keys in the heap
		List<Integer> actualKeys = new ArrayList<Integer>();
		while(!heap.isEmpty()) {
			actualKeys.add(heap.removeMin().getKey());
		}

		// check that the actual ordering matches the expected ordering by using one assert
		// Note that assertThat(actual, is(expected)), when used on lists/ arrays, also checks that the
		// ordering is the same.
		assertThat(actualKeys, is(expectedKeys));
	}

	/**
	 * This is an example of testing heap ordering by ensuring that the min key is always at the root
	 * by checking it explicitly each time, using multiple asserts rather than a list.
	 */
	@Test
	public void testRemoveMinHeapOrder() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.insert(11, "A");
		heap.insert(13, "B");
		heap.insert(64, "C");
		heap.insert(16, "D");
		heap.insert(44, "E");


		// test the heap ordering by asserting on all elements
		assertThat(heap.removeMin().getKey(), is(11));
		assertThat(heap.removeMin().getKey(), is(13));
		assertThat(heap.removeMin().getKey(), is(16));
		assertThat(heap.removeMin().getKey(), is(44));
		assertThat(heap.removeMin().getKey(), is(64));
	}

	/**
	 * To test that the remove() method given the entry removes the entry no
	 * matter where it's located and returns it.
	 */
	@Test
	public void testRemoveEntryMultipleLocatoins() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		Entry<Integer,String> rootNode = heap.insert(11, "A");
		Entry<Integer,String> nodeWithTwoChildren = heap.insert(13, "B");
		Entry<Integer,String> externalNode = heap.insert(64, "C");
		Entry<Integer,String> toBeInternalNode = heap.insert(16, "D");
		heap.insert(44, "E");

		assertThat(heap.remove(nodeWithTwoChildren).getKey(), is(13));
		assertThat(heap.remove(toBeInternalNode).getValue(), is("D"));
		assertThat(heap.remove(externalNode).getKey(), is(64));
		assertThat(heap.remove(rootNode).getValue(), is("A"));
	}

	/**
	 * To test that the remove() method given the entry maintains the
	 * priority order of the heap. This tests the upheap and downheap
	 * functionality of the remove() method. Here we test the edge case
	 * when a downheap would be necessary- i.e. when the "last" node, or
	 * the most recently added element has a key greater than one or both
	 * of the to be removed element's children's keys.
	 */
	@Test
	public void testRemoveEntryMaintainsHeapOrder() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.insert(7, "A");
		Entry<Integer,String> entryToRemove = heap.insert(10, "B");
		heap.insert(13, "C");
		heap.insert(16, "D"); // Child of 10
		heap.insert(19, "E"); // Child of 10
		heap.insert(22, "F"); // In a different subtree than 10, 16, 19

		heap.remove(entryToRemove);
		// In removing this entry, we swap it with the entry of key 22 before
		// removing it, and so the entry with 22 should then downheap, switching
		// positions with entry of key 16- we confirm this below

		// test the heap ordering by asserting on all elements
		assertThat(heap.removeMin().getKey(), is(7));
		assertThat(heap.removeMin().getKey(), is(13));
		assertThat(heap.removeMin().getKey(), is(16));
		assertThat(heap.removeMin().getKey(), is(19));
		assertThat(heap.removeMin().getKey(), is(22));
	}

	/**
	 * Simple test to check that replaceKey() method resets the key variable
	 * stored in the entry.
	 */
	@Test
	public void testReplaceKeyReplacesKey() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		Entry<Integer,String> element = heap.insert(11, "A");
		heap.replaceKey(element, 77); // The key should now be 77
		assertThat(heap.removeMin().getKey(), is(77));
	}

	/**
	 * To test that when we replace a key with a key that's less than the parent
	 * entry's key we then upheap.
	 */
	@Test
	public void testReplaceKeyUpHeaps(){
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.insert(11, "A");
		heap.insert(13, "B");
		Entry<Integer,String> rootRightChild = heap.insert(64, "C");
		heap.insert(16, "D");
		Entry<Integer,String> lastElement = heap.insert(77, "E"); // This entry should be at the bottom
		heap.replaceKey(lastElement, 1); // This entry should now be the minimum after upheaping

		assertThat(heap.removeMin().getKey(), is(1));

		heap.replaceKey(rootRightChild, 7); // This entry should now be at the min
		assertThat(heap.removeMin().getValue(), is("C"));
	}

	/**
	 * To test that when we replace a key with a key that's greater than a child
	 * entry's key we then downHeap.
	 */
	@Test
	public void testReplaceKeyDownHeaps(){
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		Entry<Integer,String> minimum = heap.insert(11, "A"); // Starts off as the minimum
		Entry<Integer,String> nextMinimum = heap.insert(13, "B");
		heap.insert(64, "C");
		heap.insert(16, "D");
		heap.insert(77, "E");
		heap.replaceKey(minimum, 100); // This entry should no longer be the minimum

		assertThat(heap.removeMin().getKey(), is(13)); // 13 should now be the min key

		heap.replaceKey(nextMinimum, 99); // This entry should no longer be the min
		assertThat(heap.removeMin().getValue(), is("D")); // 16 should now be the min key
	}

	/**
	 * To test that the replaceKey method returns the replaced key.
	 */
	@Test
	public void testReplaceKeyReturnsOldKey() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		Entry<Integer,String> element = heap.insert(67, "A");
		assertThat(heap.replaceKey(element, 77), is(67));
	}

	/**
	 * Simple test to check that replaceValue() method resets the value variable
	 * stored in the entry.
	 */
	@Test
	public void testReplaceValueReplacesValue() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		Entry<Integer,String> element = heap.insert(235, "Bad");
		heap.replaceValue(element, "Good"); // The value should now be "Good"
		assertThat(heap.removeMin().getValue(), is("Good"));
	}

	/**
	 * To test that the replaceValue method returns the replaced Value.
	 */
	@Test
	public void testReplaceValueReturnsOldValue() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		Entry<Integer,String> element = heap.insert(235, "Bad");
		assertThat(heap.replaceValue(element, "Good"), is("Bad"));
	}

	/**
	 * Since the upHeap method is a private method, we test its functionality
	 * through the replaceKey() method. We first test that we upHeap even when
	 * there are only two elements. Then we test that the recursive upheaping
	 * does stop- if we replace the key with a key that's not the minimum it
	 * should not become the minimum. And then finally we test that upheaping
	 * really does occur by replacing the key with a minimum key and confirming
	 * that this entry is now at the top.
	 *
	 * (We also test that nothing abnormal happens with a heap of all equal keys)
	 */
	@Test
	public void testUpHeap() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		// Testing with two elements
		heap.insert(11, "A");
		Entry<Integer,String> secondEntry = heap.insert(13, "B");
		heap.replaceKey(secondEntry, 7); // This should now be the minimum
		assertThat(heap.removeMin().getKey(), is(7));

		// Testing that recursion stops
		heap.insert(17, "C");
		heap.insert(37, "D");
		Entry<Integer,String> lastEntry = heap.insert(77, "E");
		heap.replaceKey(lastEntry, 16); // 11 should still be the minimum key
		assertThat(heap.removeMin().getKey(), is(11));

		// Testing that upHeaping takes place
		Entry<Integer,String> toBeMinimum = heap.insert(90, "F");
		heap.replaceKey(toBeMinimum, 1); // UpHeap should bring this element to the top
		assertThat(heap.removeMin().getKey(), is(1));

		// Removing all entries
		heap.removeMin();
		heap.removeMin();
		heap.removeMin();

		// Testing upheaping with entries with the same key
		heap.insert(8, "A");
		heap.insert(8, "B");
		heap.insert(8, "C");

		assertThat(heap.removeMin().getValue(), is("A")); // UpHeap doesn't occur if keys are equal
	}

	/**
	 * Like the upHeap method, we test the functionality of the downHeap method
	 * through the replaceKey() method. In testing the functionality of the
	 * downHeap method, we must test each case that it handles:
	 * 1) If the current node is an external node
	 * 2) If the current node only has a left child
	 * 3) If the current node has both children, but the left child is less
	 * 4) If the current node has both children, but the right child is less
	 * 5) If the current node has both children, and both children are equal
	 * 6) If none of the current node's children have lesser keys
	 */
	@Test
	public void testDownHeap() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		// Testing with an external node
		Entry<Integer,String> firstEntry = heap.insert(11, "A");
		heap.replaceKey(firstEntry, 12); // No downHeaping should occur
		assertThat(heap.removeMin().getKey(), is(12));

		// Testing with one child
		Entry<Integer,String> newEntry = heap.insert(11, "A");
		heap.insert(16, "B");
		heap.replaceKey(newEntry, 17); // DownHeap means the key of 16 should now be the minimum
		assertThat(heap.removeMin().getKey(), is(16));

		// Now heap only contains the newEntry(11, "A")

		// Testing with two children, but left child less
		heap.insert(18, "C"); // Left child
		heap.insert(19, "D"); // Right child
		heap.replaceKey(newEntry, 20); // newEntry should swap with left child so 18 minimum now
		assertThat(heap.removeMin().getKey(), is(18));

		heap.removeMin(); // clearing heap
		heap.removeMin();

		// Testing with two children, but right child less
		Entry<Integer,String> min = heap.insert(21, "A");
		Entry<Integer,String> nextMin = heap.insert(23, "B"); // Left child
		heap.insert(22, "C"); // Right child
		heap.replaceKey(min, 24); // newEntry should swap with right child so 22 minimum now
		assertThat(heap.removeMin().getKey(), is(22));

		// Testing with two children, but both equal
		heap.insert(24, "D");
		heap.replaceKey(nextMin, 25); // Randomize with which child we swap, but in either case, 24 now min
		assertThat(heap.removeMin().getKey(), is(24));

		heap.removeMin(); // clearing heap
		heap.removeMin();

		// Testing with two children, but neither less than
		Entry<Integer,String> newMin = heap.insert(26, "A");
		heap.insert(28, "B"); // Left child
		heap.insert(29, "C"); // Right child
		heap.replaceKey(newMin, 27); // No downheaping should occur
		assertThat(heap.removeMin().getKey(), is(27));
	}

	/**
	 * This is an example of how to test whether an exception you expect to be thrown on a certain line of code
	 * is actually thrown. As shown, you'd simply add the expected exception right after the @Test annotation.
	 * This test will pass if the exception expected is thrown by the test and fail otherwise.
	 *
	 * Here, we're checking to see if an IllegalStateException is being correctly thrown after we try to
	 * call setComparator on a non-empty heap.
	 */
	@Test(expected=IllegalStateException.class)
	public void testSetComparatorThrowsIllegalStateException() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.insert(1, "A");
		heap.setComparator(new IntegerComparator());
	}

	/**
	 * Here we test that an IllegalArgumentExcpetion is thrown when we try to call
	 * setComparator with a null comparator.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetComparatorThrowsIllegalArgumentException() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.setComparator(null);
	}

	/**
	 * Here we test that an EmptyPriorityQueueExcpetion is thrown when we try to call
	 * min() on an empty heap.
	 */
	@Test(expected=EmptyPriorityQueueException.class)
	public void testMinThrowsEmptyPriorityQueueException() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.min();
	}

	/**
	 * Here we test that an InvalidKeyException is thrown when we call insert() passing in
	 * null or an invalid key; by virtue we also test the private method checkKey().
	 */
	@Test(expected=InvalidKeyException.class)
	public void testInsertThrowsInvalidKeyException() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.insert(null, "Value"); // Exception throw if key is null
	}

	/**
	 * Here we test that an EmptyPriorityQueueExcpetion is thrown when we try to call
	 * removeMin() on an empty heap.
	 */
	@Test(expected=EmptyPriorityQueueException.class)
	public void testRemoveMinThrowsEmptyPriorityQueueException() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.removeMin();
	}

	/**
	 * Here we test that an InvalidEntryException is thrown when we call remove() passing in
	 * an invalid entry.
	 */
	@Test(expected=InvalidEntryException.class)
	public void testRemoveThrowsInvalidEntryException() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.insert(1, "A");
		heap.remove(null); // Exception throw if entry is null
	}

	/**
	 * Here we test that an InvalidEntryException is thrown when we call replaceKey() passing in
	 * an invalid entry.
	 */
	@Test(expected=InvalidEntryException.class)
	public void testReplaceKeyThrowsInvalidEntryException() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.insert(1, "A");
		heap.replaceKey(null, 4); // Exception throw if entry is null
	}

	/**
	 * Here we test that an InvalidKeyException is thrown when we call replaceKey() passing in
	 * null or an invalid key; by virtue we also test the private method checkKey().
	 */
	@Test(expected=InvalidKeyException.class)
	public void testReplaceKeyThrowsInvalidKeyException() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		Entry<Integer,String> entry = heap.insert(1, "A");
		heap.replaceKey(entry, null); // Exception throw if key is null
	}

	/**
	 * Here we test that an InvalidEntryException is thrown when we call replaceValue() passing in
	 * an invalid entry.
	 */
	@Test(expected=InvalidEntryException.class)
	public void testReplaceValueThrowsInvalidEntryException() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.insert(1, "A");
		heap.replaceValue(null, "G"); // Exception throw if entry is null
	}


}
