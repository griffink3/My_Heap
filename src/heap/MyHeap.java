package heap;

import java.util.Comparator;
import net.datastructures.CompleteBinaryTree;
import net.datastructures.DefaultComparator;
import net.datastructures.EmptyPriorityQueueException;
import net.datastructures.Entry;
import net.datastructures.InvalidEntryException;
import net.datastructures.InvalidKeyException;
import net.datastructures.Position;
import net.datastructures.AdaptablePriorityQueue;
import support.heap.HeapWrapper;
import net.datastructures.Position;


/**
 * An implementation of an adaptable priority queue by
 * means of a heap. 
 */

public class MyHeap<K,V> implements HeapWrapper<K,V>, AdaptablePriorityQueue<K,V> {

	// This the underlying data structure of your heap
	private MyLinkedHeapTree<MyHeapEntry<K,V>> _tree;
	private Comparator<K> _comp;

	/**
	 * Creates an empty heap with the given comparator.
	 *
	 * @param the comparator to be used for heap keys
	 */
	public MyHeap(Comparator<K> comparator) {
		_tree = new MyLinkedHeapTree<MyHeapEntry<K,V>>();
		this.setComparator(comparator);
	}

	/**
	 * Sets the comparator used for comparing items in the heap to the
	 * comparator passed in.
	 *
	 * @param comparator the comparator to be used for heap keys
	 * @throws IllegalStateException if priority queue is not empty
	 * @throws IllegalArgumentException if null comparator is passed in
	 */
	public void setComparator(Comparator<K> comparator)
			throws IllegalStateException, IllegalArgumentException {
		// Throwing illegal state exception if the heap is not empty
		if (!MyHeap.this.isEmpty()){
			throw new IllegalStateException();
		}
		// Throwing illegal argument exception if null comparator passed in
		if (comparator == null){
			throw new IllegalArgumentException();
		}
		// Otherwise we set the comparator used to the comparator passed in
		_comp = comparator;
	}

	/**
	 * Returns a CompleteBinaryTree that will allow the visualizer
	 * access to private members, shattering encapsulation, but
	 * allowing visualization of the heap. This is the only method
	 * needed to satisfy HeapWrapper interface implementation.
	 *
	 * Do not modify or call this method. It is solely
	 * necessary for the visualizer to work properly.
	 *
	 * @return the underlying binary tree on which the heap is based
	 */
	public CompleteBinaryTree<MyHeapEntry<K,V>> getTree() {
		return _tree;
	}

	/**
	 * Returns the size of the heap.
	 * This method must run in O(1) time.
	 *
	 * @return an int representing the number of entries stored
	 */
	public int size() {
		return _tree.size();
	}

	/**
	 * Returns whether the heap is empty.
	 * This method must run in O(1) time.
	 *
	 * @return true if the heap is empty; false otherwise
	 */
	public boolean isEmpty() {
		return _tree.isEmpty();
	}

	/**
	 * Returns but does not remove an entry with minimum key.
	 * This method must run in O(1) time.
	 *
	 * @return the entry with the minimum key in the heap
	 * @throws EmptyPriorityQueueException if the heap is empty
	 */
	public Entry<K,V> min() throws EmptyPriorityQueueException {
		// Throwing empty priority queue exception if the heap is empty
		// because then no minimum exists
		if (MyHeap.this.isEmpty()){
			throw new EmptyPriorityQueueException("ERROR: Heap is empty");
		}
		return _tree.getElement(_tree.root());
	}

	/**
	 * Inserts a key-value pair and returns the entry created.
	 * This method must run in O(log n) time.
	 *
	 * @param key to be used as the key the heap is sorting with
	 * @param value stored with the associated key in the heap
	 * @return the entry created using the key/value parameters
	 * @throws InvalidKeyException if the key is not suitable for this heap
	 */

	/* This method inserts the given key and value pair as an entry into
	 * the heap. Using the MyLinkedHeapTree add method, the entry is
	 * inserted into the "last" node in order to keep the tree left-
	 * complete. And then we call the helper method upHeap to re-organize
	 * the tree if need be, so that the priority order is maintained
	 * according to the keys. Finally, we return the newly created entry.
	 */

	public Entry<K,V> insert(K key, V value) throws InvalidKeyException {

		MyHeap.this.checkKey(key); // Checking validity of the key

		MyHeapEntry<K,V> newEntry = new MyHeapEntry<K,V>(key,value);
		Position<MyHeapEntry<K,V>> position = _tree.add(newEntry);
		newEntry.setPosition(position); // Set the position of the entry (just in case)
		MyHeap.this.upHeap(position);

		return newEntry;

	}

	/**
	 * Removes and returns an entry with minimum key.
	 * This method must run in O(log n) time.
	 *
	 * @return the entry with the with the minimum key, now removed
	 * @throws EmptyPriorityQueueException if the heap is empty
	 */

	/* This method removes and returns the entry with the minimum key
	 * which will always be located at the top. Since we must remove
	 * from the bottom of the tree, we must first swap elements with
	 * the "last" node. Then we can remove the "last" node along with
	 * the entry with the minimum key. After, we call the helper method
	 * downHeap to re-organize the tree if need be, so that the priority
	 * order is maintained according to the keys. Finally, we return
	 * the entry with the minimum key.
	 */

	public Entry<K,V> removeMin() throws EmptyPriorityQueueException {

		// First checks to see if heap is empty; if so, we throw an exception
		if (MyHeap.this.isEmpty()){
			throw new EmptyPriorityQueueException("ERROR: Heap is empty");
		}

		// If there's only one element in the heap, we just return this root
		// (no need to swap or downheap or anything else )

		if (MyHeap.this.size() == 1){
			return _tree.remove();
		}

		_tree.swapElements(_tree.root(), _tree.getLast());
		MyHeapEntry<K,V> min = _tree.remove();
		// Set the position of the entry (although we'll reset when we downheap)
		_tree.getElement(_tree.root()).setPosition(_tree.root());
		MyHeap.this.downHeap(_tree.root());

		return min;
	}

	/**
	 * Removes and returns the given entry from the heap.
	 * This method must run in O(log n) time.
	 *
	 * @param entry to be removed from the heap
	 * @return the entry specified for removal by the parameter
	 * @throws InvalidEntryException if the entry cannot be removed from this heap
	 */

	/* This method removes and returns an entry given that entry. We do this
	 * by accessing the position stored in the entry, then swapping elements
	 * with the last node so that the entry we want to remove is now in the
	 * last node position. After removing this last node, we then we simply
	 * downHeap/upHeap at its previous position (since the element that was
	 * previously at the last node is now at that position). Although not
	 * likely, it is possible that the parent of a node on the same level has
	 * a key greater than the key of this most recently added entry which is
	 * why we need to upHeap as well. Finally, we just return the entry that
	 * we removed.
	 *
	 * NOTE: Since we must switch the positions of the last node (or most
	 * recently added entry) and the entry to be removed before we upheap
	 * or downheap, the heap will not look the same as before- in other
	 * words, the most recently added entry may not necessarily be in the
	 * same subtree as before. However, the priority order is maintained.
	 */

	public Entry<K,V> remove(Entry<K,V> entry) throws InvalidEntryException {
		MyHeapEntry<K,V> checkedEntry = this.checkAndConvertEntry(entry);

		Position<MyHeapEntry<K,V>> position = checkedEntry.getPosition();

		_tree.swapElements(position,_tree.getLast());
		MyHeapEntry<K,V> oldEntry = _tree.remove();
		MyHeap.this.upHeap(position);
		MyHeap.this.downHeap(position);

		return oldEntry;
	}

	/**
	 * Replaces the key of the given entry.
	 * This method must run in O(log n) time.
	 *
	 * @param entry within which the key will be replaced
	 * @param key to replace the existing key in the entry
	 * @return the old key formerly associated with the entry
	 * @throws InvalidEntryException if the entry is invalid
	 * @throws InvalidKeyException if the key is invalid
	 */

	/* This method replaces the key of an entry given that entry and
	 * the new value for the key (and returns the old key). To do this,
	 * we first store the value for the old key to be returned at the
	 * end. Then we reset the key of the entry (casting the entry to a
	 * MyHeapEntry in the process so that we can call the setKey method
	 * we made). Afterwards, we upheap if the key is less than the parent
	 * key or downheap if the key is greater than either child key- we
	 * only perform one of these. Thus, runtime is O(log n).
	 */

	public K replaceKey(Entry<K,V> entry, K key) throws InvalidEntryException, InvalidKeyException {
		MyHeapEntry<K,V> checkedEntry = this.checkAndConvertEntry(entry);

		MyHeap.this.checkKey(key); // Checking the validity of the key

		K oldKey = checkedEntry.getKey();
		checkedEntry.setKey(key);
		MyHeap.this.upHeap(checkedEntry.getPosition());
		MyHeap.this.downHeap(checkedEntry.getPosition());

		return oldKey;
	}

	/**
	 * Replaces the value of the given entry.
	 * This method must run in O(1) time.
	 *
	 * @param entry within which the value will be replaced
	 * @param value to replace the existing value in the entry
	 * @return the old value formerly associated with the entry
	 * @throws InvalidEntryException if the entry cannot have its value replaced
	 */

	/* This method replaced the value of an entry given that entry and the
	 * new value. The method works very similarly to the replacekey method,
	 * except that since we aren't altering the key, the priority of the
	 * entry will remain the same. Thus, we don't have to upheap or downheap
	 * in this method.
	 */

	public V replaceValue(Entry<K,V> entry, V value) throws InvalidEntryException {
		MyHeapEntry<K,V> checkedEntry = this.checkAndConvertEntry(entry);

		V oldValue = checkedEntry.getValue();
		checkedEntry.setValue(value);

		return oldValue;
	}


	/**
	 * Determines whether a given entry is valid and converts it to a
	 * MyHeapEntry. Don't change this method.
	 *
	 * @param entry to be checked for validity with respect to the heap
	 * @return the entry cast as a MyHeapEntry if considered valid
	 *
	 * @throws InvalidEntryException if the entry is not of the proper class
	 */
	public MyHeapEntry<K,V> checkAndConvertEntry(Entry<K,V> entry)
			throws InvalidEntryException {
		if (entry == null || !(entry instanceof MyHeapEntry)) {
			throw new InvalidEntryException("Invalid entry");
		}
		return (MyHeapEntry<K, V>) entry;
	}

	/*
	 * You may find it useful to add some helper methods here.
	 * Think about actions that may be executed often in the
	 * rest of your code. For example, checking key
	 * validity, upheaping and downheaping, swapping or
	 * replacing elements, etc. Writing helper methods instead
	 * of copying and pasting helps segment your code, makes
	 * it easier to understand, and avoids problems in keeping
	 * each occurrence "up-to-date."
	 */

	/**
	 * This method checks the validity of the key being passed in.
	 * If the key being passed in is null, we throw an exception.
	 * If the comparator throws an exception when we compare the
	 * key to the comparator itself, then we also throw the
	 * invalid key exception.
	 *
	 * @param a key to check the validity of
	 */

	private void checkKey(K key){
		if (key == null){
			throw new InvalidKeyException("ERROR: Key is null");
		}
		try {
			_comp.compare(key, key); // If invalid key, ClassCastException thrown here
		} catch (ClassCastException e){
			throw new InvalidKeyException("ERROR: Key is invalid"); // We catch it and throw our own
		}
	}

	/**
	 * This method performs the "upHeap" that occurs in maintaining
	 * the order of the keys in the tree. Given the position of the
	 * recently inserted entry, we compare the key to the key of
	 * the parent entry, then swaps the entries if the key is less
	 * than that of the parent. We continue to recursively upheap
	 * until the position we pass in the root or a swap doesn't occur.
	 *
	 * @param the position of the recently inserted entry
	 */

	private void upHeap(Position<MyHeapEntry<K,V>> pos){

		// If we're not at the root
		if (!_tree.isRoot(pos)){

			// If the key of the current entry is less than the key
			// of the parent entry, swap entries- or else, stop

			if (_comp.compare(_tree.getElement(pos).getKey(),
					_tree.getElement(_tree.parent(pos)).getKey()) < 0){
				_tree.swapElements(pos, _tree.parent(pos));

				// After swapping, we make sure to reset the position that
				// are stored in the entries themselves

				_tree.getElement(pos).setPosition(pos);
				_tree.getElement(_tree.parent(pos)).setPosition(_tree.parent(pos));

				MyHeap.this.upHeap(_tree.parent(pos)); // Then recursively upheap again

			} else {
				return;
			}

		} else {
			return;
		}

	}

	/**
	 * This method performs the "downHeap" that occurs in maintaining
	 * the order of the keys in the tree. Given the position of the
	 * recently inserted entry, we compare the key to the key of
	 * whichever child entry has the lesser key. Or if there's only
	 * one child, we know that it must be the left child since the
	 * heap structure is a left-complete tree- so we compare the key
	 * to the key of the left child. In the case that there are both
	 * children and they're equal, we randomly pick either the left or
	 * right child to compare keys (so as to not create a vastly uneven
	 * tree if there are a lot of such cases). If the current key is
	 * less greater than the key to which we're comparing it to, we swap
	 * entries, bringing the current entry down a level. We continue
	 * to recursively downheap until we've reached a leaf or a swap
	 * doesn't occur.
	 *
	 * @param the position of the recently inserted entry
	 */

	private void downHeap(Position<MyHeapEntry<K,V>> pos){

		// If we're not at an external node
		if (_tree.isInternal(pos)){

			// We check if the node has a right child- if not, this
			// tells us the tree only has a left child, because it
			// must have at least one child (since internal)

			if (!_tree.hasRight(pos)){

				// If no right child, we compare keys with left child only
				if (_comp.compare(_tree.getElement(pos).getKey(),
						_tree.getElement(_tree.left(pos)).getKey()) > 0){

					// And if the left entry's key is indeed less; we swap
					// then recursively call downHeap on the left key

					_tree.swapElements(pos, _tree.left(pos));

					// After swapping, we make sure to reset the position that
					// are stored in the entries themselves

					_tree.getElement(pos).setPosition(pos);
					_tree.getElement(_tree.left(pos)).setPosition(_tree.left(pos));

					MyHeap.this.downHeap(_tree.left(pos));
				}
			} else {
				// Otherwise, the node has two children and we do the following

				// If the left child contains an entry with a lesser key,
				// then we compare the current entry's key with that key

				if (_comp.compare(_tree.getElement(_tree.left(pos)).getKey(),
						_tree.getElement(_tree.right(pos)).getKey()) < 0){
					if (_comp.compare(_tree.getElement(pos).getKey(),
							_tree.getElement(_tree.left(pos)).getKey()) > 0){

						// If the left entry's key is indeed less, we swap and
						// then recursively call downHeap again on the left child

						_tree.swapElements(pos, _tree.left(pos));

						// After swapping, we make sure to reset the position that
						// are stored in the entries themselves

						_tree.getElement(pos).setPosition(pos);
						_tree.getElement(_tree.left(pos)).setPosition(_tree.left(pos));

						MyHeap.this.downHeap(_tree.left(pos)); // Then recursively downheap again

					} else {
						return; // Otherwise, we stop
					}
				// Same goes for the right child
				} else if (_comp.compare(_tree.getElement(_tree.left(pos)).getKey(),
						_tree.getElement(_tree.right(pos)).getKey()) > 0){
					if (_comp.compare(_tree.getElement(pos).getKey(),
							_tree.getElement(_tree.right(pos)).getKey()) > 0){

						_tree.swapElements(pos, _tree.right(pos));

						_tree.getElement(pos).setPosition(pos);
						_tree.getElement(_tree.right(pos)).setPosition(_tree.right(pos));

						MyHeap.this.downHeap(_tree.right(pos));

					} else {
						return;
					}

				// In the special case that both children contain entries with
				// equal keys, we randomize which one we compare keys with

				} else {

					// Theoretically compares with the left entry's key 50% of the time
					if (Math.random() < 0.5){
						if (_comp.compare(_tree.getElement(pos).getKey(),
								_tree.getElement(_tree.left(pos)).getKey()) > 0){

							_tree.swapElements(pos, _tree.left(pos));

							// After swapping, we make sure to reset the position that
							// are stored in the entries themselves

							_tree.getElement(pos).setPosition(pos);
							_tree.getElement(_tree.left(pos)).setPosition(_tree.left(pos));

							MyHeap.this.downHeap(_tree.left(pos));

						} else {
							return;
						}
					} else {
						if (_comp.compare(_tree.getElement(pos).getKey(),
								_tree.getElement(_tree.right(pos)).getKey()) > 0){

							_tree.swapElements(pos, _tree.right(pos));

							// After swapping, we make sure to reset the position that
							// are stored in the entries themselves

							_tree.getElement(pos).setPosition(pos);
							_tree.getElement(_tree.right(pos)).setPosition(_tree.right(pos));

							MyHeap.this.downHeap(_tree.right(pos));

						} else {
							return;
						}
					}
				}
			}
		} else {
			return; // If we're at an external node, we stop
		}
	}

}
