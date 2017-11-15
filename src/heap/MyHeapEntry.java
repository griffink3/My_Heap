package heap;

import net.datastructures.Entry;
import net.datastructures.Position;

/**
 * Represents a key/value pair to be stored in a data
 * structure, such as a heap.
 */

public class MyHeapEntry<K,V> implements Entry<K,V> {

	private K _key;
	private V _value;
	private Position<MyHeapEntry<K,V>> _position;

	public MyHeapEntry(K key, V value) {
		_key = key;
		_value = value;
		_position = null;
	}

	/**
	 * @return the key stored in this entry
	 */
	public K getKey() {
		return _key;
	}

	/**
	 * Gets the value of this entry
	 *
	 * @return the value stored in this entry
	 */
	public V getValue() {
		return _value;
	}

	/**
	 * Resets the key for the MyHeapEntry
	 *
	 * @param the value to reset the key to
	 */
	public void setKey(K key) {
		_key = key;
	}

	/**
	 * Resets the value for the MyHeapEntry
	 *
	 * @param the value to reset the value to
	 */
	public void setValue(V value) {
		_value = value;
	}

	/**
	 * Gets the position of this entry
	 *
	 * @return the position of this entry
	 */
	public Position<MyHeapEntry<K,V>> getPosition() {
		return _position;
	}

	/**
	 * Resets the variable that stores the position of this entry
	 *
	 * @param the value to reset the position to
	 */
	public void setPosition(Position<MyHeapEntry<K,V>> position) {
		_position = position;
	}

}
