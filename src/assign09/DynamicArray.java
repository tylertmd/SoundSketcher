package assign09;

import java.util.Arrays;

/**
 * This class represents a dynamic array of AudioEvents.
 * 
 * A dynamic array behaves like a basic array, except it can grow
 * and shrink, as needed. Like a regular array, it is indexed beginning with 0.
 * When a DynamicArray object is created, it is empty.
 * 
 * @author CS 1420 course staff
 * @version 2025-10-28
 * 
 * DO NOT MODIFY THIS FILE
 */
public class DynamicArray {

	private AudioEvent[] elements; // the backing array

	/**
	 * Creates an empty dynamic array.
	 */
	public DynamicArray() {
		elements = new AudioEvent[0];
	}

	/**
	 * Appends the given integer to end of this dynamic array.
	 * 
	 * @param value - the integer to append
	 */
	public void add(AudioEvent value) {
		insert(elements.length, value);
	}

	/**
	 * Inserts a given integer into this dynamic array at a given index.
	 * Throws an exception if the given index is out of bounds.
	 * 
	 * @param index - the index at which to insert
	 * @param value - the integer to insert
	 */
	public void insert(int index, AudioEvent value) {
		// Ensure the index is valid.
		if(index < 0 || index > elements.length)
			throw new IndexOutOfBoundsException("Index " + index + 
					"is invalid for adding to this array with length " + elements.length);

		// Adding a new element requires more space, so make a new array.
		AudioEvent[] largerArray = new AudioEvent[elements.length + 1];

		// Copy elements from the backing array to the new array, up to the given index.
		for(int i = 0; i < index; i++) 
			largerArray[i] = elements[i];
		
		// Insert the new element at index.
		largerArray[index] = value;

		// Copy the remaining elements from the backing array to the new array, but 
		// shifted up one array slot.
		for(int i = index; i < elements.length; i++) 
			largerArray[i + 1] = elements[i];
	
		// Set the backing array reference to the new array.
		elements = largerArray;
	}

	/**
	 * Gets the integer stored in this dynamic array at the given index.
	 * Throws an exception if the given index is out of bounds.
	 * 
	 * @param index - the index of the element to get
	 * @return the element at the given index
	 */
	public AudioEvent get(int index) {
		if(index < 0 || index >= elements.length)
			throw new IndexOutOfBoundsException(
					"Index " + index + "is invalid for this array with length " + elements.length);

		return elements[index];
	}

	/**
	 * Returns the number of elements in this dynamic array.
	 * 
	 * @return the number of elements
	 */
	public int size() {
		return elements.length;
	}

	/**
	 * Sets (i.e., changes) the integer stored in this dynamic array at the given index
	 * to the given integer.
	 * Throws an exception if the given index is out of bounds.
	 * 
	 * @param index - the index of the element to set
	 * @param value - the new integer value for setting the element
	 */
	public void set(int index, AudioEvent value) {
		if(index < 0 || index >= elements.length)
			throw new IndexOutOfBoundsException(
					"Index " + index + "is invalid for this array with length " + elements.length);

		elements[index] = value;
	}

	/**
	 * Removes the integer at the given index from this dynamic array. 
	 * Throws an exception if the given index is out of bounds.
	 * 
	 * @param index - the index of the element to delete
	 */
	public void remove(int index) {
		if(index < 0 || index >= elements.length)
			throw new IndexOutOfBoundsException(
					"Index " + index + "is invalid for this array with length " + elements.length);

		// Deleting an element requires less space, so make a new array.
		AudioEvent[] smallerArray = new AudioEvent[elements.length - 1];

		// Copy elements from the backing array to the new array, up to the given index.
		for(int i = 0; i < index; i++) 
			smallerArray[i] = elements[i];

		// Copy the elements from the backing array to the new array, but 
		// shifted down one array slot.  This overwrites the deleted element.
		for(int i = index + 1; i < elements.length; i++) 
			smallerArray[i - 1] = elements[i];
		
		// Set the backing array reference to the new array.
		elements = smallerArray;
	}
	
	/**
	 * Removes the first element in the array that is equal to the given value.
	 * If no equal element is found, the array is not changed.
	 * 
	 * @param value - the AudioEvent to be removed
	 */
	public void remove(AudioEvent value) {
		int index = -1;
		for(int i = 0; i < elements.length; i++) {
			if(value.equals(elements[i])) {
				index = i;
				break;
			}
		}
		
		if(index >= 0)
			remove(index);
	}
	
	/**
	 * Removes all elements from the dynamic array.
	 */
	public void clear() {
		elements = new AudioEvent[0];
	}
	
	/**
	 * Sorts the elements of this dynamic array from largest to smallest.
	 */
	public void sort() {
		Arrays.sort(elements);
	}

	/**
	 * Generates a textual representation of this dynamic array.
	 * 
	 * @return the textual representation
	 */
	public String toString() {
		String result = "[";
		if(elements.length > 0) 
			result += elements[0];
		for(int i = 1; i < elements.length; i++) 
			result += ", " + elements[i];
		return result + "]";
	}
}