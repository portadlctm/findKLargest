package assign10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * This class represents a Binary Heap that stores values in a descending ordering.
 * 
 * NOTE: The item with the highest priority is the "maximum" item.
 * 
 * @author Joseph Porta
 * @version April 10, 2022
 *
 * @param <E>
 */
public class BinaryMaxHeap <E> implements PriorityQueue<E> {

	private int size;
	private Object[] arr;
	private Comparator<? super E> cmp;
	
	/**
	 * If this constructor is used to create an empty binary heap, it is assumed that the 
	 * elements are ordered using their natural ordering (i.e., E implements Comparable<? 
	 * super E>).
	 */
	public BinaryMaxHeap() {
		size = 0;
		arr = new Object[10];
		cmp = null;
	}
	
	/**
	 * If this constructor is used to create an empty binary heap, it is assumed that the 
	 * elements are ordered using the provided Comparator object.
	 * 
	 * @param cmpI
	 */
	public BinaryMaxHeap(Comparator<? super E> cmpI) {
		size = 0;
		arr = new Object[10];
		cmp = cmpI;
	}
	/**
	 * If this constructor is used, then the binary heap is constructed from the given list.
	 * Also, it is assumed that the elements are ordered using their natural ordering (i.e.,
	 * E implements Comparable<? super E>).
	 * 
	 * @param list
	 */
	public BinaryMaxHeap(List<? extends E> list) {
		Iterator<? extends E> it = list.iterator();
		size = 0;
		cmp = null;
		arr = new Object[10];
		
		// Add each of the items from the list into the array
		while(it.hasNext()) {
			add(it.next());
		}
	}
	
	/**
	 * If this constructor is used, then the binary heap is constructed from the given list.
	 * Also, it is assumed that the elements are ordered using the provided Comparator object.
	 * 
	 * @param list
	 * @param cmpI
	 */
	@SuppressWarnings("unchecked")
	public BinaryMaxHeap(List<? extends E> list, Comparator<? super E> cmpI) {
		Iterator<? extends E> it = list.iterator();
		size = 0;
		cmp = cmpI;
		arr = new Object[10];

		// Add each of the items from the list into the array
		while(it.hasNext()) {
			add(it.next());
		}
	}
	
	/**
	 * Adds the given item to this priority queue.
	 * O(1) in the average case, O(log N) in the worst case
	 * 
	 * @param item
	 */
	@Override
	public void add(Object item) {
		arr[size] = item;
		percolateUp(size);
		size++;
		
		// Ensure the array is sufficient size
		if(size > arr.length / 2) {
			Object[] newArr = new Object[arr.length * 2];
			for(int i = 0; i < size; i++) 
				newArr[i] = arr[i];
			arr = newArr;
		}
		
		
	}

	/**
	 * Returns, but does not remove, the maximum item this priority queue.
	 * O(1)
	 * 
	 * @return the maximum item
	 * @throws NoSuchElementException if this priority queue is empty
	 */
	@Override
	public E peek() throws NoSuchElementException {
		if (arr[0] == null)
			throw new NoSuchElementException();
		return (E) arr[0];
	}

	/**
	 * Returns and removes the maximum item this priority queue.
	 * O(log N)
	 * 
	 * @return the maximum item
	 * @throws NoSuchElementException if this priority queue is empty
	 */
	@Override
	public E extractMax() throws NoSuchElementException {
		if (arr[0] == null)
			throw new NoSuchElementException();
		// Store max value, move last item to top and percolate it down.
		Object val = arr[0];
		arr[0] = arr[size-1];
		arr[size-1] = null;
		size--;
		percolateDown(0);
		return (E) val;
	}

	/**
	 * Returns the number of items in this priority queue.
	 * O(1)
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Returns true if this priority queue is empty, false otherwise.
	 * O(1)
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Empties this priority queue of items.
	 * O(1)
	 */
	@Override
	public void clear() {
		size = 0;
		arr = new Object[10];
		
	}

	/** 
	 * Creates and returns an array of the items in this priority queue,
	 * in the same order they appear in the backing array.
	 * O(N)
	 * 
	 * (NOTE: This method is needed for grading purposes. The root item 
	 * must be stored at index 0 in the returned array, regardless of 
	 * whether it is in stored there in the backing array.) 
	 */
	@Override
	public E[] toArray() {
		Object[] outputArray = new Object[size];
		for(int i = 0; i < size; i++) {
			outputArray[i] = arr[i];
		}
		return (E[]) outputArray;
	}
	
	/**
	 * Recursive helper method to percolate up the heap
	 * 
	 * @param id to percolate up
	 */
	private void percolateUp(int id) {
		if(parentIndex(id) < 0 || compare(id, parentIndex(id)) <= 0)
			return;
		Object temp = arr[parentIndex(id)];
		arr[parentIndex(id)] = arr[id];
		arr[id] = temp;
		percolateUp(parentIndex(id));
	}
	
	/**
	 * Recursive helper method to percolate down the heap
	 * 
	 * @param id to percolate down
	 */
	private void percolateDown(int id) {
		int id2;
		// Determine if any valid children exist, assign child to look at accordingly
		if(arr[leftChildIndex(id)] == null && arr[rightChildIndex(id)] == null) {
			return;
		}else if(arr[leftChildIndex(id)] == null) {
			id2 = rightChildIndex(id);
		}else if(arr[rightChildIndex(id)] == null) {
			id2 = leftChildIndex(id);
		}else{
			if(compare(leftChildIndex(id), rightChildIndex(id)) >= 0) {
			    id2 = leftChildIndex(id);
		    }else {
			    id2 = rightChildIndex(id);
		    }
		}

		// If current item is greater than or equal to greatest child, no more need to percolate
		if(compare(id, id2) >= 0)
			return;

		// If greatest child is greater, percolate
		Object temp = arr[id2];
		arr[id2] = arr[id];
		arr[id] = temp;
		percolateDown(id2);
	}
	
	/**
	 * Compares items at two indexes either in natural order to according to a comparator.
	 * 
	 * @param id1
	 * @param id2
	 * @return
	 */
	private int compare(int id1, int id2) {
		if(cmp == null) {
			return (((Comparable)arr[id1]).compareTo((Comparable)arr[id2]));
		}
		return cmp.compare(((E)arr[id1]), ((E)arr[id2]));
	}

	/**
	 * Takes an index and returns the index of its left child
	 * 
	 * @param i
	 * @return index of left child
	 */
	private int leftChildIndex(int i) {
		return (i * 2) + 1;
	}
	
	/**
	 * Takes an index and returns the index of its right child
	 * 
	 * @param i
	 * @return index of right child
	 */
	private int rightChildIndex(int i) {
		return (i * 2) + 2;
	}
	
	/**
	 * Takes an index and returns the index of its parent
	 * 
	 * @param i
	 * @return index of parent
	 */
	private int parentIndex(int i) {
		return (i - 1) / 2;
	}
}
